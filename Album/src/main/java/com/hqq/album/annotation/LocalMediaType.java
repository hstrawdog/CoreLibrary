package com.hqq.album.annotation;

import androidx.annotation.IntDef;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.annotation
 * @FileName :   LocalMediaType
 * @Date : 2019/12/2 0002  下午 1:23
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
@IntDef({LocalMediaType.VALUE_TYPE_IMAGE, LocalMediaType.VALUE_TYPE_VIDEO})
public @interface LocalMediaType {
    /**
     * 图片类型
     */
    int VALUE_TYPE_IMAGE = 1;
    /**
     * 视频类型
     */
    int VALUE_TYPE_VIDEO = 2;
}
