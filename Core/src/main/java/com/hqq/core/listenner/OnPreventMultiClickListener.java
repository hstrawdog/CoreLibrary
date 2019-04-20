package com.hqq.core.listenner;

import android.view.View;

/**
 * ClassName: OnPreventMultiClickListener
 * Package: com.qi.core.listenner
 * Author : zzy
 * Time : 2019/03/27
 * Description : 替换OnClickListener的防止多次点击事件监听
 * History:
 */
public abstract class OnPreventMultiClickListener implements View.OnClickListener{

    /**
     * 两次点击按钮之间的点击间隔不能少于1000毫秒
     */
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    /**
     * 最后一次点击时间
     */
    private static long mLastClickTime;

    /**
     * 替换onClick的点击事件
     * @param v
     */
    public abstract void onMultiClick(View v);

    /**
     * 重写点击事件防止多次点击
     * @param v
     */
    @Override
    public void onClick(View v) {

        long curClickTime = System.currentTimeMillis();
        if((curClickTime - mLastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            mLastClickTime = curClickTime;
            onMultiClick(v);
        }
    }
}
