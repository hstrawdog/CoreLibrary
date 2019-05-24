package com.hqq.core.permission;

import android.Manifest;
import android.content.Context;

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.permission
 * @FileName :   IPermissionsImpl
 * @Date : 2018/11/22 0022  上午 9:57
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class IPermissionsImpl implements IPermissions {


    @Override
    public boolean hasPermission(Context context, String... permissions) {
        boolean result = true;
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (Manifest.permission.READ_CALENDAR.equals(permission)
                        || Manifest.permission.WRITE_CALENDAR.equals(permission)) {
                    result = result && hasCalendar(context);
                } else if (Manifest.permission.CAMERA.equals(permission)) {
                    result = result && hasCamera(context);
                } else if (Manifest.permission.READ_CONTACTS.equals(permission)
                        || Manifest.permission.WRITE_CONTACTS.equals(permission)
                        || Manifest.permission.GET_ACCOUNTS.equals(permission)) {
                    result = result && hasContacts(context);
                } else if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission)
                        || Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                    result = result && hasLocation(context);
                } else if (Manifest.permission.RECORD_AUDIO.equals(permission)) {
                    result = result && hasMicrophone(context);
                } else if (Manifest.permission.READ_PHONE_STATE.equals(permission)
                        || Manifest.permission.CALL_PHONE.equals(permission)) {
                    result = result && hasPhone(context);
                } else if (Manifest.permission.BODY_SENSORS.equals(permission)
                        ) {
                    result = result && hasSensors(context);
                } else if (Manifest.permission.SEND_SMS.equals(permission)
                        || Manifest.permission.RECEIVE_SMS.equals(permission)) {
                    result = result && hasSMS(context);
                } else if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)
                        || Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                    result = result && hasStorage(context);
                }
            }
        }
        return result;
    }

    @Override
    public boolean hasCalendar(Context context) {
        return PermissionsFragment.hasPermission(context,
                //(允许程序读取用户的日程信息)
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
        );

    }

    @Override
    public boolean hasCamera(Context context) {
        return PermissionsFragment.hasPermission(context,
                //(允许访问摄像头进行拍照)
                Manifest.permission.CAMERA);
    }

    @Override
    public boolean hasContacts(Context context) {
        return hasStorage(context) && PermissionsFragment.hasPermission(context,
                //(允许应用访问联系人通讯录信息)
                Manifest.permission.READ_CONTACTS,
                //(写入联系人，但不可读取)
                Manifest.permission.WRITE_CONTACTS,
                //(访问GMail账户列表)
                Manifest.permission.GET_ACCOUNTS
        );
    }

    @Override
    public boolean hasLocation(Context context) {
        return hasStorage(context) && PermissionsFragment.hasPermission(context,
                //(通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米)
                Manifest.permission.ACCESS_COARSE_LOCATION,
                //(通过GPS芯片接收卫星的定位信息，定位精度达10米以内)
                Manifest.permission.ACCESS_FINE_LOCATION
        );

    }

    @Override
    public boolean hasMicrophone(Context context) {
        return PermissionsFragment.hasPermission(context,
                //(录制声音通过手机或耳机的麦克)
                Manifest.permission.RECORD_AUDIO);

    }

    @Override
    public boolean hasPhone(Context context) {
        return PermissionsFragment.hasPermission(context,
                //(访问电话状态)
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
                Manifest.permission.PROCESS_OUTGOING_CALLS);

    }

    @Override
    public boolean hasSensors(Context context) {
        return PermissionsFragment.hasPermission(context,
                //(允许从传感器，用户使用来衡量什么是他/她的身体内发生的事情，如心脏速率访问数据的应用程序)
                Manifest.permission.BODY_SENSORS);
    }

    @Override
    public boolean hasSMS(Context context) {
        return PermissionsFragment.hasPermission(context,
                //(发送短信)
                Manifest.permission.SEND_SMS,
                //(接收短信)
                Manifest.permission.RECEIVE_SMS,
                //(读取短信内容)
                Manifest.permission.READ_SMS,
                //(接收WAP PUSH信息)
                Manifest.permission.RECEIVE_WAP_PUSH,
                //(接收彩信)
                Manifest.permission.RECEIVE_MMS
        );
    }

    @Override
    public boolean hasStorage(Context context) {
        return PermissionsFragment.hasPermission(context,
                //(允许程序读取外部存储，如SD卡读文件)
                Manifest.permission.READ_EXTERNAL_STORAGE,
                //(允许程序写入外部存储，如SD卡上写文件)
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}
