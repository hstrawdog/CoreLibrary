package com.hqq.example.demo.news;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.news
 * @FileName :   NewsAdapter
 * @Date : 2020/8/5 0005  上午 10:37
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class NewsAdapter extends BaseQuickAdapter<News.DataBean, BaseViewHolder> {
    public NewsAdapter() {
        super(R.layout.item_news);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, News.DataBean item) {
        helper.setText(R.id.textView18, item.getTitle());
    }
}
