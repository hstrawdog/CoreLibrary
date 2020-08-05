package com.hqq.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.databinding.ViewDataBinding;

import com.hqq.core.ui.vm.BaseListViewModel;
import com.hqq.core.ui.vm.BaseVmListActivity;
import com.hqq.core.utils.ToastUtils;
import com.hqq.example.adapter.MainAdapter;
import com.hqq.example.bean.MainBean;
import com.hqq.example.demo.joke.JokeActivity;
import com.hqq.example.demo.news.NewsActivity;
import com.hqq.example.demo.weather.WeatherActivity;
import com.hqq.example.ui.adaptation.AdaptationIndexActivity;
import com.hqq.example.ui.adaptation.DefImgActivity;
import com.hqq.example.ui.adaptation.PermissionActivity;
import com.hqq.example.ui.bar.ToolBarActivity;
import com.hqq.example.ui.customize.CustomizeIndexActivity;
import com.hqq.example.ui.dialog.TestDialogActivity;
import com.hqq.example.ui.exception.ThrowActivity;
import com.hqq.example.ui.info.BaseInfoActivity;
import com.hqq.example.ui.info.FilePathActivity;
import com.hqq.example.ui.jetpack.livedata.LiveDateActivity;
import com.hqq.example.ui.jetpack.package1.MvvmTestActivity;
import com.hqq.example.ui.launch.mode.SingleInstanceActivity;
import com.hqq.example.ui.recycle.RecycleIndexActivity;
import com.hqq.example.ui.skin.SkinAActivity;
import com.hqq.example.ui.transitions.animation.TransitionsAnimationActivity;
import com.hqq.example.ui.view.BlackAndWhiteActivity;
import com.hqq.example.ui.view.SvgActivity;
import com.hqq.example.ui.web.WebActivity;

import java.util.ArrayList;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary
 * @FileName :   MainActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
public class MainActivity extends BaseVmListActivity<ViewDataBinding, MainActivity.MainModel, MainAdapter> {

    @Override
    public MainAdapter initAdapter() {
        return new MainAdapter();
    }


    @Override
    public int getBindingViewModelId() {
        return 0;
    }

    @Override
    public void initData() {
        JokeActivity.open(this);
    }

    private long mExitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
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

    public static class MainModel extends BaseListViewModel {


        @Override
        public void onCrete() {
            super.onCrete();
            ArrayList arrayList = new ArrayList();
            arrayList.add(new MainBean("启动模式", SingleInstanceActivity.class));
            arrayList.add(new MainBean("转场动画", TransitionsAnimationActivity.class));
            arrayList.add(new MainBean("标题/状态栏设置", ToolBarActivity.class));
            arrayList.add(new MainBean("默认图显示", DefImgActivity.class));
            arrayList.add(new MainBean("Recycle 相关", RecycleIndexActivity.class));
            arrayList.add(new MainBean("权限测试", PermissionActivity.class));
            arrayList.add(new MainBean("Dialog系列", TestDialogActivity.class));
            arrayList.add(new MainBean("Shape测试", ShapeTestActivity.class));
            arrayList.add(new MainBean("自定义系列", CustomizeIndexActivity.class));
            arrayList.add(new MainBean("设备信息", BaseInfoActivity.class));
            arrayList.add(new MainBean("网页测试", WebActivity.class));
            arrayList.add(new MainBean("文件路径信息", FilePathActivity.class));
            arrayList.add(new MainBean("适配相关", AdaptationIndexActivity.class));
            arrayList.add(new MainBean("DateBinding测试", LiveDateActivity.class));
            arrayList.add(new MainBean("Throw异常测试", ThrowActivity.class));
            arrayList.add(new MainBean("黑白化测试", BlackAndWhiteActivity.class));
            arrayList.add(new MainBean("换肤测试", SkinAActivity.class));
            arrayList.add(new MainBean("SVG测试", SvgActivity.class));
            setDate(arrayList);
        }
    }
}
