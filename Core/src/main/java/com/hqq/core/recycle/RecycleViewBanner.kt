package com.hqq.core.recycle

import android.content.Context
import android.content.res.TypedArray
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.R
import com.hqq.core.recycle.adapter.RecycleBannerAdapter
import com.hqq.core.recycle.indicator.CircleIndicatorView
import com.hqq.core.utils.ScreenUtils

/**
 * https://github.com/loonggg/RecyclerViewBanner
 * https://github.com/renjianan/RecyclerBanner
 *
 * @Author : huangqiqiang
 * @Package : com.core.library.banner
 * @FileName :   RecyclerViewBanner
 * @Date : 2018/8/6 0006  下午 4:09
 * @Email :  593979591@qq.com
 * @Descrive :
 * 待完成
 * 1.多种指示器配置
 * 2. 考虑再次封装 adapter  不应该绑定在这边 需要解耦
 */
class RecycleViewBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    /**
     * 轮播间隔时间
     */
    private var mInterval = 0

    /**
     * 是否显示指示器
     */
    private var isShowIndicator = false

    /**
     * 是否显示标题
     */
    private var isShowTip = false

    /**
     * 获取RecyclerView实例，便于满足自定义[RecyclerView.ItemAnimator]或者[RecyclerView.Adapter]的需求
     *
     * @return RecyclerView实例
     */
    var recyclerView: RecyclerView? = null
        private set
    private var mLinearLayout: CircleIndicatorView? = null
    private var startX = 0
    private var startY = 0
    private var currentIndex = 0

    /**
     * 是否正在轮播
     */
    private var isPlaying = false
    var mHandlers = Handler()
    private var isTouched = false

    /**
     * 是否自动轮播
     */
    private var isAutoPlaying = true

    /**
     * 是否无限轮播
     */
    private var mIsUnlimited = true
    private var mData: ArrayList<Any> = ArrayList()
    var mLinearLayoutManager: LinearLayoutManager? = null
    private var mAdapter: RecycleBannerAdapter<Any>? = null
    private val playTask: Runnable = object : Runnable {
        override fun run() {
            recyclerView!!.smoothScrollToPosition(++currentIndex)
            switchIndicator()
            mHandlers.postDelayed(this, mInterval.toLong())
        }
    }

    val data: List<*>?
        get() = mData

    private fun init(context: Context, attrs: AttributeSet?) {
        val margin = initAttributeSet(context, attrs)
        initRecycleView()
        addView(recyclerView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT))
        val linearLayoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayoutParams.gravity = Gravity.BOTTOM
        linearLayoutParams.setMargins(margin, margin, margin, margin)
        addView(mLinearLayout, linearLayoutParams)
        initEditMode()
    }

    private fun initRecycleView() {
        // 直接使用卡片布局 貌似 也是可以的
        recyclerView!!.layoutManager = mLinearLayoutManager
        // new ScalableCardHelper().attachToRecyclerView(mRecyclerView);
        PagerSnapHelper().attachToRecyclerView(recyclerView)

        mAdapter?.data = mData
        mAdapter?.isShowTip = isShowTip;

        recyclerView!!.adapter = mAdapter
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val first = (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    val last = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
                    if (first == last && currentIndex != last) {
                        currentIndex = last
                        if (isTouched) {
                            isTouched = false
                            switchIndicator()
                        }
                    }
                }
            }
        })
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @return
     */
    private fun initAttributeSet(context: Context, attrs: AttributeSet?): Int {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
        mInterval = a.getInt(R.styleable.BannerLayout_rvb_interval, 3000)
        isShowIndicator = a.getBoolean(R.styleable.BannerLayout_rvb_showIndicator, true)
        isShowTip = a.getBoolean(R.styleable.BannerLayout_rvb_isShowTip, true)
        isAutoPlaying = a.getBoolean(R.styleable.BannerLayout_rvb_autoPlaying, true)
        val margin = a.getDimensionPixelSize(R.styleable.BannerLayout_rvb_indicatorMargin, ScreenUtils.dip2px(getContext(), 8f))
        if (recyclerView == null) {
            recyclerView = RecyclerView(context)
            mLinearLayout = CircleIndicatorView(context)
            mAdapter = RecycleBannerAdapter<Any>()
            mLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        initIndicator(a)
        a.recycle()
        return margin
    }

    /**
     * 便于在xml中编辑时观察，运行时不执行
     */
    private fun initEditMode() {
        if (isInEditMode) {
            for (i in 0..2) {
                mData!!.add("")
            }
            createIndicators()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        //手动触摸的时候，停止自动播放，根据手势变换
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = ev.x.toInt()
                val moveY = ev.y.toInt()
                val disX = moveX - startX
                val disY = moveY - startY
                val hasMoved = 2 * Math.abs(disX) > Math.abs(disY)
                parent.requestDisallowInterceptTouchEvent(hasMoved)
                if (hasMoved) {
                    setPlaying(false)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> if (!isPlaying) {
                isTouched = true
                setPlaying(true)
            }
            else -> {
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
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            // 停止轮播
            setPlaying(false)
        } else if (visibility == View.VISIBLE) {
            // 开始轮播
            setPlaying(true)
        }
        super.onWindowVisibilityChanged(visibility)
    }

    /**
     * 配置 指示器 内容
     *
     * @param a TypedArray
     */
    private fun initIndicator(a: TypedArray) {
        val sd = a.getColor(R.styleable.BannerLayout_rvb_indicatorSelectedColor, -0x78a7d2)
        mLinearLayout!!.setSelectColor(sd)
        val usd = a.getColor(R.styleable.BannerLayout_rvb_indicatorUnselectedColor, -0x1)
        mLinearLayout!!.setDefColor(usd)
        val radius = a.getDimensionPixelSize(R.styleable.BannerLayout_rvb_indicatorRadius, 0)
        if (radius > 0) {
            mLinearLayout!!.setDefRadius(radius)
        }
        val g = a.getInt(R.styleable.BannerLayout_rvb_indicatorGravity, 3)
        mLinearLayout!!.setModel(g)
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    @Synchronized
    private fun setPlaying(playing: Boolean) {
        if (isAutoPlaying) {
            if (!isPlaying && playing && mAdapter != null && mAdapter!!.itemCount > 2) {
                mHandlers.postDelayed(playTask, mInterval.toLong())
                isPlaying = true
            } else if (isPlaying && !playing) {
                mHandlers.removeCallbacksAndMessages(null)
                isPlaying = false
            }
        }
    }

    /**
     * 设置轮播数据集
     *
     * @param data Banner对象列表
     */
    fun setRvBannerData(data: MutableList<Any>) {
        setPlaying(false)
        // 避免空指针
        if (mData == null) {
            mData = ArrayList()
        }
        mData!!.clear()
        mData!!.addAll(data)
        if (mData!!.size > 1) {
            currentIndex = if (mIsUnlimited) {
                mData!!.size * 100
            } else {
                0
            }
            mAdapter!!.notifyDataSetChanged()
            recyclerView!!.scrollToPosition(currentIndex)
            if (isShowIndicator) {
                createIndicators()
            }
            setPlaying(true)
        } else {
            currentIndex = 0
            mAdapter!!.notifyDataSetChanged()
        }
    }

    fun <T : RecycleBannerAdapter<Any>> setAdapter(adapter: T) {
        mAdapter = adapter
    }

    /**
     * 指示器整体由数据列表容量数量的AppCompatImageView均匀分布在一个横向的LinearLayout中构成
     * 使用AppCompatImageView的好处是在Fragment中也使用Compat相关属性
     */
    private fun createIndicators() {
        mLinearLayout!!.setPageColumn(mData!!.size)
    }

    /**
     * 设置是否禁止滚动播放
     *
     * @param isAutoPlaying true  是自动滚动播放,false 是禁止自动滚动
     */
    fun setRvAutoPlaying(isAutoPlaying: Boolean) {
        this.isAutoPlaying = isAutoPlaying
    }

    /**
     * 设置是否显示指示器导航点
     *
     * @param show 显示
     */
    fun isShowIndicator(show: Boolean) {
        isShowIndicator = show
    }

    /**
     * 设置轮播间隔时间
     *
     * @param millisecond 时间毫秒
     */
    fun setIndicatorInterval(millisecond: Int) {
        mInterval = millisecond
    }

    /**
     * 改变导航指示器
     */
    private fun switchIndicator() {
        if (mData == null || mData!!.size == 0) {
            return
        }
        if (isShowIndicator) {
            mLinearLayout!!.setCurrentItem(currentIndex % mData!!.size)
        }
    }

    /**
     * 是否 无限轮播
     *
     * @param unlimited boolean
     */
    fun setUnlimited(unlimited: Boolean) {
        mIsUnlimited = unlimited
        mAdapter?.isUnlimited = unlimited
    }

    /**
     * 滑动到指定位置
     *
     * @param position int
     */
    fun smoothScrollToPosition(position: Int) {
        recyclerView!!.smoothScrollToPosition(position)
    }

    fun scrollToPosition(position: Int) {
        recyclerView!!.scrollToPosition(position)
    }

    fun setCurrentIndex(currentIndex: Int) {
        this.currentIndex = currentIndex
    }

    fun setOnRvBannerClickListener(onRvBannerClickListener: RecycleViewBannerClickListener<*>) {
        mAdapter!!.setOnRvBannerClickListener(onRvBannerClickListener)
    }

    fun setRecycleViewBannerChangeListener(recycleViewBannerChangeListener: RecycleViewBannerChangeListener<Any>) {
        mAdapter!!.setRecycleViewBannerChangeListener(recycleViewBannerChangeListener)
    }

    interface RecycleViewBannerClickListener<T> {
        /**
         * @param t
         */
        fun onBannerClick(t: Int)
    }

    interface RecycleViewBannerChangeListener<Any> {
        /**
         * @param t
         * @return
         */
        fun getUrl(t: Any): String

        /**
         * @param t
         * @return
         */
        fun getTitle(t: Any): String
    }

    init {
        init(context, attrs)
    }
}