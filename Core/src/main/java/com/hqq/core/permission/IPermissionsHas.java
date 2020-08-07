package com.hqq.core.permission;

import android.Manifest;
import android.content.Context;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   IPermissionsHas
 * @Date : 2018/11/22 0022  上午 9:53
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public interface IPermissionsHas {

    /**
     * <!-- 危险权限 start -->
     * <!--PHONE-->
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * <uses-permission android:name="android.permission.CALL_PHONE"/>
     * <uses-permission android:name="android.permission.READ_CALL_LOG"/>
     * <uses-permission android:name="android.permission.ADD_VOICEMAIL"/>
     * <uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
     * <uses-permission android:name="android.permission.USE_SIP"/>
     * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
     * •	READ_PHONE_STATE
     * •	CALL_PHONE
     * •	READ_CALL_LOG
     * •	WRITE_CALL_LOG
     * •	ADD_VOICEMAIL
     * •	USE_SIP
     * •	PROCESS_OUTGOING_CALLS
     * <!--CALENDAR-->
     * <uses-permission android:name="android.permission.READ_CALENDAR"/>
     * <uses-permission android:name="android.permission.WRITE_CALENDAR"/>
     * * •	READ_CALENDAR
     * * •	WRITE_CALENDAR
     * <!--CAMERA-->
     * <uses-permission android:name="android.permission.CAMERA"/>
     * •	CAMERA
     * <!--CONTACTS-->
     * <uses-permission android:name="android.permission.READ_CONTACTS"/>
     * <uses-permission android:name="android.permission.WRITE_CONTACTS"/>
     * <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
     * <!--LOCATION-->
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
     * <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"/>
     * * •	ACCESS_FINE_LOCATION
     * * •	ACCESS_COARSE_LOCATION
     * <!--MICROPHONE-->
     * <uses-permission android:name="android.permission.RECORD_AUDIO"/>
     * * •	RECORD_AUDIO
     * <!--SENSORS-->
     * <uses-permission android:name="android.permission.BODY_SENSORS"/>
     * * •	BODY_SENSORS
     * <!--SMS-->
     * <uses-permission android:name="android.permission.SEND_SMS"/>
     * <uses-permission android:name="android.permission.RECEIVE_SMS"/>
     * <uses-permission android:name="android.permission.READ_SMS"/>
     * <uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"/>
     * <uses-permission android:name="android.permission.RECEIVE_MMS"/>
     * * •	SEND_SMS
     * * •	RECEIVE_SMS
     * * •	READ_SMS
     * * •	RECEIVE_WAP_PUSH
     * * •	RECEIVE_MMS
     * <!--STORAGE-->
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * * •	READ_EXTERNAL_STORAGE
     * * •	WRITE_EXTERNAL_STORAGE
     * <!-- 危险权限 Permissions end -->
     * 链接：https://juejin.im/post/5beaf27fe51d45332a4568e9
     */
    boolean hasPermission(Context context, String... permissions);

    /**
     * 用户日志权限
     *
     * @param context
     * @return
     */
    boolean hasCalendar(Context context);

    static String[] getCalendar() {
        return new String[]{Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR};
    }

    /**
     * 摄像头权限
     *
     * @param context
     * @return
     */
    boolean hasCamera(Context context);

    /**
     * @return
     */
    static String[] getCamera() {

        return new String[]{
                Manifest.permission.CAMERA
        };
    }

    /**
     * 联系人
     * 通讯录
     *
     * @param context
     * @return
     */
    boolean hasContacts(Context context);

    static String[] getContacts() {
        return new String[]{
                //(允许应用访问联系人通讯录信息)
                Manifest.permission.READ_CONTACTS,
                //(写入联系人，但不可读取)
                Manifest.permission.WRITE_CONTACTS,
                //(访问GMail账户列表)
                Manifest.permission.GET_ACCOUNTS
        };
    }

    /**
     * 位置权限
     * 定位
     *
     * @param context
     * @return
     */
    boolean hasLocation(Context context);

    /**
     * 位置权限
     *
     * @return
     */
    static String[] getLocation() {
        return new String[]{
                //(通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米)
                Manifest.permission.ACCESS_COARSE_LOCATION,
                //(通过GPS芯片接收卫星的定位信息，定位精度达10米以内)
                Manifest.permission.ACCESS_FINE_LOCATION,
                // 允许程序访问额外的定位提供者指令
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
        };
    }

    /**
     * 手机麦克风
     *
     * @param context
     * @return
     */
    boolean hasMicrophone(Context context);

    static String[] getMicrophone() {
        return new String[]{
                //(录制声音通过手机或耳机的麦克)
                Manifest.permission.RECORD_AUDIO
        };
    }

    /**
     * 手机信息 拨打电话
     *
     * @param context
     * @return
     */
    boolean hasPhone(Context context);

    static String[] getPhone() {
        return new String[]{   //(访问电话状态)
                Manifest.permission.READ_PHONE_STATE,
                //(允许程序从非系统拨号器里输入电话号码)
                Manifest.permission.CALL_PHONE,
                //(允许应用程序读取用户的通话记录)
                Manifest.permission.READ_CALL_LOG,
                //(允许一个程序写入（但不读取）用户的通话记录资料)
                Manifest.permission.WRITE_CALL_LOG,
                //(允许应用程序添加语音邮件进入系统)
                Manifest.permission.ADD_VOICEMAIL,
                //(允许程序使用SIP视频服务)
                Manifest.permission.USE_SIP,
                //(允许程序监视，修改或放弃播出电话)
                Manifest.permission.PROCESS_OUTGOING_CALLS};
    }

    /**
     * 传感器
     *
     * @param context
     * @return
     */
    boolean hasSensors(Context context);

    static String[] getSensors() {
        return new String[]{
                //(允许从传感器，用户使用来衡量什么是他/她的身体内发生的事情，如心脏速率访问数据的应用程序)
                Manifest.permission.BODY_SENSORS
        };
    }

    /**
     * 短信 彩信
     *
     * @param context
     * @return
     */
    boolean hasSMS(Context context);

    static String[] getSMS() {
        return new String[]{        //(发送短信)
                Manifest.permission.SEND_SMS,
                //(接收短信)
                Manifest.permission.RECEIVE_SMS,
                //(读取短信内容)
                Manifest.permission.READ_SMS,
                //(接收WAP PUSH信息)
                Manifest.permission.RECEIVE_WAP_PUSH,
                //(接收彩信)
                Manifest.permission.RECEIVE_MMS};
    }

    /**
     * 读写权限
     *
     * @param context
     * @return
     */
    boolean hasStorage(Context context);

    /**
     * @return
     */
    static String[] getStorage() {
        return new String[]{
                //(允许程序读取外部存储，如SD卡读文件)
                Manifest.permission.READ_EXTERNAL_STORAGE,
                //(允许程序写入外部存储，如SD卡上写文件)
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }


}
