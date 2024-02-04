package com.easy.example.ui.media

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.log.LogUtils
import com.easy.example.databinding.ActivitySeparationVideoBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.media
 * @Date : 16:48
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SeparationVideoActivity : BaseViewBindingActivity<ActivitySeparationVideoBinding>() {

    override fun initView() {
        copyCacheFile("demo2.mp4")
        var outFile = File(filesDir, "demo2.mp4")
        LogUtils.e("outFile  :      $outFile")
        binding.button93.setOnClickListener {
            try {
                extractVideo(outFile)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        binding.button94.setOnClickListener {
            try {
                extractAudio(outFile)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

    }

    /**
     * 提取音频
     * @param outFile File
     */
    @Throws(IOException::class)

    private fun extractAudio(outFile: File) {

        val mMediaMuxer = MediaMuxer("$filesDir/text.mp3", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

        // 音频的MediaExtractor
        val mAudioExtractor = MediaExtractor()
        mAudioExtractor.setDataSource(outFile.path)
        var audioTrackIndex = -1
        for (i in 0 until mAudioExtractor.trackCount) {
            val format = mAudioExtractor.getTrackFormat(i)
            if (format.getString(MediaFormat.KEY_MIME)!!
                    .startsWith("audio/")
            ) {
                mAudioExtractor.selectTrack(i)
                audioTrackIndex = mMediaMuxer.addTrack(format)
            }
        }
        // 添加完所有轨道后start
        mMediaMuxer.start()
        // 封装音频track
        if (-1 != audioTrackIndex) {
            val info = MediaCodec.BufferInfo()
            info.presentationTimeUs = 0
            val buffer = ByteBuffer.allocate(100 * 1024)
            while (true) {
                val sampleSize = mAudioExtractor.readSampleData(buffer, 0)
                if (sampleSize < 0) {
                    break
                }
                info.offset = 0
                info.size = sampleSize
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME
                info.presentationTimeUs = mAudioExtractor.sampleTime
                mMediaMuxer.writeSampleData(audioTrackIndex, buffer, info)
                mAudioExtractor.advance()
            }
        }

        // 释放MediaExtractor
        mAudioExtractor.release()

        // 释放MediaMuxer
        mMediaMuxer.stop()
        mMediaMuxer.release()
    }
    /**
     *
     * @param outFile File
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun extractVideo(outFile: File) {
        val mMediaMuxer = MediaMuxer("$filesDir/text.mp4", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        // 视频的MediaExtractor
        val mVideoExtractor = MediaExtractor()
        mVideoExtractor.setDataSource(outFile.path)
        var videoTrackIndex = -1
        for (i in 0 until mVideoExtractor.trackCount) {
            val format = mVideoExtractor.getTrackFormat(i)
            if (format.getString(MediaFormat.KEY_MIME)!!
                    .startsWith("video/")
            ) {
                mVideoExtractor.selectTrack(i)
                videoTrackIndex = mMediaMuxer.addTrack(format)
                break
            }
        }
        // 添加完所有轨道后start
        mMediaMuxer.start()

        // 封装视频track
        if (-1 != videoTrackIndex) {
            val info = MediaCodec.BufferInfo()
            info.presentationTimeUs = 0
            val buffer = ByteBuffer.allocate(100 * 1024)
            while (true) {
                val sampleSize = mVideoExtractor.readSampleData(buffer, 0)
                if (sampleSize < 0) {
                    break
                }
                info.offset = 0
                info.size = sampleSize
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME
                info.presentationTimeUs = mVideoExtractor.sampleTime
                mMediaMuxer.writeSampleData(videoTrackIndex, buffer, info)
                mVideoExtractor.advance()
            }
        }
        // 释放MediaExtractor
        mVideoExtractor.release()
        // 释放MediaMuxer
        mMediaMuxer.stop()
        mMediaMuxer.release()
    }

     fun copyCacheFile(fileName: String): String? {
        try {
            // 获取AssetManager实例
            val assetManager = assets
            // 打开要复制的文件
            val inputStream = assetManager.open(fileName)
            // 创建目标文件路径
            val outFile = File(filesDir, fileName)
            LogUtils.e("copyCacheFile           ${outFile.path}")
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