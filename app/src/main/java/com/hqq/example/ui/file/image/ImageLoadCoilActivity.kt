package com.hqq.example.ui.file.image

import android.graphics.drawable.Drawable
import android.net.Uri
import coil.Coil
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import coil.util.CoilUtils
import com.hqq.core.CoreConfig
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityImageLoadCoilBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.Flow

class ImageLoadCoilActivity : BaseViewBindingActivity<ActivityImageLoadCoilBinding>() {

    override fun initView() {


        Coil.setImageLoader(ImageLoader.Builder(CoreConfig.applicationContext)//
            .memoryCache {
                MemoryCache.Builder(this).maxSizePercent(0.2).build()
            }// 缓存 占用比
            .diskCachePolicy(CachePolicy.ENABLED)  //磁盘缓策略 ENABLED、READ_ONLY、WRITE_ONLY、DISABLED
            .crossfade(true) //淡入淡出
            .crossfade(1000)  //淡入淡出时间
            .okHttpClient {  //设置okhttpClient实例
                OkHttpClient.Builder().build()
            }.build())


// 普通加载图片
        binding.imageView17.load(R.mipmap.bg_sty)
//        binding.imageView17.load("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
// Coil 支持加载 urls, uris, resources, drawables, bitmaps, files 等等
//        binding.imageView17.load(R.drawable.image)
//        binding.imageView17.load(File("/path/to/image.jpg"))
//        binding.imageView17.load(Uri.parse("content://com.android.externalstorage/image.jpg"))


        // Requests的配置项可以通过load的lambda参数方式实现
        binding.imageView18.load("https://www.website.com/image.jpg") {
            crossfade(true)
            placeholder(R.mipmap.ic_bg)
            transformations(CircleCropTransformation())
        }
        // 自定义targets，包含开始、成功和失败的回调


        // 下载图片
        ImageRequest.Builder(this).data("https://www.website.com/image.jpg").target(onStart = {

        }, onError = {

        }, onSuccess = {

        }

        )


        CoroutineScope(Dispatchers.IO).launch {
// 通过使用挂起函数get来直接获取图片对象
             getImageLoad()
        }

    }

    private suspend fun getImageLoad():Drawable {
        val request = ImageRequest.Builder(activity).data("https://www.website.com/image.jpg").allowHardware(false).build()
        val result = (ImageLoader.Builder(activity).crossfade(true).build().execute(request) as SuccessResult).drawable
        return result
    }
}