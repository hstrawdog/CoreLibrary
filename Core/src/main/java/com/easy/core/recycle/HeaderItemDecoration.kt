package com.easy.core.recycle

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

/**
 * @Author : huangqiqiang
 * @Package : com.shangwenwan.sww.activity.store.index
 * @FileName :   HeaderItemDecoration
 * @Date : 2019/12/11 0011  下午 3:46
 * @Email : qiqiang213@gmail.com
 * @Descrive : 悬停的解决方案
 * https://stackoverflow.com/questions/32949971/how-can-i-make-sticky-headers-in-recyclerview-without-external-lib?answertab=votes#tab-top
 */
class HeaderItemDecoration(recyclerView: RecyclerView, private val mListener: StickyHeaderInterface) : ItemDecoration() {
    private var mStickyHeaderHeight = 0
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val topChild = parent.getChildAt(0) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }
        val currentHeader = getHeaderViewForItem(topChildPosition, parent) ?: return
        fixLayoutSize(parent, currentHeader)
        val contactPoint = currentHeader.bottom
        val childInContact = getChildInContact(parent, contactPoint)
        if (childInContact != null) {
            if (mListener.isHeader(parent.getChildAdapterPosition(childInContact))) {
                moveHeader(c, currentHeader, childInContact);
            } else {
                drawHeader(c, currentHeader);
            }
        }
        drawHeader(c, currentHeader)
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View? {
        val headerPosition = mListener.getHeaderPositionForItem(itemPosition)
        if (headerPosition >= 0) {
            val layoutResId = mListener.getHeaderLayout(headerPosition)
            if (layoutResId != 0) {
                val header = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
                mListener.bindHeaderData(header, headerPosition)
                return header
            }
        }
        return null
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0f, nextHeader.top - currentHeader.height.toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint) {
                if (child.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    /**
     * Properly measures and layouts the top sticky header.
     *
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec = MeasureSpec.makeMeasureSpec(parent.width, MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(parent.height, MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)
        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight.also { mStickyHeaderHeight = it })
    }

    interface StickyHeaderInterface {
        /**
         * This method gets called by [HeaderItemDecoration] to fetch the position of the header item in the adapter
         * that is used for (represents) item at specified position.
         *
         * @param itemPosition int. Adapter's position of the item for which to do the search of the position of the header item.
         * @return int. Position of the header item in the adapter.
         */
        fun getHeaderPositionForItem(itemPosition: Int): Int

        /**
         * This method gets called by [HeaderItemDecoration] to get layout resource id for the header item at specified adapter's position.
         *
         * @param headerPosition int. Position of the header item in the adapter.
         * @return int. Layout resource id.
         */
        @LayoutRes
        fun getHeaderLayout(headerPosition: Int): Int

        /**
         * This method gets called by [HeaderItemDecoration] to setup the header View.
         *
         * @param header         View. Header to set the data on.
         * @param headerPosition int. Position of the header item in the adapter.
         */
        fun bindHeaderData(header: View?, headerPosition: Int)

        /**
         * This method gets called by [HeaderItemDecoration] to verify whether the item represents a header.
         *
         * @param itemPosition int.
         * @return true, if item at the specified adapter's position represents a header.
         */
        fun isHeader(itemPosition: Int): Boolean
    }

    init {

        // On Sticky Header Click
        recyclerView.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent): Boolean {
                return if (motionEvent.y <= mStickyHeaderHeight) {
                    // Handle the clicks on the header here ...
                    true
                } else false
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }
}