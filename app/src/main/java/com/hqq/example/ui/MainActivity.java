package com.hqq.example.ui;

import android.content.Intent;
import android.view.KeyEvent;

import com.hqq.core.ui.BaseListActivity;
import com.hqq.core.utils.ToastUtils;
import com.hqq.example.adapter.MainAdapter;
import com.hqq.example.bean.MainBean;
import com.hqq.example.ui.adaptation.AdaptationIndexActivity;
import com.hqq.example.ui.adaptation.DefImgActivity;
import com.hqq.example.ui.adaptation.PermissionActivity;
import com.hqq.example.ui.animate.AnimateIndexActivity;
import com.hqq.example.ui.bar.ToolBarActivity;
import com.hqq.example.ui.customize.CustomizeIndexActivity;
import com.hqq.example.ui.data.DataBindingActivity;
import com.hqq.example.ui.dialog.TestDialogActivity;
import com.hqq.example.ui.exception.ThrowActivity;
import com.hqq.example.ui.info.BaseInfoActivity;
import com.hqq.example.ui.info.FilePathActivity;
import com.hqq.example.ui.launch.mode.SingleInstanceActivity;
import com.hqq.example.ui.recycle.RecycleIndexActivity;
import com.hqq.example.ui.transitions.animation.TransitionsAnimationActivity;
import com.hqq.example.ui.view.TextViewActivity;
import com.hqq.example.ui.web.WebActivity;

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
    public void initDefConfig() {
        super.initDefConfig();
//        mRootViewBuild.setShowToolBar(false);
    }

    @Override
    public void initData() {
        mAdapter.addData(new MainBean("启动模式", SingleInstanceActivity.class));
        mAdapter.addData(new MainBean("转场动画", TransitionsAnimationActivity.class));
        mAdapter.addData(new MainBean("标题/状态栏设置", ToolBarActivity.class));
        mAdapter.addData(new MainBean("默认图显示", DefImgActivity.class));
        mAdapter.addData(new MainBean("Recycle 相关", RecycleIndexActivity.class));
        mAdapter.addData(new MainBean("权限测试", PermissionActivity.class));
        mAdapter.addData(new MainBean("Dialog系列", TestDialogActivity.class));
        mAdapter.addData(new MainBean("Shape测试", ShapeTestActivity.class));
        mAdapter.addData(new MainBean("自定义系列", CustomizeIndexActivity.class));
        mAdapter.addData(new MainBean("设备信息", BaseInfoActivity.class));
        mAdapter.addData(new MainBean("网页测试", WebActivity.class));
        mAdapter.addData(new MainBean("文件路径信息", FilePathActivity.class));
        mAdapter.addData(new MainBean("适配相关", AdaptationIndexActivity.class));
        mAdapter.addData(new MainBean("DateBinding测试", DataBindingActivity.class));
        mAdapter.addData(new MainBean("Throw异常测试", ThrowActivity.class));
//        PermissionActivity.open(this);
        //  startActivity(new Intent(this, EmptyListActivity.class));
        // EditTextActivity.open(this);
//        TemplatesImageActivity.open(this);
//        AnimateIndexActivity.open(this);
        TextViewActivity.open(this);

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
