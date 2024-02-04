package com.easy.readbook.ui.book

import android.app.Activity
import android.content.Intent
import com.easy.core.ui.base.BaseVmActivity
import com.easy.readbook.BR
import com.easy.readbook.Keys
import com.easy.readbook.R
import com.easy.readbook.room.entity.Book
import com.easy.readbook.databinding.ActivityBookeDetailBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.ui.book
 * @Date  : 下午 4:46
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BookDetailActivity : BaseVmActivity<BookDetailViewModel, ActivityBookeDetailBinding>() {
    companion object {
        fun open(context: Activity, item: com.easy.readbook.room.entity.Book) {
            context.startActivityForResult(Intent(context, BookDetailActivity::class.java).apply {
                putExtra(Keys.BOOK, item)
            }, -1)
        }
    }

    override fun getLayoutId(): Int {
        return  R.layout.activity_booke_detail
    }

    override fun bindingViewModelId(): Int {
       return  BR.vm
    }

    override fun initViews() {

    }

}