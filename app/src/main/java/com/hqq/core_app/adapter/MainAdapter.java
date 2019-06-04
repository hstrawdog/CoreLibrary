package com.hqq.core_app.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hqq.core_app.R;
import com.hqq.core_app.bean.MainBean;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.adapter
 * @FileName :   MainAdapter
 * @Date : 2018/11/22 0022  下午 7:45
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class MainAdapter extends BaseQuickAdapter<MainBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
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
        setOnItemClickListener(this);

    }

    @Override
    public void setOnItemClick(View v, int position) {
        super.setOnItemClick(v, position);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mContext.startActivity(new Intent(mContext, getItem(position).getClassName()));
    }

}
