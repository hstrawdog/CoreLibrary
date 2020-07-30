package com.hqq.example.ui.transitions.animation;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.ui.base.BaseListActivity;
import com.hqq.example.R;
import com.hqq.example.adapter.StringListAdapter;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.transitions.animation
 * @FileName :   TransitionsAnimationActivity
 * @Date : 2019/10/28 0028  下午 1:38
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class TransitionsAnimationActivity extends BaseListActivity<StringListAdapter> {

    public static void open(Activity context) {
        Intent starter = new Intent(context, TransitionsAnimationActivity.class);
        context.startActivityForResult(starter, -1);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_in);

    }

    /**
     *  md风格
     * @param activity
     */
    private static void openMakeCustomAnimation(Activity activity) {
        Intent starter = new Intent(activity, TransitionsAnimationActivity.class);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(activity,
                R.anim.slide_left_in, R.anim.slide_left_in);
        ActivityCompat.startActivity(activity,
                starter, compat.toBundle());
    }

    @Override
    public StringListAdapter initAdapter() {
        return new StringListAdapter();
    }

    @Override
    public void initData() {

        mAdapter.addData("overridePendingTransition");
        mAdapter.addData("makeCustomAnimation");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        switch (position) {
            case 0:
                TransitionsAnimationActivity.open(this);
                break;
            case 1:
                TransitionsAnimationActivity.openMakeCustomAnimation(this);
                break;
            default:
        }
    }


    @Override
    public void initAnimEnter() {

    }

}
