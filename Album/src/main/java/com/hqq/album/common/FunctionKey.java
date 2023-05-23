/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hqq.album.common;

/**
 * 在此写用途
 * 配置 静态文件
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.common.FunctionKey.java
 * @author: 黄其强
 * @date: 2017-05-04 20:15
 */
public class FunctionKey {
    /**
     * 请求拍照
     */
    public final static int REQUEST_CODE_REQUEST_CAMERA = 0x99;
    /**
     * 申请文件权限
     */
    public static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 0x01;
    /**
     * 申请拍照权限
     */
    public static final int REQUEST_CODE_CAMERA = 0x02;
    /**
     * 文件夹
     */
    public static final String KEY_FOLDER_NAME = "folderName";
    /**
     * position
     */
    public static final String KEY_POSITION = "position";
    /**
     * data
     */
    public static final String KEY_DATA = "data";

}
