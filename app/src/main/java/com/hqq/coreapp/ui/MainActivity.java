package com.hqq.coreapp.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hqq.core.ui.BaseListActivity;
import com.hqq.core.utils.ToastUtils;
import com.hqq.coreapp.adapter.MainAdapter;
import com.hqq.coreapp.bean.MainBean;
import com.hqq.coreapp.ui.activity.BannerActivity;
import com.hqq.coreapp.ui.activity.BottomSheetActivity;
import com.hqq.coreapp.ui.activity.DefImgActivity;
import com.hqq.coreapp.ui.activity.DimenActivity;
import com.hqq.coreapp.ui.activity.IFragmentActivity;
import com.hqq.coreapp.ui.activity.ListActivity;
import com.hqq.coreapp.ui.activity.LoadMoreActivity;
import com.hqq.coreapp.ui.activity.PermissionActivity;
import com.hqq.coreapp.ui.activity.SearchBarActivity;
import com.hqq.coreapp.ui.activity.TextViewSizeActivity;
import com.hqq.coreapp.ui.activity.ToolBarActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary
 * @FileName :   MainActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :
 */
public class MainActivity extends BaseListActivity<MainAdapter> {


    @Override
    protected MainAdapter getRcAdapter() {
        return new MainAdapter();
    }

    @Override
    protected void initData() {
        mAdapter.addData(new MainBean("1像素大小测试", DimenActivity.class));
        mAdapter.addData(new MainBean("加载数据", LoadMoreActivity.class));
        mAdapter.addData(new MainBean("标题/状态栏设置", ToolBarActivity.class));
        mAdapter.addData(new MainBean("搜索/自定义标题栏", SearchBarActivity.class));
        mAdapter.addData(new MainBean("fragment 加载", IFragmentActivity.class));
        mAdapter.addData(new MainBean("ListActivity 加载", ListActivity.class));
        mAdapter.addData(new MainBean("默认图显示", DefImgActivity.class));
        mAdapter.addData(new MainBean("Banner", BannerActivity.class));
        mAdapter.addData(new MainBean("PermissionActivity", PermissionActivity.class));
        mAdapter.addData(new MainBean("BottomSheetActivity", BottomSheetActivity.class));
        mAdapter.addData(new MainBean("TextViewSizeActivity", TextViewSizeActivity.class));

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        goActivity(mAdapter.getItem(position).getClassName());
    }


    private long exitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(mHomeIntent);
                // 退出程序
                System.exit(0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
