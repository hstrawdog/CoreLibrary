package com.easy.album.dialog

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.easy.album.R
import com.easy.album.annotation.LocalMediaType
import com.easy.album.common.Album
import com.easy.album.common.AlbumPhotoCallBack
import com.easy.core.ui.dialog.BaseDialog

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
        fun getSelectPhotoDialog(maxSelectSize: Int = 9, photoDialogClick: AlbumPhotoCallBack?): PhotoDialog {
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


    override fun getGravity(): Int {
        return Gravity.BOTTOM

    }

    override fun getDialogWeight(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT

    }

    override fun getDialogLayoutId(): Int {
        return R.layout.dialog_photo

    }

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
            Album.from(com.easy.album.annotation.LocalMediaType.VALUE_TYPE_IMAGE)
                .setStartUpCamera(true)
                .setMaxSelectNum(mSelectSize)
                .setSupportGif(isSupportGif)
                .setIsSendAlbum(isSendAlbum)
                .forResult(mPhotoDialogCallBack)
        } else if (view.id == R.id.tv_album) {
            Album.from(com.easy.album.annotation.LocalMediaType.VALUE_TYPE_IMAGE)
                .setMaxSelectNum(mSelectSize)
                .setSupportGif(isSupportGif)
                .forResult(mPhotoDialogCallBack)
        }
        dismiss()

    }


}