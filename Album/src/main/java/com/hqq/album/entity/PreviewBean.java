package com.hqq.album.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.entity
 * @Date : 上午 9:59
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class PreviewBean implements Parcelable {
    String path;
    Uri uri;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeParcelable(this.uri, flags);
    }

    public PreviewBean() {
    }

    protected PreviewBean(Parcel in) {
        this.path = in.readString();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Parcelable.Creator<PreviewBean> CREATOR = new Parcelable.Creator<PreviewBean>() {
        @Override
        public PreviewBean createFromParcel(Parcel source) {
            return new PreviewBean(source);
        }

        @Override
        public PreviewBean[] newArray(int size) {
            return new PreviewBean[size];
        }
    };
}
