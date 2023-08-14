package com.easy.readbook.ui.menu

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.core.glide.ImageLoadUtils
import com.easy.core.ui.list.BaseListActivity
import com.easy.core.utils.file.FileUtils
import com.qq.readbook.R
import com.qq.readbook.room.RoomUtils
import com.easy.readbook.room.entity.Book

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.menu
 * @Date : 下午 4:43
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class CacheManagementActivity : BaseListActivity() {
    override val layoutViewId: Int = R.layout.activity_cache_management
    override val adapter: CacheAdapter = CacheAdapter()

    override fun initData() {
        val list = RoomUtils.getBookDao().getAll()
        adapter.setList(list)

    }

    inner class CacheAdapter : BaseQuickAdapter<com.easy.readbook.room.entity.Book, BaseViewHolder>(R.layout.item_cache_management) {
        override fun convert(viewHolder: BaseViewHolder, book: com.easy.readbook.room.entity.Book) {
            ImageLoadUtils.withFillet(book.imgUrl, viewHolder.getView(R.id.iv_book_img))
            viewHolder.setText(R.id.tv_bookName, book.name)
            viewHolder.setText(R.id.tv_author, book.author)
            val cacheSize = FileUtils.getFileDatabaseSize(book.bookIdName+".db") +
                    FileUtils.getFileDatabaseSize(book.bookIdName + ".db-shm") +
                    FileUtils.getFileDatabaseSize(book.bookIdName + ".db-wal")
            viewHolder.setText(R.id.tv_cacheSize, "总缓存:" + FileUtils.getFormatSize(cacheSize.toDouble()))
        }
    }
}