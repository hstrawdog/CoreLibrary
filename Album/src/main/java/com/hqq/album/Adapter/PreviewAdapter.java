package com.hqq.album.Adapter;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hqq.album.R;
import com.hqq.album.annotation.LocalMediaType;
import com.hqq.album.entity.LocalMedia;
import com.hqq.album.utils.AlbumFileUtils;

import java.io.File;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.Adapter
 * @FileName :   PreviewAdapter
 * @Date : 2020/1/16 0016  上午 11:42
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {

    Context mContext;
    List<LocalMedia> mLocalMediaList;


    public PreviewAdapter(Context context, List<LocalMedia> localMediaList) {
        mContext = context;
        mLocalMediaList = localMediaList;
    }

    @NonNull
    @Override
    public PreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_album_preview, viewGroup, false);
        return new PreviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewAdapter.ViewHolder viewHolder, int i) {
        initData(mContext, viewHolder, mLocalMediaList.get(i));
    }

    @Override
    public int getItemCount() {
        return mLocalMediaList.size();
    }


    private void initData(final Context context, final PreviewAdapter.ViewHolder viewHolder, LocalMedia localMedia) {
        switch (localMedia.getLocalMediaType()) {
            case LocalMediaType.VALUE_TYPE_IMAGE:
                String url = getPathFromUri(mContext, localMedia.getUri());
                int degree = AlbumFileUtils.readPictureDegree(url);
                //旋转图片
                if (degree > 0) {
                    Bitmap bitmap = AlbumFileUtils.rotateBitmap(degree, BitmapFactory.decodeFile(url));
                    viewHolder.photoDraweeView.setImage(ImageSource.bitmap(bitmap));
                } else {
                    viewHolder.photoDraweeView.setImage(ImageSource.uri(localMedia.getUri()));
                }
                viewHolder.photoDraweeView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
//                        Toast.makeText(context,"111",1).show();

                        return true;
                    }
                });

                break;
            case LocalMediaType.VALUE_TYPE_VIDEO:
                viewHolder.videoView.setMediaController(new MediaController(context));
                viewHolder.videoView.setVideoURI(localMedia.getUri());
                viewHolder.videoView.start();
                viewHolder.videoView.requestFocus();
                viewHolder.videoView.setVisibility(View.VISIBLE);
                viewHolder.progressBar.setVisibility(View.GONE);

                break;
            default:

        }
    }

    public static String getPathFromUri(Context context, Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri，通过document id处理，内部会调用Uri.decode(docId)进行解码
            String docId = DocumentsContract.getDocumentId(uri);
            //primary:Azbtrace.txt
            //video:A1283522
            String[] splits = docId.split(":");
            String type = null, id = null;
            if (splits.length == 2) {
                type = splits[0];
                id = splits[1];
            }
            switch (uri.getAuthority()) {
                case "com.android.externalstorage.documents":
                    if ("primary".equals(type)) {
                        path = Environment.getExternalStorageDirectory() + File.separator + id;
                    }
                    break;
                case "com.android.providers.downloads.documents":
                    if ("raw".equals(type)) {
                        path = id;
                    } else {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                        path = getMediaPathFromUri(context, contentUri, null, null);
                    }
                    break;
                case "com.android.providers.media.documents":
                    Uri externalUri = null;
                    switch (type) {
                        case "image":
                            externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "video":
                            externalUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "audio":
                            externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "document":
                            externalUri = MediaStore.Files.getContentUri("external");
                            break;
                    }
                    if (externalUri != null) {
                        String selection = "_id=?";
                        String[] selectionArgs = new String[]{id};
                        path = getMediaPathFromUri(context, externalUri, selection, selectionArgs);
                    }
                    break;
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            path = getMediaPathFromUri(context, uri, null, null);
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri(uri.fromFile)，直接获取图片路径即可
            path = uri.getPath();
        }
        //确保如果返回路径，则路径合法
        return path == null ? null : (new File(path).exists() ? path : null);
    }

    private static String getMediaPathFromUri(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path;
        String authroity = uri.getAuthority();
        path = uri.getPath();
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (!path.startsWith(sdPath)) {
            int sepIndex = path.indexOf(File.separator, 1);
            if (sepIndex == -1) path = null;
            else {
                path = path.substring(sepIndex);
            }
        }

        if (path == null || !new File(path).exists()) {
            ContentResolver resolver = context.getContentResolver();
            String[] projection = new String[]{MediaStore.MediaColumns.DATA};
            Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    try {
                        int index = cursor.getColumnIndexOrThrow(projection[0]);
                        if (index != -1) path = cursor.getString(index);
                        Log.i(TAG, "getMediaPathFromUri query " + path);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        path = null;
                    } finally {
                        cursor.close();
                    }
                }
            }
        }
        return path;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        ProgressBar progressBar;
        SubsamplingScaleImageView photoDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.vv_view);
            progressBar = itemView.findViewById(R.id.pb_bar);
            photoDraweeView = itemView.findViewById(R.id.image_item);

        }
    }


}