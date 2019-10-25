package com.hqq.example.bean;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.bean
 * @FileName :   MainBean
 * @Date : 2018/11/22 0022  下午 7:45
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
public class MainBean<T extends AppCompatActivity> {
    String title;
    Class< T> className;

    public MainBean(String title, Class<T> activityClass) {
        this.title = title;
        className = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<T> getClassName() {
        return className;
    }

    public void setClassName(Class<T> className) {
        this.className = className;
    }
}
