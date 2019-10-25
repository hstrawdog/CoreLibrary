package com.hqq.example.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hqq.example.app.R;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.adapter
 * @FileName :   StringAdapter
 * @Date : 2019/4/29 0029  下午 2:23
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class StringAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public StringAdapter() {
        super(R.layout.item_main);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);
    }
}
