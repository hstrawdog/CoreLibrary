package com.easy.readbook.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.bean
 * @Date : 下午 2:53
 * @Email : qiqiang213@gmail.com
 * @Describe :  书籍资源
 */
@Entity
public class SearchLog {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    /**
     * 书籍id
     */
    String bookId;
    /**
     * 书籍名称
     */
    String bookName;

    /**
     * 源名称
     */
    String sourcesName;
    /**
     * 书籍详情URL
     */
    String bookDetailUrl;
    /**
     * 章节Url
     */
    String bookChapterUrl;


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public String getSourcesName() {
        return sourcesName;
    }

    public void setSourcesName(String sourcesName) {
        this.sourcesName = sourcesName;
    }

    public String getBookDetailUrl() {
        return bookDetailUrl;
    }

    public String getBookChapterUrl() {
        return bookChapterUrl;
    }

    public void setBookChapterUrl(String bookChapterUrl) {
        this.bookChapterUrl = bookChapterUrl;
    }

    public void setBookDetailUrl(String bookDetailUrl) {
        this.bookDetailUrl = bookDetailUrl;
    }
}
