package com.hqq.core.utils;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.hqq.core.CoreConfig;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @Date : 下午 3:54
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class StringUtils {

    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    public static void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    public static void copyContentToClipboard(String content) {
        copyContentToClipboard(content, CoreConfig.Companion.getApplicationContext());
    }

}
