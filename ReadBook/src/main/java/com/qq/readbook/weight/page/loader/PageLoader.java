package com.qq.readbook.weight.page.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hqq.core.utils.DateUtils;
import com.hqq.core.utils.ScreenUtils;
import com.hqq.core.utils.TimeTool;
import com.qq.readbook.R;
import com.qq.readbook.room.entity.Book;
import com.qq.readbook.room.entity.Chapter;
import com.qq.readbook.repository.ReadRepository;
import com.qq.readbook.room.entity.LocalRecord;
import com.qq.readbook.weight.page.PageMode;
import com.qq.readbook.weight.page.PageStyle;
import com.qq.readbook.weight.page.PageView;
import com.qq.readbook.weight.page.ReadSettingManager;
import com.qq.readbook.weight.page.StringUtils;
import com.qq.readbook.weight.page.TxtPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.weight.page.loader
 * @FileName :   PageLoader
 * @Date : 2021/2/20 0020  上午 11:39
 * @Email :  qiqiang213@gmail.com
 * @Describe : Created by zlj
 */
public abstract class PageLoader {
    /**************************** 当前页面的状态*****************************/

    /**
     * 正在加载
     */
    public static final int STATUS_LOADING = 1;
    /**
     * 加载完成
     */
    public static final int STATUS_FINISH = 2;
    /**
     * 加载错误 (一般是网络加载情况)
     */
    public static final int STATUS_ERROR = 3;
    /**
     * 空数据
     */
    public static final int STATUS_EMPTY = 4;
    /**
     * 正在解析 (装载本地数据)
     */
    public static final int STATUS_PARING = 5;
    /**
     * 本地文件解析错误(暂未被使用)
     */
    public static final int STATUS_PARSE_ERROR = 6;
    /**
     * 获取到的目录为空
     */
    public static final int STATUS_CATEGORY_EMPTY = 7;
    /**************************** 默认的显示参数配置*****************************/
    /**
     * 高间距 显示的标题与底部的状态是覆盖上去的 这边需要设置一些距离才能正常显示
     */
    private static final int DEFAULT_MARGIN_HEIGHT = 20;
    /**
     * 宽间距
     */
    private static final int DEFAULT_MARGIN_WIDTH = 15;
    private static final int DEFAULT_TIP_SIZE = 12;
    private static final int EXTRA_TITLE_SIZE = 4;

    /**
     * 当前章节列表
     */
    protected List<Chapter> mChapterList;
    /**
     * 书本对象
     */
    protected Book mCollBook;
    /**
     * 监听器
     */
    protected OnPageChangeListener mPageChangeListener;
    private Context mContext;
    /**
     * 页面显示类
     */
    private PageView mPageView;
    /**
     * 当前显示的页
     */
    private TxtPage mCurPage;
    /**
     * 上一章的页面列表缓存
     */
    private List<TxtPage> mPrePageList;
    /**
     * 当前章节的页面列表
     */
    private List<TxtPage> mCurPageList;
    /**
     * 下一章的页面列表缓存
     */
    private List<TxtPage> mNextPageList;

    /**
     * 绘制电池的画笔
     */
    private Paint mBatteryPaint;
    /**
     * 绘制提示的画笔
     */
    private Paint mTipPaint;
    /**
     * 绘制标题的画笔
     */
    private Paint mTitlePaint;
    /**
     * 绘制背景颜色的画笔(用来擦除需要重绘的部分)
     */
    private Paint mBgPaint;
    /**
     * 绘制小说内容的画笔
     */
    private TextPaint mTextPaint;
    private Paint mSelectPaint;
    /**
     * 阅读器的配置选项
     */
    private ReadSettingManager mSettingManager;
    /**
     * 被遮盖的页，或者认为被取消显示的页
     */
    private TxtPage mCancelPage;
    /**
     * 存储阅读记录类
     */
    private LocalRecord mLocalRecord;

    /*****************params**************************/
    /**
     * 当前的状态
     */
    protected int mStatus = STATUS_LOADING;
    /**
     * 判断章节列表是否加载完成
     */
    protected boolean isChapterListPrepare;

    /**
     * 是否打开过章节
     */
    private boolean isChapterOpen;
    private boolean isFirstOpen = true;
    private boolean isClose;
    /**
     * 页面的翻页效果模式
     */
    private PageMode mPageMode;
    /**
     * 加载器的颜色主题
     */
    private PageStyle mPageStyle;
    /**
     * 当前是否是夜间模式
     */
    private boolean isNightMode;
    /**
     * 书籍绘制区域的宽高
     */
    private int mVisibleWidth;
    private int mVisibleHeight;
    /**
     * 应用的宽高
     */
    private int mDisplayWidth;
    private int mDisplayHeight;
    /**
     * 间距
     */
    private int mMarginWidth;
    private int mMarginHeight;
    /**
     * 字体的颜色
     */
    private int mTextColor;
    /**
     * 标题的大小
     */
    private int mTitleSize;
    /**
     * 字体的大小
     */
    private int mTextSize;
    /**
     * 行间距
     */
    private int mTextInterval;
    /**
     * 标题的行间距
     */
    private int mTitleInterval;
    /**
     * 段落距离(基于行间距的额外距离)
     */
    private int mTextPara;

    private int mTitlePara;
    /**
     * 电池的百分比
     */
    private int mBatteryLevel;
    /**
     * 背景颜色
     */
    private int mBgColor;

    /**
     * 当前章
     */
    protected int mCurChapterPos = 0;
    /**
     * 上一章的记录
     */
    private int mLastChapterPos = 0;
    /**
     * 将章节数据，解析成页面列表
     * chapter：章节信息
     * br：章节的文本流
     */
    private List<String> pics = new ArrayList<>();
    /**
     * 返回书架的按钮
     */
    RectF mBookshelfRectF;

    public PageLoader(PageView pageView, Book collBook) {
        mPageView = pageView;
        mContext = pageView.getContext();
        mCollBook = collBook;
        mChapterList = new ArrayList<>(1);

        // 初始化数据
        initData();
        // 初始化画笔
        initPaint();
        // 初始化PageView
        initPageView();
        // 初始化书籍
        prepareBook();
    }

    private void initData() {
        // 获取配置管理器
        mSettingManager = ReadSettingManager.Companion.getInstance();
        // 获取配置参数
        mPageMode = mSettingManager.getPageMode();
        mPageStyle = mSettingManager.getPageStyle();
        // 初始化参数
        mMarginWidth = ScreenUtils.dip2px(DEFAULT_MARGIN_WIDTH);
        mMarginHeight = ScreenUtils.dip2px(DEFAULT_MARGIN_HEIGHT);
        // 配置文字有关的参数
        setUpTextParams(mSettingManager.getTextSize());
    }

    /**
     * 作用：设置与文字相关的参数
     *
     * @param textSize
     */
    private void setUpTextParams(int textSize) {
        // 文字大小
        mTextSize = textSize;
        mTitleSize = mTextSize + (int) ScreenUtils.spToPx(EXTRA_TITLE_SIZE);
        // 行间距(大小为字体的一半)
        mTextInterval = mTextSize / 2;
        mTitleInterval = mTitleSize / 2;
        // 段落间距(大小为字体的高度)
        mTextPara = mTextSize;
        mTitlePara = mTitleSize;
    }

    private void initPaint() {
        // 绘制提示的画笔
        mTipPaint = new Paint();
        mTipPaint.setColor(mTextColor);
        // 绘制的起始点
        mTipPaint.setTextAlign(Paint.Align.LEFT);
        // Tip默认的字体大小
        mTipPaint.setTextSize(ScreenUtils.spToPx(DEFAULT_TIP_SIZE));
        mTipPaint.setAntiAlias(true);
        mTipPaint.setSubpixelText(true);

        // 绘制页面内容的画笔
        mTextPaint = new TextPaint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);

        mSelectPaint = new TextPaint();
        mSelectPaint.setColor(mContext.getResources().getColor(R.color.colorSelect));
        mSelectPaint.setTextSize(mTextSize);
        mSelectPaint.setAntiAlias(true);

        // 绘制标题的画笔
        mTitlePaint = new TextPaint();
        mTitlePaint.setColor(mTextColor);
        mTitlePaint.setTextSize(mTitleSize);
        mTitlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTitlePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTitlePaint.setAntiAlias(true);

        // 绘制背景的画笔
        mBgPaint = new Paint();
        mBgPaint.setColor(mBgColor);

        // 绘制电池的画笔
        mBatteryPaint = new Paint();
        mBatteryPaint.setAntiAlias(true);
        mBatteryPaint.setDither(true);

        // 初始化页面样式
        setNightMode(mSettingManager.isNightMode());
    }

    private void initPageView() {
        //配置参数
        mPageView.setPageMode(mPageMode);
        mPageView.setBgColor(mBgColor);
    }

    /**
     * 跳转到上一章
     *
     * @return
     */
    public boolean skipPreChapter() {
        if (!hasPrevChapter()) {
            return false;
        }

        // 载入上一章。
        if (parsePrevChapter()) {
            mCurPage = getCurPage(0);
        } else {
            mCurPage = new TxtPage();
        }
        mPageView.drawCurPage(false);
        return true;
    }

    /**
     * 跳转到下一章
     */
    public boolean skipNextChapter() {
        if (!hasNextChapter()) {
            return false;
        }

        //判断是否达到章节的终止点
        if (parseNextChapter()) {
            mCurPage = getCurPage(0);
        } else {
            mCurPage = new TxtPage();
        }
        mPageView.drawCurPage(false);
        return true;
    }

    /**
     * 跳转到指定章节
     *
     * @param pos:从 0 开始。
     */
    public void skipToChapter(int pos) {
        // 设置参数
        mCurChapterPos = pos;

        // 将上一章的缓存设置为null
        mPrePageList = null;
        // 将下一章缓存设置为null
        mNextPageList = null;

        // 打开指定章节
        openChapter();
    }

    /**
     * 跳转到指定的页
     *
     * @param pos
     */
    public boolean skipToPage(int pos) {
        if (!isChapterListPrepare) {
            return false;
        }
        mCurPage = getCurPage(pos);
        mPageView.drawCurPage(false);
        return true;
    }

    /**
     * 翻到上一页
     */
    public boolean skipToPrePage() {
        return mPageView.autoPrevPage();
    }

    /**
     * 翻到下一页
     */
    public boolean skipToNextPage() {
        return mPageView.autoNextPage();
    }

    /**
     * 更新时间
     */
    public void updateTime() {
        if (!mPageView.isRunning()) {
            mPageView.drawCurPage(true);
        }
    }

    /**
     * 更新电量
     *
     * @param level
     */
    public void updateBattery(int level) {
        mBatteryLevel = level;

        if (!mPageView.isRunning()) {
            mPageView.drawCurPage(true);
        }
    }

    /**
     * 设置提示的文字大小
     *
     * @param textSize:单位为 px。
     */
    public void setTipTextSize(int textSize) {
        mTipPaint.setTextSize(textSize);

        // 如果屏幕大小加载完成
        mPageView.drawCurPage(false);
    }

    /**
     * 设置文字相关参数
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        // 设置文字相关参数
        setUpTextParams(textSize);

        // 设置画笔的字体大小
        mTextPaint.setTextSize(mTextSize);
        mSelectPaint.setTextSize(mTextSize);
        // 设置标题的字体大小
        mTitlePaint.setTextSize(mTitleSize);
        // 存储文字大小
        mSettingManager.setTextSize(mTextSize);
        // 取消缓存
        mPrePageList = null;
        mNextPageList = null;

        // 如果当前已经显示数据
        if (isChapterListPrepare && mStatus == STATUS_FINISH) {
            // 重新计算当前页面
            dealLoadPageList(mCurChapterPos);

            // 防止在最后一页，通过修改字体，以至于页面数减少导致崩溃的问题
            if (mCurPage.getPosition() >= mCurPageList.size()) {
                mCurPage.position = mCurPageList.size() - 1;
            }

            // 重新获取指定页面
            mCurPage = mCurPageList.get(mCurPage.position);
        }

        mPageView.drawCurPage(false);
    }

    /**
     * 设置夜间模式
     *
     * @param nightMode
     */
    public void setNightMode(boolean nightMode) {
        mSettingManager.setNightMode(nightMode);
        isNightMode = nightMode;

        if (isNightMode) {
            mBatteryPaint.setColor(Color.WHITE);
            setPageStyle(PageStyle.NIGHT);
        } else {
            mBatteryPaint.setColor(Color.BLACK);
            setPageStyle(mPageStyle);
        }
    }

    /**
     * 设置页面样式
     *
     * @param pageStyle:页面样式
     */
    public void setPageStyle(PageStyle pageStyle) {
        if (pageStyle != PageStyle.NIGHT) {
            mPageStyle = pageStyle;
            mSettingManager.setPageStyle(pageStyle);
        }

        if (isNightMode && pageStyle != PageStyle.NIGHT) {
            return;
        }

        // 设置当前颜色样式
        mTextColor = ContextCompat.getColor(mContext, pageStyle.getFontColor());
        mBgColor = ContextCompat.getColor(mContext, pageStyle.getBgColor());

        mTipPaint.setColor(mTextColor);
        mTitlePaint.setColor(mTextColor);
        mTextPaint.setColor(mTextColor);

        mBgPaint.setColor(mBgColor);

        mPageView.drawCurPage(false);
    }

    /**
     * 翻页动画
     *
     * @param pageMode:翻页模式
     * @see PageMode
     */
    public void setPageMode(PageMode pageMode) {
        mPageMode = pageMode;

        mPageView.setPageMode(mPageMode);
        mSettingManager.setPageMode(mPageMode);

        // 重新绘制当前页
        mPageView.drawCurPage(false);
    }

    /**
     * 设置内容与屏幕的间距
     *
     * @param marginWidth  :单位为 px
     * @param marginHeight :单位为 px
     */
    public void setMargin(int marginWidth, int marginHeight) {
        mMarginWidth = marginWidth;
        mMarginHeight = marginHeight;
        // 如果是滑动动画，则需要重新创建了
        if (mPageMode == PageMode.SCROLL) {
            mPageView.setPageMode(PageMode.SCROLL);
        }
        mPageView.drawCurPage(false);
    }

    /**
     * 设置页面切换监听
     *
     * @param listener
     */
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mPageChangeListener = listener;
        // 如果目录加载完之后才设置监听器，那么会默认回调
        if (isChapterListPrepare) {
            mPageChangeListener.onCategoryFinish(mChapterList);
        }
    }

    /**
     * 获取当前页的状态
     *
     * @return
     */
    public int getPageStatus() {
        return mStatus;
    }

    /**
     * 获取书籍信息
     *
     * @return
     */
    public Book getCollBook() {
        return mCollBook;
    }

    /**
     * 获取章节目录。
     *
     * @return
     */
    public List<Chapter> getChapterCategory() {
        return mChapterList;
    }

    public TxtPage getCurPage() {
        return mCurPage;
    }

    public void setCurPage(TxtPage mCurPage) {
        this.mCurPage = mCurPage;
    }

    public List<TxtPage> getCurPageList() {
        if (mCurPageList == null) {
            return new ArrayList<>();
        }
        return mCurPageList;
    }

    public List<TxtPage> getNextPageList() {
        if (mNextPageList == null) {
            return new ArrayList<>();
        }
        return mNextPageList;
    }

    /**
     * 获取当前页的页码
     */
    public int getPagePos() {
        return mCurPage.position;
    }

    /**
     * 获取当前章节的章节位置
     */
    public int getChapterPos() {
        return mCurChapterPos;
    }

    /**
     * 获取距离屏幕的高度
     */
    public int getMarginHeight() {
        return mMarginHeight;
    }

    /**
     * 保存阅读记录
     */
    public void saveRecord() {

        if (mChapterList.isEmpty()) {
            return;
        }

        mLocalRecord.setBookId(mCollBook.getBookId());
        mLocalRecord.setChapter(mCurChapterPos);

        if (mCurPage != null) {
            mLocalRecord.setPagePos(mCurPage.position);
        } else {
            mLocalRecord.setPagePos(0);
        }

        //存储到数据库
        ReadRepository.saveBookRecord(mCollBook, mLocalRecord);
    }

    /**
     * 初始化书籍
     */
    private void prepareBook() {
        //todo 获取阅读记录
        mLocalRecord = ReadRepository.getBookRecord(mCollBook);
        if (mLocalRecord == null) {
            mLocalRecord = new LocalRecord();
        }
        mCurChapterPos = mLocalRecord.getChapter();
        mLastChapterPos = mCurChapterPos;
    }

    /**
     * 打开指定章节
     */
    public void openChapter() {
        isFirstOpen = false;
        if (mPageView == null || !mPageView.isPrepare()) {
            return;
        }

        // 如果章节目录没有准备好
        if (!isChapterListPrepare) {
            mStatus = STATUS_LOADING;
            mPageView.drawCurPage(false);
            return;
        }

        // 如果获取到的章节目录为空
        if (mChapterList.isEmpty()) {
            mStatus = STATUS_CATEGORY_EMPTY;
            mPageView.drawCurPage(false);
            return;
        }

        if (parseCurChapter()) {
            // 如果章节从未打开
            if (!isChapterOpen) {
                int position = mLocalRecord.getPagePos();

                // 防止记录页的页号，大于当前最大页号
                if (position >= mCurPageList.size()) {
                    position = mCurPageList.size() - 1;
                }
                mCurPage = getCurPage(position);
                mCancelPage = mCurPage;
                // 切换状态
                isChapterOpen = true;
            } else {
                mCurPage = getCurPage(0);
            }
        } else {
            mCurPage = new TxtPage();
        }

        mPageView.drawCurPage(false);
    }

    /**
     * 加载错误
     */
    public void chapterError() {
        mStatus = STATUS_ERROR;
        mPageView.drawCurPage(false);
    }

    /**
     * 关闭书本
     */
    public void closeBook() {
        isChapterListPrepare = false;
        isClose = true;


        clearList(mChapterList);
        clearList(mCurPageList);
        clearList(mNextPageList);

        mChapterList = null;
        mCurPageList = null;
        mNextPageList = null;
        mPageView = null;
        mCurPage = null;
    }

    private void clearList(List list) {
        if (list != null) {
            list.clear();
        }
    }

    public boolean isClose() {
        return isClose;
    }

    public boolean isChapterOpen() {
        return isChapterOpen;
    }

    /**
     * 加载页面列表
     *
     * @param chapterPos:章节序号
     * @return
     */
    private List<TxtPage> loadPageList(int chapterPos) throws Exception {
        // 获取章节
        Chapter chapter = mChapterList.get(chapterPos);
        // 判断章节是否存在
        if (!hasChapterData(chapter)) {
            return null;
        }
        // 获取章节的文本流
        BufferedReader reader = getChapterReader(chapter);
        return loadPages(chapter, reader);
    }

    /**
     * 刷新章节列表
     */
    public abstract void refreshChapterList();

    /**
     * 获取章节的文本流
     */
    protected abstract BufferedReader getChapterReader(Chapter chapter) throws Exception;

    /**
     * 章节数据是否存在
     */
    protected abstract boolean hasChapterData(Chapter chapter);

    /***********************************default method***********************************************/

    public void drawPage(Bitmap bitmap, boolean isUpdate) {
        drawBackground(mPageView.getBgBitmap(), isUpdate);
        if (!isUpdate) {
            drawContent(bitmap);
        }
        //更新绘制
        mPageView.invalidate();
    }

    private void drawBackground(Bitmap bitmap, boolean isUpdate) {
        Canvas canvas = new Canvas(bitmap);
        int tipMarginHeight = ScreenUtils.dip2px(3);
        if (!isUpdate) {
            //绘制背景
            canvas.drawColor(mBgColor);

            if (!mChapterList.isEmpty()) {
                //初始化标题的参数
                //需要注意的是:绘制text的y的起始点是text的基准线的位置，而不是从text的头部的位置
                float tipTop = tipMarginHeight - mTipPaint.getFontMetrics().top;
                //根据状态不一样，数据不一样
                if (mStatus != STATUS_FINISH) {
                    if (isChapterListPrepare) {
                        //todo 目前不清楚发生的情形,只能这样防止用户瞎逼操作导致数组越界
                        if (mChapterList.size() > mCurChapterPos) {
                            canvas.drawText(mChapterList.get(mCurChapterPos).getTitle()
                                    , mMarginWidth, tipTop, mTipPaint);
                        }
                    }
                } else {
                    String title = "";
                    if (mCurPage.title != null && !mCurPage.title.isEmpty()) {
                        title = mCurPage.title;
                    }
                    canvas.drawText(title, mMarginWidth, tipTop, mTipPaint);
                }

                //绘制页码
                // 底部的字显示的位置Y
                float y = mDisplayHeight - mTipPaint.getFontMetrics().bottom - tipMarginHeight;
                // 只有finish的时候采用页码
                if (mStatus == STATUS_FINISH) {
                    int total = 0;
                    if (mCurPageList != null) {
                        total = mCurPageList.size();
                    }

                    String percent = (mCurPage.position + 1) + "/" + total;
                    canvas.drawText(percent, mMarginWidth, y, mTipPaint);
                }
            }
        } else {
            //擦除区域
            mBgPaint.setColor(mBgColor);
            canvas.drawRect(mDisplayWidth >> 1, mDisplayHeight - mMarginHeight + ScreenUtils.dip2px(2), mDisplayWidth, mDisplayHeight, mBgPaint);
        }

        //绘制电池
        int visibleRight = mDisplayWidth - mMarginWidth;
        int visibleBottom = mDisplayHeight - tipMarginHeight;

        int outFrameWidth = (int) mTipPaint.measureText("xxx");
        int outFrameHeight = (int) mTipPaint.getTextSize();

        int polarHeight = ScreenUtils.dip2px(6);
        int polarWidth = ScreenUtils.dip2px(2);
        int border = 1;
        int innerMargin = 1;

        //电极的制作
        int polarLeft = visibleRight - polarWidth;
        int polarTop = visibleBottom - (outFrameHeight + polarHeight) / 2;
        Rect polar = new Rect(polarLeft, polarTop, visibleRight, polarTop + polarHeight - ScreenUtils.dip2px(2));

        mBatteryPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(polar, mBatteryPaint);

        //外框的制作
        int outFrameLeft = polarLeft - outFrameWidth;
        int outFrameTop = visibleBottom - outFrameHeight;
        int outFrameBottom = visibleBottom - ScreenUtils.dip2px(2);
        Rect outFrame = new Rect(outFrameLeft, outFrameTop, polarLeft, outFrameBottom);

        mBatteryPaint.setStyle(Paint.Style.STROKE);
        mBatteryPaint.setStrokeWidth(border);
        canvas.drawRect(outFrame, mBatteryPaint);

        //内框的制作
        float innerWidth = (outFrame.width() - innerMargin * 2 - border) * (mBatteryLevel / 100.0f);
        RectF innerFrame = new RectF(outFrameLeft + border + innerMargin, outFrameTop + border + innerMargin, outFrameLeft + border + innerMargin + innerWidth, outFrameBottom - border - innerMargin);

        mBatteryPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(innerFrame, mBatteryPaint);

        //绘制当前时间
        //底部的字显示的位置Y
        float y = mDisplayHeight - mTipPaint.getFontMetrics().bottom - tipMarginHeight;
        String time = TimeTool.INSTANCE.formatData2All(System.currentTimeMillis(), "HH:mm");
        float x = outFrameLeft - mTipPaint.measureText(time) - ScreenUtils.dip2px(4);
        canvas.drawText(time, x, y, mTipPaint);
    }

    private void drawContent(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);

        if (mPageMode == PageMode.SCROLL) {
            canvas.drawColor(mBgColor);
        }

        //绘制内容
        if (mStatus != STATUS_FINISH) {
            //绘制字体
            String tip = "";
            switch (mStatus) {
                case STATUS_LOADING:
                    tip = "正在拼命加载中...";
                    break;
                case STATUS_ERROR:
                    tip = "加载失败(点击边缘重试)";
                    break;
                case STATUS_EMPTY:
                    tip = "文章内容为空";
                    break;
                case STATUS_PARING:
                    tip = "正在排版请等待...";
                    break;
                case STATUS_PARSE_ERROR:
                    tip = "文件解析错误";
                    break;
                case STATUS_CATEGORY_EMPTY:
                    tip = "目录列表为空";
                    break;
                default:
            }
            //将提示语句放到正中间
            drawCenter(tip, canvas);
        } else {
            float top;
            if (mPageMode == PageMode.SCROLL) {
                top = -mTextPaint.getFontMetrics().top;
            } else {
                top = mMarginHeight - mTextPaint.getFontMetrics().top;
            }

            //设置总距离
            int interval = mTextInterval + (int) mTextPaint.getTextSize();
            int para = mTextPara + (int) mTextPaint.getTextSize();
            int titleInterval = mTitleInterval + (int) mTitlePaint.getTextSize();
            int titlePara = mTitlePara + (int) mTextPaint.getTextSize();
            String str;

            //对标题进行绘制
            for (int i = 0; i < mCurPage.titleLines; ++i) {
                str = mCurPage.getLines().get(i);
                //设置顶部间距
                if (i == 0) {
                    top += mTitlePara;
                }

                //计算文字显示的起始点
                int start = (int) (mDisplayWidth - mTitlePaint.measureText(str)) / 2;
                //进行绘制
                mTitlePaint.setColor(mTextColor);
                canvas.drawText(str, start, top, mTitlePaint);

                //设置尾部间距
                if (i == mCurPage.titleLines - 1) {
                    top += titlePara;
                } else {
                    //行间距
                    top += titleInterval;
                }
            }

            //对内容进行绘制
            if (mCurPage.lines != null) {
                for (int i = mCurPage.titleLines; i < mCurPage.lines.size(); ++i) {
                    str = mCurPage.getLines().get(i);
//                if (i == 0) {
//                    top = top + 15;
//                }
                    canvas.drawText(str, mMarginWidth, top, mTextPaint);
                    if (str.endsWith("\n")) {
                        top += para;
                    } else {
                        top += interval;
                    }
                }
            }

            if (!TextUtils.isEmpty(getCurPage().getPic())) {
                Glide.with(mContext).asBitmap().load(getCurPage().getPic()).thumbnail(0.1f).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        canvas.save();
                        drawCenter(mContext.getString(R.string.pic_loading), canvas);
                        canvas.restore();
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {

                        if (resource.getWidth() > mDisplayWidth) {
                            resource = scaleBitmap(resource);
                        }
                        float pivotX = (mDisplayWidth - resource.getWidth()) >> 1;
                        float pivotY = (mDisplayHeight - resource.getHeight()) >> 1;
                        if (!TextUtils.isEmpty(getCurPage().getPic())) {
                            canvas.drawBitmap(resource, pivotX, pivotY, mTextPaint);
                            mPageView.invalidate();
                        }
                    }
                });
            }
        }
    }

    private void drawEnd(Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textHeight = fontMetrics.top - fontMetrics.bottom;
        float textWidth = mTextPaint.measureText("返回书架");
        float pivotX = (mDisplayWidth - textWidth) / 2;
        float pivotY = (mDisplayHeight - textHeight) / 2 + 150;
        canvas.drawText("返回书架", pivotX, pivotY, mTextPaint);

        Paint paint = new Paint();
        paint.setColor(mTextColor);
        paint.setTextSize(mTextSize);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        float left = 150;
        float top = (mDisplayHeight - textHeight) / 2 + 75;
        float right = mDisplayWidth - 150;
        float bottom = (mDisplayHeight - textHeight) / 2 + 200;
        mBookshelfRectF = new RectF(left, top, right, bottom);

        canvas.drawRoundRect(mBookshelfRectF, 20f, 20f, paint);

    }

    /**
     * 中心文字绘制
     *
     * @param tip
     * @param canvas
     */
    private void drawCenter(String tip, Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textHeight = fontMetrics.top - fontMetrics.bottom;
        float textWidth = mTextPaint.measureText(tip);
        float pivotX = (mDisplayWidth - textWidth) / 2;
        float pivotY = (mDisplayHeight - textHeight) / 2;
        canvas.drawText(tip, pivotX, pivotY, mTextPaint);
    }

    /**
     * 图片缩放
     *
     * @param origin
     * @return
     */
    private Bitmap scaleBitmap(Bitmap origin) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale((float) 0.5, (float) 0.5);
        Bitmap newBm = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return newBm;
    }

    public void prepareDisplay(int w, int h) {
        // 获取PageView的宽高
        mDisplayWidth = w;
        mDisplayHeight = h;

        // 获取内容显示位置的大小
        mVisibleWidth = mDisplayWidth - mMarginWidth * 2;
        mVisibleHeight = mDisplayHeight - mMarginHeight * 2;

        // 重置 PageMode
        mPageView.setPageMode(mPageMode);

        if (!isChapterOpen) {
            // 展示加载界面
            mPageView.drawCurPage(false);
            // 如果在 display 之前调用过 openChapter 肯定是无法打开的。
            // 所以需要通过 display 再重新调用一次。
            if (!isFirstOpen) {
                // 打开书籍
                openChapter();
            }
        } else {
            // 如果章节已显示，那么就重新计算页面
            if (mStatus == STATUS_FINISH) {
                dealLoadPageList(mCurChapterPos);
                // 重新设置文章指针的位置
                mCurPage = getCurPage(mCurPage.position);
            }
            mPageView.drawCurPage(false);
        }
    }

    /**
     * 翻阅上一页
     */
    public boolean prev() {
        // 以下情况禁止翻页
        if (!canTurnPage()) {
            return false;
        }

        if (mStatus == STATUS_FINISH) {
            // 先查看是否存在上一页
            TxtPage prevPage = getPrevPage();
            if (prevPage != null) {
                mCancelPage = mCurPage;
                mCurPage = prevPage;
                mPageView.drawNextPage();
                return true;
            }
        }

        if (!hasPrevChapter()) {
            return false;
        }

        mCancelPage = mCurPage;
        if (parsePrevChapter()) {
            mCurPage = getPrevLastPage();
        } else {
            mCurPage = new TxtPage();
        }
        mPageView.drawNextPage();
        return true;
    }

    /**
     * 解析上一章数据
     *
     * @return :数据是否解析成功
     */
    boolean parsePrevChapter() {
        // 加载上一章数据
        int prevChapter = mCurChapterPos - 1;

        mLastChapterPos = mCurChapterPos;
        mCurChapterPos = prevChapter;

        // 当前章缓存为下一章
        mNextPageList = mCurPageList;

        // 判断是否具有上一章缓存
        if (mPrePageList != null) {
            mCurPageList = mPrePageList;
            mPrePageList = null;

            // 回调
            chapterChangeCallback();
        } else {
            dealLoadPageList(prevChapter);
        }
        return mCurPageList != null;
    }

    private boolean hasPrevChapter() {
        //判断是否上一章节为空
        return mCurChapterPos - 1 >= 0;
    }

    /**
     * 翻到下一页
     *
     * @return :是否允许翻页
     */
    public boolean next() {
        // 以下情况禁止翻页
        if (!canTurnPage()) {
            return false;
        }
        if (mStatus == STATUS_FINISH) {
            // 先查看是否存在下一页
            TxtPage nextPage = getNextPage();
            if (nextPage != null) {
                mCancelPage = mCurPage;
                mCurPage = nextPage;
                mPageView.drawNextPage();
                return true;
            }
        }
        if (!hasNextChapter()) {
            return false;
        }
        mCancelPage = mCurPage;
        // 解析下一章数据
        if (parseNextChapter()) {
            mCurPage = mCurPageList.get(0);
        } else {
            mCurPage = new TxtPage();
        }
        mPageView.drawNextPage();
        return true;
    }

    private boolean hasNextChapter() {
        // 判断是否到达目录最后一章
        return mCurChapterPos + 1 < mChapterList.size();
    }

    boolean parseCurChapter() {
        // 解析数据
        dealLoadPageList(mCurChapterPos);
        // 预加载下一页面
        preLoadNextChapter();
        return mCurPageList != null;
    }

    /**
     * 解析下一章数据
     *
     * @return : 返回解析成功还是失败
     */
    boolean parseNextChapter() {
        int nextChapter = mCurChapterPos + 1;

        mLastChapterPos = mCurChapterPos;
        mCurChapterPos = nextChapter;

        // 将当前章的页面列表，作为上一章缓存
        mPrePageList = mCurPageList;

        // 是否下一章数据已经预加载了
        if (mNextPageList != null) {
            mCurPageList = mNextPageList;
            mNextPageList = null;
            // 回调
            chapterChangeCallback();
        } else {
            // 处理页面解析
            dealLoadPageList(nextChapter);
        }
        // 预加载下一页面
        preLoadNextChapter();
        return mCurPageList != null;
    }

    /**
     * 加载章节内容
     *
     * @param chapterPos
     */
    private void dealLoadPageList(int chapterPos) {
        try {
            mCurPageList = loadPageList(chapterPos);
            if (mCurPageList != null) {
                if (mCurPageList.isEmpty()) {
                    mStatus = STATUS_EMPTY;
                    // 添加一个空数据
                    TxtPage page = new TxtPage();
                    page.lines = new ArrayList<>(1);
                    mCurPageList.add(page);
                } else {
                    mStatus = STATUS_FINISH;
                }
            } else {
                mStatus = STATUS_LOADING;
            }
        } catch (Exception e) {
            e.printStackTrace();

            mCurPageList = null;
            mStatus = STATUS_ERROR;
        }
        // 回调
        chapterChangeCallback();
    }

    /**
     * --
     * 数据已缓存
     */
    private void chapterChangeCallback() {
        if (mPageChangeListener != null && mCurChapterPos < mChapterList.size()) {
            mPageChangeListener.onChapterChange(mCurChapterPos);
            pics.clear();
            mPageChangeListener.onPageCountChange(mCurPageList != null ? mCurPageList.size() : 0);
        }
    }

    /**
     * 预加载下一章
     */
    private void preLoadNextChapter() {
        int nextChapter = mCurChapterPos + 1;
        // 如果不存在下一章，且下一章没有数据，则不进行加载。
        if (!hasNextChapter()
                || !hasChapterData(mChapterList.get(nextChapter))) {
            return;
        }
        try {
            //todo  需要异步加载 取消之前的加载
            mNextPageList = loadPageList(nextChapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 取消翻页
     */
    public void pageCancel() {
        if (mCurPage.position == 0 && mCurChapterPos > mLastChapterPos) {
            // 加载到下一章取消了
            if (mPrePageList != null) {
                cancelNextChapter();
            } else {
                if (parsePrevChapter()) {
                    mCurPage = getPrevLastPage();
                } else {
                    mCurPage = new TxtPage();
                }
            }
        } else if (mCurPageList == null
                || (mCurPage.position == mCurPageList.size() - 1
                && mCurChapterPos < mLastChapterPos)) {  // 加载上一章取消了

            if (mNextPageList != null) {
                cancelPreChapter();
            } else {
                if (parseNextChapter()) {
                    mCurPage = mCurPageList.get(0);
                } else {
                    mCurPage = new TxtPage();
                }
            }
        } else {
            // 假设加载到下一页，又取消了。那么需要重新装载。
            mCurPage = mCancelPage;
        }
    }

    private void cancelNextChapter() {
        int temp = mLastChapterPos;
        mLastChapterPos = mCurChapterPos;
        mCurChapterPos = temp;

        mNextPageList = mCurPageList;
        mCurPageList = mPrePageList;
        mPrePageList = null;

        chapterChangeCallback();

        mCurPage = getPrevLastPage();
        mCancelPage = null;
    }

    private void cancelPreChapter() {
        // 重置位置点
        int temp = mLastChapterPos;
        mLastChapterPos = mCurChapterPos;
        mCurChapterPos = temp;
        // 重置页面列表
        mPrePageList = mCurPageList;
        mCurPageList = mNextPageList;
        mNextPageList = null;

        chapterChangeCallback();

        mCurPage = getCurPage(0);
        mCancelPage = null;
    }


    private List<TxtPage> loadPages(Chapter chapter, BufferedReader br) {

        //生成的页面
        List<TxtPage> pages = new ArrayList<>();
        //使用流的方式加载
        List<String> lines = new ArrayList<>();
        int rHeight = mVisibleHeight;
        int titleLinesCount = 0;
        // 是否展示标题
        boolean showTitle = true;
        //默认展示标题
        String paragraph = chapter.getTitle();
        String title = StringUtils.INSTANCE.convertCC(chapter.getTitle());
        String half;
        try {
            while (showTitle || (paragraph = br.readLine()) != null) {
                half = paragraph;
                paragraph = StringUtils.INSTANCE.convertCC(paragraph);
                // 重置段落
                if (!showTitle) {
                    paragraph = paragraph.replaceAll("\\s", "");
                    // 如果只有换行符，那么就不执行
                    if ("".equals(paragraph)) {
                        continue;
                    }
                    paragraph = StringUtils.INSTANCE.halfToFull("  " + paragraph + "\n");
                } else {
                    //设置 title 的顶部间距
                    rHeight -= mTitlePara;
                }

                int wordCount;
                String subStr;
                while (paragraph.length() > 0) {
                    //当前空间，是否容得下一行文字
                    if (showTitle) {
                        rHeight -= mTitlePaint.getTextSize();
                    } else {
                        rHeight -= mTextPaint.getTextSize();
                    }

                    // 一页已经填充满了，创建 TextPage
                    if (rHeight <= 0) {
                        // 创建Page
                        TxtPage page = new TxtPage();
                        page.position = pages.size();
                        page.title = title;
                        page.lines = new ArrayList<>(lines);
                        page.titleLines = titleLinesCount;
                        pages.add(page);
                        // 重置Lines
                        lines.clear();
                        rHeight = mVisibleHeight;
                        titleLinesCount = 0;
                        continue;
                    }

                    //测量一行占用的字节数
                    if (showTitle) {
                        wordCount = mTitlePaint.breakText(paragraph,
                                true, mVisibleWidth, null);
                    } else {
                        wordCount = mTextPaint.breakText(paragraph,
                                true, mVisibleWidth, null);
                    }

                    subStr = paragraph.substring(0, wordCount);
                    if (!"\n".equals(subStr)) {
                        //将一行字节，存储到lines中
                        lines.add(subStr);

                        //设置段落间距
                        if (showTitle) {
                            titleLinesCount += 1;
                            rHeight -= mTitleInterval;
                        } else {
                            rHeight -= mTextInterval;
                        }
                    }
                    //裁剪
                    paragraph = paragraph.substring(wordCount);
                }

                //增加段落的间距
                if (!showTitle && lines.size() != 0) {
                    rHeight = rHeight - mTextPara + mTextInterval;
                }

                if (showTitle) {
                    rHeight = rHeight - mTitlePara + mTitleInterval;
                    showTitle = false;
                }

                pics.addAll(getImgs(half));
            }
            if (lines.size() != 0) {
                //创建Page
                TxtPage page = new TxtPage();
                page.position = pages.size();
                page.title = title;
                page.lines = new ArrayList<>(lines);
                page.titleLines = titleLinesCount;
                pages.add(page);
                //重置Lines
                lines.clear();
            }

            if (pics.size() > 0) {
                for (int i = 0; i < pics.size(); i++) {
                    TxtPage page = new TxtPage();
                    page.position = pages.size();
                    page.title = title;
                    page.lines = new ArrayList<>();
                    page.titleLines = 0;
                    page.setPic(pics.get(i));
                    pages.add(page);
                    // 重置Lines
                    lines.clear();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pages;
    }

    Pattern pattern = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)");

    private List<String> getImgs(String content) {
        String img;
        Pattern p_image;
        Matcher m_image;
        List<String> images = new ArrayList<>();
        String regExImg = "(<img.*src\\s*=\\s*(.*?)[^>]*?>)";
        p_image = Pattern.compile(regExImg, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(content);
        while (m_image.find()) {
            img = m_image.group();
            Matcher m = pattern.matcher(img);
            while (m.find()) {
                String tempSelected = m.group(1);
                images.add(tempSelected);
            }
        }

        return images;
    }

    /**
     * @return :获取初始显示的页面
     */
    private TxtPage getCurPage(int pos) {
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageChange(pos);
        }
        if (pos >= mCurPageList.size()) {
            return mCurPageList.get(mCurPageList.size() - 1);
        }
        return mCurPageList.get(pos);
    }

    /**
     * @return :获取上一个页面
     */
    private TxtPage getPrevPage() {
        int pos = mCurPage.position - 1;
        if (pos < 0) {
            return null;
        }
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageChange(pos);
        }
        return mCurPageList.get(pos);
    }

    /**
     * @return :获取下一的页面
     */
    private TxtPage getNextPage() {
        int pos = mCurPage.position + 1;
        if (pos >= mCurPageList.size()) {
            return null;
        }
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageChange(pos);
        }
        return mCurPageList.get(pos);
    }

    /**
     * @return :获取上一个章节的最后一页
     */
    private TxtPage getPrevLastPage() {
        int pos = mCurPageList.size() - 1;

        if (mPageChangeListener != null) {
            mPageChangeListener.onPageChange(pos);
        }

        return mCurPageList.get(pos);
    }

    /**
     * 根据当前状态，决定是否能够翻页
     */
    private boolean canTurnPage() {

        if (!isChapterListPrepare) {
            return false;
        }
        if (mStatus == STATUS_PARSE_ERROR || mStatus == STATUS_PARING) {
            return false;
        } else if (mStatus == STATUS_ERROR) {
            mStatus = STATUS_ERROR;
        }
        return true;
    }


    public int getDisplayHeight() {
        return mDisplayHeight;
    }

    public int getDisplayWidth() {
        return mDisplayWidth;
    }

    public TextPaint getTextPaint() {
        return mTextPaint;
    }

    public RectF getBookshelfRectF() {
        return mBookshelfRectF;
    }
}
