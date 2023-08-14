package com.qq.readbook.weight.page.loader;


import com.hqq.core.utils.DateUtils;
import com.hqq.core.utils.TimeUtils;
import com.qq.readbook.room.entity.Book;
import com.qq.readbook.room.entity.BookContent;
import com.qq.readbook.room.entity.Chapter;
import com.qq.readbook.room.RoomUtils;
import com.qq.readbook.weight.page.PageView;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.weight.page.loader
 * @FileName :   NetPageLoader
 * @Date : 2020/12/15 0015  上午 9:04
 * @Email :  qiqiang213@gmail.com
 * @Describe : 网络页面加载器
 */
public class NetPageLoader extends PageLoader {

    public NetPageLoader(PageView pageView, Book collBook) {
        super(pageView, collBook);
    }

    @Override
    public void refreshChapterList() {
        if (mCollBook.getBookChapterList() == null) {
            return;
        }
//         将 BookChapter 转换成当前可用的 Chapter
//        mChapterList = convertTxtChapter(mCollBook.getBookChapters());
        mChapterList = mCollBook.getBookChapterList();
        isChapterListPrepare = true;
        // 目录加载完成，执行回调操作。
        if (mPageChangeListener != null) {
            mPageChangeListener.onCategoryFinish(mChapterList);
        }
        // 如果章节未打开
        if (!isChapterOpen()) {
            // 打开章节
            openChapter();
        }
    }

    @Override
    protected BufferedReader getChapterReader(Chapter chapter) throws Exception {
        BookContent bookContent = RoomUtils.INSTANCE.getChapterDataBase(mCollBook.getName() + "_" + mCollBook.getAuthor()).bookContentDao().getContent(chapter.getNumber());
        if (bookContent == null || bookContent.getContent().isEmpty()) {
            return null;
        }
        Reader reader = new StringReader(bookContent.getContent());
        return new BufferedReader(reader);
    }

    /**
     * 判断 是否有缓存数据
     *
     * @param chapter
     * @return
     */
    @Override
    protected boolean hasChapterData(Chapter chapter) {
        //单独从数据库中查询
        BookContent bookContent = RoomUtils.INSTANCE.getChapterDataBase(mCollBook.getName() + "_" + mCollBook.getAuthor()).bookContentDao().getContent(chapter.getNumber());
        if (bookContent != null && bookContent.getContent() != null && !bookContent.getContent().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 装载上一章节的内容
     *
     * @return
     */
    @Override
    boolean parsePrevChapter() {
        boolean isRight = super.parsePrevChapter();
        if (mStatus == STATUS_FINISH) {
            loadPrevChapter();
        } else if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }
        return isRight;
    }

    /**
     * 装载当前章内容。
     *
     * @return
     */
    @Override
    boolean parseCurChapter() {
        boolean isRight = super.parseCurChapter();

        if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }
        return isRight;
    }

    /**
     * 装载下一章节的内容
     *
     * @return
     */
    @Override
    boolean parseNextChapter() {
        boolean isRight = super.parseNextChapter();

        if (mStatus == STATUS_FINISH) {
            loadNextChapter();
        } else if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }

        return isRight;
    }

    /**
     * 加载当前页的前面两个章节
     */
    private void loadPrevChapter() {
        if (mPageChangeListener != null) {
            int end = mCurChapterPos;
            int begin = end - 2;
            if (begin < 0) {
                begin = 0;
            }

            requestChapters(begin, end);
        }
    }

    /**
     * 加载前一页，当前页，后一页。
     */
    private void loadCurrentChapter() {
        if (mPageChangeListener != null) {
            int begin = mCurChapterPos;
            int end = mCurChapterPos;

            // 是否当前不是最后一章
            if (end < mChapterList.size()) {
                end = end + 1;
                if (end >= mChapterList.size()) {
                    end = mChapterList.size() - 1;
                }
            }

            // 如果当前不是第一章
            if (begin != 0) {
                begin = begin - 1;
                if (begin < 0) {
                    begin = 0;
                }
            }

            requestChapters(begin, end);
        }
    }

    /**
     * 加载当前页的后两个章节
     */
    private void loadNextChapter() {
        if (mPageChangeListener != null) {

            // 提示加载后两章
            int begin = mCurChapterPos + 1;
            int end = begin + 1;

            // 判断是否大于最后一章
            if (begin >= mChapterList.size()) {
                // 如果下一章超出目录了，就没有必要加载了
                return;
            }

            if (end > mChapterList.size()) {
                end = mChapterList.size() - 1;
            }

            requestChapters(begin, end);
        }
    }

    private void requestChapters(int start, int end) {
        // 检验输入值
        if (start < 0) {
            start = 0;
        }
        if (end >= mChapterList.size()) {
            end = mChapterList.size() - 1;
        }
        List<Chapter> chapters = new ArrayList<>();
        // 过滤，哪些数据已经加载了
        for (int i = start; i <= end; ++i) {
            Chapter txtChapter = mChapterList.get(i);
            if (!hasChapterData(txtChapter)) {
                chapters.add(txtChapter);
            }
        }
        if (!chapters.isEmpty()) {
            mPageChangeListener.requestChapters(chapters);
        }
    }

    @Override
    public void saveRecord() {
        super.saveRecord();
        if (mCollBook != null && isChapterListPrepare) {
            //表示当前CollBook已经阅读
            mCollBook.setUpdate(false);
            mCollBook.setLastRead(TimeUtils.INSTANCE.getNowDate());
            //更新阅读时间
            RoomUtils.INSTANCE.getBookDao().update(mCollBook);
        }
    }
}

