package com.qq.readbook.ui

import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.observe
import com.hqq.core.ui.dialog.SelectDialog
import com.hqq.core.ui.list.BaseVmListActivity
import com.hqq.core.utils.DateUtils
import com.hqq.core.utils.TimeTool
import com.hqq.core.utils.keyboard.SoftKeyboardUtils
import com.hqq.core.utils.log.LogUtils
import com.qq.readbook.BR
import com.qq.readbook.R
import com.qq.readbook.adapter.BookAdapter
import com.qq.readbook.adapter.SearchLogAdapter
import com.qq.readbook.databinding.ActivitySearchBinding
import com.qq.readbook.ui.book.BookDetailActivity
import com.qq.readbook.room.RoomUtils
import com.qq.readbook.room.entity.Book
import com.qq.readbook.weight.flowlayout.FlowLayout
import com.qq.readbook.weight.flowlayout.TagAdapter
import com.qq.readbook.weight.flowlayout.TagFlowLayout

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui
 * @Date  : 下午 2:24
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SearchActivity : BaseVmListActivity<SearchViewModel, ActivitySearchBinding>() {
    override val layoutId: Int = R.layout.activity_search
    override val bindingViewModelId: Int = BR.vm
    override val adapter: BookAdapter = BookAdapter().apply {
        setOnItemClickListener { _, _, position ->
            BookDetailActivity.open(activity, (getItem(position) ))
        }
    }
    override fun initConfig() {
        super.initConfig()
        rootViewImpl.iToolBarBuilder.showToolBar = false
        rootViewImpl.iToolBarBuilder.showLine = false
    }

    private val rawData = ArrayList<Book>()
    override fun initData() {
        onClickInit()
        viewMode.liveBooks.observe(this) { t ->
            t?.let {
                rawData.addAll(it)
                // 迭代 去除重复数据
                addData2Adapter(it)
            }
        }
        viewMode.searchModel.observe(this) {
            adapter.setNewInstance(ArrayList<Book>())
            addData2Adapter(rawData)
        }
        viewMode.searchKey.observe(this) {
            if (it.isNotEmpty()) {
                // 字有变化 且 不是是item 点击的
                if (mKey.equals(viewMode.searchKey.value)) {
                    viewMode.bookNameMatch.value = false
                } else {
                    // 模糊匹配
                    viewMode.bookNameMatch.value = true
                    viewMode.onBooks()
                }
            }
        }


        viewMode.bookNames.observe(this) {
            val tagAdapter = object : TagAdapter<String>(it) {
                override fun getView(parent: FlowLayout?, position: Int, string: String): View {
                    val tv = layoutInflater.inflate(R.layout.layout_tv_report, binding.flBooks, false)
                    (tv as TextView).setText(string)
                    return tv
                }
            }
            binding.flBooks.setAdapter(tagAdapter)
        }
        binding.flBooks.setOnSelectListener(object : TagFlowLayout.OnSelectListener {
            override fun onSelected(selectPosSet: Set<Int>) {
                for (i in selectPosSet) {
                    val bookName = binding.flBooks.adapter.getItem(i) as String
                    mKey = bookName
                    binding.edtSearch.setText(bookName)
                    onSearch()
                    return
                }
            }
        })
        initLocalLog()
    }

    private fun initLocalLog() {
        val list = RoomUtils.getLocalSearchKeyDao().getAll()
        val logAdapter = SearchLogAdapter().apply {
            setOnItemClickListener { _, _, position ->
                mKey = getItem(position).key
                binding.edtSearch.setText(getItem(position).key)
                binding.edtSearch.setSelection(getItem(position).key.length)
                getItem(position).searchTime = TimeTool.nowDate
                RoomUtils.getLocalSearchKeyDao().update(getItem(position))
                onSearch()
            }
        }
        val headView = layoutInflater.inflate(R.layout.item_head_search_history, listView, false)
        headView.findViewById<ImageView>(R.id.iv_delete).setOnClickListener {
            SelectDialog.Builder().setContent("清空历史记录?")
                .setTitle("提示")
                .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        RoomUtils.getLocalSearchKeyDao().deleteAll()
                        RoomUtils.getLocalSearchKeyDao().resetId()
                        adapter.setNewInstance(ArrayList())
                        adapter.notifyDataSetChanged()
                    }

                }).create().show(supportFragmentManager)


        }
        logAdapter.addHeaderView(headView)
        logAdapter.replaceData(list)
        listView?.adapter = logAdapter
    }

    private fun addData2Adapter(it: ArrayList<Book>) {
        for (book in it) {
            LogUtils.e4Debug("SearchActivity   迭代书籍 " + book.name)
            if (viewMode.searchModel.value == true) {
                if (binding.edtSearch.text.toString() != book.name) {
                    continue
                }
            }
            adapter.data.find { it.name == book.name && it.author == book.author }.let {
                if (it == null) {
                    LogUtils.e4Debug("SearchActivity   未找到书籍 添加到列表 " + book.name)
                    adapter.addData(book)
                } else {
                    LogUtils.e4Debug("SearchActivity   找到书籍 排除重复数据 " + book.name)
                }
            }
        }
    }

    private fun onClickInit() {
        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearch()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.tvBarRight.setOnClickListener {
            onSearch()
        }
        binding.ivBarBack.setOnClickListener { onBackPressed() }
    }
    var mKey = ""
    /**
     * 搜索
     */
    private fun onSearch() {
        listView?.adapter = adapter
        val key = binding.edtSearch.text.toString()
        rawData.clear()
        adapter.data.clear()
        listView?.adapter = adapter
        adapter.notifyDataSetChanged()
        SoftKeyboardUtils.hideSoftInput(this)
        viewMode.onSearch(key)
    }


}