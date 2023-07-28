package com.hqq.album.dialog

import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.hqq.album.R
import com.hqq.album.annotation.LocalMediaType
import com.hqq.album.common.Album.Companion.from
import com.hqq.album.entity.LocalMedia
import com.hqq.core.ui.dialog.BaseDialog

/**
 * @Author : huangqiqiang
 * @Package :
 * @FileName :   PhotoDialog
 * @Date : 2018/7/4 0004  上午 9:24
 * @Descrive : TODO
 * @Email :
 */
class PhotoDialog : BaseDialog(), View.OnClickListener {
    /**
     * PhotoDialogCallBack 是空的时候 会直接回到给Activity
     */
    var mPhotoDialogCallBack: PhotoDialogCallBack? = null

    /**
     * 最大选择1
     */
    var mSelectSize = 1
    var isSupportGif = false
    var isSendAlbum = true
    fun setPhotoDialogCallBack(photoDialogCallBack: PhotoDialogCallBack?): PhotoDialog {
        mPhotoDialogCallBack = photoDialogCallBack
        return this
    }

    fun setSelectSize(selectSize: Int) {
        mSelectSize = selectSize
    }


    override val gravity: Int = Gravity.BOTTOM

    override val weight: Int = ViewGroup.LayoutParams.MATCH_PARENT

    override val layoutId: Int = R.layout.dialog_photo

    override fun initView() {
        rootView?.findViewById<View>(R.id.btn_taking_pictures)
            ?.setOnClickListener(this)
        rootView?.findViewById<View>(R.id.tv_album)
            ?.setOnClickListener(this)
        rootView?.findViewById<View>(R.id.btn_cancel)
            ?.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_taking_pictures) {
            from(this@PhotoDialog).choose(LocalMediaType.VALUE_TYPE_IMAGE)
                .setStartUpCamera(true)
                .setMaxSelectNum(mSelectSize)
                .setSupportGif(isSupportGif)
                .setIsSendAlbum(isSendAlbum)
                .forResult(0x1)
            if (mPhotoDialogCallBack == null) {
                dismiss()
            }
        } else if (view.id == R.id.tv_album) {
            from(this@PhotoDialog).choose(LocalMediaType.VALUE_TYPE_IMAGE)
                .setMaxSelectNum(mSelectSize)
                .setSupportGif(isSupportGif)
                .forResult(0x1)
            if (mPhotoDialogCallBack == null) {
                dismiss()
            }
        } else if (view.id == R.id.btn_cancel) {
            dismiss()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val list = data!!.getParcelableArrayListExtra<LocalMedia>("data")
            if (mPhotoDialogCallBack != null) {
                mPhotoDialogCallBack!!.onSelectLocalMedia(list)
            }
            dismiss()
        }
    }

    interface PhotoDialogCallBack {
        fun onSelectLocalMedia(arrayList: ArrayList<LocalMedia>?)
    }

    companion object {
        /**
         * 简单入口
         *
         * @return
         */
        @JvmStatic
        val selectPhotoDialog: PhotoDialog
            get() = PhotoDialog()

        /**
         * 通过回调
         *
         * @param maxSelectSize
         * @param photoDialogClick
         * @return
         */

        @JvmStatic
        fun getSelectPhotoDialog(maxSelectSize: Int, photoDialogClick: PhotoDialogCallBack?): PhotoDialog {
            val photoDialog = selectPhotoDialog
            photoDialog.setSelectSize(maxSelectSize)
            photoDialog.setPhotoDialogCallBack(photoDialogClick)
            return photoDialog
        }
    }
}