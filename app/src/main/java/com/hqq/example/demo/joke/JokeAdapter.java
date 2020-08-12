package com.hqq.example.demo.joke;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.joke
 * @FileName :   JokeAdapter
 * @Date : 2020/8/5 0005  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class JokeAdapter extends BaseQuickAdapter<Joke.DataBean, BaseViewHolder> implements LoadMoreModule {
    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Joke.DataBean item) {
        helper.setText(R.id.textView19, item.getContent());
        helper.setText(R.id.textView20, item.getUpdatetime());
    }
}
