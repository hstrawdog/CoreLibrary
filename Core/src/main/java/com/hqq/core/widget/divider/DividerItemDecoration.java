package com.hqq.core.widget.divider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.widget.divider
 * @FileName :   DividerItemDecoration
 * @Date : 2018/7/5 0005  下午 5:11
 * @Descrive : TODO
 * @Email :
 * https://juejin.im/post/5940a020a0bb9f006b734228
 * 使用这个 在rc 一直滑动的时候 会不断的去创建对象
 */
public abstract class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;

    public DividerItemDecoration() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //left, top, right, bottom
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();
            Divider divider = getDivider(parent, itemPosition);
            if (divider.getLeftSideLine().isHave()) {
                drawChildLeftVertical(child, c, parent, divider.getLeftSideLine().getColor(), (int) divider.getLeftSideLine().getWidthDp()
                        , (int) divider.getLeftSideLine().getStartPaddingDp()
                        , (int) divider.getLeftSideLine().getEndPaddingDp());
            }
            if (divider.getTopSideLine().isHave()) {
                drawChildTopHorizontal(child, c, parent, divider.topSideLine.getColor(), (int) divider.getTopSideLine().getWidthDp()
                        , (int) divider.getTopSideLine().getStartPaddingDp()
                        , (int) divider.getTopSideLine().getEndPaddingDp());
            }
            if (divider.getRightSideLine().isHave()) {
                drawChildRightVertical(child, c, parent, divider.getRightSideLine().getColor(), (int) divider.getRightSideLine().getWidthDp()
                        , (int) divider.getRightSideLine().getStartPaddingDp()
                        , (int) divider.getRightSideLine().getEndPaddingDp());
            }
            if (divider.getBottomSideLine().isHave()) {
                drawChildBottomHorizontal(child, c, parent, divider.getBottomSideLine().getColor(), (int) divider.getBottomSideLine().getWidthDp()
                        , (int) divider.getBottomSideLine().getStartPaddingDp()
                        , (int) divider.getBottomSideLine().getEndPaddingDp());
            }
        }
    }

    private void drawChildBottomHorizontal(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx, int startPaddingPx, int endPaddingPx) {

        int leftPadding = 0;
        int rightPadding = 0;

        if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            leftPadding = -lineWidthPx;
        } else {
            leftPadding = startPaddingPx;
        }

        if (endPaddingPx <= 0) {
            rightPadding = lineWidthPx;
        } else {
            rightPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getLeft() - params.leftMargin + leftPadding;
        int right = child.getRight() + params.rightMargin + rightPadding;
        int top = child.getBottom() + params.bottomMargin;
        int bottom = top + lineWidthPx;
        mPaint.setColor(color);

        c.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildTopHorizontal(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx, int startPaddingPx, int endPaddingPx) {
        int leftPadding = 0;
        int rightPadding = 0;

        if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            leftPadding = -lineWidthPx;
        } else {
            leftPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0) {
            rightPadding = lineWidthPx;
        } else {
            rightPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getLeft() - params.leftMargin + leftPadding;
        int right = child.getRight() + params.rightMargin + rightPadding;
        int bottom = child.getTop() - params.topMargin;
        int top = bottom - lineWidthPx;
        mPaint.setColor(color);

        c.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildLeftVertical(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx, int startPaddingPx, int endPaddingPx) {
        int topPadding = 0;
        int bottomPadding = 0;

        if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            topPadding = -lineWidthPx;
        } else {
            topPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0) {
            bottomPadding = lineWidthPx;
        } else {
            bottomPadding = -endPaddingPx;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() - params.topMargin + topPadding;
        int bottom = child.getBottom() + params.bottomMargin + bottomPadding;
        int right = child.getLeft() - params.leftMargin;
        int left = right - lineWidthPx;
        mPaint.setColor(color);

        c.drawRect(left, top, right, bottom, mPaint);

    }

    private void drawChildRightVertical(View child, Canvas c, RecyclerView parent, @ColorInt int color, int lineWidthPx, int startPaddingPx, int endPaddingPx) {
        int topPadding = 0;
        int bottomPadding = 0;
        if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            topPadding = -lineWidthPx;
        } else {
            topPadding = startPaddingPx;
        }
        if (endPaddingPx <= 0) {
            bottomPadding = lineWidthPx;
        } else {
            bottomPadding = -endPaddingPx;
        }
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() - params.topMargin + topPadding;
        int bottom = child.getBottom() + params.bottomMargin + bottomPadding;
        int left = child.getRight() + params.rightMargin;
        int right = left + lineWidthPx;
        mPaint.setColor(color);
        c.drawRect(left, top, right, bottom, mPaint);
    }

    /**
     * 设置  偏移量
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
        //作为传递left,right,top,bottom的偏移值来用的
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        Divider divider = getDivider(parent, itemPosition);

        if (divider == null) {
            divider = new DividerBuilder().create();
        }
        int left = divider.getLeftSideLine().isHave() ? (int) divider.getLeftSideLine().getWidthDp() : 0;
        int top = divider.getTopSideLine().isHave() ? (int) divider.getTopSideLine().getWidthDp() : 0;
        int right = divider.getRightSideLine().isHave() ? (int) divider.getRightSideLine().getWidthDp() : 0;
        int bottom = divider.getBottomSideLine().isHave() ? (int) divider.getBottomSideLine().getWidthDp() : 0;

        outRect.set(left, top, right, bottom);
    }
    /**
     * 获取 分割线的参数
     * @param parent parent
     * @param itemPosition  itemPosition
     * @return
     */
    @Nullable
    public abstract Divider getDivider(RecyclerView parent, int itemPosition);


}
