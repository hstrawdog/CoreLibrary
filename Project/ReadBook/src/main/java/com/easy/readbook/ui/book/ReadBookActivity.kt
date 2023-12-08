package com.easy.readbook.ui.book

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.observe
import com.easy.core.ui.base.BaseVmActivity
import com.easy.core.utils.TimeUtils
import com.easy.core.utils.ToastUtils
import com.easy.core.utils.log.LogUtils
import com.easy.readbook.BR
import com.easy.readbook.Keys
import com.easy.readbook.R
import com.easy.readbook.databinding.ActivityReadBookBinding
import com.easy.readbook.down.DownChapter
import com.easy.readbook.down.DownService
import com.easy.readbook.repository.BookChaptersRepository
import com.easy.readbook.room.RoomUtils
import com.easy.readbook.room.entity.Book
import com.easy.readbook.room.entity.Chapter
import com.easy.readbook.ui.dialog.SettingDialog
import com.easy.readbook.weight.page.BrightnessUtils
import com.easy.readbook.weight.page.PageView
import com.easy.readbook.weight.page.ReadSettingManager
import com.easy.readbook.weight.page.loader.OnPageChangeListener
import com.easy.readbook.weight.page.loader.PageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Objects

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.ui.book
 * @Date  : 下午 2:25
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ReadBookActivity : BaseVmActivity<ReadBookViewModel, ActivityReadBookBinding>() {
    companion object {
        fun open(context: Activity, item: com.easy.readbook.room.entity.Book) {
            context.startActivityForResult(
                    Intent(context, ReadBookActivity::class.java)
                            .putExtra(Keys.BOOK, item), -1
            )
        }
    }

    override val layoutId: Int = R.layout.activity_read_book

    override val bindingViewModelId: Int = BR.vm

    /**
     *  阅读加载器
     */
    var pageLoader: com.easy.readbook.weight.page.loader.PageLoader? = null

    /**
     *  下载通信对象
     */
    var taskBuilder: DownService.TaskBuilder? = null

    /**
     * 书籍对象
     */
    lateinit var book: com.easy.readbook.room.entity.Book

    override fun initConfig() {
        super.initConfig()
        rootViewImpl.iToolBarBuilder.showToolBar = false
        rootViewImpl.iToolBarBuilder.statusBarColor = ReadSettingManager.getInstance().pageStyle.bgColor
    }

    /**
     * 接收电池信息和时间更新的广播
     */
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (Objects.requireNonNull(intent.action) == Intent.ACTION_BATTERY_CHANGED) {
                val level = intent.getIntExtra("level", 0)
                pageLoader?.updateBattery(level)
            } else if (intent.action == Intent.ACTION_TIME_TICK) {
                pageLoader?.updateTime()
            }
        }
    }

    /**
     *  连接service
     */
    private val service = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LogUtils.e4Debug("service 连接成功")
            taskBuilder = service as DownService.TaskBuilder
            if (taskBuilder?.onDownloadListener == null) {
                taskBuilder?.onDownloadListener = object : DownService.OnDownloadListener {
                    override fun onSuccess(boolean: Boolean, downChapter: DownChapter, chapter: com.easy.readbook.room.entity.Chapter) {
                        LogUtils.e4Debug(" ----- 收到 service 回调  当前position: " + chapter.number + "      ---   总数量" + downChapter.list.size + "         已完成" + downChapter.successMap.size)
                        if (chapter.number == pageLoader?.chapterPos && downChapter.model == 1) {
                            hintMenu()
                            if (boolean) {
                                pageLoader?.openChapter()
                            } else {
                                pageLoader?.chapterError()
                            }
                        } else {
                            binding.tvDownCache.text =
                                    "总缓存章节数: ${downChapter.list.indexOf(chapter)}/${downChapter.list.size - 1}\n正在缓存第" + chapter.title
                            if (downChapter.list.size == downChapter.successMap.size) {
                                viewMode.cacheSchedule.value = false

                            }
                        }
                    }
                }
            }
            // 连接成功够在去 初始化 书籍文章
            initPageLoader()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtils.e4Debug("service 连接失败")
        }

    }

    /**
     *  初始化View
     */
    override fun initViews() {
        //注册广播
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        registerReceiver(mReceiver, intentFilter)
        book = intent.getParcelableExtra(Keys.BOOK)!!
        initService(book)
        binding.tvBarTitle.text = book.name
        pageLoader = binding.pageView.getPageLoader(book)

        initChapter()

        initClick(book)

    }

    /**
     *  加载章节信息
     */
    private fun initChapter() {
        loadingView.show()
        GlobalScope.launch(Dispatchers.IO) {
            // 需要异步加载
            val chapters = book.sourceName?.let {
                RoomUtils.getChapterDataBase(book.name + "_" + book.author).chapterDao().getAll(it)
            }
            launch(Dispatchers.Main) {
                if (chapters.isNullOrEmpty()) {
                    // 数据库中没有查询到章节数据
                    BookDetailActivity.open(activity, book)
                    finish()
                } else {
                    book.bookChapterList = chapters
                    pageLoader?.refreshChapterList()
                }
                loadingView.dismiss()
            }

        }
    }

    /**
     *   初始化 文章加载器
     */
    private fun initPageLoader() {
        pageLoader?.refreshChapterList()
        pageLoader?.setOnPageChangeListener(object : com.easy.readbook.weight.page.loader.OnPageChangeListener {
            override fun onChapterChange(pos: Int) {
                LogUtils.e4Debug("onChapterChange  :    $pos")
                binding.tvUrl.text = pageLoader?.chapterCategory?.get(pos)?.url
            }

            override fun requestChapters(requestChapters: MutableList<com.easy.readbook.room.entity.Chapter>) {
                // 理论上需要用队列去维护 避免重复请求
                LogUtils.e4Debug("requestChapters:  " + requestChapters.size)
                LogUtils.e4Debug("taskBuilder:  $taskBuilder")
                taskBuilder?.sendLoadMessage(1, requestChapters)
            }

            override fun onCategoryFinish(chapters: MutableList<com.easy.readbook.room.entity.Chapter>) {
                LogUtils.e4Debug("onCategoryFinish")
                // 刷新章节成功
                //      pageLoader?.openChapter()
            }

            override fun onPageCountChange(count: Int) {
                LogUtils.e4Debug("onPageCountChange : $count")
            }

            override fun onPageChange(pos: Int) {
                LogUtils.e4Debug("onPageChange  :  $pos")
            }
        })
    }

    private fun initService(book: com.easy.readbook.room.entity.Book?) {
        bindService(Intent(this, DownService::class.java).apply {
            putExtra(Keys.BOOK, book)
        }, service, BIND_AUTO_CREATE)
    }

    private fun initClick(book: com.easy.readbook.room.entity.Book?) {
        binding.pageView.setTouchListener(object : com.easy.readbook.weight.page.PageView.TouchListener {
            override fun onTouch(): Boolean {
                // 隐藏按钮
                return hintMenu()
            }

            override fun center() {
                if (binding.flLayout.visibility == View.GONE) {
                    binding.llBottomMenu.visibility = View.VISIBLE
                    binding.flLayout.visibility = View.VISIBLE
                    binding.tvUrl.visibility = View.VISIBLE
                } else {
                    binding.flLayout.visibility = View.GONE
                    binding.tvUrl.visibility = View.GONE
                    binding.llBottomMenu.visibility = View.GONE
                }
            }

            override fun prePage() {}
            override fun nextPage() {}
            override fun cancel() {}
        })
        binding.sbBrightness.progress = ReadSettingManager.getInstance().brightness
        binding.sbBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val progress = seekBar.progress
                //设置当前 Activity 的亮度
                BrightnessUtils.setBrightness(activity, progress)
                //存储亮度的进度条
                ReadSettingManager.getInstance().brightness = progress
            }
        })
        // 设置
        binding.tvSetting.setOnClickListener {
            pageLoader?.let { it1 ->
                SettingDialog(it1).show(supportFragmentManager)
                binding.llBottomMenu.visibility = View.GONE
            }
        }
        // 目录
        binding.tvCategory.setOnClickListener {
            pageLoader?.let { it1 ->
                ChaptersDialog(it1, book?.bookChapterList).show(supportFragmentManager)
            }
        }

        binding.tvCache50.setOnClickListener {
            doCache(book, 50)
        }
        binding.tvCache100.setOnClickListener {
            doCache(book, 100)
        }
        binding.tvCacheAll.setOnClickListener {
            doCache(book, 9999999)

        }
        binding.flLayout.setOnClickListener {}
        binding.tvRight.setOnClickListener {
            SwitchBookSourceActivity.open(activity, book)
        }
        viewMode.themeMode.observe(this) {
            pageLoader?.setNightMode(!it)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val sourceName = data?.getStringExtra(Keys.BOOK_SOURCE_NAME)
            sourceName?.let {
                book.sourceName = sourceName
                val bookSources = RoomUtils.getSearchLogDao().getBookSource(it, book.bookId)
                book.chapterUrl = bookSources?.bookChapterUrl
                // 更新信息
                readChapters(book)
            }
        }
    }


    /**
     * 执行缓存
     */
    private fun doCache(book: com.easy.readbook.room.entity.Book?, i: Int) {
        taskBuilder?.sendLoadMessage(2, getCacheList(book, i))
        viewMode.cacheSchedule.value = true
        hintMenu()
    }

    /**
     *  获取需要缓存的章节
     */
    private fun getCacheList(book: com.easy.readbook.room.entity.Book?, size: Int): MutableList<com.easy.readbook.room.entity.Chapter> {
        if (pageLoader != null) {
            book?.bookChapterList?.let {
                val position = pageLoader!!.chapterPos
                val start = if (position < it.size) position else {
                    ToastUtils.showToast("全部已缓存完毕")
                    return@let
                }
                val end = if ((position + size) < it.size) {
                    (position + size)
                } else it.size
                // 截取部分集合
                return it.subList(start, end)
            }
        }
        return ArrayList<com.easy.readbook.room.entity.Chapter>()
    }

    /**
     *  隐藏按钮
     */
    private fun hintMenu(): Boolean {
        if (binding.flLayout.visibility == View.VISIBLE) {
            binding.llLight.visibility = View.GONE
            binding.llBottomMenu.visibility = View.GONE
            binding.flLayout.visibility = View.GONE
            binding.tvUrl.visibility = View.GONE
            binding.llCache.visibility = View.GONE
            return false
        } else {
            binding.llCache.visibility = View.GONE
            binding.llLight.visibility = View.GONE
            binding.llBottomMenu.visibility = View.GONE
            return true
        }
    }

    override fun onPause() {
        super.onPause()
        pageLoader?.saveRecord()
    }

    override fun onDestroy() {
        super.onDestroy()
        taskBuilder?.onDownloadListener = null
        unbindService(service)
        intent.getParcelableExtra<com.easy.readbook.room.entity.Book>(Keys.BOOK)?.apply {
            lastRead = TimeUtils.nowDate
            RoomUtils.getBookDao().update(this)

        }
        pageLoader?.closeBook()
        unregisterReceiver(mReceiver)
    }

    /**
     *  获取章节信息
     * @param it Book
     */
    private fun readChapters(it: com.easy.readbook.room.entity.Book) {
        // 爬取新目录
        loadingView.show()
        BookChaptersRepository.getBookChapters(it, object : BookChaptersRepository.BookChaptersCall {
            override fun onSuccess(arrayList: List<com.easy.readbook.room.entity.Chapter>) {
                book.bookChapterList = arrayList
                loadingView.dismiss()

            }
        })
    }
}