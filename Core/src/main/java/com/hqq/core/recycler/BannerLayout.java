package com.hqq.core.recycler;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.hqq.core.recycler.manager.BannerLayoutManager;
import com.hqq.core.recycler.manager.CenterSnapHelper;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.banner
 * @FileName :   BannerLayout
 * @Date : 2018/12/20 0020
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  Banner 轮播控件 支持卡片布局
 */
@Deprecated
public class BannerLayout extends FrameLayout {

    /**
     * 刷新间隔时间
     */
    private int autoPlayDuration;

    /**
     * 是否显示指示器
     */
    private boolean showIndicator;
    /**
     * 是否显示自定义指示器
     */
    private boolean showCustomIndicator;
    private RecyclerView indicatorContainer;
    private Drawable mSelectedDrawable;
    private Drawable mUnselectedDrawable;
    private IndicatorAdapter indicatorAdapter;
    /**
     * 指示器间距
     */
    private int indicatorMargin;
    private RecyclerView mRecyclerView;

    private BannerLayoutManager mLayoutManager;

    private int WHAT_AUTO_PLAY = 1000;

    private boolean hasInit;
    private int bannerSize = 1;
    private int currentIndex;
    private boolean isPlaying = false;

    private boolean isAutoPlaying = true;
    int itemSpace;
    float centerScale;
    float moveSpeed;
    protected Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_AUTO_PLAY) {
                if (currentIndex == mLayoutManager.getCurrentPosition()) {
                    ++currentIndex;
                    mRecyclerView.smoothScrollToPosition(currentIndex);
                    mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration);
                    refreshIndicator();
                }
            }
            return false;
        }
    });

    public BannerLayout(Context context) {
        this(context, null);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, com.hqq.core.R.styleable.BannerLayout);
        showIndicator = a.getBoolean(com.hqq.core.R.styleable.BannerLayout_showIndicator, true);
        autoPlayDuration = a.getInt(com.hqq.core.R.styleable.BannerLayout_interval, 4000);
        isAutoPlaying = a.getBoolean(com.hqq.core.R.styleable.BannerLayout_autoPlaying, true);
        itemSpace = a.getInt(com.hqq.core.R.styleable.BannerLayout_itemSpace, 20);
        centerScale = a.getFloat(com.hqq.core.R.styleable.BannerLayout_centerScale, 1.2f);
        moveSpeed = a.getFloat(com.hqq.core.R.styleable.BannerLayout_moveSpeed, 1.0f);
        if (mSelectedDrawable == null) {
            //绘制默认选中状态图形
            GradientDrawable selectedGradientDrawable = new GradientDrawable();
            selectedGradientDrawable.setShape(GradientDrawable.OVAL);
            selectedGradientDrawable.setColor(Color.RED);
            selectedGradientDrawable.setSize(dp2px(5), dp2px(5));
            selectedGradientDrawable.setCornerRadius(dp2px(5) / 2);
            mSelectedDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
        }
        if (mUnselectedDrawable == null) {
            //绘制默认未选中状态图形
            GradientDrawable unSelectedGradientDrawable = new GradientDrawable();
            unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
            unSelectedGradientDrawable.setColor(Color.GRAY);
            unSelectedGradientDrawable.setSize(dp2px(5), dp2px(5));
            unSelectedGradientDrawable.setCornerRadius(dp2px(5) / 2);
            mUnselectedDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
        }

        indicatorMargin = dp2px(4);
        int marginLeft = dp2px(16);
        int marginRight = dp2px(0);
        int marginBottom = dp2px(11);
        int gravity = GravityCompat.START;
        int o = a.getInt(com.hqq.core.R.styleable.BannerLayout_orientation, 0);
        int orientation = 0;
        if (o == 0) {
            orientation = OrientationHelper.HORIZONTAL;
        } else if (o == 1) {
            orientation = OrientationHelper.VERTICAL;
        }
        a.recycle();
        //轮播图部分
        mRecyclerView = new RecyclerView(context);
        LayoutParams vpLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, vpLayoutParams);
        mLayoutManager = new BannerLayoutManager(getContext(), orientation);
        mLayoutManager.setItemSpace(itemSpace);
        mLayoutManager.setCenterScale(centerScale);
        mLayoutManager.setMoveSpeed(moveSpeed);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new CenterSnapHelper().attachToRecyclerView(mRecyclerView);


        //指示器部分
        indicatorContainer = new RecyclerView(context);
        LinearLayoutManager indicatorLayoutManager = new LinearLayoutManager(context, orientation, false);
        indicatorContainer.setLayoutManager(indicatorLayoutManager);
        indicatorAdapter = new IndicatorAdapter();
        indicatorContainer.setAdapter(indicatorAdapter);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | gravity;
        params.setMargins(marginLeft, 0, marginRight, marginBottom);
        addView(indicatorContainer, params);
        if (!showIndicator) {
            indicatorContainer.setVisibility(GONE);
        }
    }

    /**
     * 设置是否禁止滚动播放
     *
     * @param isAutoPlaying
     */
    public void setAutoPlaying(boolean isAutoPlaying) {
        this.isAutoPlaying = isAutoPlaying;
        setPlaying(this.isAutoPlaying);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * 设置是否显示指示器
     *
     * @param showIndicator
     */
    public void setShowIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
        indicatorContainer.setVisibility(showIndicator ? VISIBLE : GONE);
    }

    //设置是否显示指示器
    public void setShowCustomIndicator(boolean showCustomIndicator) {
        this.showCustomIndicator = showCustomIndicator;
    }

    /**
     * 设置当前图片缩放系数
     *
     * @param centerScale
     */
    public void setCenterScale(float centerScale) {
        this.centerScale = centerScale;
        mLayoutManager.setCenterScale(centerScale);
    }

    /**
     * 设置跟随手指的移动速度
     *
     * @param moveSpeed
     */
    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
        mLayoutManager.setMoveSpeed(moveSpeed);
    }

    /**
     * 设置图片间距
     *
     * @param itemSpace
     */
    public void setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
        mLayoutManager.setItemSpace(itemSpace);
    }

    /**
     * 设置轮播间隔时间
     *
     * @param autoPlayDuration 时间毫秒
     */
    public void setAutoPlayDuration(int autoPlayDuration) {
        this.autoPlayDuration = autoPlayDuration;
    }

    public void setOrientation(int orientation) {
        mLayoutManager.setOrientation(orientation);
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    protected synchronized void setPlaying(boolean playing) {
        if (isAutoPlaying && hasInit) {
            if (!isPlaying && playing) {
                mHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, autoPlayDuration);
                isPlaying = true;
            } else if (isPlaying && !playing) {
                mHandler.removeMessages(WHAT_AUTO_PLAY);
                isPlaying = false;
            }
        }
    }


    /**
     * 设置轮播数据集
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        hasInit = false;
        mRecyclerView.setAdapter(adapter);
        bannerSize = adapter.getItemCount();
        mLayoutManager.setInfinite(bannerSize >= 3);
        setPlaying(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dx != 0) {
                    setPlaying(false);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int first = mLayoutManager.getCurrentPosition();
                Log.d("xxx", "onScrollStateChanged");
                if (currentIndex != first) {
                    currentIndex = first;
                }
                if (newState == SCROLL_STATE_IDLE) {
                    setPlaying(true);
                }
                refreshIndicator();
            }
        });
        hasInit = true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPlaying(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setPlaying(true);
                break;
            default:
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
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            setPlaying(true);
        } else {
            setPlaying(false);
        }
    }

    /**
     * 标示点适配器
     */
    protected class IndicatorAdapter extends RecyclerView.Adapter {
        int currentPosition = 0;
        public void setPosition(int currentPosition) {
            this.currentPosition = currentPosition;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ImageView bannerPoint = new ImageView(getContext());
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
            bannerPoint.setLayoutParams(lp);
            return new RecyclerView.ViewHolder(bannerPoint) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView bannerPoint = (ImageView) holder.itemView;
            bannerPoint.setImageDrawable(currentPosition == position ? mSelectedDrawable : mUnselectedDrawable);

        }

        @Override
        public int getItemCount() {
            return bannerSize;
        }
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 改变导航的指示点
     */
    protected synchronized void refreshIndicator() {
        if (showIndicator && bannerSize > 1) {
            indicatorAdapter.setPosition(currentIndex % bannerSize);
            indicatorAdapter.notifyDataSetChanged();
        }
        else {

            //显示自定义指示器
            if (showCustomIndicator && bannerSize > 1) {
                int currentPosition = currentIndex % bannerSize;
                if (onConverFlowRollingListener != null) {
                    onConverFlowRollingListener.onConverFlowRolling(currentPosition);
                }
            }
        }
    }

    public interface OnBannerItemClickListener {
        void onItemClick(int position);
    }


    //======================自定义扩展事件--轮播页滚动事件 START=========================
    private OnDispatchTouchListener onDispatchTouchListener;

    public interface OnDispatchTouchListener {
        void onAllowState(boolean allowState);
    }

    public void setOnDispatchTouchListener(OnDispatchTouchListener onDispatchTouchListener) {
        this.onDispatchTouchListener = onDispatchTouchListener;
    }

    //声明内容项监听
    private OnConverFlowRollingListener onConverFlowRollingListener;

    public interface OnConverFlowRollingListener {
        void onConverFlowRolling(int position);
    }

    public void setConverFlowRollingListener(OnConverFlowRollingListener OnConverFlowRollingListener) {
        this.onConverFlowRollingListener = OnConverFlowRollingListener;
    }
    //======================自定义扩展事件--轮播页滚动事件 END=========================


}