package com.hqq.core.permission;

import android.content.Context;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   IPermissions
 * @Date : 2018/11/22 0022  上午 9:53
 * @Descrive :
 * @Email :
 */
public interface IPermissions {
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
     * 来源：掘金
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */



    boolean hasPermission(Context  context, String... permissions);

    boolean hasCalendar(Context context);

    boolean hasCamera(Context context);

    boolean hasContacts(Context context);

    boolean hasLocation(Context context);

    boolean hasMicrophone(Context context);

    boolean hasPhone(Context context);

    boolean hasSensors(Context context);

    boolean hasSMS(Context context);

    boolean hasStorage(Context context);
}
