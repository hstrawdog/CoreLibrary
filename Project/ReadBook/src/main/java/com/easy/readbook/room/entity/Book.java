package com.easy.readbook.room.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook
 * @Date : 下午 3:52
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Entity
public class Book implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    /**
     * 书籍id
     */
    private String bookId;
    /**
     * 书名
     */
    private String name;
    /**
     * 作者
     */
    private String author;
    /**
     * 资源
     */
    @Nullable
    private String sourceName;
    /**
     * 书籍详情地址   正常情况与章节列表页面相同
     */
    private String bookDetailUrl;
    /**
     * 书目Url
     */
    private String chapterUrl;
    /**
     * 封面图片url
     */
    private String imgUrl;
    /**
     * 简介
     */
    private String desc;

    /**
     * 类型
     */
    private String type;
    /**
     * 字数
     */
    private String wordCount;
    /**
     * 更新时间
     * 目前这把只用于显示 格式并不统一
     */
    private String updateDate;

    /**
     * 最新章节标题
     */
    @Nullable
    private String newestChapterTitle;

    /**
     * 最新阅读日期
     */
    String lastRead = "";
    /**
     * 置顶时间
     */
    String topTime;

    /**
     * 是否更新或未阅读
     */
    boolean isUpdate = true;
    /**
     * 本地刷新时间
     */
    private String refreshTime = "";
    /**
     * 章节数量
     */
    int chapterListSize = 0;
    /**
     * 加入书架
     * 0 未加入书架
     * 1 加入书架
     */
    int localType = 0;

    /**
     * 章节列表
     */
    @Ignore
    List<Chapter> bookChapterList = new ArrayList();

    @Ignore
    boolean isNeedRefresh = true;


    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        isNeedRefresh = needRefresh;
    }

    public String getTopTime() {
        return topTime;
    }

    public void setTopTime(String topTime) {
        this.topTime = topTime;
    }

    public String getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(String refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getBookDetailUrl() {
        return bookDetailUrl;
    }

    public void setBookDetailUrl(String bookDetailUrl) {
        this.bookDetailUrl = bookDetailUrl;
    }

    public List<Chapter> getBookChapterList() {
        if (bookChapterList == null) {
            return new ArrayList<>();
        }
        return bookChapterList;
    }

    public int getChapterListSize() {
        return chapterListSize;
    }

    public void setChapterListSize(int chapterListSize) {
        this.chapterListSize = chapterListSize;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public void setBookChapterList(List<Chapter> bookChapterList) {
        this.bookChapterList = bookChapterList;
    }

    public String getLastRead() {
        return lastRead == null ? "" : lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public String getBookId() {
        return bookId == null ? "" : bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(@Nullable String sourceName) {
        this.sourceName = sourceName;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Nullable
    public String getNewestChapterTitle() {
        return newestChapterTitle;
    }

    public void setNewestChapterTitle(@Nullable String newestChapterTitle) {
        this.newestChapterTitle = newestChapterTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLocalType() {
        return localType;
    }

    public void setLocalType(int localType) {
        this.localType = localType;
    }

    /**
     * 数据库名称
     *
     * @return
     */
    public String getBookIdName() {
        return name + "_" + author;
    }

    public Book() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.bookId);
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeString(this.sourceName);
        dest.writeString(this.bookDetailUrl);
        dest.writeString(this.chapterUrl);
        dest.writeString(this.imgUrl);
        dest.writeString(this.desc);
        dest.writeString(this.type);
        dest.writeString(this.wordCount);
        dest.writeString(this.updateDate);
        dest.writeString(this.newestChapterTitle);
        dest.writeString(this.lastRead);
        dest.writeString(this.topTime);
        dest.writeByte(this.isUpdate ? (byte) 1 : (byte) 0);
        dest.writeString(this.refreshTime);
        dest.writeInt(this.chapterListSize);
        dest.writeInt(this.localType);
        dest.writeTypedList(this.bookChapterList);
    }

    protected Book(Parcel in) {
        this.id = in.readInt();
        this.bookId = in.readString();
        this.name = in.readString();
        this.author = in.readString();
        this.sourceName = in.readString();
        this.bookDetailUrl = in.readString();
        this.chapterUrl = in.readString();
        this.imgUrl = in.readString();
        this.desc = in.readString();
        this.type = in.readString();
        this.wordCount = in.readString();
        this.updateDate = in.readString();
        this.newestChapterTitle = in.readString();
        this.lastRead = in.readString();
        this.topTime = in.readString();
        this.isUpdate = in.readByte() != 0;
        this.refreshTime = in.readString();
        this.chapterListSize = in.readInt();
        this.localType = in.readInt();
        this.bookChapterList = in.createTypedArrayList(Chapter.CREATOR);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
