package com.hqq.album.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.hqq.album.R;
import com.hqq.album.annotation.LocalMediaType;
import com.hqq.album.common.Album;
import com.hqq.album.entity.LocalMedia;

import java.util.ArrayList;

/**
 * @Author : huangqiqiang
 * @Package :
 * @FileName :   PhotoDialog
 * @Date : 2018/7/4 0004  上午 9:24
 * @Descrive : TODO
 * @Email :
 */
public class PhotoDialog extends AbsDialog implements View.OnClickListener {
    /**
     * PhotoDialogCallBack 是空的时候 会直接回到给Activity
     */
    PhotoDialogCallBack mPhotoDialogCallBack;
    /**
     * 最大选择1
     */
    int mSelectSize = 1;


    boolean isSupportGif = false;

    public boolean isSendAlbum = true;

    /**
     * 简单入口
     *
     * @return
     */
    public static PhotoDialog getSelectPhotoDialog() {
        return new PhotoDialog();
    }

    /**
     * 通过回调
     *
     * @param maxSelectSize
     * @param photoDialogClick
     * @return
     */
    public static PhotoDialog getSelectPhotoDialog(int maxSelectSize, PhotoDialogCallBack photoDialogClick) {
        PhotoDialog photoDialog = getSelectPhotoDialog();
        photoDialog.setSelectSize(maxSelectSize);
        photoDialog.setPhotoDialogCallBack(photoDialogClick);
        return photoDialog;
    }


    public PhotoDialog setPhotoDialogCallBack(PhotoDialogCallBack photoDialogCallBack) {
        mPhotoDialogCallBack = photoDialogCallBack;
        return this;
    }

    public void setSelectSize(int selectSize) {
        mSelectSize = selectSize;
    }


    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected int setWeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int setView() {
        return R.layout.dialog_photo;
    }

    @Override
    protected void initView() {
        mRootView.findViewById(R.id.btn_taking_pictures).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_album).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_taking_pictures) {
            Album.from(PhotoDialog.this)
                    .choose(LocalMediaType.VALUE_TYPE_IMAGE)
                    .setStartUpCamera(true)
                    .setMaxSelectNum(mSelectSize)
                    .setSupportGif(isSupportGif)
                    .setIsSendAlbum(isSendAlbum)
                    .forResult(0x1);
            if (mPhotoDialogCallBack == null) {
                dismiss();
            }
        } else if (i == R.id.tv_album) {
            Album.from(PhotoDialog.this)
                    .choose(LocalMediaType.VALUE_TYPE_IMAGE)
                    .setMaxSelectNum(mSelectSize)
                    .setSupportGif(isSupportGif)
                    .forResult(0x1);
            if (mPhotoDialogCallBack == null) {
                dismiss();
            }
        } else if (i == R.id.btn_cancel) {
            dismiss();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<LocalMedia> list = data.getParcelableArrayListExtra("data");
            if (mPhotoDialogCallBack != null) {
                mPhotoDialogCallBack.onSelectLocalMedia(list);
            }
            dismiss();
        }
    }

    public interface PhotoDialogCallBack {
        void onSelectLocalMedia(ArrayList<LocalMedia> arrayList);
    }
}
