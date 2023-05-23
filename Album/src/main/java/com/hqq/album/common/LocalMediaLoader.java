/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hqq.album.common;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.text.TextUtils;

import com.hqq.album.R;
import com.hqq.album.annotation.LocalMediaType;
import com.hqq.album.entity.LocalMedia;
import com.hqq.album.entity.LocalMediaFolder;
import com.hqq.album.utils.AlbumFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.common.LocalMediaLoader.java
 * @author: 黄其强
 * @date: 2017-05-04 20:14
 */
public class LocalMediaLoader {
    public boolean isGif;
    public int index = 0;
    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.SIZE
    };

    private final static String[] VIDEO_PROJECTION = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION,
    };

    @LocalMediaType
    private int mLocalMediaType = LocalMediaType.VALUE_TYPE_IMAGE;
    private FragmentActivity activity;

    public LocalMediaLoader(FragmentActivity activity, @LocalMediaType int localMediaType, boolean isGif) {
        this.activity = activity;
        this.mLocalMediaType = localMediaType;
        this.isGif = isGif;
    }

    public void loadAllImage(final LocalMediaLoadListener imageLoadListener) {
        activity.getSupportLoaderManager().initLoader(mLocalMediaType, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader cursorLoader = null;
                String select[] = null;
                String condition = "";
                if (isGif) {
                    select = new String[]{"image/jpeg", "image/png", "image/gif"};
                    condition = MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?" + " or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?";
                } else {
                    select = new String[]{"image/jpeg", "image/png"};
                    condition = MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?";
                }
                if (id == LocalMediaType.VALUE_TYPE_IMAGE) {
                    cursorLoader = new CursorLoader(
                            activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            IMAGE_PROJECTION, condition,
                            select, IMAGE_PROJECTION[2] + " DESC");
                } else if (id == LocalMediaType.VALUE_TYPE_VIDEO) {
                    cursorLoader = new CursorLoader(
                            activity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            VIDEO_PROJECTION, null, null, VIDEO_PROJECTION[2] + " DESC");
                }
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                try {
                    ArrayList<LocalMediaFolder> imageFolders = new ArrayList<LocalMediaFolder>();
                    LocalMediaFolder allImageFolder = new LocalMediaFolder();
                    List<LocalMedia> allImages = new ArrayList<LocalMedia>();
                    if (data != null) {
                        int count = data.getCount();
                        if (count > 0) {
                            data.moveToFirst();
                            do {
                                String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                                // 如原图路径不存在或者路径存在但文件不存在,就结束当前循环
                                if (TextUtils.isEmpty(path) || !new File(path).exists()) {
                                    continue;
                                }
                                long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                                long id = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
//                                Uri uri = AlbumFileUtils.getFile2Uri(activity, path);
                                Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
                                int duration = 0;
                                if (mLocalMediaType == LocalMediaType.VALUE_TYPE_VIDEO) {
                                    duration = data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[4]));
                                    uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + id);
                                } else {
                                    int size = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                                    //是否大于10K
                                    if (size < (1024 * 10)) {
                                        continue;
                                    }
                                }
                                LocalMedia image = new LocalMedia(path, dateTime, duration, mLocalMediaType);
                                image.setUri(uri);
                                LocalMediaFolder folder = getImageFolder(path, uri, imageFolders);
                                folder.getImages().add(image);
                                folder.setType(mLocalMediaType);
                                index++;
                                folder.setImageNum(folder.getImageNum() + 1);
                                // 最近相册中  只添加最新的100条
                                if (index <= 100) {
                                    allImages.add(image);
                                    allImageFolder.setType(mLocalMediaType);
                                    allImageFolder.setImageNum(allImageFolder.getImageNum() + 1);
                                }
                            } while (data.moveToNext());
                            if (allImages.size() > 0) {
                                sortFolder(imageFolders);
                                imageFolders.add(0, allImageFolder);
                                String title = "";
                                switch (mLocalMediaType) {
                                    case LocalMediaType.VALUE_TYPE_VIDEO:
                                        title = activity.getString(R.string.lately_video);
                                        break;
                                    case LocalMediaType.VALUE_TYPE_IMAGE:
                                        title = activity.getString(R.string.lately_image);
                                        break;
                                    default:
                                }
                                allImageFolder.setFirstImagePath(allImages.get(0).getPath());
                                allImageFolder.setFirstImageUri(allImages.get(0).getUri());
                                allImageFolder.setName(title);
                                allImageFolder.setType(mLocalMediaType);
                                allImageFolder.setImages(allImages);
                            }
                            imageLoadListener.loadComplete(imageFolders);
                        } else {
                            // 如果没有相册
                            imageLoadListener.loadComplete(imageFolders);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
            }
        });
    }

    private void sortFolder(List<LocalMediaFolder> imageFolders) {
        // 文件夹按图片数量排序
        Collections.sort(imageFolders, new Comparator<LocalMediaFolder>() {
            @Override
            public int compare(LocalMediaFolder lhs, LocalMediaFolder rhs) {
                if (lhs.getImages() == null || rhs.getImages() == null) {
                    return 0;
                }
                int lsize = lhs.getImageNum();
                int rsize = rhs.getImageNum();
                return lsize == rsize ? 0 : (lsize < rsize ? 1 : -1);
            }
        });
    }

    private LocalMediaFolder getImageFolder(String path, Uri uri, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();
        String name = folderFile.getName();
        if (FunctionOptions.getInstance().getChooseFolder() != null && !FunctionOptions.getInstance().getChooseFolder().isEmpty()) {
            if (path.contains(FunctionOptions.getInstance().getChooseFolder())) {
                name = FunctionOptions.getInstance().getChooseFolder();
            }
        }

        for (LocalMediaFolder folder : imageFolders) {
            if (folder.getName().equals(name)) {
                return folder;
            }
        }
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(name);
        newFolder.setPath(folderFile.getAbsolutePath());
        newFolder.setFirstImagePath(path);
        newFolder.setFirstImageUri(uri);
        imageFolders.add(newFolder);
        return newFolder;
    }

    public interface LocalMediaLoadListener {
        void loadComplete(List<LocalMediaFolder> folders);
    }


}
