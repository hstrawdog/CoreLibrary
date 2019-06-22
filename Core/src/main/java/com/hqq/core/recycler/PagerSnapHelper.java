package com.hqq.core.recycler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.View;

import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity.banner
 * @FileName :   PagerSnapHelper
 * @Date : 2019/6/18 0018  上午 10:16
 * @Email : qiqiang213@gmail.com
 * @Descrive : 多个item组合分页 只支持横向分页滑动
 */
public class PagerSnapHelper extends SnapHelper {

    /**
     * SnapHelper中该值为100，这里改为150  然滑动速度变慢
     */
    private static final float MILLISECONDS_PER_INCH = 150f;

    /**
     * 当前滑动的距离 判断 左右
     */
    private int mCurrentScrolledX = 0;
    /**
     * 滑动的总距离
     */
    private int mScrolledX = 0;
    /**
     * view 的宽度
     */
    private int mRecyclerViewWidth = 0;
    private OrientationHelper mHorizontalHelper = null;
    boolean mFlung = false;
    private RecyclerView mRecyclerView;
    /**
     * 一页大小
     */
    private int itemCount;

    public PagerSnapHelper(int itemCount) {
        this.itemCount = itemCount;
    }


    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private boolean scrolledByUser = false;

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                scrolledByUser = true;
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolledByUser) {
                scrolledByUser = false;
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            mScrolledX += dx;
            if (scrolledByUser) {
                mCurrentScrolledX += dx;
            }
        }
    };


    /**
     * 滑动的距离 如果值未0 是更具手指移动
     * 0  是x
     * 1  y
     *
     * @param layoutManager
     * @param targetView
     * @return
     */
    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
            out[1] = 0;
        } else if (layoutManager.canScrollVertically()) {
            out[0] = 0;
            out[1] = 0;
        }
        LogUtils.e("calculateDistanceToFinalSnap              " + out[0]);
        return out;
    }

    @Override
    public void attachToRecyclerView(@Nullable final RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(mOnScrollListener);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerViewWidth = recyclerView.getWidth();

            }
        });

    }


    /**
     * 找找到要对齐的View
     * null 保证不冲突 依赖   findTargetSnapPosition 坐位滑动判断
     *
     * @param layoutManager
     * @return
     */
    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        LogUtils.e("findSnapView    mFlung    " + mFlung);
        if (mFlung) {
            resetCurrentScrolled();
            mFlung = false;
            return null;
        }
        if (layoutManager == null) {
            return null;
        }
        int targetPosition = getTargetPosition();
        if (targetPosition == RecyclerView.NO_POSITION) {
            return null;
        }
        LogUtils.e("findSnapView   " + targetPosition);
        RecyclerView.SmoothScroller smoothScroller = createScroller(layoutManager);
        //通过setTargetPosition()方法设置滚动器的滚动目标位置
        smoothScroller.setTargetPosition(targetPosition);
        //利用layoutManager启动平滑滚动器，开始滚动到目标位置
        layoutManager.startSmoothScroll(smoothScroller);

        return null;
    }


    /**
     * 找出需要对齐的 ItemView 在 Adapter 中的位置
     *
     * @param layoutManager
     * @param i
     * @param i1
     * @return
     */
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i, int i1) {
        int targetPosition = getTargetPosition();
        mFlung = (targetPosition != RecyclerView.NO_POSITION);
        LogUtils.e("findTargetSnapPosition     " + targetPosition + "           mFlung " + mFlung);
        return targetPosition;
    }


    /**
     * 创建滑动
     *
     * @param layoutManager
     * @return
     */
    @Override
    @Nullable
    protected LinearSmoothScroller createScroller(final RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(mRecyclerView.getContext()) {
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(), targetView);
                final int dx = snapDistances[0];
                final int dy = snapDistances[1];
                final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator);
                }
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
    }

    private void resetCurrentScrolled() {
        mCurrentScrolledX = 0;
    }


    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null || mHorizontalHelper.getLayoutManager() != layoutManager) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }

    /**
     * 总距离/ 当前滑动的 当前页数
     *
     * @return
     */
    private int getTargetPosition() {
        LogUtils.e(" getTargetPosition, mScrolledX: " + mScrolledX + ", mCurrentScrolledX: " + mCurrentScrolledX + "    ,mRecyclerViewWidth   " + mRecyclerViewWidth);
        int page;
        if (mCurrentScrolledX > 0) {
            page = mScrolledX / mRecyclerViewWidth + 1;
        } else if (mCurrentScrolledX < 0) {
            page = mScrolledX / mRecyclerViewWidth;
        } else {
            page = RecyclerView.NO_POSITION;
        }

        resetCurrentScrolled();
        if (page == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        } else {
            return page * itemCount;
        }

    }


    private int distanceToStart(View targetView, OrientationHelper orientationHelper) {
        return orientationHelper.getDecoratedStart(targetView) - orientationHelper.getStartAfterPadding();
    }


}
