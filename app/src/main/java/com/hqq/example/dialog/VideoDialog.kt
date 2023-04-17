package com.hqq.example.dialog

import android.media.MediaPlayer
import android.net.Uri
import android.widget.MediaController
import com.hqq.core.ui.dialog.BaseBindingDialog
import com.hqq.core.ui.dialog.BaseDialog
import com.hqq.example.R
import com.hqq.example.databinding.DialogVideoBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.dialog
 * @Date : 19:13
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class VideoDialog : BaseBindingDialog<DialogVideoBinding>() {
    override fun initView() {
        val uri =
            Uri.parse("https://vlogtest.shangwenwan.com/mall/64cf2a3f-b049-4d98-ae68-ec5cbaa8dc64")
        binding.videoView.setVideoURI(uri)
        binding.videoView.setMediaController(MediaController(activity))
        binding.videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            mp.isLooping = true
        })
        binding.videoView.start()
    }
}