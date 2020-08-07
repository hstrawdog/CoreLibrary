package com.hqq.core.widget.divider

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.widget.divider
 * @FileName :   DividerItemDecoration
 * @Date : 2018/7/5 0005  下午 5:11
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 * https://juejin.im/post/5940a020a0bb9f006b734228
 * 使用这个 在rc 一直滑动的时候 会不断的去创建对象
 */
abstract class DividerItemDecoration : ItemDecoration() {
    private val mPaint: Paint
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //left, top, right, bottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val itemPosition = (child.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            val divider = getDivider(parent, itemPosition)
            if (divider.getLeftSideLine().isHave()) {
                drawChildLeftVertical(child, c, parent, divider.getLeftSideLine().getColor(), divider.getLeftSideLine().getWidthDp() as Int
                        , divider.getLeftSideLine().getStartPaddingDp() as Int
                        , divider.getLeftSideLine().getEndPaddingDp() as Int)
            }
            if (divider.getTopSideLine().isHave()) {
                drawChildTopHorizontal(child, c, parent, divider!!.topSideLine.getColor(), divider.getTopSideLine().getWidthDp() as Int
                        , divider.getTopSideLine().getStartPaddingDp() as Int
                        , divider.getTopSideLine().getEndPaddingDp() as Int)
            }
            if (divider.getRightSideLine().isHave()) {
                drawChildRightVertical(child, c, parent, divider.getRightSideLine().getColor(), divider.getRightSideLine().getWidthDp() as Int
                        , divider.getRightSideLine().getStartPaddingDp() as Int
                        , divider.getRightSideLine().getEndPaddingDp() as Int)
            }
            if (divider.getBottomSideLine().isHave()) {
                drawChildBottomHorizontal(child, c, parent, divider.getBottomSideLine().getColor(), divider.getBottomSideLine().getWidthDp() as Int
                        , divider.getBottomSideLine().getStartPaddingDp() as Int
                        , divider.getBottomSideLine().getEndPaddingDp() as Int)
            }
        }
    }

    private fun drawChildBottomHorizontal(child: View, c: Canvas, parent: RecyclerView, @ColorInt color: Int, lineWidthPx: Int, startPaddingPx: Int, endPaddingPx: Int) {
        var leftPadding = 0
        var rightPadding = 0
        leftPadding = if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            -lineWidthPx
        } else {
            startPaddingPx
        }
        rightPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
                .layoutParams as RecyclerView.LayoutParams
        val left = child.left - params.leftMargin + leftPadding
        val right = child.right + params.rightMargin + rightPadding
        val top = child.bottom + params.bottomMargin
        val bottom = top + lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    private fun drawChildTopHorizontal(child: View, c: Canvas, parent: RecyclerView, @ColorInt color: Int, lineWidthPx: Int, startPaddingPx: Int, endPaddingPx: Int) {
        var leftPadding = 0
        var rightPadding = 0
        leftPadding = if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            -lineWidthPx
        } else {
            startPaddingPx
        }
        rightPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
                .layoutParams as RecyclerView.LayoutParams
        val left = child.left - params.leftMargin + leftPadding
        val right = child.right + params.rightMargin + rightPadding
        val bottom = child.top - params.topMargin
        val top = bottom - lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    private fun drawChildLeftVertical(child: View, c: Canvas, parent: RecyclerView, @ColorInt color: Int, lineWidthPx: Int, startPaddingPx: Int, endPaddingPx: Int) {
        var topPadding = 0
        var bottomPadding = 0
        topPadding = if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            -lineWidthPx
        } else {
            startPaddingPx
        }
        bottomPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
                .layoutParams as RecyclerView.LayoutParams
        val top = child.top - params.topMargin + topPadding
        val bottom = child.bottom + params.bottomMargin + bottomPadding
        val right = child.left - params.leftMargin
        val left = right - lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    private fun drawChildRightVertical(child: View, c: Canvas, parent: RecyclerView, @ColorInt color: Int, lineWidthPx: Int, startPaddingPx: Int, endPaddingPx: Int) {
        var topPadding = 0
        var bottomPadding = 0
        topPadding = if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            -lineWidthPx
        } else {
            startPaddingPx
        }
        bottomPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
                .layoutParams as RecyclerView.LayoutParams
        val top = child.top - params.topMargin + topPadding
        val bottom = child.bottom + params.bottomMargin + bottomPadding
        val left = child.right + params.rightMargin
        val right = left + lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    /**
     * 设置  偏移量
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
        //作为传递left,right,top,bottom的偏移值来用的
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        var divider = getDivider(parent, itemPosition)
        if (divider == null) {
            divider = DividerBuilder().create()
        }
        val left = if (divider.getLeftSideLine().isHave()) divider.getLeftSideLine().getWidthDp() else 0
        val top = if (divider.getTopSideLine().isHave()) divider.getTopSideLine().getWidthDp() else 0
        val right = if (divider.getRightSideLine().isHave()) divider.getRightSideLine().getWidthDp() else 0
        val bottom = if (divider.getBottomSideLine().isHave()) divider.getBottomSideLine().getWidthDp() else 0
        outRect[left, top, right] = bottom
    }

    /**
     * 获取 分割线的参数
     * @param parent parent
     * @param itemPosition  itemPosition
     * @return
     */
    abstract fun getDivider(parent: RecyclerView?, itemPosition: Int): Divider?

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.FILL
    }
}