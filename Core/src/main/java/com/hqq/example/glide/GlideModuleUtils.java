package com.hqq.example.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.glide
 * @FileName :   GlideModuleUtils
 * @Date : 2018/10/19 0019  下午 4:45
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
@GlideModule
public class GlideModuleUtils extends AppGlideModule {

    /**
     * MemorySizeCalculator类通过考虑设备给定的可用内存和屏幕大小想出合理的默认大小.
     * 通过LruResourceCache进行缓存。
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).setMemoryCacheScreens(2).build();
        // 小米4c 4+32  16588800
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
        //自定义图片质量
        //builder.setDefaultRequestOptions(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}
