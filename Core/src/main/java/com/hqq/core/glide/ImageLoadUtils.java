package com.hqq.core.glide;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hqq.core.CoreBuildConfig;
import com.hqq.core.R;
import com.hqq.core.utils.ResourcesUtils;

/**
 * 这里的 封装 只是为了更方便的替换 glide  并不只是为了更好的是使用
 * http://blog.csdn.net/hexingen/article/details/72578066
 * http://blog.csdn.net/wyb112233/article/details/52337392
 *
 * @Author : huangqiqiang
 * @Package : com.hqq.blibrary.glide
 * @FileName :   ImageLoadUtils
 * @Date : 2018/2/9  9:23
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */

public class ImageLoadUtils {
    protected static RequestOptions mDefRequestOptions;
    protected static RequestOptions mRoundRequestOptions;

    /**
     * 默认配置
     *
     * @return
     */
    public static synchronized RequestOptions getRequestOptions() {
        if (mDefRequestOptions == null) {
            mDefRequestOptions = new RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    //缓存SOURC和RESULT
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //不做内存缓存
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .placeholder(CoreBuildConfig.getInstance().getDefImg());
        }
        return mDefRequestOptions;
    }

    /**
     * 默认配置
     *
     * @return
     */
    public static synchronized RequestOptions getRoundRequestOptions() {
        if (mRoundRequestOptions == null) {
            mRoundRequestOptions = new RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    //缓存SOURC和RESULT
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //不做内存缓存
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .placeholder(CoreBuildConfig.getInstance().getDefImg());
        }
        return mRoundRequestOptions;
    }

    public static RequestOptions getDefRoundRequestOptions(Context context) {

        return getRoundRequestOptions((int) ResourcesUtils.getDimen(context, R.dimen.x10));
    }

    /**
     * 圆角的 图片
     *
     * @param px
     * @return
     */
    public static RequestOptions getRoundRequestOptions(int px) {
        if (mRoundRequestOptions == null) {
            mRoundRequestOptions = new RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    //缓存SOURC和RESULT
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //不做内存缓存
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .placeholder(CoreBuildConfig.getInstance().getDefImg())
                    .transform(new GlideRoundTransform(px));
        }
        return mRoundRequestOptions;
    }


    /**
     * 验证  view   是否 合法
     * 判断  界面是否销毁
     *
     * @param view
     * @return
     */
    public static boolean checkFinish(View view) {

        if (view == null || view.getContext() == null) {
            return true;
        }
        if (view.getContext() instanceof Activity) {
            Activity activity = (Activity) view.getContext();
            if (activity.isFinishing()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 加载图片
     *
     * @param url       String 地址
     * @param imageView
     */
    public static void with(String url, ImageView imageView) {
        if (checkFinish(imageView)) {
            return;
        }
        GlideApp.with(imageView)
                .load(url)
                .apply(getRequestOptions())
                .into(imageView);
    }

    /**
     * 圆角
     *
     * @param url
     * @param imageView
     */
    public static void withFillet(String url, ImageView imageView) {
        if (checkFinish(imageView)) {
            return;
        }
        GlideApp.with(imageView)
                .load(url)
                .apply(getDefRoundRequestOptions(imageView.getContext()))
                .into(imageView);
    }


    /**
     * @param url
     * @param imageView
     */
    public static void with(@RawRes @DrawableRes @Nullable int url, ImageView imageView) {
        if (checkFinish(imageView)) {
            return;
        }
        GlideApp.with(imageView)
                .load(url)
                .apply(getRequestOptions()
                ).into(imageView);
    }


    /**
     * @param url
     * @param imageView
     * @param width     宽
     * @param height    高
     */
    public static void with(String url, ImageView imageView, int width, int height) {
        if (checkFinish(imageView)) {
            return;
        }
        GlideApp.with(imageView)
                .load(url)
                .apply(
                        getRequestOptions()
                                .override(width, height)
                ).into(imageView);

    }

    /**
     * 加载圆形图
     *
     * @param url
     * @param imageView
     */
    public static void transformHead(String url, ImageView imageView) {
        if (checkFinish(imageView)) {
            return;
        }
        GlideApp.with(imageView).load(url).apply(
                RequestOptions.circleCropTransform()
                        .placeholder(R.mipmap.ic_def_head)
        ).into(imageView);
    }

    /**
     * 圆角头像
     *
     * @param url
     * @param imageView
     */
    public static void transformCircularHead(String url, ImageView imageView) {
        if (checkFinish(imageView)) {
            return;
        }
        GlideApp.with(imageView)
                .load(url)
                .thumbnail()
                .apply(RequestOptions.circleCropTransform()
                        //不做内存缓存
                        .skipMemoryCache(true)
                        .dontAnimate()
                        .placeholder(R.mipmap.ic_def_head_circular)
                )
                .into(imageView);
    }

    /**
     * 圆角图片加载
     * transformCircularHead
     *
     * @param url
     * @param imageView
     */
    @Deprecated
    public static void withRounded(String url, ImageView imageView) {
        if (checkFinish(imageView)) {
            return;
        }
        GlideApp.with(imageView)
                .load(url)
                .apply(getRoundRequestOptions()
                        .transform(new GlideRoundTransform(20))
                )
                .into(imageView);
    }

    /**
     * @param url
     * @param imageView
     * @param radius    圆角  单位 px
     */
    public static void withRound2PX(String url, ImageView imageView, int radius) {
        if (checkFinish(imageView)) {
            return;
        }
        GlideApp.with(imageView)
                .load(url)
                .apply(
                        getRoundRequestOptions().transforms(new CenterCrop(), new RoundedCorners(radius))
                )
                .into(imageView);
    }

    /**
     * 通过图片Uri加载图片
     *
     * @param context
     * @param resource
     * @param imageView
     */
    public static void loadImagebyUri(Context context, Uri resource, ImageView imageView) {
        if (context != null) {
            if (context instanceof Activity && ((Activity) context).isDestroyed()) {
                return;
            }
            Glide.with(context).load(resource).thumbnail().apply(RequestOptions.centerCropTransform().placeholder(CoreBuildConfig.getInstance().getDefImg())).into(imageView);
        }
    }
}
