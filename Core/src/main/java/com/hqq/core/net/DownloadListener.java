package com.hqq.core.net;

public interface DownloadListener {

    /**
     * 开始下载
     */
    void start(long max);

    /**
     * 正在下载
     * @param progress
     */
    void loading(float progress);

    /**
     * 下载完成
     */
    void complete(String filePath);

    /**
     * 请求失败
     *
     */
    void fail(int code, Exception e);
}