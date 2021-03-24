package com.qq.readbook.down

import android.util.ArrayMap
import com.qq.readbook.room.entity.Chapter

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.down.DownChapter.java
 * @emain: 593979591@qq.com
 * @date: 2021-03-12 22:30
</描述当前版本功能> */
data class DownChapter(var model: Int) {

    /**
     *  需要换成的章节
     */
    var list: List<Chapter> = ArrayList()

    /**
     *  已经加载的章节
     */
    val successMap = ArrayMap<Int, Chapter>()

}