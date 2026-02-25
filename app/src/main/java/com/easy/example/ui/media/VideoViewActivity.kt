package com.easy.example.ui.media

import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.log.LogUtils
import com.easy.example.R
import com.easy.example.databinding.ActivityVideoViewBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.video
 * @Date  : 14:07
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class VideoViewActivity : BaseViewBindingActivity<ActivityVideoViewBinding>() {


    override fun initView() {
        copyCacheFile("demo2.mp4")
        var outFile = File(filesDir, "demo2.mp4")

        binding.videoView2.setVideoPath(outFile.path)

        //调整从第一毫秒进行播放
        binding.videoView2.seekTo(1)


    }

    fun copyCacheFile(fileName: String): String? {
        try {
            // 获取AssetManager实例
            val assetManager = assets
            // 打开要复制的文件
            val inputStream = assetManager.open(fileName)
            // 创建目标文件路径
            val outFile = File(filesDir, fileName)
            LogUtils.e { "copyCacheFile           ${outFile.path}" }
            outFile.delete()
            // 创建输出流并复制文件
            val outputStream: OutputStream = FileOutputStream(outFile)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer)
                    .also { length = it } > 0
            ) {
                outputStream.write(buffer, 0, length)
            }
            // 关闭资源
            outputStream.close()
            inputStream.close()
            return outFile.path
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}