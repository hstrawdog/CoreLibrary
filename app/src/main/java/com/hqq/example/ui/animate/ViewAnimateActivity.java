package com.hqq.example.ui.animate;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.R;

public class ViewAnimateActivity extends BaseActivity {


    private android.widget.TextView mTextView6;

    @Override
    public int getMLayoutViewId() {
        return R.layout.activity_view_animate;
    }

    @Override
    public void initView() {

        mTextView6 = findViewById(R.id.textView6);
        mTextView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTextView6.animate().alpha(0).setDuration(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        mTextView6.animate().alpha(1);
                    }
                });

            }
        });

        findViewById(R.id.textView7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Animation animation = new AlphaAnimation(1F, 0F);
                animation.setDuration(400);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        findViewById(R.id.textView7).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                findViewById(R.id.textView7).startAnimation(animation);
                findViewById(R.id.textView7).setVisibility(View.GONE);


            }
        });

    }
}
