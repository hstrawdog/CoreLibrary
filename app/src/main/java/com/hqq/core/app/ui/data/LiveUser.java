package com.hqq.core.app.ui.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.data
 * @FileName :   LiveUser
 * @Date : 2019/10/23 0023  下午 2:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class LiveUser extends MutableLiveData<User> {

    private static LiveUser mLiveUser;

    public LiveUser(Context context) {

    }


    public static LiveUser getInstance(Context context) {
        if (mLiveUser == null) {
            mLiveUser = new LiveUser(context);
        }
        return mLiveUser;
    }


}
