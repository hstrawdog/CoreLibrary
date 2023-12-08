package com.easy.readbook.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.core.glide.ImageLoadUtils
import com.easy.core.utils.TimeUtils
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.R
import com.easy.readbook.down.UpdateManager
import com.easy.readbook.repository.ReadRepository
import com.easy.readbook.room.entity.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep
import java.text.ParseException

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.adapter
 * @Date : 上午 10:52
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class MainAdapter : BaseQuickAdapter<com.easy.readbook.room.entity.Book, BaseViewHolder>(R.layout.item_book_main), LoadMoreModule {
    init {
        addChildClickViewIds(R.id.tv_delete, R.id.tv_detail, R.id.ll_content, R.id.tv_top, R.id.iv_book_img)
    }

    override fun convert(holder: BaseViewHolder, item: com.easy.readbook.room.entity.Book) {
        ImageLoadUtils.withFillet(item.imgUrl, holder.getView(R.id.iv_book_img))
        holder.setText(R.id.tv_book_name, item.name)
        holder.setText(R.id.tv_book_author, item.author)
        holder.setText(R.id.tv_update_time, formatData(item.updateDate) + "  " + item.newestChapterTitle)
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
        LogUtils.dInfo(item.isNeedRefresh)
        if (item.topTime.isNullOrEmpty()) {
            holder.setText(R.id.tv_top, "置顶")
        } else {
            holder.setText(R.id.tv_top, "取消置顶")
        }
        if ((System.currentTimeMillis() - TimeUtils.string2Millisecond(item.refreshTime)) > (1 * 60 * 1000)) {
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

    private fun formatData(updateDate: String): String {

        try {
            return TimeUtils.dateConvert(TimeUtils.defaultFormat.parse(updateDate).time / 1000, 0)+"前更新"
        } catch (e: ParseException) {
            LogUtils.e4Debug("格式不正确 $updateDate")
        }
        return updateDate
    }

}