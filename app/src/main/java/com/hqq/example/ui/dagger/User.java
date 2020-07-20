package com.hqq.example.ui.dagger;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.dagger
 * @FileName :   User
 * @Date : 2020/7/20 0020  上午 11:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class User {

    String name;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
