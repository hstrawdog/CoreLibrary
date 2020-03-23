package com.hqq.example.ui.animate;

import android.view.View;

import com.hqq.core.ui.BaseActivity;
import com.hqq.example.R;

public class ViewAnimateActivity extends BaseActivity {


    private android.widget.TextView mTextView6;

    @Override
    public int setViewId() {
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
    }
}
