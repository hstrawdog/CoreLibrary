package com.easy.readbook.ui

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.core.toolbar.DefToolBar
import com.easy.core.ui.list.BaseVmListActivity
import com.easy.core.utils.TimeUtils
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.BR
import com.easy.readbook.R
import com.easy.readbook.adapter.MainAdapter
import com.easy.readbook.bean.ActivityBean
import com.easy.readbook.databinding.ActivityMainBinding
import com.easy.readbook.down.UpdateManager
import com.easy.readbook.room.RoomUtils
import com.easy.readbook.ui.book.BookDetailActivity
import com.easy.readbook.ui.book.ReadBookActivity
import com.easy.readbook.ui.menu.CacheManagementActivity
import com.easy.readbook.ui.menu.ExportBookSourceActivity
import com.easy.readbook.ui.menu.ReadSettingActivity
import com.easy.readbook.ui.source.BookSourceListActivity


class MainActivity : BaseVmListActivity<MainViewModel, ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun bindingViewModelId(): Int {

        return BR.vm
    }

    override val adapter: MainAdapter = MainAdapter().apply {
        setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.iv_book_img, R.id.tv_detail -> {
                    BookDetailActivity.open(activity, getItem(position))
                }

                R.id.tv_delete -> {
                    RoomUtils.getBookDao()
                        .delete(getItem(position))
                    removeAt(position)
                }

                R.id.ll_content -> {
                    ReadBookActivity.open(activity, getItem(position))
                }

                R.id.tv_top -> {
                    val book = getItem(position)
                    if (book.topTime.isNullOrEmpty()) {
                        // 置顶
                        book.topTime = TimeUtils.nowDate
                    } else {
                        // 取消置顶
                        book.topTime = null
                    }
                    RoomUtils.getBookDao()
                        .update(book)
                    notifyItemChanged(position)
                }

                else -> {
                }
            }
        }

    }

    override fun initData() {
        LinearLayoutManager(this)
        listModel.emptyTextMessage = "没有收藏书籍!请前往搜索查找喜欢的书籍"
        (iToolBar as DefToolBar).leftView.visibility = View.GONE
//        //SAM 写法?
        (iToolBar as DefToolBar).addRightImageView(R.mipmap.ic_search) {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
        binding.rcMenu.layoutManager = LinearLayoutManager(this)
        binding.rcMenu.adapter = MenuAdapter().apply {
            addData(ActivityBean("书源列表", BookSourceListActivity::class.java))
            addData(ActivityBean("阅读设置", ReadSettingActivity::class.java))
            addData(ActivityBean("缓存管理", CacheManagementActivity::class.java))
            addData(ActivityBean("导出书源", ExportBookSourceActivity::class.java))
            setOnItemClickListener { _, _, position ->
                startActivity(Intent(activity, getItem(position).className))
            }
        }
        UpdateManager.liveBook.observe(this) {
            //  监听到 更新的数据
            if (it != null) {
                synchronized(this) {
                    it.isNeedRefresh = false
                    val position = adapter.data.indexOf(it)
                    if (position > -1) {
                        adapter.setData(position, it)
                        LogUtils.e("通知了第${position}条")
                    }
                }
            }
        }


    }

    class MenuAdapter : BaseQuickAdapter<ActivityBean, BaseViewHolder>(R.layout.item_main_menu) {
        override fun convert(holder: BaseViewHolder, item: ActivityBean) {
            holder.setText(R.id.tv_name, item.titile)
        }


    }


}