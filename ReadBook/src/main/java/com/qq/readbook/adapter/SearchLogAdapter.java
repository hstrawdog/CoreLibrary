package com.qq.readbook.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hqq.core.utils.DateUtils;
import com.hqq.core.utils.TimeTool;
import com.qq.readbook.R;
import com.qq.readbook.room.entity.LocalSearchKey;

import org.jetbrains.annotations.NotNull;

/**
 * @version V1.0 <描述当前版本功能>
 * 在此写用途
 * @author: huangqiqiang
 * @FileName: com.qq.readbook.adapter.SearchLogAdapter.java
 * @emain: 593979591@qq.com
 * @date: 2021-02-27 17:01
 */
public class SearchLogAdapter extends BaseQuickAdapter<LocalSearchKey, BaseViewHolder> {
    public SearchLogAdapter() {
        super(R.layout.item_search_log);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LocalSearchKey localSearchKey) {
        baseViewHolder.setText(R.id.tv_key, localSearchKey.getKey());
        baseViewHolder.setText(R.id.tv_update_time, TimeTool.INSTANCE.formatData(localSearchKey.getSearchTime()));
    }
}
