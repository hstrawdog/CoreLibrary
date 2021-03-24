package com.qq.readbook.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @version V1.0 <描述当前版本功能>
 * 在此写用途
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.room.bean.SearchLog.java
 * @emain: 593979591@qq.com
 * @date: 2021-02-27 15:08
 */
@Entity
public class LocalSearchKey {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    public String key;

    public String searchTime;

    public String getKey() {
        return key == null ? "" : key;
    }

    public LocalSearchKey setKey(String key) {
        this.key = key;
        return this;
    }

    public String getSearchTime() {
        return searchTime == null ? "" : searchTime;
    }

    public LocalSearchKey setSearchTime(String searchTime) {
        this.searchTime = searchTime;
        return this;
    }
}
