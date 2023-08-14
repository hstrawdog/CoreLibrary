package com.easy.core.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.glide
 * @FileName :   GlideModuleUtils
 * @Date : 2018/10/19 0019  下午 4:45
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
@GlideModule
class GlideModuleUtils : AppGlideModule() {
    /**
     * MemorySizeCalculator类通过考虑设备给定的可用内存和屏幕大小想出合理的默认大小.
     * 通过LruResourceCache进行缓存。
     *
     * @param context
     * @param builder
     */
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val calculator = MemorySizeCalculator.Builder(context).setMemoryCacheScreens(2f).build()
        // 小米4c 4+32  16588800
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
        val diskCacheSizeBytes = 1024 * 1024 * 100 // 100 MB
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes.toLong()))
        //自定义图片质量
        //builder.setDefaultRequestOptions(DecodeFormat.PREFER_ARGB_8888);
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}