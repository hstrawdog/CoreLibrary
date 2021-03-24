package com.qq.readbook.bean

import androidx.annotation.Keep
import com.google.gson.JsonElement

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.bean
 * @Date : 下午 1:31
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@Keep
class RuleChapterBean {
    /**
     * chapterList : {"elementType":"class","elementValue":"read","ruleChild":{"elementType":"tag","elementValue":"dl","position":1}}
     * title : {"elementType":"tag","elementValue":"dd","attrValue":"text"}
     * url : {"elementType":"tag","elementValue":"a","attrValue":"href","formatRule":{"elementValue":"https://www.dstiejuan.com","elementType":"addStart"}}
     */
    var chapterList: String? = null
    var title: String? = null
    var url: String? = null
}