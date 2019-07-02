package com.hqq.core.app.ui.activity;

import android.widget.TextView;

import com.hqq.core.app.R;
import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.CacheUtil;
import com.hqq.core.utils.ScreenUtils;
import com.hqq.core.utils.TextSpannableBuilder;
import com.hqq.core.utils.VersionUtils;

import butterknife.BindView;

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity
 * @FileName :   BaseInfoActivity
 * @Date : 2019-07-02  21:51
 * @Descrive : TODO
 * @Email :
 */
public class BaseInfoActivity extends BaseActivity {

    @BindView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    public int setViewId() {
        return R.layout.activity_base_info;
    }

    @Override
    public void initView() {
        mTvInfo.setText(new TextSpannableBuilder()
                .addTextPart("包名    ")
                .addTextPart(VersionUtils.getPackageName(this))
                .addTextPart("\n版本    ")
                .addTextPart(VersionUtils.getVerName(this))
                .addTextPart("\n版本号    ")
                .addTextPart(VersionUtils.getVersionCode(this) + "")
                .addTextPart("\nGlide缓存大小    ")
                .addTextPart(CacheUtil.getCacheSize(this) + "")
                .addTextPart("\n缓存总大小    ")
                .addTextPart(CacheUtil.getTotalCacheSize(this) + "")
                .addTextPart("\n状态栏高度    ")
                .addTextPart(ScreenUtils.getStatusBarHeight4Resources(this) + "px")
                .addTextPart("\n屏幕宽度    ")
                .addTextPart(ScreenUtils.getScreenWidth(this) + "px")
                .addTextPart("\n屏幕高度    ")
                .addTextPart(ScreenUtils.getScreenHeight(this) + "px")
                .addTextPart("\nx密度    ")
                .addTextPart(ScreenUtils.getScreenXDPI(this) + "dpi")
                .addTextPart("\ny密度    ")
                .addTextPart(ScreenUtils.getScreenYDPI(this) + "dpi")
                .addTextPart("\n屏幕密度    ")
                .addTextPart(ScreenUtils.getScreenDensityDpi(this) + "dpi")
                .build());
    }


}
