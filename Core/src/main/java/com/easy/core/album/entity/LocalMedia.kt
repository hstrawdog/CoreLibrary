/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.easy.core.album.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.easy.core.album.annotation.LocalMediaType;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.entity.LocalMedia.java
 * @author: 黄其强
 * @date: 2017-05-04 20:17
 */
public class LocalMedia implements Parcelable {
    /**
     * 物理地址
     */
    private String path;
    /**
     * 相册对应的 uri
     */
    Uri uri;
    /**
     * 压缩后的地址
     */
    private String compressPath;
    /**
     * 裁剪地址
     */
    private String cutPath;

    /**
     * 应该是 视频的时长
     */
    private long duration;

    /**
     * 添加时间
     */
    private long lastUpdateAt;

    /**
     * 是否选中
     */
    private boolean isChecked;
    /**
     * 是否裁剪
     */
    private boolean isCut;
    /**
     * 预览下标
     */
    public int position;
    /**
     * 类型 图片 视频
     */
    @LocalMediaType
    private int localMediaType;


    public LocalMedia() {
    }

    public LocalMedia(String path, long lastUpdateAt, long duration, @LocalMediaType int type) {
        this.path = path;
        this.duration = duration;
        this.lastUpdateAt = lastUpdateAt;
        this.localMediaType = type;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getCutPath() {
        return cutPath;
    }

    public void setCutPath(String cutPath) {
        this.cutPath = cutPath;
    }

    public boolean isCut() {
        return isCut;
    }

    public void setCut(boolean cut) {
        isCut = cut;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public int getLocalMediaType() {
        return localMediaType;
    }

    public LocalMedia setLocalMediaType(@LocalMediaType int localMediaType) {
        this.localMediaType = localMediaType;
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(long lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.compressPath);
        dest.writeString(this.cutPath);
        dest.writeLong(this.duration);
        dest.writeLong(this.lastUpdateAt);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCut ? (byte) 1 : (byte) 0);
        dest.writeInt(this.position);
        dest.writeInt(this.localMediaType);
        dest.writeParcelable(this.uri, flags);
    }

    protected LocalMedia(Parcel in) {
        this.path = in.readString();
        this.compressPath = in.readString();
        this.cutPath = in.readString();
        this.duration = in.readLong();
        this.lastUpdateAt = in.readLong();
        this.isChecked = in.readByte() != 0;
        this.isCut = in.readByte() != 0;
        this.position = in.readInt();
        this.localMediaType = in.readInt();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<LocalMedia> CREATOR = new Creator<LocalMedia>() {
        @Override
        public LocalMedia createFromParcel(Parcel source) {
            return new LocalMedia(source);
        }

        @Override
        public LocalMedia[] newArray(int size) {
            return new LocalMedia[size];
        }
    };
}
