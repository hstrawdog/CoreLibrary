package com.easy.example.ui.crash;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.easy.core.CoreConfig;
import com.easy.example.ui.KillerActivity;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2020/02/03
 *    desc   : Crash 处理类
 */
public final class CrashHandler implements Thread.UncaughtExceptionHandler {

    /** Crash 文件名 */
    private static final String CRASH_FILE_NAME = "crash_file";
    /** Crash 时间记录 */
    private static final String KEY_CRASH_TIME = "key_crash_time";
    
    /**
     * 注册 Crash 监听
     */
    public static void register(Application application) {
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(application));
    }
    
    private final Application mApplication;
    private final Thread.UncaughtExceptionHandler mNextHandler;

    private CrashHandler(Application application) {
        mApplication = application;
        mNextHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (getClass().getName().equals(mNextHandler.getClass().getName())) {
            // 请不要重复注册 Crash 监听
            throw new IllegalStateException("are you ok?");
        }
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        SharedPreferences sharedPreferences = mApplication.getSharedPreferences(CRASH_FILE_NAME, Context.MODE_PRIVATE);
        long currentCrashTime = System.currentTimeMillis();
        long lastCrashTime = sharedPreferences.getLong(KEY_CRASH_TIME, 0);
        // 记录当前崩溃的时间，以便下次崩溃时进行比对
        sharedPreferences.edit().putLong(KEY_CRASH_TIME, currentCrashTime).commit();

        // 致命异常标记：如果上次崩溃的时间距离当前崩溃小于 5 分钟，那么判定为致命异常
        boolean deadlyCrash = currentCrashTime - lastCrashTime < 1000 * 60 * 5;

        // 如果是致命的异常，或者是调试模式下
        if (deadlyCrash || CoreConfig.Companion.get().isDebug()) {
//            CrashDetailActivity.start(mApplication, throwable);
            RestartActivity.start(mApplication,throwable);
        } else {
            restart();
        }

        // 不去触发系统的崩溃处理（com.android.internal.os.RuntimeInit$KillApplicationHandler）
        if (mNextHandler != null && !mNextHandler.getClass().getName().startsWith("com.android.internal.os")) {
            mNextHandler.uncaughtException(thread, throwable);
        }

        // 杀死进程（这个事应该是系统干的，但是它会多弹出一个崩溃对话框，所以需要我们自己手动杀死进程）
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
    public static void restart() {
//        Intent intent;
//        intent = new Intent(context, MainActivity.class);
//        if (!(context instanceof Activity)) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        context.startActivity(intent);


        KillerActivity.Companion.launch((FragmentActivity) CoreConfig.Companion.get().getCurrActivity());



//        Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//        LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(LaunchIntent);
    }




}