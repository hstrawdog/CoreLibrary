package com.hqq.coreapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hqq.coreapp.R;
import com.hqq.coreapp.bean.MainBean;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.adapter
 * @FileName :   MainAdapter
 * @Date : 2018/11/22 0022  下午 7:45
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class MainAdapter extends BaseQuickAdapter<MainBean, BaseViewHolder> {
    public MainAdapter() {
        super(R.layout.item_main);
    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    protected void convert(BaseViewHolder helper, MainBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
    }
}
