package com.easy.example.ui.skin;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import com.easy.core.ui.base.BaseActivity;
import com.easy.example.R;

import skin.support.SkinCompatManager;

/**
 * @version V1.0 <描述当前版本功能>
 * 在此写用途
 * @author: huangqiqiang
 * @FileName: com.easy.example.ui.skin.BaseSkinActivity.java
 * @emain: 593979591@qq.com
 * @date: 2020-04-10 10:01
 */
public abstract class BaseSkinActivity extends BaseActivity {
    @Override
    public int getLayoutViewId() {
        return R.layout.activity_skin_base;
    }


    @Override
    public void initView() {

        findViewById(R.id.button32).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinCompatManager.getInstance().restoreDefaultTheme();
            }
        });
        findViewById(R.id.button33).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载

            }
        });
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);

    }
}
