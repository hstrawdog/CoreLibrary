package com.hqq.album.common;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.hqq.album.activity.AlbumDirectoryActivity;
import com.hqq.album.activity.AlbumFolderActivity;
import com.hqq.album.annotation.LocalMediaType;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: cn.huangqiqiang.halbum.common.FunctionOptions.java
 * @emain: 593979591@qq.com
 * @date: 2017-05-07 22:19
 */
public class FunctionOptions {
    /**
     * 缓存的单利对象
     */
    Album mAlbum;
    /**
     * 多选最大可选数量
     */
    private int maxSelectNum = 9;
    /**
     * 是否显示拍照图片
     */
    private boolean displayCamera = false;
    /**
     * 启动相机 拍照
     */
    private boolean startUpCamera = false;
    /**
     * 默认显示的图片
     */
    private int albumType = LocalMediaType.VALUE_TYPE_IMAGE;

    /**
     * 默认显示的文件夹
     */
    private String chooseFolder = "最近照片";


    /**
     * 是否支持gif
     */

    private boolean isSupportGif = false;

    /**
     * 拍照后是否发送至相册
     */
    private boolean isSendAlbum = true;


    public FunctionOptions() {
    }

    public static FunctionOptions getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private void rest() {
        this.startUpCamera = false;
        startUpCamera = false;
        maxSelectNum = 9;
        albumType = LocalMediaType.VALUE_TYPE_IMAGE;
    }

    public boolean isStartUpCamera() {
        return startUpCamera;
    }

    public FunctionOptions setStartUpCamera(boolean startUpCamera) {
        this.startUpCamera = startUpCamera;
        return this;
    }

    public FunctionOptions setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
        return this;
    }

    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    public boolean isDisplayCamera() {
        return displayCamera;
    }

    public FunctionOptions setDisplayCamera(boolean displayCamera) {
        this.displayCamera = displayCamera;
        return this;
    }

    public int getAlbumType() {
        return albumType;
    }

    public FunctionOptions setAlbum(Album album) {
        mAlbum = album;
        return this;
    }

    public FunctionOptions setAlbumType(int albumType) {
        rest();
        this.albumType = albumType;
        return this;
    }

    public FunctionOptions setSupportGif(boolean supportGif) {
        isSupportGif = supportGif;
        return this;
    }

    public FunctionOptions setIsSendAlbum(boolean isSendAlbum) {
        this.isSendAlbum = isSendAlbum;
        return this;
    }


    public boolean isSupportGif() {
        return isSupportGif;
    }

    public boolean isSendAlbum() {
        return isSendAlbum;
    }

    public String getChooseFolder() {
        return chooseFolder;
    }

    public FunctionOptions setChooseFolder(String chooseFolder) {
        this.chooseFolder = chooseFolder;
        return this;
    }

    /**
     * 数据将回调给调用的 activity 或者Fragment
     *
     * @param requestCode
     */
    public void forResult(int requestCode) {
        Activity activity = mAlbum.getActivity();
        SelectOptions.getInstance().reset();

        Intent intent = new Intent(activity, AlbumDirectoryActivity.class);
        Fragment fragment = mAlbum.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 打开指定文件夹
     *
     * @param requestCode
     */
    public void forFolderResult(int requestCode) {
        Activity activity = mAlbum.getActivity();
        SelectOptions.getInstance().reset();
        Intent intent = new Intent(activity, AlbumFolderActivity.class);
        Fragment fragment = mAlbum.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    private static final class InstanceHolder {
        private static final FunctionOptions INSTANCE = new FunctionOptions();
    }


}
