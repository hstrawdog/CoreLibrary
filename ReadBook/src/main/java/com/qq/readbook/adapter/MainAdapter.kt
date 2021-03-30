package com.qq.readbook.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hqq.core.glide.ImageLoadUtils
import com.hqq.core.utils.DateUtils
import com.hqq.core.utils.log.LogUtils
import com.qq.readbook.R
import com.qq.readbook.down.UpdateManager
import com.qq.readbook.repository.ReadRepository
import com.qq.readbook.room.entity.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.adapter
 * @Date : 上午 10:52
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class MainAdapter : BaseQuickAdapter<Book, BaseViewHolder>(R.layout.item_book_main), LoadMoreModule {
    init {
        addChildClickViewIds(R.id.tv_delete, R.id.tv_detail, R.id.ll_content, R.id.tv_top, R.id.iv_book_img)
    }

    override fun convert(holder: BaseViewHolder, item: Book) {
        ImageLoadUtils.withFillet(item.imgUrl, holder.getView(R.id.iv_book_img))
        holder.setText(R.id.tv_book_name, item.name)
        holder.setText(R.id.tv_book_author, item.author)
        holder.setText(R.id.tv_update_time, item.updateDate + "前更新  " + item.newestChapterTitle)
        holder.setText(R.id.tv_sources, "来源: " + item.sourceName)
        CoroutineScope(Dispatchers.IO).launch {
            item.sourceName?.let {
                ReadRepository.getBookRecord(item)?.let {
                    val num = item.chapterListSize - it.chapter
                    withContext(Dispatchers.Main) {
                        holder.setText(R.id.tv_book_author, item.author + " · " + num + "章未读")
                    }
                }
            }
        }
        LogUtils.e(item.isNeedRefresh)
        if (item.topTime.isNullOrEmpty()) {
            holder.setText(R.id.tv_top, "置顶")
        } else {
            holder.setText(R.id.tv_top, "取消置顶")
        }
        if ((System.currentTimeMillis() - DateUtils.string2Millisecond(item.refreshTime)) > (1 * 60 * 1000)) {
            if (item.isNeedRefresh) {
                LogUtils.e4Debug("添加刷新: " + item.name)
                holder.setGone(R.id.pb_bar, false)
                CoroutineScope(Dispatchers.IO).launch {
                    // 避免请求太快导致界面异常
                    sleep(500)
                    // 刷新逻辑
                    UpdateManager.handlerBook(item)
                }
            } else {
                holder.setGone(R.id.pb_bar, true)
            }
        } else {
            holder.setGone(R.id.pb_bar, true)
        }

    }

}