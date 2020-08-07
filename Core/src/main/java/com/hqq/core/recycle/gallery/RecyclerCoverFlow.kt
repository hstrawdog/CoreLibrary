package com.hqq.core.recycle.gallery

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.recycle.gallery.CoverFlowLayoutManger.OnSelected

/**
 * 继承RecyclerView重写[.getChildDrawingOrder]对Item的绘制顺序进行控制
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @version V1.0
 * @Datetime 2017-04-18
 */
class RecyclerCoverFlow : RecyclerView {
    /**
     * 按下的X轴坐标
     */
    private var mDownX = 0f

    /**
     * 布局器构建者
     */
    private var mManagerBuilder: CoverFlowLayoutManger.Builder? = null

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {
        init()
    }

    private fun init() {
        createManageBuilder()
        layoutManager = mManagerBuilder!!.build()
        //开启重新排序
        isChildrenDrawingOrderEnabled = true
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    /**
     * 创建布局构建器
     */
    private fun createManageBuilder() {
        if (mManagerBuilder == null) {
            mManagerBuilder = CoverFlowLayoutManger.Builder()
        }
    }

    /**
     * 设置是否为普通平面滚动
     *
     * @param isFlat true:平面滚动；false:叠加缩放滚动
     */
    fun setFlatFlow(isFlat: Boolean) {
        createManageBuilder()
        mManagerBuilder!!.setFlat(isFlat)
        layoutManager = mManagerBuilder!!.build()
    }

    /**
     * 设置Item灰度渐变
     *
     * @param greyItem true:Item灰度渐变；false:Item灰度不变
     */
    fun setGreyItem(greyItem: Boolean) {
        createManageBuilder()
        mManagerBuilder!!.setGreyItem(greyItem)
        layoutManager = mManagerBuilder!!.build()
    }

    /**
     * 设置Item灰度渐变
     *
     * @param alphaItem true:Item半透渐变；false:Item透明度不变
     */
    fun setAlphaItem(alphaItem: Boolean) {
        createManageBuilder()
        mManagerBuilder!!.setAlphaItem(alphaItem)
        layoutManager = mManagerBuilder!!.build()
    }

    /**
     * 设置Item的间隔比例
     *
     * @param intervalRatio Item间隔比例。
     * 即：item的宽 x intervalRatio
     */
    fun setIntervalRatio(intervalRatio: Float) {
        createManageBuilder()
        mManagerBuilder!!.setIntervalRatio(intervalRatio)
        layoutManager = mManagerBuilder!!.build()
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        require(layout is CoverFlowLayoutManger) { "The layout manager must be CoverFlowLayoutManger" }
        super.setLayoutManager(layout)
    }

    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        var center = (coverFlowLayout.getCenterPosition()
                - coverFlowLayout.getFirstVisiblePosition()) //计算正在显示的所有Item的中间位置
        if (center < 0) {
            center = 0
        } else if (center > childCount) {
            center = childCount
        }
        val order: Int
        order = if (i == center) {
            childCount - 1
        } else if (i > center) {
            center + childCount - 1 - i
        } else {
            i
        }
        return order
    }

    /**
     * 获取LayoutManger，并强制转换为CoverFlowLayoutManger
     */
    val coverFlowLayout: CoverFlowLayoutManger?
        get() = layoutManager as CoverFlowLayoutManger?

    /**
     * 获取被选中的Item位置
     */
    val selectedPos: Int
        get() = coverFlowLayout.getSelectedPos()

    /**
     * 设置选中监听
     *
     * @param l 监听接口
     */
    fun setOnItemSelectedListener(l: OnSelected?) {
        coverFlowLayout!!.setOnSelectedListener(l)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = ev.x
                //设置父类不拦截滑动事件
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> if (ev.x > mDownX && coverFlowLayout.getCenterPosition() == 0 ||
                    ev.x < mDownX && coverFlowLayout.getCenterPosition() ==
                    coverFlowLayout!!.itemCount - 1) {

                //如果是滑动到了最前和最后，开放父类滑动事件拦截
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                //滑动到中间，设置父类不拦截滑动事件
                parent.requestDisallowInterceptTouchEvent(true)
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}