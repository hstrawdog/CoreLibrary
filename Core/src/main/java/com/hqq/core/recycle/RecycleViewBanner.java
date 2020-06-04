package com.hqq.core.recycle;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hqq.core.R;
import com.hqq.core.recycle.adapter.RecycleBannerAdapter;
import com.hqq.core.recycle.indicator.CircleIndicatorView;
import com.hqq.core.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

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
public class RecycleViewBanner extends FrameLayout {
    /**
     * 轮播间隔时间
     */
    private int mInterval;
    /**
     * 是否显示指示器
     */
    private boolean isShowIndicator;
    /**
     * 是否显示标题
     */
    private boolean isShowTip;
    private RecyclerView mRecyclerView;
    private CircleIndicatorView mLinearLayout;
    private int startX, startY, currentIndex;
    /**
     * 是否正在轮播
     */
    private boolean isPlaying;
    private Handler handler = new Handler();
    private boolean isTouched;
    /**
     * 是否自动轮播
     */
    private boolean isAutoPlaying = true;
    /**
     * 是否无限轮播
     */
    private boolean mIsUnlimited = true;
    private List mData = new ArrayList<>();

    LinearLayoutManager mLinearLayoutManager;
    private RecycleBannerAdapter mAdapter;
    private Runnable playTask = new Runnable() {

        @Override
        public void run() {
            mRecyclerView.smoothScrollToPosition(++currentIndex);
            switchIndicator();

            handler.postDelayed(this, mInterval);
        }
    };

    public List getData() {
        return mData;
    }

    public RecycleViewBanner(Context context) {
        this(context, null);
    }

    public RecycleViewBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecycleViewBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int margin = initAttributeSet(context, attrs);
        initRecycleView();


        addView(mRecyclerView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        LayoutParams linearLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.BOTTOM;
        linearLayoutParams.setMargins(margin, margin, margin, margin);
        addView(mLinearLayout, linearLayoutParams);

        initEditMode();

    }

    private void initRecycleView() {
        // 直接使用卡片布局 貌似 也是可以的
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // new ScalableCardHelper().attachToRecyclerView(mRecyclerView);
        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);
        mAdapter.setData(mData);
        mAdapter.setIsShowTip(isShowTip);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (first == last && currentIndex != last) {
                        currentIndex = last;
                        if (isTouched) {
                            isTouched = false;
                            switchIndicator();
                        }
                    }
                }
            }
        });
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @return
     */
    private int initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout);
        mInterval = a.getInt(R.styleable.BannerLayout_rvb_interval, 3000);
        isShowIndicator = a.getBoolean(R.styleable.BannerLayout_rvb_showIndicator, true);
        isShowTip = a.getBoolean(R.styleable.BannerLayout_rvb_isShowTip, true);
        isAutoPlaying = a.getBoolean(R.styleable.BannerLayout_rvb_autoPlaying, true);
        int margin = a.getDimensionPixelSize(R.styleable.BannerLayout_rvb_indicatorMargin, ScreenUtils.dip2px(getContext(), 8));

        if (mRecyclerView == null) {
            mRecyclerView = new RecyclerView(context);
            mLinearLayout = new CircleIndicatorView(context);
            mAdapter = new RecycleBannerAdapter();
            mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }

        initIndicator(a);
        a.recycle();
        return margin;
    }

    /**
     * 便于在xml中编辑时观察，运行时不执行
     */
    private void initEditMode() {
        if (isInEditMode()) {
            for (int i = 0; i < 3; i++) {
                mData.add("");
            }
            createIndicators();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //手动触摸的时候，停止自动播放，根据手势变换
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                int disX = moveX - startX;
                int disY = moveY - startY;
                boolean hasMoved = 2 * Math.abs(disX) > Math.abs(disY);
                getParent().requestDisallowInterceptTouchEvent(hasMoved);
                if (hasMoved) {
                    setPlaying(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!isPlaying) {
                    isTouched = true;
                    setPlaying(true);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == GONE || visibility == INVISIBLE) {
            // 停止轮播
            setPlaying(false);
        } else if (visibility == VISIBLE) {
            // 开始轮播
            setPlaying(true);
        }
        super.onWindowVisibilityChanged(visibility);
    }

    /**
     * 配置 指示器 内容
     *
     * @param a TypedArray
     */
    private void initIndicator(TypedArray a) {

        int sd = a.getColor(R.styleable.BannerLayout_rvb_indicatorSelectedColor, 0xff87582e);
        mLinearLayout.setSelectColor(sd);

        int usd = a.getColor(R.styleable.BannerLayout_rvb_indicatorUnselectedColor, 0xffffffff);
        mLinearLayout.setDefColor(usd);

        int radius = a.getDimensionPixelSize(R.styleable.BannerLayout_rvb_indicatorRadius, 0);

        if (radius > 0) {
            mLinearLayout.setDefRadius(radius);
        }

        int g = a.getInt(R.styleable.BannerLayout_rvb_indicatorGravity, 3);
        mLinearLayout.setModel(g);


    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    private synchronized void setPlaying(boolean playing) {
        if (isAutoPlaying) {
            if (!isPlaying && playing && mAdapter != null && mAdapter.getItemCount() > 2) {
                handler.postDelayed(playTask, mInterval);
                isPlaying = true;
            } else if (isPlaying && !playing) {
                handler.removeCallbacksAndMessages(null);
                isPlaying = false;
            }
        }
    }

    /**
     * 设置轮播数据集
     *
     * @param data Banner对象列表
     */
    public <T> void setRvBannerData(List<T> data) {
        setPlaying(false);
        // 避免空指针
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.clear();
        mData.addAll(data);
        if (mData.size() > 1) {
            if (mIsUnlimited) {
                currentIndex = mData.size() * 100;
            } else {
                currentIndex = 0;
            }
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(currentIndex);
            if (isShowIndicator) {
                createIndicators();
            }
            setPlaying(true);
        } else {
            currentIndex = 0;
            mAdapter.notifyDataSetChanged();
        }
    }


    public <T extends RecycleBannerAdapter> void setAdapter(T adapter) {
        mAdapter = adapter;
    }


    /**
     * 指示器整体由数据列表容量数量的AppCompatImageView均匀分布在一个横向的LinearLayout中构成
     * 使用AppCompatImageView的好处是在Fragment中也使用Compat相关属性
     */
    private void createIndicators() {
        mLinearLayout.setPageColumn(mData.size());
    }

    /**
     * 设置是否禁止滚动播放
     *
     * @param isAutoPlaying true  是自动滚动播放,false 是禁止自动滚动
     */
    public void setRvAutoPlaying(boolean isAutoPlaying) {
        this.isAutoPlaying = isAutoPlaying;
    }

    /**
     * 设置是否显示指示器导航点
     *
     * @param show 显示
     */
    public void isShowIndicator(boolean show) {
        this.isShowIndicator = show;
    }

    /**
     * 设置轮播间隔时间
     *
     * @param millisecond 时间毫秒
     */
    public void setIndicatorInterval(int millisecond) {
        this.mInterval = millisecond;
    }

    /**
     * 改变导航指示器
     */
    private void switchIndicator() {
        if (mData == null || mData.size() == 0) {
            return;
        }
        if (isShowIndicator) {
            mLinearLayout.setCurrentItem(currentIndex % mData.size());
        }
    }

    /**
     * 获取RecyclerView实例，便于满足自定义{@link RecyclerView.ItemAnimator}或者{@link RecyclerView.Adapter}的需求
     *
     * @return RecyclerView实例
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    /**
     * 是否 无限轮播
     *
     * @param unlimited boolean
     */
    public void setUnlimited(boolean unlimited) {
        mIsUnlimited = unlimited;
        mAdapter.setUnlimited(unlimited);
    }


    /**
     * 滑动到指定位置
     *
     * @param position int
     */
    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setOnRvBannerClickListener(RecycleViewBanner.RecycleViewBannerClickListener onRvBannerClickListener) {
        mAdapter.setOnRvBannerClickListener(onRvBannerClickListener);
    }

    public void setRecycleViewBannerChangeListener(RecycleViewBanner.RecycleViewBannerChangeListener recycleViewBannerChangeListener) {
        mAdapter.setRecycleViewBannerChangeListener(recycleViewBannerChangeListener);
    }

    public interface RecycleViewBannerClickListener<T> {
        /**
         * @param t
         */
        void onBannerClick(T t);

    }


    public interface RecycleViewBannerChangeListener<T> {
        /**
         * @param t
         * @return
         */
        String getUrl(T t);

        /**
         * @param t
         * @return
         */
        String getTitle(T t);
    }

}