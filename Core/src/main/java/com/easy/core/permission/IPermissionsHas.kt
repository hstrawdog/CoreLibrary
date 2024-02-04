package com.easy.core.permission

import android.Manifest
import android.content.Context

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   IPermissionsHas
 * @Date : 2018/11/22 0022  上午 9:53
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
interface IPermissionsHas {
    /**
     *
     *
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
     * <uses-permission android:name="android.permission.CALL_PHONE"></uses-permission>
     * <uses-permission android:name="android.permission.READ_CALL_LOG"></uses-permission>
     * <uses-permission android:name="android.permission.ADD_VOICEMAIL"></uses-permission>
     * <uses-permission android:name="android.permission.WRITE_CALL_LOG"></uses-permission>
     * <uses-permission android:name="android.permission.USE_SIP"></uses-permission>
     * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"></uses-permission>
     * •	READ_PHONE_STATE
     * •	CALL_PHONE
     * •	READ_CALL_LOG
     * •	WRITE_CALL_LOG
     * •	ADD_VOICEMAIL
     * •	USE_SIP
     * •	PROCESS_OUTGOING_CALLS
     *
     * <uses-permission android:name="android.permission.READ_CALENDAR"></uses-permission>
     * <uses-permission android:name="android.permission.WRITE_CALENDAR"></uses-permission>
     * * •	READ_CALENDAR
     * * •	WRITE_CALENDAR
     *
     * <uses-permission android:name="android.permission.CAMERA"/>
     * •	CAMERA
     *
     * <uses-permission android:name="android.permission.READ_CONTACTS"></uses-permission>
     * <uses-permission android:name="android.permission.WRITE_CONTACTS"></uses-permission>
     * <uses-permission android:name="android.permission.GET_ACCOUNTS"></uses-permission>
     *
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"></uses-permission>
     * <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"></uses-permission>
     * * •	ACCESS_FINE_LOCATION
     * * •	ACCESS_COARSE_LOCATION
     *
     * <uses-permission android:name="android.permission.RECORD_AUDIO"></uses-permission>
     * * •	RECORD_AUDIO
     *
     * <uses-permission android:name="android.permission.BODY_SENSORS"></uses-permission>
     * * •	BODY_SENSORS
     *
     * <uses-permission android:name="android.permission.SEND_SMS"></uses-permission>
     * <uses-permission android:name="android.permission.RECEIVE_SMS"></uses-permission>
     * <uses-permission android:name="android.permission.READ_SMS"></uses-permission>
     * <uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"></uses-permission>
     * <uses-permission android:name="android.permission.RECEIVE_MMS"></uses-permission>
     * * •	SEND_SMS
     * * •	RECEIVE_SMS
     * * •	READ_SMS
     * * •	RECEIVE_WAP_PUSH
     * * •	RECEIVE_MMS
     *
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
     * * •	READ_EXTERNAL_STORAGE
     * * •	WRITE_EXTERNAL_STORAGE
     *
     * 链接：https://juejin.im/post/5beaf27fe51d45332a4568e9
     */
    fun hasPermission(context: Context?, vararg permissions: String): Boolean

    /**
     * 用户日志权限
     *
     * @param context
     * @return
     */
    fun hasCalendar(context: Context?): Boolean

    /**
     * 摄像头权限
     *
     * @param context
     * @return
     */
    fun hasCamera(context: Context?): Boolean

    /**
     * 联系人
     * 通讯录
     *
     * @param context
     * @return
     */
    fun hasContacts(context: Context?): Boolean

    /**
     * 位置权限
     * 定位
     *
     * @param context
     * @return
     */
    fun hasLocation(context: Context?): Boolean

    /**
     * 手机麦克风
     *
     * @param context
     * @return
     */
    fun hasMicrophone(context: Context?): Boolean

    /**
     * 手机信息 拨打电话
     *
     * @param context
     * @return
     */
    fun hasPhone(context: Context?): Boolean

    /**
     * 传感器
     *
     * @param context
     * @return
     */
    fun hasSensors(context: Context?): Boolean

    /**
     * 短信 彩信
     *
     * @param context
     * @return
     */
    fun hasSMS(context: Context?): Boolean

    /**
     * 读写权限
     *
     * @param context
     * @return
     */
    fun hasStorage(context: Context?): Boolean

    companion object {
        val calendar: Array<String>
            get() = arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)

        /**
         * @return
         */
        val camera: Array<String>
            get() = arrayOf(Manifest.permission.CAMERA)

        //(允许应用访问联系人通讯录信息)
        //(写入联系人，但不可读取)
        //(访问GMail账户列表)
        val contacts: Array<String>
            get() = arrayOf( //(允许应用访问联系人通讯录信息)
                Manifest.permission.READ_CONTACTS,  //(写入联系人，但不可读取)
                Manifest.permission.WRITE_CONTACTS,  //(访问GMail账户列表)
                Manifest.permission.GET_ACCOUNTS
            ) //(通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米)
        //(通过GPS芯片接收卫星的定位信息，定位精度达10米以内)
        // 允许程序访问额外的定位提供者指令

        /**
         * 位置权限
         *
         * @return
         */
        val location: Array<String>
            get() = arrayOf( //(通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米)
                Manifest.permission.ACCESS_COARSE_LOCATION,  //(通过GPS芯片接收卫星的定位信息，定位精度达10米以内)
                Manifest.permission.ACCESS_FINE_LOCATION,  // 允许程序访问额外的定位提供者指令
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )

        //(录制声音通过手机或耳机的麦克)
        val microphone: Array<String>
            get() = arrayOf( //(录制声音通过手机或耳机的麦克)
                Manifest.permission.RECORD_AUDIO
            )

        //(访问电话状态)
        //(允许程序从非系统拨号器里输入电话号码)
        //(允许应用程序读取用户的通话记录)
        //(允许一个程序写入（但不读取）用户的通话记录资料)
        //(允许应用程序添加语音邮件进入系统)
        //(允许程序使用SIP视频服务)
        //(允许程序监视，修改或放弃播出电话)
        val phone: Array<String>
            get() = arrayOf( //(访问电话状态)
                Manifest.permission.READ_PHONE_STATE,  //(允许程序从非系统拨号器里输入电话号码)
                Manifest.permission.CALL_PHONE,  //(允许应用程序读取用户的通话记录)
                Manifest.permission.READ_CALL_LOG,  //(允许一个程序写入（但不读取）用户的通话记录资料)
                Manifest.permission.WRITE_CALL_LOG,  //(允许应用程序添加语音邮件进入系统)
                Manifest.permission.ADD_VOICEMAIL,  //(允许程序使用SIP视频服务)
                Manifest.permission.USE_SIP,  //(允许程序监视，修改或放弃播出电话)
                Manifest.permission.PROCESS_OUTGOING_CALLS
            )

        //(允许从传感器，用户使用来衡量什么是他/她的身体内发生的事情，如心脏速率访问数据的应用程序)
        val sensors: Array<String>
            get() = arrayOf( //(允许从传感器，用户使用来衡量什么是他/她的身体内发生的事情，如心脏速率访问数据的应用程序)
                Manifest.permission.BODY_SENSORS
            )

        //(发送短信)
        //(接收短信)
        //(读取短信内容)
        //(接收WAP PUSH信息)
        //(接收彩信)
        val sMS: Array<String>
            get() = arrayOf( //(发送短信)
                Manifest.permission.SEND_SMS,  //(接收短信)
                Manifest.permission.RECEIVE_SMS,  //(读取短信内容)
                Manifest.permission.READ_SMS,  //(接收WAP PUSH信息)
                Manifest.permission.RECEIVE_WAP_PUSH,  //(接收彩信)
                Manifest.permission.RECEIVE_MMS
            ) //(允许程序读取外部存储，如SD卡读文件)
        //(允许程序写入外部存储，如SD卡上写文件)

        /**
         * @return
         */
        val storage: Array<String>
            get() = arrayOf( //(允许程序读取外部存储，如SD卡读文件)
                Manifest.permission.READ_EXTERNAL_STORAGE,  //(允许程序写入外部存储，如SD卡上写文件)
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

        /**
         *  蓝牙
         */
        val bluetooth: Array<String>
            get() = arrayOf(
                Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH
            )

        /**
         * 高德定位权限
         */
        val localAround :Array<String>
            get() = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            )

        /**
         * 摄像头和读写存储权限
         */
        val cameraAndStorageAndSystem: Array<String>
            get() = arrayOf(Manifest.permission.CAMERA,
                //(允许程序读取外部存储，如SD卡读文件)
                Manifest.permission.READ_EXTERNAL_STORAGE,  //(允许程序写入外部存储，如SD卡上写文件)
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)


        /**
         * 摄像头和读写存储权限
         */
        val cameraAndStorage: Array<String>
            get() = arrayOf(Manifest.permission.CAMERA,
                //(允许程序读取外部存储，如SD卡读文件)
                Manifest.permission.READ_EXTERNAL_STORAGE,  //(允许程序写入外部存储，如SD卡上写文件)
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}