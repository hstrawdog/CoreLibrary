package com.hqq.core.recycle

import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
import androidx.recyclerview.widget.SnapHelper
import com.hqq.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity.banner
 * @FileName :   PagerSnapHelper
 * @Date : 2019/6/18 0018  上午 10:16
 * @Email : qiqiang213@gmail.com
 * @Descrive : 多个item组合分页 只支持横向分页滑动
 */
class PagerSnapHelper(
        /**
         * 一页大小
         */
        private val itemCount: Int) : SnapHelper() {
    /**
     * 当前滑动的距离 判断 左右
     */
    private var mCurrentScrolledX = 0

    /**
     * 滑动的总距离
     */
    private var mScrolledX = 0

    /**
     * view 的宽度
     */
    private var mRecyclerViewWidth = 0
    private var mHorizontalHelper: OrientationHelper? = null
    var mFlung = false
    private var mRecyclerView: RecyclerView? = null

    var mOnScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        private var scrolledByUser = false
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                scrolledByUser = true
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolledByUser) {
                scrolledByUser = false
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            mScrolledX += dx
            if (scrolledByUser) {
                mCurrentScrolledX += dx
            }
        }
    }

    /**
     * 滑动的距离 如果值未0 是更具手指移动
     * 0  是x
     * 1  y
     *
     * @param layoutManager
     * @param targetView
     * @return
     */
    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
            out[1] = 0
        } else if (layoutManager.canScrollVertically()) {
            out[0] = 0
            out[1] = 0
        }
        LogUtils.e4Debug("calculateDistanceToFinalSnap              " + out[0])
        return out
    }

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        recyclerView!!.addOnScrollListener(mOnScrollListener)
        recyclerView.post { mRecyclerViewWidth = recyclerView.width }
    }

    /**
     * 找找到要对齐的View
     * null 保证不冲突 依赖   findTargetSnapPosition 坐位滑动判断
     *
     * @param layoutManager
     * @return
     */
    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        LogUtils.e4Debug("findSnapView    mFlung    $mFlung")
        if (mFlung) {
            resetCurrentScrolled()
            mFlung = false
            return null
        }
        if (layoutManager == null) {
            return null
        }
        val targetPosition = targetPosition
        if (targetPosition == RecyclerView.NO_POSITION) {
            return null
        }
        LogUtils.e4Debug("findSnapView   $targetPosition")
        val smoothScroller: SmoothScroller? = createScroller(layoutManager)
        //通过setTargetPosition()方法设置滚动器的滚动目标位置
        smoothScroller!!.targetPosition = targetPosition
        //利用layoutManager启动平滑滚动器，开始滚动到目标位置
        layoutManager.startSmoothScroll(smoothScroller)
        return null
    }

    /**
     * 找出需要对齐的 ItemView 在 Adapter 中的位置
     *
     * @param layoutManager
     * @param i
     * @param i1
     * @return
     */
    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, i: Int, i1: Int): Int {
        val targetPosition = targetPosition
        mFlung = targetPosition != RecyclerView.NO_POSITION
        LogUtils.e4Debug("findTargetSnapPosition     $targetPosition           mFlung $mFlung")
        return targetPosition
    }

    /**
     * 创建滑动
     *
     * @param layoutManager
     * @return
     */
    override fun createScroller(layoutManager: RecyclerView.LayoutManager): LinearSmoothScroller? {
        return if (layoutManager !is ScrollVectorProvider) {
            null
        } else object : LinearSmoothScroller(mRecyclerView!!.context) {
            override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) {
                val snapDistances = calculateDistanceToFinalSnap(mRecyclerView!!.layoutManager!!, targetView)
                val dx = snapDistances!![0]
                val dy = snapDistances[1]
                val time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)))
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator)
                }
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }
        }
    }

    private fun resetCurrentScrolled() {
        mCurrentScrolledX = 0
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mHorizontalHelper == null || mHorizontalHelper!!.layoutManager !== layoutManager) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper
    }

    /**
     * 总距离/ 当前滑动的 当前页数
     *
     * @return
     */
    private val targetPosition: Int
        private get() {
            LogUtils.e4Debug(" getTargetPosition, mScrolledX: $mScrolledX, mCurrentScrolledX: $mCurrentScrolledX    ,mRecyclerViewWidth   $mRecyclerViewWidth")
            val page: Int
            page = if (mCurrentScrolledX > 0) {
                mScrolledX / mRecyclerViewWidth + 1
            } else if (mCurrentScrolledX < 0) {
                mScrolledX / mRecyclerViewWidth
            } else {
                RecyclerView.NO_POSITION
            }
            resetCurrentScrolled()
            return if (page == RecyclerView.NO_POSITION) {
                RecyclerView.NO_POSITION
            } else {
                page * itemCount
            }
        }

    private fun distanceToStart(targetView: View, orientationHelper: OrientationHelper?): Int {
        return orientationHelper!!.getDecoratedStart(targetView) - orientationHelper.startAfterPadding
    }

    companion object {
        /**
         * SnapHelper中该值为100，这里改为150  然滑动速度变慢
         */
        private const val MILLISECONDS_PER_INCH = 150f
    }

}