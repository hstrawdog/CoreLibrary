package com.hqq.album.common;

import com.hqq.album.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.common
 * @FileName :   SelectOptions
 * @Date : 2020/7/1 0001  上午 9:39
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class SelectOptions {

    /**
     * 选择后的数据
     */
    private ArrayList<LocalMedia> mSelectLocalMedia = new ArrayList<>();
    /**
     * 文件夹缓存内容
     */
    private List<LocalMedia> mFolderLocalMedia = new ArrayList<>();

    public static SelectOptions getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void setSelectLocalMedia(ArrayList<LocalMedia> selectLocalMedia) {
        mSelectLocalMedia = selectLocalMedia;
    }

    public ArrayList<LocalMedia> getSelectLocalMedia() {
        return mSelectLocalMedia;
    }

    public void setFolderLocalMedia(List<LocalMedia> folderLocalMedia) {
        mFolderLocalMedia = folderLocalMedia;
    }

    public List<LocalMedia> getFolderLocalMedia() {
        return mFolderLocalMedia;
    }

    public void reset() {
        mFolderLocalMedia.clear();
        mSelectLocalMedia.clear();

    }


    private static final class InstanceHolder {
        private static final SelectOptions INSTANCE = new SelectOptions();
    }
}
