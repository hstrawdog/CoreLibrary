package com.hqq.core.net.download;

/**
 *  需要更换成 DownloadListener
 */
@Deprecated
public interface DownloadCallback {

	public void onDownloaded();
	
	public void onProgress(float percent);
	
	public void onFailure(int code, String errMsg);
}
