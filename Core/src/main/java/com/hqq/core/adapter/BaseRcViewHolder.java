package com.hqq.core.adapter;

import android.support.annotation.Keep;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @Author : huangqiqiang
 * @Package : com.core.sellercenter.adapter
 * @FileName :   BaseRcViewHolder
 * @Date : 2018/11/30 0030  下午 1:18
 * @Descrive : 用于RecycleView 的嵌套 Adapter也可以复用
 * @Email :
 */
@Keep
public class BaseRcViewHolder<T extends BaseQuickAdapter> extends BaseViewHolder {

    BaseQuickAdapter mBaseQuickAdapter;

    public BaseQuickAdapter getBaseQuickAdapter() {
        return mBaseQuickAdapter;
    }

    public void setBaseQuickAdapter(BaseQuickAdapter baseQuickAdapter) {
        mBaseQuickAdapter = baseQuickAdapter;
    }

    public BaseRcViewHolder(View view) {
        super(view);
    }
}
