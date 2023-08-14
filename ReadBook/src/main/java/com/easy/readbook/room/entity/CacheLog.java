package com.easy.readbook.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.room.bean
 * @Date : 下午 5:26
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Entity
public class CacheLog {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    /**
     * 书籍id
     */
    String bookId;
    /**
     * 当前源
     */
    String sourceName;
    /**
     * 详情缓存时间
     */
    String detailCacheTime;

    /**
     * 最新章节缓存时间
     */
    String newsChapterCacheTime;
}
