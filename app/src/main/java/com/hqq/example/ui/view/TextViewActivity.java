package com.hqq.example.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.ResourcesUtils;
import com.hqq.core.utils.TextSpannableBuilder;
import com.hqq.core.utils.ToastUtils;
import com.hqq.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.view
 * @FileName :   TextViewActivity
 * @Date : 2020/6/9 0009  下午 3:34
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class TextViewActivity extends BaseActivity {
    @BindView(R.id.textView8)
    TextView mTextView8;
    @BindView(R.id.textView9)
    TextView mTextView9;
    @BindView(R.id.textView10)
    TextView mTextView10;

    public static void open(Context context) {
        Intent starter = new Intent(context, TextViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_text_view;
    }

    @Override
    public void initView() {
        mTextView10.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView10.setHighlightColor(ResourcesUtils.getColor(R.color.transparent));
        mTextView10.setText(new TextSpannableBuilder()
                .addTextPart("12312312312312312")
                .addTextPart("sddsfgsdfgsdfsgd", ResourcesUtils.getColor(R.color.color_main), new TextSpannableBuilder.OnClickListener() {

                    @Override
                    public void onClick(View widget, CharSequence clickedText) {
                        ToastUtils.showToast("sddsfgsdfgsdfsgd");
                    }
                })
                .addTextPart("12312312312312312")
                .addTextPart("12312312312312312")
                .build());


    }


}
