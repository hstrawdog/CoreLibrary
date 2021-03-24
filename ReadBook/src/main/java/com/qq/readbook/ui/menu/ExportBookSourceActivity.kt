package com.qq.readbook.ui.menu

import android.content.Intent
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.FileUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.core.utils.gson.GsonExclusionStrategy
import com.qq.readbook.databinding.ActivityExportBookSourceBinding
import com.qq.readbook.room.RoomUtils

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.menu
 * @Date : 上午 9:35
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ExportBookSourceActivity : BaseViewBindingActivity<ActivityExportBookSourceBinding>() {
    override fun initView() {

        val sources = RoomUtils.getBookSourceDao().getAll()
        for (source in sources) {
            source.ruleSearch = RoomUtils.getSearchRuleDao().getRuleSearch(source.sourceName)
            source.ruleChapter = RoomUtils.getChapterInfoRuleDao().getChapterInfoRule(source.sourceName)
            source.ruleArticleContent = RoomUtils.getArticleContentRuleDao().gerArticleContentRule(source.sourceName)
            source.ruleBookInfo = RoomUtils.getBookInfoRuleDao().getBookInfoRule(source.sourceName)
        }
        val gs: Gson = GsonBuilder()
            .setPrettyPrinting()
            .setExclusionStrategies( GsonExclusionStrategy())
            .disableHtmlEscaping()
            .create()
        val json = gs.toJson(sources)
        LogUtils.e(json)
        binding.tvContent.text = json

        /**
         *  写入数据至cache 目录下
         */
        val file = FileUtils.createNewFile("bookSource.json")
        FileUtils.writerFile(file, json)

        // 单文件分享
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, FileUtils.getFile2Uri(file.path))
        startActivity(Intent.createChooser(intent, "Share"))
    }
}