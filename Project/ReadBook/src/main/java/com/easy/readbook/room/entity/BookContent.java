package com.easy.readbook.room.entity;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.bean
 * @Date : 下午 5:26
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Entity
public class BookContent {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    /**
     * 章节序号
     */
    private int number;
    /**
     * 章节正文
     */
    @Nullable
    private String content;

    /**
     * 源名称
     */
    private String sourceName = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }
}
