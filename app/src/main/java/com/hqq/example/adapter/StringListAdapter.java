package com.hqq.example.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hqq.example.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.adapter
 * @FileName :   StringListAdapter
 * @Date : 2019/10/28 0028  下午 1:42
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class StringListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public StringListAdapter() {
        super(R.layout.item_main);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);

    }
}
