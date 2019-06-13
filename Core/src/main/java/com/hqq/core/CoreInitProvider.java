package com.hqq.core;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hqq.core.utils.RegexUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core
 * @FileName :   CoreInitProvider
 * @Date : 2019/6/6 0006  上午 9:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class CoreInitProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        /**
         * 默认初始化
         */
        if (RegexUtils.checkNull(CoreBuildConfig.getInstance().getApplication())) {
            CoreBuildConfig.getInstance().init((Application) getContext(), true);
        }

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
