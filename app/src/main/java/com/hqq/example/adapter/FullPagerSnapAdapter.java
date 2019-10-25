package com.hqq.example.adapter;

import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hqq.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.adapter
 * @FileName :   FullPagerSnapAdapter
 * @Date : 2019/6/18 0018  上午 9:53
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class FullPagerSnapAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int mScreenWidth;
    int itemCountPerRowOrColumn = 3;
    List<Integer> mIntegers;

    public FullPagerSnapAdapter() {
        super(R.layout.item_full_pager_snap);
        mIntegers = new ArrayList<>();
        mIntegers.add(R.color.colorPrimaryDark);
        mIntegers.add(R.color.colorPrimary);
        mIntegers.add(R.color.colorAccent);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) helper.itemView.getLayoutParams();
        layoutParams.width = (mScreenWidth) / itemCountPerRowOrColumn;
        layoutParams.height = layoutParams.width * 4 / 3;
        helper.itemView.setLayoutParams(layoutParams);

        helper.itemView.setBackgroundResource(mIntegers.get((int) ((Math.random() * 100) % 3)));


        helper.setText(R.id.tv_item, item);


    }
}
