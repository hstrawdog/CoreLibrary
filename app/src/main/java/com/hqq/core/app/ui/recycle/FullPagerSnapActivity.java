package com.hqq.core.app.ui.recycle;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hqq.core.app.R;
import com.hqq.core.app.adapter.FullPagerSnapAdapter;
import com.hqq.core.recycler.PagerSnapHelper;
import com.hqq.core.ui.BaseActivity;
import com.hqq.core.widget.divider.Divider;
import com.hqq.core.widget.divider.DividerBuilder;
import com.hqq.core.widget.divider.DividerItemDecoration;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity.bannner
 * @FileName :   FullPagerSnapActivity
 * @Date : 2019/6/18 0018  上午 9:50
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class FullPagerSnapActivity extends BaseActivity {

    @BindView(R.id.rc_list)
    RecyclerView mRcList;

    @Override
    public int setViewId() {
        return R.layout.activity_full_pager_snap;
    }

    FullPagerSnapAdapter mFullPagerSnapAdapter;

    @Override
    public void initView() {
        mRcList.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false));
        mFullPagerSnapAdapter = new FullPagerSnapAdapter();
        mRcList.setAdapter(mFullPagerSnapAdapter);
        for (int i = 0; i < 35; i++) {
            mFullPagerSnapAdapter.addData("item " + i);
        }

        new PagerSnapHelper(6).attachToRecyclerView(mRcList);

        mRcList.addItemDecoration(new DividerItemDecoration() {
            @Nullable
            @Override
            public Divider getDivider(RecyclerView parent, int itemPosition) {
                DividerBuilder divider;
                divider = new DividerBuilder();
                divider.setRightSideLine(mActivity, R.color.color_main, R.dimen.x5);
                divider.setBottomSideLine(mActivity, R.color.color_main, R.dimen.x5);
                return divider.create();
            }
        });

    }

}
