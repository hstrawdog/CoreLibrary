package com.hqq.core.recycle.gallery

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Rect
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

/**
 * Cover Flow布局类
 *
 * 通过重写LayoutManger布局方法[.onLayoutChildren]
 * 对Item进行布局，并对超出屏幕的Item进行回收
 *
 * 通过重写LayoutManger中的[.scrollHorizontallyBy]
 * 进行水平滚动处理
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @version V1.0
 * @Datetime 2017-04-18
 */
/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.recycler.gallery
 * @FileName :   CoverFlowLayoutManger
 * @Date : 2019/6/22 0022  下午 4:01
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * https://github.com/ChenLittlePing/RecyclerCoverFlow
 */
class CoverFlowLayoutManger(isFlat: Boolean, isGreyItem: Boolean,
                            isAlphaItem: Boolean, cstInterval: Float) : RecyclerView.LayoutManager() {
    /**
     * 最大存储item信息存储数量，
     * 超过设置数量，则动态计算来获取
     */
    private val MAX_RECT_COUNT = 100

    /**
     * 滑动总偏移量
     */
    private var mOffsetAll = 0

    /**
     * Item宽
     */
    private var mDecoratedChildWidth = 0

    /**
     * Item高
     */
    private var mDecoratedChildHeight = 0

    /**
     * Item间隔与item宽的比例
     */
    private var mIntervalRatio = 0.5f

    /**
     * 起始ItemX坐标
     */
    private var mStartX = 0

    /**
     * 起始Item Y坐标
     */
    private var mStartY = 0

    /**
     * 保存所有的Item的上下左右的偏移量信息
     */
    private val mAllItemFrames = SparseArray<Rect>()

    /**
     * 记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
     */
    private val mHasAttachedItems = SparseBooleanArray()

    /**
     * RecyclerView的Item回收器
     */
    private var mRecycle: Recycler? = null

    /**
     * RecyclerView的状态器
     */
    private var mState: RecyclerView.State? = null

    /**
     * 滚动动画
     */
    private var mAnimation: ValueAnimator? = null

    /**
     * 获取被选中Item位置
     */
    /**
     * 正显示在中间的Item
     */
    var selectedPos = 0
        private set

    /**
     * 前一个正显示在中间的Item
     */
    private var mLastSelectPosition = 0

    /**
     * 选中监听
     */
    private var mSelectedListener: OnSelected? = null

    /**
     * 是否为平面滚动，Item之间没有叠加，也没有缩放
     */
    private var mIsFlatFlow = false

    /**
     * 是否启动Item灰度值渐变
     */
    private var mItemGradualGrey = false

    /**
     * 是否启动Item半透渐变
     */
    private var mItemGradualAlpha = false
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        //如果没有item，直接返回
        //跳过preLayout，preLayout主要用于支持动画
        if (itemCount <= 0 || state.isPreLayout) {
            mOffsetAll = 0
            return
        }
        mAllItemFrames.clear()
        mHasAttachedItems.clear()

        //得到子view的宽和高，这边的item的宽高都是一样的，所以只需要进行一次测量
        val scrap = recycler.getViewForPosition(0)
        addView(scrap)
        measureChildWithMargins(scrap, 0, 0)
        //计算测量布局的宽高
        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap)
        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap)
        mStartX = Math.round((horizontalSpace - mDecoratedChildWidth) * 1.0f / 2)
        mStartY = Math.round((verticalSpace - mDecoratedChildHeight) * 1.0f / 2)
        var offset = mStartX.toFloat()

        /**只存[MAX_RECT_COUNT]个item具体位置 */
        var i = 0
        while (i < itemCount && i < MAX_RECT_COUNT) {
            var frame = mAllItemFrames[i]
            if (frame == null) {
                frame = Rect()
            }
            frame[Math.round(offset), mStartY, Math.round(offset + mDecoratedChildWidth)] = mStartY + mDecoratedChildHeight
            mAllItemFrames.put(i, frame)
            mHasAttachedItems.put(i, false)
            //原始位置累加，否则越后面误差越大
            offset = offset + intervalDistance
            i++
        }
        //在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        detachAndScrapAttachedViews(recycler)
        //在为初始化前调用smoothScrollToPosition 或者 scrollToPosition,只会记录位置
        if ((mRecycle == null || mState == null) && selectedPos != 0) {
            //所以初始化时需要滚动到对应位置
            mOffsetAll = calculateOffsetForPosition(selectedPos)
            onSelectedCallBack()
        }
        layoutItems(recycler, state, SCROLL_RIGHT)
        mRecycle = recycler
        mState = state
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler,
                                      state: RecyclerView.State): Int {
        if (mAnimation != null && mAnimation!!.isRunning) {
            mAnimation!!.cancel()
        }
        var travel = dx
        if (dx + mOffsetAll < 0) {
            travel = -mOffsetAll
        } else if (dx + mOffsetAll > maxOffset) {
            travel = (maxOffset - mOffsetAll).toInt()
        }
        mOffsetAll += travel //累计偏移量
        layoutItems(recycler, state, if (dx > 0) SCROLL_RIGHT else SCROLL_LEFT)
        return travel
    }

    /**
     * 布局Item
     *
     * 注意：1，先清除已经超出屏幕的item
     *
     *      2，再绘制可以显示在屏幕里面的item
     */
    private fun layoutItems(recycler: Recycler?,
                            state: RecyclerView.State?, scrollDirection: Int) {
        if (state!!.isPreLayout) {
            return
        }
        val displayFrame = Rect(mOffsetAll, 0, mOffsetAll + horizontalSpace, verticalSpace)
        var position = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            position = getPosition(child!!)
            val rect = getFrame(position)
            if (!Rect.intersects(displayFrame, rect)) { //Item没有在显示区域，就说明需要回收
                removeAndRecycleView(child, recycler!!) //回收滑出屏幕的View
                mHasAttachedItems.delete(position)
            } else { //Item还在显示区域内，更新滑动后Item的位置
                layoutItem(child, rect) //更新Item位置
                mHasAttachedItems.put(position, true)
            }
        }
        if (position == 0) {
            position = selectedPos
        }
        val min = if (position - 50 >= 0) position - 50 else 0
        val max = if (position + 50 < itemCount) position + 50 else itemCount
        for (i in min until max) {
            val rect = getFrame(i)
            if (Rect.intersects(displayFrame, rect) &&
                    !mHasAttachedItems[i]) { //重新加载可见范围内的Item
                val scrap = recycler!!.getViewForPosition(i)
                measureChildWithMargins(scrap, 0, 0)
                if (scrollDirection == SCROLL_LEFT || mIsFlatFlow) { //向左滚动，新增的Item需要添加在最前面
                    addView(scrap, 0)
                } else { //向右滚动，新增的item要添加在最后面
                    addView(scrap)
                }
                layoutItem(scrap, rect) //将这个Item布局出来
                mHasAttachedItems.put(i, true)
            }
        }
    }

    /**
     * 布局Item位置
     *
     * @param child 要布局的Item
     * @param frame 位置信息
     */
    private fun layoutItem(child: View?, frame: Rect) {
        layoutDecorated(child!!,
                frame.left - mOffsetAll,
                frame.top,
                frame.right - mOffsetAll,
                frame.bottom)
        if (!mIsFlatFlow) { //不是平面普通滚动的情况下才进行缩放
            child.scaleX = computeScale(frame.left - mOffsetAll) //缩放
            child.scaleY = computeScale(frame.left - mOffsetAll) //缩放
        }
        if (mItemGradualAlpha) {
            child.alpha = computeAlpha(frame.left - mOffsetAll)
        }
        if (mItemGradualGrey) {
            greyItem(child, frame)
        }
    }

    /**
     * 动态获取Item的位置信息
     *
     * @param index item位置
     * @return item的Rect信息
     */
    private fun getFrame(index: Int): Rect {
        var frame = mAllItemFrames[index]
        if (frame == null) {
            frame = Rect()
            val offset = mStartX + intervalDistance * index //原始位置累加（即累计间隔距离）
            frame[Math.round(offset), mStartY, Math.round(offset + mDecoratedChildWidth)] = mStartY + mDecoratedChildHeight
        }
        return frame
    }

    /**
     * 变化Item的灰度值
     *
     * @param child 需要设置灰度值的Item
     * @param frame 位置信息
     */
    private fun greyItem(child: View?, frame: Rect) {
        val value = computeGreyScale(frame.left - mOffsetAll)
        val cm = ColorMatrix(floatArrayOf(
                value, 0f, 0f, 0f, 120 * (1 - value), 0f, value, 0f, 0f, 120 * (1 - value), 0f, 0f, value, 0f, 120 * (1 - value), 0f, 0f, 0f, 1f, 250 * (1 - value)))
        //            cm.setSaturation(0.9f);

        // Create a paint object with color matrix
        val greyPaint = Paint()
        greyPaint.colorFilter = ColorMatrixColorFilter(cm)

        // Create a hardware layer with the grey paint
        child!!.setLayerType(View.LAYER_TYPE_HARDWARE, greyPaint)
        if (value >= 1) {
            // Remove the hardware layer
            child.setLayerType(View.LAYER_TYPE_NONE, null)
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE ->                 //滚动停止时
                fixOffsetWhenFinishScroll()
            RecyclerView.SCROLL_STATE_DRAGGING -> {
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
            }
            else -> {
            }
        }
    }

    override fun scrollToPosition(position: Int) {
        if (position < 0 || position > itemCount - 1) {
            return
        }
        mOffsetAll = calculateOffsetForPosition(position)
        //如果RecyclerView还没初始化完，先记录下要滚动的位置
        if (mRecycle == null || mState == null) {
            selectedPos = position
        } else {
            layoutItems(mRecycle, mState, if (position > selectedPos) SCROLL_RIGHT else SCROLL_LEFT)
            onSelectedCallBack()
        }
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
        val finalOffset = calculateOffsetForPosition(position)
        //如果RecyclerView还没初始化完，先记录下要滚动的位置
        if (mRecycle == null || mState == null) {
            selectedPos = position
        } else {
            startScroll(mOffsetAll, finalOffset)
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun onAdapterChanged(oldAdapter: RecyclerView.Adapter<*>?, newAdapter: RecyclerView.Adapter<*>?) {
        removeAllViews()
        mRecycle = null
        mState = null
        mOffsetAll = 0
        selectedPos = 0
        mLastSelectPosition = 0
        mHasAttachedItems.clear()
        mAllItemFrames.clear()
    }

    /**
     * 获取整个布局的水平空间大小
     */
    private val horizontalSpace: Int
        private get() = width - paddingRight - paddingLeft

    /**
     * 获取整个布局的垂直空间大小
     */
    private val verticalSpace: Int
        private get() = height - paddingBottom - paddingTop

    /**
     * 获取最大偏移量
     */
    private val maxOffset: Float
        private get() = (itemCount - 1) * intervalDistance

    /**
     * 计算Item缩放系数
     *
     * @param x Item的偏移量
     * @return 缩放系数
     */
    private fun computeScale(x: Int): Float {
        var scale = 1 - Math.abs(x - mStartX) * 1.0f / Math.abs(mStartX + mDecoratedChildWidth / mIntervalRatio)
        if (scale < 0) {
            scale = 0f
        }
        if (scale > 1) {
            scale = 1f
        }
        return scale
    }

    /**
     * 计算Item的灰度值
     *
     * @param x Item的偏移量
     * @return 灰度系数
     */
    private fun computeGreyScale(x: Int): Float {
        val itemMidPos = x + mDecoratedChildWidth / 2.toFloat() //item中点x坐标
        val itemDx2Mid = Math.abs(itemMidPos - horizontalSpace / 2) //item中点距离控件中点距离
        var value = 1 - itemDx2Mid * 1.0f / (horizontalSpace / 2)
        if (value < 0.1) {
            value = 0.1f
        }
        if (value > 1) {
            value = 1f
        }
        value = Math.pow(value.toDouble(), .8).toFloat()
        return value
    }

    /**
     * 计算Item半透值
     *
     * @param x Item的偏移量
     * @return 缩放系数
     */
    private fun computeAlpha(x: Int): Float {
        var alpha = 1 - Math.abs(x - mStartX) * 1.0f / Math.abs(mStartX + mDecoratedChildWidth / mIntervalRatio)
        if (alpha < 0.3f) {
            alpha = 0.3f
        }
        if (alpha > 1) {
            alpha = 1.0f
        }
        return alpha
    }

    /**
     * 计算Item所在的位置偏移
     *
     * @param position 要计算Item位置
     */
    private fun calculateOffsetForPosition(position: Int): Int {
        return Math.round(intervalDistance * position)
    }

    /**
     * 修正停止滚动后，Item滚动到中间位置
     */
    private fun fixOffsetWhenFinishScroll() {
        var scrollN = (mOffsetAll * 1.0f / intervalDistance).toInt()
        val moreDx = mOffsetAll % intervalDistance
        if (moreDx > intervalDistance * 0.5) {
            scrollN++
        }
        val finalOffset = (scrollN * intervalDistance).toInt()
        startScroll(mOffsetAll, finalOffset)
        selectedPos = Math.round(finalOffset * 1.0f / intervalDistance)
    }

    /**
     * 滚动到指定X轴位置
     *
     * @param from X轴方向起始点的偏移量
     * @param to   X轴方向终点的偏移量
     */
    private fun startScroll(from: Int, to: Int) {
        if (mAnimation != null && mAnimation!!.isRunning) {
            mAnimation!!.cancel()
        }
        val direction = if (from < to) SCROLL_RIGHT else SCROLL_LEFT
        mAnimation = ValueAnimator.ofFloat(from.toFloat(), to.toFloat())
        mAnimation.setDuration(500)
        mAnimation.setInterpolator(DecelerateInterpolator())
        mAnimation.addUpdateListener(AnimatorUpdateListener { animation ->
            mOffsetAll = Math.round(animation.animatedValue as Float)
            layoutItems(mRecycle, mState, direction)
        })
        mAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                onSelectedCallBack()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        mAnimation.start()
    }

    /**
     * 获取Item间隔
     */
    private val intervalDistance: Float
        private get() = mDecoratedChildWidth * mIntervalRatio

    /**
     * 计算当前选中位置，并回调
     */
    private fun onSelectedCallBack() {
        selectedPos = Math.round(mOffsetAll / intervalDistance)
        if (mSelectedListener != null && selectedPos != mLastSelectPosition) {
            mSelectedListener!!.onItemSelected(selectedPos)
        }
        mLastSelectPosition = selectedPos
    }

    /**
     * 获取第一个可见的Item位置
     *
     * Note:该Item为绘制在可见区域的第一个Item，有可能被第二个Item遮挡
     */
    val firstVisiblePosition: Int
        get() {
            val displayFrame = Rect(mOffsetAll, 0, mOffsetAll + horizontalSpace, verticalSpace)
            val cur = centerPosition
            for (i in cur - 1 downTo 0) {
                val rect = getFrame(i)
                if (!Rect.intersects(displayFrame, rect)) {
                    return i + 1
                }
            }
            return 0
        }

    /**
     * 获取最后一个可见的Item位置
     *
     * Note:该Item为绘制在可见区域的最后一个Item，有可能被倒数第二个Item遮挡
     */
    val lastVisiblePosition: Int
        get() {
            val displayFrame = Rect(mOffsetAll, 0, mOffsetAll + horizontalSpace, verticalSpace)
            val cur = centerPosition
            for (i in cur + 1 until itemCount) {
                val rect = getFrame(i)
                if (!Rect.intersects(displayFrame, rect)) {
                    return i - 1
                }
            }
            return cur
        }

    /**
     * 获取可见范围内最大的显示Item个数
     */
    val maxVisibleCount: Int
        get() {
            val oneSide = ((horizontalSpace - mStartX) / intervalDistance).toInt()
            return oneSide * 2 + 1
        }

    /**
     * 获取中间位置
     *
     * Note:该方法主要用于[RecyclerCoverFlow.]判断中间位置
     *
     * 如果需要获取被选中的Item位置，调用[.getSelectedPos]
     */
    val centerPosition: Int
        get() {
            var pos = (mOffsetAll / intervalDistance).toInt()
            val more = (mOffsetAll % intervalDistance).toInt()
            if (more > intervalDistance * 0.5f) {
                pos++
            }
            return pos
        }

    /**
     * 设置选中监听
     *
     * @param l 监听接口
     */
    fun setOnSelectedListener(l: OnSelected?) {
        mSelectedListener = l
    }

    /**
     * 选中监听接口
     */
    interface OnSelected {
        /**
         * 监听选中回调
         *
         * @param position 显示在中间的Item的位置
         */
        fun onItemSelected(position: Int)
    }

    class Builder {
        var isFlat = false
        var isGreyItem = false
        var isAlphaItem = false
        var cstIntervalRatio = -1f
        fun setFlat(flat: Boolean): Builder {
            isFlat = flat
            return this
        }

        fun setGreyItem(greyItem: Boolean): Builder {
            isGreyItem = greyItem
            return this
        }

        fun setAlphaItem(alphaItem: Boolean): Builder {
            isAlphaItem = alphaItem
            return this
        }

        fun setIntervalRatio(ratio: Float): Builder {
            cstIntervalRatio = ratio
            return this
        }

        fun build(): CoverFlowLayoutManger {
            return CoverFlowLayoutManger(isFlat, isGreyItem,
                    isAlphaItem, cstIntervalRatio)
        }
    }

    companion object {
        /**
         * 滑动的方向：左
         */
        private const val SCROLL_LEFT = 1

        /**
         * 滑动的方向：右
         */
        private const val SCROLL_RIGHT = 2
    }

    init {
        mIsFlatFlow = isFlat
        mItemGradualGrey = isGreyItem
        mItemGradualAlpha = isAlphaItem
        if (cstInterval >= 0) {
            mIntervalRatio = cstInterval
        } else {
            if (mIsFlatFlow) {
                mIntervalRatio = 1.1f
            }
        }
    }
}