package com.hqq.example.ui.crash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import com.hqq.core.ui.base.BaseViewBindingActivity;
import com.hqq.example.databinding.ActivityRestartBinding;
import com.hqq.example.ui.MainActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.utils
 * @FileName :   RestartActivity
 * @Date : 2021/5/31 0031  下午 3:22
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
public final class RestartActivity extends BaseViewBindingActivity<ActivityRestartBinding> {

    public static void start(Context context, Throwable throwable) {
        Intent intent = new Intent(context, RestartActivity.class);
        intent.putExtra("Throwable", throwable);
        context.startActivity(intent);
    }

    /** 报错代码行数正则表达式 */
    private static final Pattern CODE_REGEX = Pattern.compile("\\(\\w+\\.\\w+:\\d+\\)");
    private String mStackTrace;

    @Override
    public void initView() {
        Throwable throwable = (Throwable) getIntent().getSerializableExtra("Throwable");
        if (throwable == null) {
            return;
        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        if (cause != null) {
            cause.printStackTrace(printWriter);
        }
        mStackTrace = stringWriter.toString();
        Matcher matcher = CODE_REGEX.matcher(mStackTrace);
        SpannableStringBuilder spannable = new SpannableStringBuilder(mStackTrace);
        if (spannable.length() > 0) {
            for (int index = 0; matcher.find(); index++) {
                // 不包含左括号（
                int start = matcher.start() + "(".length();
                // 不包含右括号 ）
                int end = matcher.end() - ")".length();
                // 设置前景
                spannable.setSpan(new ForegroundColorSpan(index < 3 ? 0xFF287BDE : 0xFF999999), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                // 设置下划线
                spannable.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            binding.tvCrashMessage.setText(spannable);
        }

        binding.button57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart(getActivity());
            }
        });


    }

    public static void restart(Context context) {
        Intent intent;
        intent = new Intent(context, MainActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


}