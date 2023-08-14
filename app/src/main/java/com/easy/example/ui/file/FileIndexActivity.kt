package com.easy.example.ui.file

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.file
 * @Date : 17:41
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class FileIndexActivity : BaseListActivity() {
    override val adapter: MainAdapter = MainAdapter()

    override fun initData() {
        adapter.addData(com.easy.example.bean.MainBean("图片保存", SaveBitmapActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("文件下载测试", DownLoadActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("文件路径信息", FilePathActivity::class.java))

    }
}