package com.hqq.album.activity

import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hqq.album.Adapter.PreviewAdapter
import com.hqq.album.AppManager
import com.hqq.album.R
import com.hqq.album.annotation.LocalMediaType.VALUE_TYPE_IMAGE
import com.hqq.album.annotation.LocalMediaType.VALUE_URL_IMAGE
import com.hqq.album.common.FunctionKey
import com.hqq.album.common.FunctionOptions
import com.hqq.album.common.SelectOptions
import com.hqq.album.databinding.ActivityAlbumPreviewV2Binding
import com.hqq.album.dialog.OptAnimationLoader
import com.hqq.album.entity.LocalMedia
import com.hqq.core.glide.ImageLoadUtils
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.ui.base.open
import com.hqq.core.ui.dialog.SelectDialog
import com.hqq.core.utils.ToastUtils
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.file.SaveBitmapUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.activity
 * @FileName :   AlbumPreviewV2Activity
 * @Date : 2019/9/16 0016  下午 8:42
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class AlbumPreviewActivity : BaseAlbumActivity<ActivityAlbumPreviewV2Binding>(), View.OnClickListener {
    companion object {
        /**
         * @param activity BaseActivity
         * @param list ArrayList<String>  url 访问的预览
         */
        fun openPreview(activity: BaseActivity, list: ArrayList<String>) {
            val loc = ArrayList<LocalMedia>()
            for (s in list) {
                loc.add(LocalMedia(s, 0L, 0, VALUE_URL_IMAGE))
            }
            SelectOptions.instance.mFolderLocalMedia = loc

            activity.open(AlbumPreviewActivity::class.java, Bundle().apply {
                putBoolean("isShowSelect", false)
                putBoolean("isSavaBitmap", true)
            })
        }
    }


    var mLocalMediaList: List<LocalMedia>? = null
    var mPosition = 0
    var mPreviewAdapter: PreviewAdapter = PreviewAdapter()

    /**
     *  是否 可以勾选
     */
    var _isShowSelect = true

    /**
     *  是否可以保存图片
     */
    var _isSavaBitmap = false

    override fun initConfig() {
        super.initConfig()
        rootViewImpl.iToolBarBuilder.showLine = false
        rootViewImpl.iToolBarBuilder.showToolBar = false
    }

    override fun initView() {
        _isShowSelect = getBoolean("isShowSelect", true)
        _isSavaBitmap = getBoolean("isSavaBitmap", false)
        if (_isShowSelect) {
            binding.llCheck.visibility = View.VISIBLE
        } else {
            binding.llCheck.visibility = View.GONE
        }

        var currPosition = intent.getIntExtra(FunctionKey.KEY_POSITION, 1)
        mPosition = currPosition - 1
        mLocalMediaList = SelectOptions.instance.mFolderLocalMedia
        mPreviewAdapter.setNewInstance(mLocalMediaList?.toMutableList())
        initSaveBitmap()



        binding.albumBack.setOnClickListener(this)
        binding.llCheck.setOnClickListener(this)
        binding.albumFinish.setOnClickListener(this)
        binding.albumTitle.text = currPosition.toString() + "/" + (mLocalMediaList?.size ?: 0)


        binding.rcAlbumList.setItemViewCacheSize(3)
        binding.rcAlbumList.adapter = mPreviewAdapter

        binding.rcAlbumList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(binding.rcAlbumList)
        binding.rcAlbumList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val first = (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                    val last = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
                    if (first == last && mPosition != last) {
                        mPosition = last
                        updateSelectMenu()
                    }
                }
            }
        })
        binding.rcAlbumList.scrollToPosition(mPosition)
        updateSelectMenu()

    }

    private fun initSaveBitmap() {
        if (_isSavaBitmap) {
            mPreviewAdapter.call = {
                SelectDialog.Builder()
                    .setContent("是否保存图片")
                    .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            ImageLoadUtils.getBitmapByFail(activity, it.path, object :
                                ImageLoadUtils.GlideLoadBitmapCallback {
                                override fun getBitmapCallback(bitmap: Bitmap) {
                                    SaveBitmapUtils.saveBitmap2Pictures(bitmap, "", FileUtils.getDefFileName(".png"))
                                    ToastUtils.showToast("保存成功")
                                }

                                override fun onLoadFailed() {
                                }
                            })
                            dialog?.dismiss()
                        }
                    })
                    .create()
                    .show(supportFragmentManager)
            }
        }
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.album_back) {
            onBackPressed()
        } else if (i == R.id.album_finish) {
            setResult(RESULT_OK)
            AppManager.appManager?.finishAllActivityAndCallBack()
        } else if (i == R.id.ll_check) {
            if (binding.tvCheck.isSelected) {
                binding.tvCheck.isSelected = false
                for (media in SelectOptions.instance.selectLocalMedia) {
                    if (media.path == mLocalMediaList!![mPosition].path) {
                        SelectOptions.instance.selectLocalMedia.remove(media)
                        break
                    }
                }
            } else {
                if (SelectOptions.instance.selectLocalMedia.size < FunctionOptions.instance.maxSelectNum) {
                    SelectOptions.instance.selectLocalMedia.add(mLocalMediaList!![mPosition])
                    binding.tvCheck.isSelected = true
                    binding.tvCheck.startAnimation(activity.let { OptAnimationLoader.loadAnimation(it, R.anim.modal_in) })
                } else {
                    Toast.makeText(activity, activity?.getString(R.string.picture_message_max_num, FunctionOptions.instance.maxSelectNum.toString() + ""), Toast.LENGTH_LONG)
                        .show()
                }
            }
            updateSelectMenu()
        }
    }

    private fun updateSelectMenu() {
        if (SelectOptions.instance.selectLocalMedia.size > 0) {
            binding.albumFinish.visibility = View.VISIBLE
            binding.albumFinish.text =
                "完成(" + SelectOptions.instance.selectLocalMedia.size + "/" + FunctionOptions.instance.maxSelectNum + ")"
        } else {
            binding.albumFinish.visibility = View.GONE
        }
        binding.albumTitle.text = (mPosition + 1).toString() + "/" + mLocalMediaList!!.size
        binding.tvCheck.isSelected = isSelected(mLocalMediaList!![mPosition])
    }

    /**
     * 当前图片是否选中
     *
     * @param image
     * @return
     */
    fun isSelected(image: LocalMedia): Boolean {
        for (media in SelectOptions.instance.selectLocalMedia) {
            if (media.path == image.path) {
                return true
            }
        }
        return false
    }
}