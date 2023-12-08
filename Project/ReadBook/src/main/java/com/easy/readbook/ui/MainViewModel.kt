package com.easy.readbook.ui

import androidx.lifecycle.LifecycleOwner
import com.easy.core.ui.list.BaseListViewModel
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.room.RoomUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.ui
 * @Date : 上午 11:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class MainViewModel : BaseListViewModel() {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        onLoadMore()

    }
    var updateTime = System.currentTimeMillis()

    override fun onLoadMore() {
        super.onLoadMore()
        val list = RoomUtils.getBookDao().getFollowAll()
        //1分钟
        if (System.currentTimeMillis() - updateTime > 1000 * 60) {
            // 同步更新数据
            val newList = ArrayList<com.easy.readbook.room.entity.Book>()
            CoroutineScope(Dispatchers.Main).launch {
                for (book in list) {
                    //doChapterUrl(book, newList)
                    newList.add(book)
                }

                LogUtils.e4Debug("----------------------1 "+ newList.size +"                "+ list.size)
               data.value=(newList)
            }
            updateTime = System.currentTimeMillis()
        } else {
            LogUtils.e4Debug("----------------------2 "+ list.size)
            data.value=(list)
        }
    }

    private suspend fun doChapterUrl(book: com.easy.readbook.room.entity.Book, newList: ArrayList<com.easy.readbook.room.entity.Book>) {
//        NewestChapterRepository.doChapterUrl(book.chapterUrl,
//            object : NewestChapterRepository.LatestChapter {
//                override fun onEnd(book: String, isSuccess: Boolean) {
//                    LogUtils.e4Debug(book.name + "   " + book.newestChapterTitle)
//                    newList.add(book)
//                }
//            })
    }

}