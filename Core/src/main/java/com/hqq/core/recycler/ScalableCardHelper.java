package com.hqq.core.recycler;

import android.graphics.Rect;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.testapp
 * @FileName :   ScalableCardHelper
 * @Date : 2018/12/20 0020  下午 5:21
 * @Descrive :
 * @Email :
 * https://github.com/yjwfn/recyclerview-gallery
 * 实现卡片布局
 */
public class ScalableCardHelper {

    private float STAY_SCALE = 0.9f;

    private String TAG = "ScalableCardHelper";
    private PagerSnapHelper mPagerSnapHelper = new PagerSnapHelper();
    private RecyclerView mRecyclerView;
    private OnPageChangeListener mOnPageChangeListenerWeakReference;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            pageScrolled();
        }
    };


    public ScalableCardHelper(OnPageChangeListener pageChangeListener) {
        if (pageChangeListener != null) {
            this.mOnPageChangeListenerWeakReference = (pageChangeListener);
        }
    }

    public ScalableCardHelper(float STAY_SCALE, OnPageChangeListener pageChangeListener) {
        this.mOnPageChangeListenerWeakReference =(pageChangeListener);
        this.STAY_SCALE = STAY_SCALE;

    }

    public ScalableCardHelper() {
        this(null);
    }

    public ScalableCardHelper(float STAY_SCALE) {
        this.STAY_SCALE = STAY_SCALE;
    }

    private void pageScrolled() {
        if (mRecyclerView == null || mRecyclerView.getChildCount() == 0) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

        View snapingView = mPagerSnapHelper.findSnapView(layoutManager);
        int snapingViewPosition = mRecyclerView.getChildAdapterPosition(snapingView);
        View leftSnapingView = layoutManager.findViewByPosition(snapingViewPosition - 1);
        View rightSnapingView = layoutManager.findViewByPosition(snapingViewPosition + 1);


        float leftSnapingOffset = calculateOffset(mRecyclerView, leftSnapingView);
        float rightSnapingOffset = calculateOffset(mRecyclerView, rightSnapingView);
        float currentSnapingOffset = calculateOffset(mRecyclerView, snapingView);

        if (snapingView != null) {
            snapingView.setScaleX(currentSnapingOffset);
            snapingView.setScaleY(currentSnapingOffset);
        }

        if (leftSnapingView != null) {
            leftSnapingView.setScaleX(leftSnapingOffset);
            leftSnapingView.setScaleY(leftSnapingOffset);
        }

        if (rightSnapingView != null) {
            rightSnapingView.setScaleX(rightSnapingOffset);
            rightSnapingView.setScaleY(rightSnapingOffset);
        }


        if (snapingView != null && currentSnapingOffset >= 1) {
            OnPageChangeListener listener = mOnPageChangeListenerWeakReference != null ? mOnPageChangeListenerWeakReference: null;
            if (listener != null) {
                listener.onPageSelected(snapingViewPosition);
            }
        }

        Log.d(TAG, String.format("left: %f, right: %f, current: %f", leftSnapingOffset, rightSnapingOffset, currentSnapingOffset));
    }


    public int getCurrentPage() {
        View page = mPagerSnapHelper.findSnapView(mRecyclerView.getLayoutManager());
        if (page == null) {
            return -1;
        }

        return mRecyclerView.getChildAdapterPosition(page);
    }


    public void attachToRecyclerView(final RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mPagerSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(mOnScrollListener);
        recyclerView.addItemDecoration(new ScalableCardItemDecoration());
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                pageScrolled();
            }
        });
    }

    public void detachFromRecyclerView(RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.removeOnScrollListener(mOnScrollListener);
        }
        this.mRecyclerView = null;
    }

    /**
     * 通过计算{@code view}中间点与{@link RecyclerView}的中间点的距离，算出{@code view}的偏移量。
     *
     * @param view view
     * @return
     */
    private float calculateOffset(RecyclerView recyclerView, View view) {
        if (view == null) {
            return -1;
        }


        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        boolean isVertical = layoutManager.canScrollVertically();
        int viewStart = isVertical ? view.getTop() : view.getLeft();
        int viewEnd = isVertical ? view.getBottom() : view.getRight();
        int centerX = isVertical ? recyclerView.getHeight() / 2 : recyclerView.getWidth() / 2;
        int childCenter = (viewStart + viewEnd) / 2;
        int distance = Math.abs(childCenter - centerX);

        if (distance > centerX) {
            return STAY_SCALE;
        }

        float offset = 1.f - (distance / (float) centerX);
        return (1.f - STAY_SCALE) * offset + STAY_SCALE;
    }


    public interface OnPageChangeListener {
        void onPageSelected(int position);
    }


    private static class ScalableCardItemDecoration extends RecyclerView.ItemDecoration {


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
            int position = holder.getAdapterPosition() == RecyclerView.NO_POSITION ? holder.getOldPosition() : holder.getAdapterPosition();
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            int itemCount = layoutManager.getItemCount();

            if (position != 0 && position != itemCount - 1) {
                return;
            }

            int peekWidth = getPeekWidth(parent, view);
            boolean isVertical = layoutManager.canScrollVertically();
            //移除item时adapter position为-1。

            if (isVertical) {
                if (position == 0) {
                    outRect.set(0, peekWidth, 0, 0);
                } else if (position == itemCount - 1) {
                    outRect.set(0, 0, 0, peekWidth);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            } else {
                if (position == 0) {
                    outRect.set(peekWidth, 0, 0, 0);
                } else if (position == itemCount - 1) {
                    outRect.set(0, 0, peekWidth, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

    public static int getPeekWidth(RecyclerView recyclerView, View itemView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        boolean isVertical = layoutManager.canScrollVertically();
        int position = recyclerView.getChildAdapterPosition(itemView);
        //TODO RecyclerView使用wrap_content时，获取的宽度可能会是0。
        int parentWidth = recyclerView.getMeasuredWidth();
        //有时会拿到0
        int parentHeight = recyclerView.getMeasuredHeight();
        parentWidth = parentWidth == 0 ? recyclerView.getWidth() : parentWidth;
        parentHeight = parentHeight == 0 ? recyclerView.getHeight() : parentHeight;
        int parentEnd = isVertical ? parentHeight : parentWidth;
        int parentCenter = parentEnd / 2;

        int itemSize = isVertical ? itemView.getMeasuredHeight() : itemView.getMeasuredWidth();

        if (itemSize == 0) {

            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            int widthMeasureSpec =
                    RecyclerView.LayoutManager.getChildMeasureSpec(parentWidth,
                            layoutManager.getWidthMode(),
                            recyclerView.getPaddingLeft() + recyclerView.getPaddingRight(),
                            layoutParams.width, layoutManager.canScrollHorizontally());

            int heightMeasureSpec =
                    RecyclerView.LayoutManager.getChildMeasureSpec(parentHeight,
                            layoutManager.getHeightMode(),
                            recyclerView.getPaddingTop() + recyclerView.getPaddingBottom(),
                            layoutParams.height, layoutManager.canScrollVertically());


            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            itemSize = isVertical ? itemView.getMeasuredHeight() : itemView.getMeasuredWidth();
        }


        /*
            计算ItemDecoration的大小，确保插入的大小正好使view的start + itemSize / 2等于parentCenter。
         */
        int startOffset = parentCenter - itemSize / 2;
        int endOffset = parentEnd - (startOffset + itemSize);

        return position == 0 ? startOffset : endOffset;
    }

}