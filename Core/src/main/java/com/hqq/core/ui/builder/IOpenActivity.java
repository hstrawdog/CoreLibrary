package com.hqq.core.ui.builder;

import com.hqq.core.ui.vm.BaseViewModel;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.builder
 * @FileName :   IOpenActivity
 * @Date : 2020/7/31 0031  下午 3:57
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public interface IOpenActivity {
    /**
     * 暴露出接口 打开Activity
     * Fragment  有可能需要把onActivityResult 回调给Activity
     *
     * @param openActivityComponent
     */
    void openActivity(BaseViewModel.OpenActivityComponent openActivityComponent);
}
