package com.hqq.example.ui.file

import com.chad.library.adapter.base.BaseQuickAdapter
import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.info.FilePathActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.file
 * @Date : 17:41
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class FileIndexActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
        adapter.addData(MainBean("图片保存", SaveBitmapActivity::class.java))
        adapter.addData(MainBean("文件下载测试", DownLoadActivity::class.java))
        adapter.addData(MainBean("文件路径信息", FilePathActivity::class.java))

    }
}