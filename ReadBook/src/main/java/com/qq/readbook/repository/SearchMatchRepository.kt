package com.qq.readbook.repository

import com.hqq.core.net.ok.OkHttp
import com.hqq.core.net.ok.OkNetCallback
import com.hqq.core.utils.gson.GsonUtil
import com.qq.readbook.bean.search.YueDu
import com.qq.readbook.bean.search.ZhuiShuShenQi
import com.qq.readbook.bean.search.ZongHeng

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.repository
 * @Date : 上午 11:02
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object SearchMatchRepository {

    fun doSearch(key: String, call: SearchMatchCallback) {
        doZongHeng(key, call)
        doYueDu(key, call)
        doZhuiShuShenQi(key, call)
    }

    /**
     *  纵横
     * @param key String
     * @param call SearchMatchCallback
     */
    fun doZongHeng(key: String, call: SearchMatchCallback) {
        OkHttp.newHttpCompat().get("http://search.zongheng.com/search/suggest?keyword=" + key,
            OkHttp.newParamsCompat(),
            object : OkNetCallback {
                override fun onSuccess(statusCode: String?, response: String?) {
                    val b = GsonUtil.fromJson(response, ZongHeng::class.java)
                    if (b != null && !b.books.isNullOrEmpty()) {
                        call.onSuccess(b.books)
                    }
                }

                override fun onFailure(statusCode: String?, errMsg: String?, response: String?) {
                }
            })
    }

    /**
     * 阅读163
     * @param key String
     * @param call SearchMatchCallback
     */
    fun doYueDu(key: String, call: SearchMatchCallback) {
        OkHttp.newHttpCompat().get("http://yuedu.163.com/querySearchHints.do?keyword=" + key,
            OkHttp.newParamsCompat(),
            object : OkNetCallback {
                override fun onSuccess(statusCode: String?, response: String?) {
                    val b = GsonUtil.fromJson(response, YueDu::class.java)
                    b?.let {
                        var list = ArrayList<String>()
                        for (bookDTO in b.book) {
                            list.add(bookDTO.name)
                        }
                        call.onSuccess(list)
                    }
                }

                override fun onFailure(statusCode: String?, errMsg: String?, response: String?) {
                }
            })
    }

    /**
     * 追书神器 貌似挂了 0226
     * @param key String
     * @param call SearchMatchCallback
     */
    fun doZhuiShuShenQi(key: String, call: SearchMatchCallback) {
        OkHttp.newHttpCompat().get("http://api.zhuishushenqi.com/book/auto-complete?query=" + key,
            OkHttp.newParamsCompat(),
            object : OkNetCallback {
                override fun onSuccess(statusCode: String?, response: String?) {
                    val b = GsonUtil.fromJson(response, ZhuiShuShenQi::class.java)
                    b?.let {
                        var list = ArrayList<String>()
                        it.keywords?.let {
                            for (keyword in it) {
                                list.add(keyword)
                            }
                            call.onSuccess(list)
                        }
                    }
                }

                override fun onFailure(statusCode: String?, errMsg: String?, response: String?) {
                }
            })
    }


    interface SearchMatchCallback {
        fun onSuccess(list: ArrayList<String>)
    }
}