package com.easy.core.album.annotation;

import androidx.annotation.IntDef;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.album.annotation
 * @FileName :   LocalMediaType
 * @Date : 2019/12/2 0002  下午 1:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@IntDef({LocalMediaType.VALUE_TYPE_IMAGE, LocalMediaType.VALUE_TYPE_VIDEO, LocalMediaType.VALUE_URL_IMAGE})
public @interface LocalMediaType {
    /**
     * 图片类型
     */
    int VALUE_TYPE_IMAGE = 1;
    /**
     * 视频类型
     */
    int VALUE_TYPE_VIDEO = 2;
    /**
     * url 图片预览
     */
    int VALUE_URL_IMAGE = 3;
}
