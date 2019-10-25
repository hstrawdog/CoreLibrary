package com.hqq.example.app.ui.recycle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hqq.example.app.R;
import com.hqq.example.app.adapter.FullPagerSnapAdapter;
import com.hqq.example.recycler.PagerSnapHelper;
import com.hqq.example.ui.BaseActivity;
import com.hqq.example.widget.divider.Divider;
import com.hqq.example.widget.divider.DividerBuilder;
import com.hqq.example.widget.divider.DividerItemDecoration;

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
