package com.hqq.album.common;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.common
 * @FileName :   Album
 * @Date : 2020/6/30 0030  上午 10:13
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class Album {


    public static Album from(Activity activity) {
        return new Album(activity);
    }

    public static Album from(Fragment fragment) {
        return new Album(fragment.getActivity(), fragment);
    }


    private final WeakReference<Activity> mContext;

    private final WeakReference<Fragment> mFragment;

    private Album(Activity activity) {
        this(activity, null);
    }

    private Album(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private Album(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    public FunctionOptions choose(int valueTypeImage) {
        return FunctionOptions.getInstance().setAlbum(this).setAlbumType(valueTypeImage);
    }

    public Activity getActivity() {
        return mContext.get();
    }

    public Fragment getFragment() {
        if (mFragment != null) {
            return mFragment.get();
        }
        return null;
    }
}
