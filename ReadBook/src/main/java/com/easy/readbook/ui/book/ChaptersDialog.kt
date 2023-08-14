package com.easy.readbook.ui.book

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy.core.ui.dialog.BaseBindingDialog
import com.easy.core.utils.ScreenUtils
import com.easy.core.utils.keyboard.SoftKeyboardUtils
import com.qq.readbook.R
import com.qq.readbook.adapter.ChaptersAdapter
import com.qq.readbook.databinding.DialogChaptersBinding
import com.easy.readbook.room.entity.Chapter
import com.easy.readbook.weight.page.loader.PageLoader

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.book
 * @Date : 上午 11:06
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ChaptersDialog(var mPageLoader: com.easy.readbook.weight.page.loader.PageLoader, var bookChapterList: List<com.easy.readbook.room.entity.Chapter>?) :
    BaseBindingDialog<DialogChaptersBinding>() {

    /**
     *  true  升序
     *  false 降序
     */
    var soft = false
    var adapter = ChaptersAdapter(mPageLoader.chapterPos).apply {
        setOnItemClickListener { _, _, position ->
            mPageLoader.skipToChapter(position)
            dismiss()
        }
    }

    override fun initView() {
        binding.clContent.setPadding(0, ScreenUtils.getStatusBarHeight(), 0, 0)
        binding.rcList.apply {
            layoutManager = LinearLayoutManager(context).apply {
                // scrollToPosition   才会生效
                reverseLayout = true
            }
            postDelayed({
                goCurrPosition()
            }, 500)
        }
        binding.rcList.adapter = adapter
        bookChapterList?.let { adapter.addData(it) }

        binding.ivClose.setOnClickListener {
            dismiss()
        }
        binding.ivLocate.setOnClickListener {
            goCurrPosition()
        }
        binding.llRight.setOnClickListener {
            val listView = rootView?.findViewById<RecyclerView>(R.id.rc_list)
            val manager = listView?.layoutManager as? LinearLayoutManager
            if (soft) {
                (it as TextView).text = "降序"
            } else {
                (it as TextView).text = "升序"
            }
            soft = !soft
            manager?.stackFromEnd = !soft
            manager?.reverseLayout = !soft
            goCurrPosition()
        }

        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                SoftKeyboardUtils.hideSoftInput(binding.edtSearch)
                val str = binding.edtSearch.text.toString()
                if (!str.isNullOrEmpty()) {
                    for ((position, chapter) in (bookChapterList as ArrayList).withIndex()) {
                        if (chapter.title.contains(str)) {
                            adapter.showSearchPosition = position
                            goCurrPosition(position)
                        }
                    }
                }
            }
            return@setOnEditorActionListener false
        }
    }

    private fun goCurrPosition(position: Int) {
        val manager = binding.rcList.layoutManager as? LinearLayoutManager
        context?.let {
            // 设置偏移量屏幕的1/4  为什么是4  我也不懂
            manager?.scrollToPositionWithOffset(
                position, ScreenUtils.getScreenHeight(it) / 4
            )
        }
    }

    private fun goCurrPosition() {
        val position = mPageLoader.chapterPos;
        goCurrPosition(position)
    }


}