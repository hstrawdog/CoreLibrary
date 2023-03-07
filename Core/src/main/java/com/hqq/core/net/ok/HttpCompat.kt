package com.hqq.core.net.ok

import com.hqq.core.net.DownloadListener
import okhttp3.Call
import okhttp3.RequestBody
import java.io.File

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.net.ok
 * @FileName :   HttpCompat
 * @Date : 2016/12/14 15:40.
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  坚持封装的 OkHttp 请求
 */
interface HttpCompat {

    /**
     * 请求对象
     *
     * @return
     */
    fun create(): HttpCompat?

    /**
     * get方法
     *
     * @param url
     * @param params
     * @param callback
     */
    operator fun get(url: String, params: ParamsCompat, callback: OkNetCallback)

    /**
     * 同步的get
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    fun getExecute(url: String, params: ParamsCompat, callback: OkNetCallback): Call

    /**
     * post  方法
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    fun post(url: String, params: ParamsCompat, callback: OkNetCallback): Call

    /**
     * 同步的post
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    fun postExecute(url: String, params: ParamsCompat, callback: OkNetCallback): Call //  其他请求方式

    /**
     *   文件下载
     * @param url String 地址
     * @param startsPoint Long 开始续传的位置   正常文件大小
     * @param fileName String  文件名称
     * @param downloadPath String  文件地址
     * @param downloadListener DownloadListener 回调
     * @return Call
     */
    fun downloadFile(
        url: String,
        startsPoint: Long,
        fileName: String,
        downloadPath: String,
        downloadListener: DownloadListener
    ): Call

    /**
     *  文件上传 接口
     * @param url String
     * @param bodyParams Map<String, String>
     * @param fileKey String
     * @param files List<File>
     * @return Call
     */
    fun preUpload(
        url: String, bodyParams: Map<String, String>, fileKey: String, files: List<File>
    ): Call

}
