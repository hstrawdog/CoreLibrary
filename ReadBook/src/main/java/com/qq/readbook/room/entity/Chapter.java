package com.qq.readbook.room.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.bean
 * @Date : 下午 1:42
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Entity
public class Chapter implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    /**
     * 章节所属书的ID
     */
    private String bookId;
    /**
     * 章节序号
     */
    private int number;
    /**
     * 章节标题
     */
    private String title;
    /**
     * 章节链接
     */
    private String url;

    /**
     * 是否缓存成功
     * 用于 章节列表标识
     */
    private boolean isCache;

    /**
     * 当前源
     */
    private String sources;


    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public static Creator<Chapter> getCREATOR() {
        return CREATOR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }


    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Chapter() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.bookId);
        dest.writeInt(this.number);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeByte(this.isCache ? (byte) 1 : (byte) 0);
    }

    protected Chapter(Parcel in) {
        this.id = in.readInt();
        this.bookId = in.readString();
        this.number = in.readInt();
        this.title = in.readString();
        this.url = in.readString();
        this.isCache = in.readByte() != 0;
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel source) {
            return new Chapter(source);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };
}
