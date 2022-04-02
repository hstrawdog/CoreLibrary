package com.qq.readbook.down

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.hqq.core.lifecycle.BaseService
import com.hqq.core.utils.log.LogUtils
import com.qq.readbook.Keys
import com.qq.readbook.repository.BookArticleRepository
import com.qq.readbook.room.RoomUtils
import com.qq.readbook.room.entity.Book
import com.qq.readbook.room.entity.Chapter
import kotlinx.coroutines.*

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook
 * @Date : 上午 10:12
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DownService : BaseService() {
    /**
     *  判断Service 是否
     */
    var bind = true

    private val taskBuilder = TaskBuilder()


    override fun onBind(intent: Intent): IBinder {
        val book = intent.getParcelableExtra<Book>(Keys.BOOK)
        LogUtils.e4Debug("----------onBind")
        //  liveData 观察数据  监听需要缓存的 数据
        taskBuilder.downChapter.observe(this, { downChapter ->
            book?.let {
                processChapter(it, downChapter)
            }
        })
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        return taskBuilder
    }

    override fun onUnbind(intent: Intent): Boolean {
        LogUtils.e4Debug("----------onUnbind")
        bind = false
        return super.onUnbind(intent)
    }

    /**
     *  处理收到要下载的章节 请求
     * @param book Book?
     * @param downChapter List<Chapter>
     */
    private fun processChapter(book: Book, downChapter: DownChapter) {
        CoroutineScope(Dispatchers.IO).launch {
            // 收到新的通知  清空统计
            LogUtils.e4Debug("processChapter:   ")
            for (chapter in ArrayList<Chapter>().apply { addAll(downChapter.list) }) {
                if (bind) {
                    LogUtils.e4Debug("observe: 迭代数据 判断本地是否存在    " + chapter.title)
                    // 判断本地是否存在
                    val bookContent = RoomUtils.getBookContentDao(book.bookIdName).getContent(chapter.number)
                    if (bookContent == null) {
                        doGet(chapter, book, downChapter)
                    } else {
                        LogUtils.e4Debug("observe: 排除已下载:   " + chapter.title)
                        downChapter.successMap.put(chapter.number, chapter)
                        sendSuccess(true, downChapter, chapter)
                    }
                } else {
                    LogUtils.dInfo("Service 解除绑定  停止下载与缓存")
                    break
                }
            }
        }
    }

    /**
     * 挂起函数
     * 执行 get同步 请求
     * @param chapter Chapter
     * @param book Book
     * @param downChapter DownChapter
     */
    private suspend fun doGet(chapter: Chapter, book: Book, downChapter: DownChapter) {
        withContext(Dispatchers.IO) {
            LogUtils.e4Debug("------------------- 开始请求 ------------------- ")
            LogUtils.e4Debug(chapter.number.toString() + "----" + chapter.title)
            LogUtils.e4Debug(chapter.url)
            LogUtils.e4Debug("-------------------  ------------------- ")

            BookArticleRepository.getChapterContent(chapter, book.name + "_" + book.author,
                object : BookArticleRepository.ArticleNetCallBack {
                    override fun onSuccess(boolean: Boolean) {
                        LogUtils.e4Debug(" 收到回调 $boolean 开始回调  " + chapter.number)
                        if (boolean) {
                            downChapter.successMap.put(chapter.number, chapter)
                        }
                        sendSuccess(boolean, downChapter, chapter)
                    }
                })
        }
    }

    /**
     * 发送数据给Activity
     */
    private fun sendSuccess(boolean: Boolean, downChapter: DownChapter, chapter: Chapter) {
        GlobalScope.launch(Dispatchers.Main) {
            taskBuilder.onDownloadListener?.onSuccess(boolean, downChapter, chapter)
        }
    }

    /**
     * Activity 与 Service  通信
     */
    open class TaskBuilder : Binder() {
        val downChapter = MutableLiveData<DownChapter>(DownChapter(0))
        var onDownloadListener: OnDownloadListener? = null

        /**
         * 两种模式
         * 1 加载显示的数据 需要回调界面 显示章节
         * 2 缓存  回调给界面  现在进度
         */
        fun sendLoadMessage(model: Int, list: List<Chapter>) {
            downChapter.value = DownChapter(model).apply {
                this.list = list
            }
        }

    }


    interface OnDownloadListener {
        /**
         *
         * @param boolean 当前章节是否加载成功
         * @param model Int 模式
         * @param int Int 序列id
         * @param totalSize Int  总大小
         * @param successSize Int  成功序列
         */
        fun onSuccess(boolean: Boolean, downChapter: DownChapter, chapter: Chapter)
    }


}