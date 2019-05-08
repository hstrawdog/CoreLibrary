package com.hqq.core.toolbar;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqq.core.R;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   IDefToolBarImpl
 * @Date : 2018/11/15 0015  下午 7:27
 * @Descrive :
 * @Email :
 */
public class IDefToolBarImpl extends IToolBar {
    protected ImageView mImageViewBg;
    protected int mDefColor = R.color.toolbar_bg_color;
    protected int mDefTitleColor = R.color.color_333;

    @Override
    public View iniToolBar(final Activity activity, ViewGroup viewGroup) {
        Toolbar toolbar = (Toolbar)
                LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.layout_def_toolbar, viewGroup, false);
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        }
        toolbar.findViewById(R.id.iv_bar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        mImageViewBg = toolbar.findViewById(R.id.iv_toolBar_Bg);
        mImageViewBg.setBackgroundResource(mDefColor);
        ((TextView) toolbar.findViewById(R.id.tv_bar_title)).setText(activity.getTitle());
        ((TextView) toolbar.findViewById(R.id.tv_bar_title)).setTextColor(ContextCompat.getColor(toolbar.getContext(), mDefTitleColor));
        return toolbar;
    }


    /**
     * 滑动效果
     *
     * @param ahpla 0 -1
     */
    @Override
    public void initScroll(float ahpla) {
        if (ahpla > 1) {
            ahpla = 1;
        }
        super.initScroll(ahpla);
        if (ahpla < 0.5) {
            getLeftView().setImageResource(R.mipmap.ic_black_whit);
            getLeftView().setAlpha(1 - ahpla * 2);
        } else {
            getLeftView().setImageResource(R.mipmap.ic_back_gray);
            getLeftView().setAlpha((float) ((ahpla - 0.5) * 2));
        }

        if (getToolbarTitle() != null) {
            getToolbarTitle().setAlpha(ahpla);
        }
        if (mImageViewBg != null) {
            mImageViewBg.setAlpha(ahpla);
        }

    }

    public void initScrollNoImage(float ahpla) {
        if (ahpla > 1) {
            ahpla = 1;
        }
        super.initScroll(ahpla);
        if (ahpla < 0.5) {
            getLeftView().setAlpha(1 - ahpla * 2);
        } else {
            getLeftView().setAlpha((float) ((ahpla - 0.5) * 2));
        }

        if (getToolbarTitle() != null) {
            getToolbarTitle().setAlpha(ahpla);
        }
        if (mImageViewBg != null) {
            mImageViewBg.setAlpha(ahpla);
        }

    }


    /**
     * 获取左边 图标View
     *
     * @return ImageView
     */
    public ImageView getLeftView() {
        return mRootView.findViewById(R.id.iv_bar_back);
    }


    /**
     * 背景View
     *
     * @returnImageView
     */
    public ImageView getToolBarBg() {
        return mImageViewBg;
    }

    /**
     * 设置 bar 的颜色
     *
     * @param color
     */
    public void setToolBarColor(@DrawableRes int color) {
        getToolBarBg().setImageResource(color);
        getStatusBar().setBackgroundResource(color);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public IDefToolBarImpl setToolbarTitle(String title) {
        if (mRootView == null) {
            return null;
        }
        getToolbarTitle().setText(title);
        return this;
    }

    /**
     * 获取标题View
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mRootView.findViewById(R.id.tv_bar_title);
    }

    /**
     * 获取右边TextView
     *
     * @return TextView
     */
    public TextView getRightTextView() {
        return mRootView.findViewById(R.id.tv_bar_right);
    }

    /**
     * 二级 text  获取
     *
     * @return
     */
    public TextView getRightSecondaryTextView() {
        return mRootView.findViewById(R.id.tv_menu_right_secondary);
    }

    /**
     * 二级 text 设置
     *
     * @param text
     * @param clickListener
     */
    public void setRightSecondaryTextView(String text, View.OnClickListener clickListener) {
        getRightSecondaryTextView().setText(text);
        getRightSecondaryTextView().setVisibility(View.VISIBLE);
        getRightSecondaryTextView().setOnClickListener(clickListener);
    }


    /**
     * 设置右边 文字 点击事件
     *
     * @param text            text
     * @param onClickListener onClickListener
     */
    public void setRightTextView(String text, View.OnClickListener onClickListener) {
        TextView textView = getRightTextView();
        textView.setText(text);
        textView.setVisibility(View.VISIBLE);
        textView.setOnClickListener(onClickListener);

    }

    /**
     * 设置右边　文字
     *
     * @param text text
     */
    public void setRightTextView(String text) {
        TextView textView = getRightTextView();
        textView.setText(text);
        textView.setVisibility(View.VISIBLE);
    }

    /**
     * 获取右边View
     *
     * @return ImageView
     */
    public ImageView getRightImageView() {
        if (mRootView != null) {
            return mRootView.findViewById(R.id.iv_bar_right);
        } else {
            return new ImageView(mActivity);
        }
    }


    /**
     * 设置  右边图标
     *
     * @param ic ic
     */
    public void setRightBackgroundResource(@DrawableRes int ic) {
        ImageView imageView = getRightImageView();
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(ic);
    }

    /**
     * 右边点击事件
     *
     * @param onClickListener onClickListener
     */
    public void setRightClickListener(View.OnClickListener onClickListener) {
        getRightTextView().setOnClickListener(onClickListener);
        getRightImageView().setOnClickListener(onClickListener);

    }

    /**
     * 设置背景颜色 需要在view创建完成
     *
     * @param resid
     */
    public IDefToolBarImpl setToolBarBg(@DrawableRes int resid) {
        mDefColor = resid;
        if (mImageViewBg != null) {
            mImageViewBg.setImageResource(resid);
        }
        return this;
    }

    /**
     * 配置默认颜色
     *
     * @param defColor
     * @return
     */
    public IDefToolBarImpl setDefColor(int defColor) {
        mDefColor = defColor;
        return this;
    }

    public IDefToolBarImpl setDefTitleColor(int defTitleColor) {
        getToolbarTitle().setTextColor(ContextCompat.getColor(getToolbarTitle().getContext(), defTitleColor));
        return this;
    }

    /**
     * 设置  右边图标  以及点击事件
     *
     * @param ic              ic
     * @param onClickListener onClickListener
     */
    public IDefToolBarImpl setRightBackgroundResource(int ic, View.OnClickListener onClickListener) {
        ImageView imageView = getRightImageView();
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(ic);
        imageView.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 设置 左边图片
     *
     * @param id
     * @return
     */
    public IDefToolBarImpl setLetImageView(int id) {
        getLeftView().setImageResource(id);
        return this;
    }
}

