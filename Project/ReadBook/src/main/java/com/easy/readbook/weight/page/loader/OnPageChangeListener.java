package com.easy.readbook.weight.page.loader;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.weight.page
 * @Date : 下午 4:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */

import com.easy.readbook.room.entity.Chapter;
import com.easy.readbook.room.entity.Chapter;
import com.easy.readbook.room.entity.Chapter;

import java.util.List;


public interface OnPageChangeListener {
    /**
     * 作用：章节切换的时候进行回调
     *
     * @param pos:切换章节的序号
     */
    void onChapterChange(int pos);

    /**
     * 作用：请求加载章节内容
     *
     * @param requestChapters:需要下载的章节列表
     */
    void requestChapters(List<Chapter> requestChapters);

    /**
     * 作用：章节目录加载完成时候回调
     *
     * @param chapters：返回章节目录
     */
    void onCategoryFinish(List<Chapter> chapters);

    /**
     * 作用：章节页码数量改变之后的回调。==> 字体大小的调整，或者是否关闭虚拟按钮功能都会改变页面的数量。
     *
     * @param count:页面的数量
     */
    void onPageCountChange(int count);

    /**
     * 作用：当页面改变的时候回调
     *
     * @param pos:当前的页面的序号
     */
    void onPageChange(int pos);
}

