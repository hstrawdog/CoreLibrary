package com.hqq.album.dialog

import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.hqq.album.R
import com.hqq.album.annotation.LocalMediaType
import com.hqq.album.common.Album
import com.hqq.album.common.FunctionOptions
import com.hqq.album.common.AlbumPhotoCallBack
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
    companion object {
        /**
         * 通过回调
         *
         * @param maxSelectSize
         * @param photoDialogClick
         * @return
         */

        @JvmStatic
        fun getSelectPhotoDialog( maxSelectSize: Int = 9, photoDialogClick: AlbumPhotoCallBack?): PhotoDialog {
            val photoDialog = PhotoDialog()
            photoDialog.setSelectSize(maxSelectSize)
            photoDialog.setPhotoDialogCallBack(photoDialogClick)
            return photoDialog
        }
    }

    /**
     * PhotoDialogCallBack 是空的时候 会直接回到给Activity
     */
    var mPhotoDialogCallBack: AlbumPhotoCallBack? = null

    /**
     * 最大选择1
     */
    var mSelectSize = 1
    var isSupportGif = false
    var isSendAlbum = true
    fun setPhotoDialogCallBack(photoDialogCallBack: AlbumPhotoCallBack?): PhotoDialog {
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
            Album.from(LocalMediaType.VALUE_TYPE_IMAGE)
                .setStartUpCamera(true)
                .setMaxSelectNum(mSelectSize)
                .setSupportGif(isSupportGif)
                .setIsSendAlbum(isSendAlbum)
                .forResult(mPhotoDialogCallBack)
            if (mPhotoDialogCallBack == null) {
                dismiss()
            }
        } else if (view.id == R.id.tv_album) {
            Album.from(LocalMediaType.VALUE_TYPE_IMAGE)
                .setMaxSelectNum(mSelectSize)
                .setSupportGif(isSupportGif)
                .forResult(mPhotoDialogCallBack)
            if (mPhotoDialogCallBack == null) {
                dismiss()
            }
        } else if (view.id == R.id.btn_cancel) {
            dismiss()
        }
    }


}