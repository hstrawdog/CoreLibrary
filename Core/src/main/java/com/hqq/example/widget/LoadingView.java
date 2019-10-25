/*
 * Copyright (c) 2017. auth huangqiqiang Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hqq.example.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hqq.example.R;




 /**
  * @Author : huangqiqiang
  * @Package : com.qi.core.widget
  * @FileName :   LoadingView
  * @Date  : 2017-02-11 22:02
  * @Email :  qiqiang213@gmail.com
  * @Descrive : loadingView 
  */
public class LoadingView extends Dialog {
    private TextView tvMsg;
    Context mContext;


    @Override
    public void dismiss() {
        if (this == null) {
            return;
        }
        super.dismiss();
    }

    public LoadingView(Context context) {
        this(context, R.style.LoadingDialogStyle);
    }

    String mMsg = "正在加载中";

    public LoadingView(Context context, String msg) {
        this(context, R.style.LoadingDialogStyle);
        mMsg = msg;
    }

    public LoadingView(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.loading_layout);
         this.tvMsg = (TextView) findViewById(R.id.tv_msg);
         tvMsg.setText(mMsg);
         //这里我选了最高级
        // getWindow().setType(WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL);
     }

    public void setBg() {
        ((ImageView) this.findViewById(R.id.rl_loading)).setBackgroundResource(R.color.transparent);
    }

    public void setTipMsg(String msg) {
        this.tvMsg.setText(msg);
    }

    public void setTipMsg(int msg) {
        this.tvMsg.setText(msg);
    }

//    public void start() {
//        this.rlContent.setVisibility(0);
//        if(this.circleAnim != null) {
//            this.ivLoading.startAnimation(this.circleAnim);
//        }
//
//    }
//
//    public void stop() {
//        if(this.ivLoading != null) {
//            this.ivLoading.clearAnimation();
//        }
//
//        this.rlContent.setVisibility(8);
//    }
}
