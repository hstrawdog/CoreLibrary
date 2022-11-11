/*
 * Create on 2016-12-14 下午3:54
 * FileName: OkHttpImpl.java
 * Author: huang qiqiang
 * Contact: http://www.huangqiqiang.cn
 * Email 593979591@QQ.com
 *
 */
package com.hqq.core.net.ok

import android.text.TextUtils
import com.hqq.core.CoreConfig
import com.hqq.core.net.DownloadListener
import com.hqq.core.net.ok.HttpCompat.ParamsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.*
import java.net.URLConnection
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.core.net.ok
 * @FileName :   OkHttpImpl
 * @Date : 2016/12/14  下午15:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
</描述当前版本功能> */
class OkHttpImpl : HttpCompat {


    val WRITE_TIMEOUT = 60L

    /**
     * 修改  延迟加载
     */
    val mOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder() //设置读取超时时间
            .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //设置写的超时时
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).connectTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build()
    }


    /**
     * 创建 OkHttpClient
     *
     * @return HttpCompat
     */
    override fun create(): HttpCompat {

        return this
    }

    /**
     * get 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    override fun get(url: String, params: ParamsCompat, callback: OkNetCallback) {
        doGet(Dispatchers.IO, url, params, callback)
    }

    /**
     * 同步的get  请求
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    override fun getExecute(url: String, params: ParamsCompat, callback: OkNetCallback): Call {
        val request: Request.Builder = getBuilder(url, params)

        val call = mOkHttpClient.newCall(request.build())
        try {
            val response = call.execute()
            postHandler(Dispatchers.Default, callback, response, params)
        } catch (e: IOException) {
            e.printStackTrace()
            postHandler(Dispatchers.Default, callback, null, null)
        }
        return call
    }

    /**
     * post 请求
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    override fun post(url: String, params: ParamsCompat, callback: OkNetCallback): Call {
        return doPost(url, params, callback, Dispatchers.IO)
    }

    /**
     * 同步请求
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    override fun postExecute(url: String, params: ParamsCompat, callback: OkNetCallback): Call {
        val body = params.paramForm()
        val request: Request.Builder = Request.Builder().url(url).post(body)
        val call = mOkHttpClient!!.newCall(request.build())
        try {
            val response = call.execute()
            postHandler(Dispatchers.Default, callback, response, params)
        } catch (e: IOException) {
            e.printStackTrace()
            postHandler(Dispatchers.Default, callback, null, null)
        }
        return call
    }

    /**
     *  文件下载
     * @param url String
     * @param startsPoint Long
     * @param fileName String
     * @param downloadPath String
     * @param downloadListener DownloadListener
     * @return Call
     * @throws IOException
     */
    override fun downloadFile(url: String, startsPoint: Long, fileName: String, downloadPath: String, downloadListener: DownloadListener): Call {
        val request: Request.Builder = Request.Builder().url(url).addHeader("Connection", "close") //这里不设置可能产生EOF错误
            .header("RANGE", "bytes=$startsPoint-") //断点续传

        var call = mOkHttpClient!!.newCall(request.build())

        try {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    downloadListener.fail(-1, e)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val code = response.code
                    try {
                        if (response.isSuccessful) {
                            var tempStartPoint = startsPoint
                            val file = File(downloadPath + File.separator + fileName)
                            val length = response.body!!.contentLength()
                            if (length == 0L) {
                                // 说明文件已经下载完，直接跳转安装就好
                                downloadListener.complete(file.absolutePath)
                                return
                            }
                            if (response.code == 200) {
                                //说明后台不支持断点续传
                                tempStartPoint = 0
                                if (file.exists()) {
                                    file.delete()
                                }
                            }
                            downloadListener.start(tempStartPoint)
                            // 保存文件到本地
                            var `is`: InputStream? = null
                            var randomAccessFile: RandomAccessFile? = null
                            var bis: BufferedInputStream? = null
                            val buff = ByteArray(1024)
                            var len = 0
                            try {
                                `is` = response.body!!.byteStream()
                                bis = BufferedInputStream(`is`)
                                // 随机访问文件，可以指定断点续传的起始位置
                                randomAccessFile = RandomAccessFile(file, "rwd")
                                randomAccessFile.seek(tempStartPoint)
                                var fileSize = startsPoint
                                while (bis.read(buff).also { len = it } != -1) {
                                    randomAccessFile.write(buff, 0, len)
                                    fileSize += len
                                    val fileLength = randomAccessFile.length()
                                    val index = fileSize.toFloat() / length.toFloat()
                                    downloadListener.loading(index)
                                }
                                // 下载完成
                                downloadListener.complete(file.absolutePath)
                            } catch (e: Exception) {
                                //所有异常都处理
                                if (CoreConfig.get().isDebug) {
                                    e.printStackTrace()
                                }
                                downloadListener.fail(-1, e)
                            } finally {
                                try {
                                    `is`?.close()
                                    bis?.close()
                                    randomAccessFile?.close()
                                } catch (e: Exception) {
                                    //所有异常都处理
                                    if (CoreConfig.get().isDebug) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        } else {
                            downloadListener.fail(code, null)
                        }
                    } catch (e: Exception) {
                        //所有异常都处理
                        if (CoreConfig.get().isDebug) {
                            e.printStackTrace()
                        }
                        downloadListener.fail(-1, e)
                    }
                }
            })
        } catch (e: Exception) {
            //所有异常都处理
            if (CoreConfig.get().isDebug) {
                e.printStackTrace()
            }
            downloadListener.fail(-1, e)
        }

        return call
    }

    override fun preUpload(url: String, bodyParams: Map<String, String>, fileKey: String, files: List<File>): Call {
        var call: Call? = null
        var requestBody: RequestBody? = null
        if (TextUtils.isEmpty(fileKey) || files == null || files.isEmpty()) {
            requestBody = setRequestBody(bodyParams)
        } else {
            val builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            if (bodyParams != null) {
                val entries = bodyParams.entries
                val entryIterator = entries.iterator()
                while (entryIterator.hasNext()) {
                    val (key, value) = entryIterator.next()
                    builder.addFormDataPart(key, value)
                }
            }
            //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
            for (file in files) {
                val fileName = file.name
                val mimeType: String = guessMimeType(fileName)

                builder.addFormDataPart(fileKey, fileName, RequestBody.create(mimeType.toMediaTypeOrNull(), file))
            }
            requestBody = builder.build()
        }
        val request: Request = Request.Builder().url(url).post(requestBody!!).build()
        call = mOkHttpClient.newCall(request)
        return call
    }

    /**
     * 获取文件后缀的方法
     *
     * @param path
     * @return
     */
    private fun guessMimeType(path: String): String {
        val fileNameMap = URLConnection.getFileNameMap()
        var contentType = fileNameMap.getContentTypeFor(path)
        if (contentType == null) {
            contentType = "application/octet-stream"
        }
        return contentType
    }
    /**
     * post的请求参数，构造RequestBody
     *
     * @param bodyParams
     * @return
     */
    private fun setRequestBody(bodyParams: Map<String, String>?): RequestBody? {
        var body: RequestBody? = null
        val formEncodingBuilder = FormBody.Builder()
        if (bodyParams != null) {
            val entries = bodyParams.entries
            val entryIterator = entries.iterator()
            while (entryIterator.hasNext()) {
                val (key, value) = entryIterator.next()
                formEncodingBuilder.add(key, value)
            }
        }
        body = formEncodingBuilder.build()
        return body
    }

    /**
     * 拼接成URL
     * @param url String
     * @param params ParamsCompat?
     * @return Request.Builder
     */
    private fun getBuilder(url: String, params: ParamsCompat?): Request.Builder {
        val request = Request.Builder()
        var realUrl: String = url
        params?.let {
            it.paramGet()?.let { paramget ->
                if (paramget.isNotEmpty()) {
                    realUrl = "$url?$paramget"
                }
            }
            for (key in it.headers.keys) {
                it.headers[key]?.let { it1 -> request.addHeader(key, it1) }
            }
        }
        return request.url(realUrl).get()
    }

    /**
     * 执行get 请求
     * @param coroutineContext CoroutineContext   协程 上下文
     * @param url String
     * @param params ParamsCompat
     * @param callback OkNetCallback
     * @return Call
     */
    private fun doGet(coroutineContext: CoroutineContext, url: String, params: ParamsCompat, callback: OkNetCallback): Call {
        val request: Request.Builder = getBuilder(url, params)
        val call = mOkHttpClient.newCall(request.build())
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postHandler(coroutineContext, callback, null, null)
            }

            override fun onResponse(call: Call, response: Response) {
                postHandler(coroutineContext, callback, response, params)
            }
        })
        return call
    }

    /**
     * 执行post 请求
     *
     * @param url
     * @param params
     * @param callback
     * @param o
     * @return
     */
    private fun doPost(url: String, params: ParamsCompat, callback: OkNetCallback, coroutineContext: CoroutineContext): Call {
        val body = params.paramForm()
        val request: Request.Builder = Request.Builder().url(url).post(body)
        return doRequest(coroutineContext, request, callback, params)
    }

    /**
     * 是否需要切换线程
     *
     * @param handler
     * @param callback
     * @param response
     * @param paramsCompat
     */
    private fun postHandler(coroutineContext: CoroutineContext, callback: OkNetCallback, response: Response?, paramsCompat: ParamsCompat?) {
        CoroutineScope(coroutineContext).launch {
            post(callback, response, paramsCompat)
        }
    }

    /**
     * 处理 post  请求
     *
     * @param request
     * @param callback
     * @param params
     * @return
     */
    private fun doRequest(coroutineContext: CoroutineContext, request: Request.Builder, callback: OkNetCallback, params: ParamsCompat): Call {
        val call = mOkHttpClient.newCall(request.build())
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postHandler(coroutineContext, callback, null, null)
            }

            override fun onResponse(call: Call, response: Response) {
                postHandler(coroutineContext, callback, response, params)
            }
        })
        return call
    }

    /**
     * 回调数据
     *
     * @param callback
     * @param response
     * @param paramsCompat
     */
    private fun post(callback: OkNetCallback, response: Response?, paramsCompat: ParamsCompat?) {
        if (response != null) {
            try {
                val string = getDecodeResponse(response, paramsCompat)
                // 错误码二次校验
                val code = response.code
                callback.onSuccess(code.toString() + "", string)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            callback.onFailure("0", "网络连接失败,请检查网络", "")
        }
    }

    /**
     * 解码数据
     *
     * @param response
     * @param paramsCompat
     * @return返回数据进行解码
     * @throws IOException
     */
    private fun getDecodeResponse(response: Response, paramsCompat: ParamsCompat?): String {
        var string = ""
        val body = response.body
        if (body != null) {
            if (paramsCompat != null && paramsCompat.decode?.isNotEmpty() == true) {
                val bytes = body.bytes()
                string = String(bytes, Charset.forName(paramsCompat.decode))
            } else {
                string = body.string()
            }
        }
        return string
    }
}