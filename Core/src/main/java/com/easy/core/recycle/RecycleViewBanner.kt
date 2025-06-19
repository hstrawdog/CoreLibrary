package com.easy.core.recycle

import android.content.Context
import android.content.res.TypedArray
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.easy.core.R
import com.easy.core.recycle.adapter.RecycleBannerAdapter
import com.easy.core.recycle.indicator.CircleIndicatorView
import com.easy.core.recycle.indicator.HollowCircleIndicatorView
import com.easy.core.recycle.indicator.IndicatorView
import com.easy.core.recycle.indicator.RectangleIndicatorView
import com.easy.core.utils.ScreenUtils

class RecycleViewBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
                                                 ) : FrameLayout(context, attrs, defStyleAttr) {

    private var mInterval = 3000
    private var isShowIndicator = true
    private var isShowTip = true
    private var isAutoPlaying = true
    private var mIsUnlimited = true
    private var selectSpeed = 0f

    private var recyclerView: RecyclerView = RecyclerView(context)
    private var indicatorView: IndicatorView? = null
    private var currentIndex = 0
    private var isPlaying = false
    private var isTouched = false

    private val mData = ArrayList<Any>()
    private var mAdapter: RecycleBannerAdapter<Any>? = null
    private val mHandlers = Handler()

    var recycleViewBannerCurrentListener: RecycleViewBannerCurrentListener? = null

    private val playTask = object : Runnable {
        override fun run() {
            recyclerView.smoothScrollToPosition(++currentIndex)
            switchIndicator()
            mHandlers.postDelayed(this, mInterval.toLong())
        }
    }

    init {
        val margin = initAttributeSet(context, attrs)
        initRecycleView()
        addView(recyclerView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        indicatorView?.let {
            val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.BOTTOM
                setMargins(margin, margin, margin, margin)
            }
            addView(it, params)
        }
        if (isInEditMode) initEditMode()
    }

    private fun initAttributeSet(context: Context, attrs: AttributeSet?): Int {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
        mInterval = a.getInt(R.styleable.BannerLayout_rvb_interval, 3000)
        isShowIndicator = a.getBoolean(R.styleable.BannerLayout_rvb_showIndicator, true)
        isShowTip = a.getBoolean(R.styleable.BannerLayout_rvb_isShowTip, true)
        isAutoPlaying = a.getBoolean(R.styleable.BannerLayout_rvb_autoPlaying, true)
        mIsUnlimited = true

        val margin = a.getDimensionPixelSize(
            R.styleable.BannerLayout_rvb_indicatorMargin,
            ScreenUtils.dip2px(context, 8f)
                                            )

        indicatorView = when (a.getInt(R.styleable.BannerLayout_rvb_tip_type, 0)) {
            1 -> HollowCircleIndicatorView(context)
            2 -> RectangleIndicatorView(context)
            else -> CircleIndicatorView(context)
        }

        initIndicatorStyle(a)

        mAdapter = RecycleBannerAdapter()
        mAdapter?.isUnlimited = mIsUnlimited
        mAdapter?.isShowTip = isShowTip

        a.recycle()
        return margin
    }

    private fun initIndicatorStyle(a: TypedArray) {
        selectSpeed = a.getFloat(R.styleable.BannerLayout_rvb_selectSpeed, 0f)
        indicatorView?._selectSpeed = selectSpeed
        indicatorView?.setSelectColor(a.getColor(R.styleable.BannerLayout_rvb_indicatorSelectedColor, -0x78a7d2))
        indicatorView?.setDefColor(a.getColor(R.styleable.BannerLayout_rvb_indicatorUnselectedColor, -0x1))
        val radius = a.getDimensionPixelSize(R.styleable.BannerLayout_rvb_indicatorRadius, 0)
        if (radius > 0 && indicatorView is CircleIndicatorView) {
            (indicatorView as CircleIndicatorView).setDefRadius(radius)
        }
        indicatorView?.setModel(a.getInt(R.styleable.BannerLayout_rvb_indicatorGravity, 3))
    }

    private fun initRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(recyclerView)
        recyclerView.adapter = mAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val first = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    currentIndex = first
                    if (isTouched) {
                        isTouched = false
                        switchIndicator()
                    }
                }
            }
        })
    }

    private fun initEditMode() {
        repeat(3) { mData.add("") }
        createIndicators()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                setPlaying(false)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (!isPlaying) {
                    isTouched = true
                    setPlaying(true)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setPlaying(true)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        setPlaying(false)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        if (visibility != View.VISIBLE) {
            setPlaying(false)
        } else {
            setPlaying(true)
        }
        super.onWindowVisibilityChanged(visibility)
    }

    private fun setPlaying(playing: Boolean) {
        if (isAutoPlaying) {
            if (!isPlaying && playing && mAdapter?.itemCount ?: 0 > 1) {
                mHandlers.postDelayed(playTask, mInterval.toLong())
                isPlaying = true
            } else if (isPlaying && !playing) {
                mHandlers.removeCallbacksAndMessages(null)
                isPlaying = false
            }
        }
    }

    fun setRvBannerData(data: MutableList<Any>) {
        setPlaying(false)
        mData.clear()
        mData.addAll(data)
        mAdapter?.data = mData
        currentIndex = if (mIsUnlimited && mData.size > 1) Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % mData.size) else 0
        recyclerView.scrollToPosition(currentIndex)
        if (isShowIndicator && mData.size > 1) {
            createIndicators()
        }
        if (mData.size > 1) setPlaying(true)
        recycleViewBannerCurrentListener?.onCurrentPosition(currentIndex % mData.size)
    }

    private fun createIndicators() {
        indicatorView?.setPageColumn(mData.size)
    }

    private fun switchIndicator() {
        if (mData.isEmpty()) return
        val pos = currentIndex % mData.size
        indicatorView?.setCurrentItem(pos)
        recycleViewBannerCurrentListener?.onCurrentPosition(pos)
    }

    fun setImageScaleType(scaleType: ImageView.ScaleType) {
        mAdapter?.scaleType = scaleType
    }

    fun setRvAutoPlaying(autoPlay: Boolean) {
        isAutoPlaying = autoPlay
    }

    fun isShowIndicator(show: Boolean) {
        isShowIndicator = show
    }

    fun setIndicatorInterval(millisecond: Int) {
        mInterval = millisecond
    }

    fun setUnlimited(unlimited: Boolean) {
        mIsUnlimited = unlimited
        mAdapter?.isUnlimited = unlimited
    }

    fun smoothScrollToPosition(position: Int) {
        recyclerView.smoothScrollToPosition(position)
    }

    fun scrollToPosition(position: Int) {
        recyclerView.scrollToPosition(position)
    }

    fun setCurrentIndex(index: Int) {
        currentIndex = index
    }

    fun setOnRvBannerClickListener(listener: RecycleViewBannerClickListener) {
        mAdapter?.setOnRvBannerClickListener(listener)
    }

    fun setRecycleViewBannerChangeListener(listener: RecycleViewBannerChangeListener<Any>) {
        mAdapter?.setRecycleViewBannerChangeListener(listener)
    }


}
