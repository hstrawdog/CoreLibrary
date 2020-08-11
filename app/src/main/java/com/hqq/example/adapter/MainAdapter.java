package com.hqq.example.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hqq.example.R;
import com.hqq.example.bean.MainBean;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.adapter
 * @FileName :   MainAdapter
 * @Date : 2018/11/22 0022  下午 7:45
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class MainAdapter extends BaseQuickAdapter<MainBean, BaseViewHolder> implements OnItemClickListener, LoadMoreModule {
    public MainAdapter() {
        super(R.layout.item_main);
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
        getContext().startActivity(new Intent(getContext(), getItem(position).getClassName()));
    }

}
