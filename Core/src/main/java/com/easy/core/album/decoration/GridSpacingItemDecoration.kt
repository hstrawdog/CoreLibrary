package com.easy.core.album.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * @author: huangqiqiang
 * 在此写用途
 * @FileName:
 * ${PACKAGE_NAME}.${NAME}.java
 * @emain: 593979591@qq.com
 * @date: ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE}
 * @version V1.0 <描述当前版本功能>
</描述当前版本功能> */
class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int,
                                private val includeEdge: Boolean) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            //            if (position >= spanCount) {
//                outRect.top = spacing;
//            }
            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        }
    }
}