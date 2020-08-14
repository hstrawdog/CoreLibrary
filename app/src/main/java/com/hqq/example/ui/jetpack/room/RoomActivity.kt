package com.hqq.example.ui.jetpack.room

import android.app.Activity
import android.content.Intent
import com.hqq.core.ui.binding.BaseBindingActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityRoomBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomActivity(override val layoutId: Int = R.layout.activity_room) : BaseBindingActivity<ActivityRoomBinding>() {
    companion object {
        fun open(context: Activity) {
            context.startActivityForResult(Intent(context, RoomActivity::class.java), -1)
        }
    }

    @Inject
    lateinit var userDao: UserDao
    override fun initView() {


        userDao.insertAll(User(1, "名称1", "名称2"))
        var user = userDao.getAll().get(0)
        mBinding?.textView27?.setText(user.lastName + "--" + user.firstName)
    }
}