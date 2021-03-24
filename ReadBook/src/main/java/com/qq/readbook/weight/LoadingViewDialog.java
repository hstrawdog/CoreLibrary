package com.qq.readbook.weight;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.qq.readbook.R;


/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 *          Created by huangqiqiang on 17/2/11.
 * @FileName: com.arogo.media.widget.LoadingView.java
 * Author 黄其强
 * @date: 2017-02-11 22:02
 */


public class LoadingViewDialog extends Dialog {
    private TextView tvMsg;
    Context mContext;

    public LoadingViewDialog(Context context) {
        this(context, R.style.LoadingDialogStyle);
    }
    String mMsg="正在加载中";
    public LoadingViewDialog(Context context, String  msg) {
        this(context, R.style.LoadingDialogStyle);
        mMsg=msg;
    }

    public LoadingViewDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading_layout);
        this.tvMsg = (TextView) findViewById(R.id.tv_msg);
        tvMsg.setText(mMsg);

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
