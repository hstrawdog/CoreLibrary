package com.easy.example.ui.jetpack.package1;

import com.easy.example.ui.jetpack.livedata.User;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.package1
 * @FileName :   UserDataRepository
 * @Date : 2020/7/21 0021  下午 4:19
 * @Email : qiqiang213@gmail.com
 * @Describe :  数据源
 */
class UserDataRepository {


    public User getUser() {
        return User.newInstance();
    }



}
