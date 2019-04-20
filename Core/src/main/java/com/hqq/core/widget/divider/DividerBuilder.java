package com.hqq.core.widget.divider;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

import com.hqq.core.R;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.widget.divider
 * @FileName :   DividerBuilder
 * @Date : 2018/7/5 0005  下午 5:16
 * @Descrive : TODO
 * @Email :
 */
public class DividerBuilder {
    private SideLine leftSideLine;
    private SideLine topSideLine;
    private SideLine rightSideLine;
    private SideLine bottomSideLine;


    public DividerBuilder setLeftSideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.leftSideLine = new SideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp);
        return this;
    }

    public DividerBuilder setLeftSideLine(@ColorInt int color, float widthDp) {
        return setLeftSideLine(true, color, widthDp, 0, 0);
    }

    public DividerBuilder setLeftSideLine(Context context, int color, int widthDp) {
        return setLeftSideLine(true, ContextCompat.getColor(context, color), context.getResources().getDimension(widthDp), 0, 0);
    }

    public DividerBuilder setLeftSideLine(Context context, int widthDp) {
        return setLeftSideLine(true, ContextCompat.getColor(context, R.color.transparent), context.getResources().getDimension(widthDp), 0, 0);
    }


    public DividerBuilder setTopSideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.topSideLine = new SideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp);
        return this;
    }

    public DividerBuilder setTopSideLine(boolean isHave, @ColorInt int color, float widthDp) {
        return setTopSideLine(isHave, color, widthDp, 0, 0);
    }

    public DividerBuilder setTopSideLine(@ColorInt int color, float widthDp) {
        return setTopSideLine(true, color, widthDp, 0, 0);
    }

    public DividerBuilder setTopSideLine(Context context, int color, int widthDp) {
        return setTopSideLine(true, ContextCompat.getColor(context, color), context.getResources().getDimension(widthDp), 0, 0);
    }

    public DividerBuilder setTopSideLine(Context context, int widthDp) {
        return setTopSideLine(true, ContextCompat.getColor(context, R.color.transparent), context.getResources().getDimension(widthDp), 0, 0);
    }


    public DividerBuilder setRightSideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.rightSideLine = new SideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp);
        return this;
    }

    public DividerBuilder setRightSideLine(@ColorInt int color, float widthDp) {
        return setRightSideLine(true, color, widthDp, 0, 0);
    }

    public DividerBuilder setRightSideLine(Context context, int color, int widthDp) {
        return setRightSideLine(true, ContextCompat.getColor(context, color), context.getResources().getDimension(widthDp), 0, 0);
    }

    public DividerBuilder setRightSideLine(Context context, int widthDp) {
        return setRightSideLine(true, ContextCompat.getColor(context, R.color.transparent), context.getResources().getDimension(widthDp), 0, 0);
    }

    public DividerBuilder setBottomSideLine(boolean isHave, @ColorInt int color, float widthDp, float startPaddingDp, float endPaddingDp) {
        this.bottomSideLine = new SideLine(isHave, color, widthDp, startPaddingDp, endPaddingDp);
        return this;
    }

    public DividerBuilder setBottomSideLine(@ColorInt int color, float widthDp) {
        return setBottomSideLine(true, color, widthDp, 0, 0);
    }

    public DividerBuilder setBottomSideLine(Context context, int color, int widthDp) {
        return setBottomSideLine(true, ContextCompat.getColor(context, color), context.getResources().getDimension(widthDp), 0, 0);
    }

    public DividerBuilder setBottomSideLine(Context context, int widthDp) {
        return setBottomSideLine(true, ContextCompat.getColor(context, R.color.transparent), context.getResources().getDimension(widthDp), 0, 0);
    }


    public Divider create() {
        //提供一个默认不显示的sideline，防止空指针
        SideLine defaultSideLine = new SideLine(false, 0xff666666, 0, 0, 0);
        leftSideLine = (leftSideLine != null ? leftSideLine : defaultSideLine);
        topSideLine = (topSideLine != null ? topSideLine : defaultSideLine);
        rightSideLine = (rightSideLine != null ? rightSideLine : defaultSideLine);
        bottomSideLine = (bottomSideLine != null ? bottomSideLine : defaultSideLine);
        return new Divider(leftSideLine, topSideLine, rightSideLine, bottomSideLine);
    }

}
