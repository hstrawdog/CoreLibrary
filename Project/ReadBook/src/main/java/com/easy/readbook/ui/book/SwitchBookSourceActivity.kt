package com.easy.readbook.ui.book

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.core.ui.list.BaseListActivity
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.Keys
import com.easy.readbook.R
import com.easy.readbook.bean.book.BookSource
import com.easy.readbook.room.entity.Book
import com.easy.readbook.repository.NewestChapterRepository
import com.easy.readbook.room.RoomUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.ui.book
 * @Date : 下午 3:00
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SwitchBookSourceActivity : BaseListActivity() {
    override val layoutViewId: Int = R.layout.activity_switch_booke_source

    companion object {
        fun open(context: Activity, book: com.easy.readbook.room.entity.Book?) {
            context.startActivityForResult(Intent(context, SwitchBookSourceActivity::class.java).apply {
                putExtra(Keys.BOOK, book)
            }, 0x2)
        }
    }

    override val adapter: BookSourceAdapter = BookSourceAdapter().apply {
        setOnItemClickListener { _, _, position ->
            setResult(Activity.RESULT_OK, Intent().apply {
                hashMap.get(getItem(position).sourceName)?.let {
                    putExtra(Keys.BOOK_SOURCE_NAME, getItem(position).sourceName)
                }
            })
            finish()
        }
    }
    lateinit var book: com.easy.readbook.room.entity.Book
    override fun initData() {
        book = intent.getParcelableExtra<com.easy.readbook.room.entity.Book>(Keys.BOOK)!!
        findViewById<TextView>(R.id.tv_currSource).text = "当前源:" + book.sourceName
        val list = ArrayList<BookSource>()

        RoomUtils.getBookSourceDao().getAll()?.let {
            for (readSource in it) {
                if (readSource.sourceName != book.sourceName) {
                    list.add(readSource)
                }
            }
        }
        listModel.fillingData(list)
    }

    inner class BookSourceAdapter : BaseQuickAdapter<BookSource, BaseViewHolder>(R.layout.item_book_source) {
        val hashMap = HashMap<String, Boolean>()
        override fun convert(baseViewHolder: BaseViewHolder, readSource: BookSource) {
            baseViewHolder.setText(R.id.tv_title, "来自: " + readSource.sourceName)
            val status = hashMap.get(readSource.sourceName)
            if (status == null) {
                baseViewHolder.setText(R.id.tv_status, "正在检测当前源是否可用")
            } else if (status == false) {
                baseViewHolder.setText(R.id.tv_status, "当前源不可用")
                baseViewHolder.getView<View>(R.id.pb_bar).visibility = View.GONE
            } else {
                baseViewHolder.setText(R.id.tv_status, "正在检测当前源可用")
            }
            val url =
                RoomUtils.getSearchLogDao().getBookSource(readSource.sourceName, book.bookId)?.bookDetailUrl
            doNet(url, readSource, baseViewHolder)
        }

        private fun doNet(url: String?, readSource: BookSource, baseViewHolder: BaseViewHolder) {
            if (url.isNullOrEmpty()) {
                // 搜索查询详情页面
                //todo 没有在搜索上查询到数据源 当做本源不可用 等在优化
                LogUtils.e4Debug("BookSourceAdapter   " + book.name + "---" + readSource.sourceName + " :   空的 ")
                hashMap.put(readSource.sourceName, false)
                baseViewHolder.setText(R.id.tv_status, "当前源不可用")
                baseViewHolder.getView<View>(R.id.pb_bar).visibility = View.GONE
            } else {
                // 请求url   查询源是否可用
                LogUtils.e4Debug("BookSourceAdapter  加载:" + book.name + "---" + readSource.sourceName)
                LogUtils.e4Debug(url)
                NewestChapterRepository.doChapterUrl(url,
                    readSource.sourceName,
                    object : NewestChapterRepository.LatestChapter {
                        override fun onEnd(chapterTitle: String, isSuccess: Boolean) {
                            CoroutineScope(Dispatchers.Main).launch {
                                baseViewHolder.getView<View>(R.id.pb_bar).visibility = View.GONE
                                baseViewHolder.setText(R.id.tv_status, chapterTitle)
                                hashMap.put(readSource.sourceName, true)
                            }
                        }
                    })
            }
        }
    }

}