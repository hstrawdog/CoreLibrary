package com.hqq.example.ui.skin;

import android.content.Context;
import android.content.Intent;

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.skin
 * @FileName :   SkinCActivity
 * @Date : 2020/4/10  上午10:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class SkinCActivity extends BaseSkinActivity {
    public static void open(Context context) {
        Intent starter = new Intent(context, SkinCActivity.class);
        context.startActivity(starter);
    }
}
