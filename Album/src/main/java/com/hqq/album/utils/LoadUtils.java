package com.hqq.album.utils;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.hqq.album.R;
import com.hqq.album.annotation.LocalMediaType;

import java.io.File;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.utils
 * @FileName :   LoadUtils
 * @Date : 2020/1/16 0016  上午 10:56
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class LoadUtils {
    /**
     * 加载 图片
     *
     * @param path
     * @param picture
     */
    public static void loadImage(Uri path, ImageView picture) {
        RequestOptions options = new RequestOptions().placeholder(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).centerCrop();
        Glide.with(picture.getContext())
                .load(path)
                .apply(options)
                // .override(150, 150)
                .into(picture);
    }

    /**
     * 加载视频
     *
     * @param path
     * @param picture
     */
    public static void loadVideo(Uri path, ImageView picture) {
        RequestOptions options = new RequestOptions().placeholder(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop();
        options.signature(new ObjectKey(System.currentTimeMillis()));
        Glide.with(picture.getContext())
                .load(path)
                .thumbnail(0.5f)
                .into(picture);
    }

    /**
     * 加载  本地 文件
     *
     * @param localMediaType 文件类型
     * @param path           地址
     * @param picture        控件
     */
    public static void loadLocalMediaPath(int localMediaType, Uri path, ImageView picture) {
        if (localMediaType == LocalMediaType.VALUE_TYPE_IMAGE) {
            LoadUtils.loadImage(path, picture);
        } else if (localMediaType == LocalMediaType.VALUE_TYPE_VIDEO) {
            LoadUtils.loadVideo(path, picture);
        }
    }
}
