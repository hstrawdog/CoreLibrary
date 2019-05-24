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
import com.hqq.coreapp.ui.activity.TestDialogActivity;
import com.hqq.coreapp.ui.activity.TextViewSizeActivity;
import com.hqq.coreapp.ui.activity.ThrowActivity;
import com.hqq.coreapp.ui.activity.ToolBarActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary
 * @FileName :   MainActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class MainActivity extends BaseListActivity<MainAdapter> {


    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();
    }

    @Override
    public void initData() {
        mAdapter.addData(new MainBean("加载数据", LoadMoreActivity.class));
        mAdapter.addData(new MainBean("标题/状态栏设置", ToolBarActivity.class));
        mAdapter.addData(new MainBean("fragment 加载", IFragmentActivity.class));
        mAdapter.addData(new MainBean("ListActivity 加载", ListActivity.class));
        mAdapter.addData(new MainBean("默认图显示", DefImgActivity.class));
        mAdapter.addData(new MainBean("轮播图", BannerActivity.class));
        mAdapter.addData(new MainBean("权限测试", PermissionActivity.class));
        mAdapter.addData(new MainBean("仿知乎评论列表", BottomSheetActivity.class));
        mAdapter.addData(new MainBean("dialog样式测试", TestDialogActivity.class));
        mAdapter.addData(new MainBean("文字适配测试", TextViewSizeActivity.class));
        mAdapter.addData(new MainBean("1像素大小测试", DimenActivity.class));
        mAdapter.addData(new MainBean("Throw异常测试", ThrowActivity.class));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        startActivity(new Intent(this, mAdapter.getItem(position).getClassName()));
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
