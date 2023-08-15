package com.easy.example.ui.jetpack.room

import android.app.Activity
import android.content.Intent
import com.easy.core.ui.base.BaseDataBindingActivity
import com.easy.example.R
import com.easy.example.databinding.ActivityRoomBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomActivity() : BaseDataBindingActivity<ActivityRoomBinding>() {
    companion object {
        fun open(context: Activity) {
            context.startActivityForResult(Intent(context, RoomActivity::class.java), -1)
        }
    }

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var studentDao: StudentDao

    override fun initView() {
        userDao.insertAll(User("名称1", "名称2"))
        studentDao.insertAll(Student("1班级", "15"))
        val user = userDao.getAll()[0]
        val student = studentDao.getAll().first()
        binding.textView27.text = user.lastName + "--" + user.firstName + " " + student.grade + "   " + student.age
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_room
    }
}