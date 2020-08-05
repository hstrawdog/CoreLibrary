package com.hqq.core.toolbar;

import android.app.Activity;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqq.core.R;
import com.hqq.core.utils.ResourcesUtils;
import com.hqq.core.widget.FilterImageView;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.toolbar
 * @FileName :   BaseDefToolBarImpl
 * @Date : 2018/11/15 0015  下午 7:27
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class BaseDefToolBarImpl extends BaseToolBar {
    private ImageView mImageViewBg;
    private int mDefToolBarColor = R.color.toolbar_bg_color;
    private int mDefTitleColor = R.color.color_333;

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
        mImageViewBg.setBackgroundResource(mDefToolBarColor);
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

    @Override
    public void setToolBarColor(int color) {
        if (null != getToolBarBg()) {
            getToolBarBg().setImageResource(color);
        } else {
            mDefToolBarColor = color;
        }
        if (null != getStatusBar()) {
            getStatusBar().setBackgroundResource(color);
        } else {
            mDefStatusColor = color;
        }
    }


    /**
     * 设置背景的透明度
     *
     * @param ahpla
     */
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

    /**
     * 设置标题
     *
     * @param title
     */
    public BaseDefToolBarImpl setToolbarTitle(CharSequence title) {
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
     * 设置背景颜色 需要在view创建完成
     *
     * @param resid
     */
    public BaseDefToolBarImpl setToolBarBg(@DrawableRes int resid) {
        mDefToolBarColor = resid;
        if (mImageViewBg != null) {
            mImageViewBg.setImageResource(resid);
        }
        return this;
    }

    /**
     * 配置默认颜色
     *
     * @param defToolBarColor
     * @return
     */
    public BaseDefToolBarImpl setDefToolBarColor(int defToolBarColor) {
        mDefToolBarColor = defToolBarColor;
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param defTitleColor
     * @return
     */
    public BaseDefToolBarImpl setDefTitleColor(int defTitleColor) {
        getToolbarTitle().setTextColor(ContextCompat.getColor(getToolbarTitle().getContext(), defTitleColor));
        return this;
    }


    /**
     * 设置 左边图片
     *
     * @param id
     * @return
     */
    public BaseDefToolBarImpl setLetImageView(int id) {
        getLeftView().setImageResource(id);
        return this;
    }

    /**
     * 添加View
     *
     * @param view
     */
    public void addRightView(View view) {
        ((LinearLayout) mRootView.findViewById(R.id.ll_right)).addView(view);
    }

    //region 添加右边文字

    /**
     * 添加文字
     *
     * @param title
     * @param clickListener
     */
    public void addRightTextView(String title, View.OnClickListener clickListener) {
        addRightTextView(title, R.color.color_333, clickListener);
    }

    /**
     * @param title
     * @param color
     * @param clickListener
     */
    public void addRightTextView(String title, @ColorRes int color, View.OnClickListener clickListener) {
        addRightTextView(title, R.color.color_333, R.dimen.x28, clickListener);

    }

    /**
     * @param title
     * @param color
     * @param size
     * @param clickListener
     */
    public void addRightTextView(String title, @ColorRes int color, @DimenRes int size, View.OnClickListener clickListener) {
        addRightView(newTextView(title, color, size, clickListener));
    }

    /**
     * 默认文本
     *
     * @param title
     * @param color
     * @param size
     * @param clickListener
     * @return
     */
    private View newTextView(String title, @ColorRes int color, @DimenRes int size, View.OnClickListener clickListener) {
        TextView textView = new TextView(mRootView.getContext());
        textView.setText(title);
        textView.setPadding(0, 0, (int) ResourcesUtils.getDimen(R.dimen.x20), 0);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourcesUtils.getDimen(size));
        textView.setOnClickListener(clickListener);
        textView.setTextColor(ResourcesUtils.getColor(color));
        return textView;
    }


    //endregion


    /**
     * 添加图片
     *
     * @param icImage
     * @param listener
     */
    public void addRightImageView(@DrawableRes int icImage, View.OnClickListener listener) {
        addRightImageView(icImage, 0, 0, (int) ResourcesUtils.getDimen(R.dimen.x20), 0, listener);
    }

    public void addRightImageView(@DrawableRes int icImage, int left, int top, int right, int bottom, View.OnClickListener listener) {
        addRightView(newImageView(icImage, left, top, right, bottom, listener));
    }

    /**
     * 默认的图片
     *
     * @param icImage
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param listener
     * @return
     */
    private View newImageView(@DrawableRes int icImage, int left, int top, int right, int bottom, View.OnClickListener listener) {
        FilterImageView imageView = new FilterImageView(mRootView.getContext());
        imageView.setAdjustViewBounds(true);
        imageView.setImageResource(icImage);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setPadding(left, top, right, bottom);
        imageView.setOnClickListener(listener);
        return imageView;
    }


}

