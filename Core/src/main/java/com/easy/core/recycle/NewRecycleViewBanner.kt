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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import  com.easy.core.R
import com.easy.core.recycle.adapter.NewRecycleBannerAdapter
import com.easy.core.recycle.adapter.RecycleBannerAdapter
import com.easy.core.recycle.indicator.CircleIndicatorView
import com.easy.core.recycle.indicator.HollowCircleIndicatorView
import com.easy.core.recycle.indicator.IndicatorView
import com.easy.core.recycle.indicator.RectangleIndicatorView
import  com.easy.core.utils.ScreenUtils

/**
 * https://github.com/loonggg/RecyclerViewBanner
 * https://github.com/renjianan/RecyclerBanner
 *
 * @Author : huangqiqiang
 * @Package : com.core.library.banner
 * @FileName :   RecyclerViewBanner
 * @Date : 2018/8/6 0006  下午 4:09
 * @Email :  593979591@qq.com
 * @Describe :
 * 待完成
 * 1.多种指示器配置
 * 2. 考虑再次封装 adapter  不应该绑定在这边 需要解耦
 * 3. 不能与BRVAH 搭配使用
 */
class NewRecycleViewBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                     defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    /**
     * 轮播间隔时间
     */
    private var mInterval = 0

    /**
     * 是否显示指示器
     */
    private var isShowIndicator = false

    /**
     * 获取RecyclerView实例，便于满足自定义[RecyclerView.ItemAnimator]或者[RecyclerView.Adapter]的需求
     *
     * @return RecyclerView实例
     */
    var recyclerView: RecyclerView? = null
        private set
    private var mLinearLayout: IndicatorView? = null

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
     * 倍数
     */
    private var selectSpeed = 0f

    var recycleViewBannerCurrentListener: RecycleViewBannerCurrentListener? = null

    /**
     * 是否无限轮播
     */
    private var mIsUnlimited = true
    private var mAdapter: NewRecycleBannerAdapter<*, *>? = null
    private val playTask: Runnable = object : Runnable {
        override fun run() {
            recyclerView!!.smoothScrollToPosition(++currentIndex)
            val itemCount = mAdapter?.items?.size ?: 0
            if (itemCount > 0) {
                switchIndicator(currentIndex % itemCount)
            }
            mHandlers.postDelayed(this, mInterval.toLong())
        }
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val margin = initAttributeSet(context, attrs)
        initRecycleView()
        addView(recyclerView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        val linearLayoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayoutParams.gravity = Gravity.BOTTOM
        linearLayoutParams.setMargins(margin, margin, margin, margin)
        addView(mLinearLayout, linearLayoutParams)
    }

    private fun initRecycleView() {
        // 直接使用卡片布局 貌似 也是可以的
        recyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        // new ScalableCardHelper().attachToRecyclerView(mRecyclerView);
        PagerSnapHelper().attachToRecyclerView(recyclerView)
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
                            val itemCount = mAdapter?.items?.size ?: 0
                            if (itemCount > 0) {
                                switchIndicator(currentIndex % itemCount)
                            }
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
        isAutoPlaying = a.getBoolean(R.styleable.BannerLayout_rvb_autoPlaying, true)
        val margin =
            a.getDimensionPixelSize(R.styleable.BannerLayout_rvb_indicatorMargin, ScreenUtils.dip2px(getContext(), 8f))
        if (recyclerView == null) {
            recyclerView = RecyclerView(context)
            val type = a.getInt(R.styleable.BannerLayout_rvb_tip_type, 0)
            when (type) {
                1 -> {
                    // 空心圆
                    mLinearLayout = HollowCircleIndicatorView(context)
                }
                2 -> {
                    //  长方形
                    mLinearLayout = RectangleIndicatorView(context)
                }
                else -> {
                    // 标准 实心圆
                    mLinearLayout = CircleIndicatorView(context)
                }
            }
        }
        // 初始化 指示器 中的内容
        initIndicator(a)
        a.recycle()
        return margin
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

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility != VISIBLE) {
            setPlaying(false)
        } else {
            setPlaying(true)
        }
    }

    /**
     * 配置 指示器 内容
     *
     * @param a TypedArray
     */
    private fun initIndicator(a: TypedArray) {

        selectSpeed = a.getFloat(R.styleable.BannerLayout_rvb_selectSpeed, 0f)
        // 圆形目前 不支持
        mLinearLayout?._selectSpeed = selectSpeed

        val sd = a.getColor(R.styleable.BannerLayout_rvb_indicatorSelectedColor, -0x78a7d2)
        mLinearLayout!!.setSelectColor(sd)
        val usd = a.getColor(R.styleable.BannerLayout_rvb_indicatorUnselectedColor, -0x1)
        mLinearLayout!!.setDefColor(usd)
        val radius = a.getDimensionPixelSize(R.styleable.BannerLayout_rvb_indicatorRadius, 0)
        if (radius > 0) {
            if (mLinearLayout is CircleIndicatorView) {
                (mLinearLayout as CircleIndicatorView).setDefRadius(radius)
            }
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


    fun setAdapter(adapter: NewRecycleBannerAdapter<*, *>) {
        mAdapter = adapter
        recyclerView?.adapter = mAdapter

        mLinearLayout?.setPageColumn(adapter.items.size)
        mLinearLayout?.invalidate()
        var size = adapter.items.size
        if (size > 1) {
            // 定位到中间位置实现无限滚动效果
            val mid = Int.MAX_VALUE / 2
            val offset = mid - mid % adapter.items.size
            recyclerView?.scrollToPosition(offset)

            if (isShowIndicator) {
                mLinearLayout?.visibility = View.VISIBLE
            } else {
                mLinearLayout?.visibility = View.GONE
            }
            setPlaying(true)
        } else {
            currentIndex = 0
            mAdapter!!.notifyDataSetChanged()
        }

    }

    /**
     * 指示器整体由数据列表容量数量的AppCompatImageView均匀分布在一个横向的LinearLayout中构成
     * 使用AppCompatImageView的好处是在Fragment中也使用Compat相关属性
     */
    private fun createIndicators() {
        mLinearLayout!!.setPageColumn(0)
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
    private fun switchIndicator(currentIndex: Int) {
        if (isShowIndicator) {
            mLinearLayout!!.setCurrentItem(currentIndex)
        }
        recycleViewBannerCurrentListener?.onCurrentPosition(currentIndex)

    }

    /**
     * 是否 无限轮播
     *
     * @param unlimited boolean
     */
    fun setUnlimited(unlimited: Boolean) {
        mIsUnlimited = unlimited
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

    /**
     *  当前选项
     */
    interface RecycleViewBannerCurrentListener {
        fun onCurrentPosition(position: Int)
    }

    init {
        init(context, attrs)
    }
}