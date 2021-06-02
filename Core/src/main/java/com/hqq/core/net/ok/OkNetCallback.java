/*
 * Create on 2016-12-14 下午3:42
 * FileName: OKNetCallback.java
 * Author: huang qiqiang
 * Contact: http://www.huangqiqiang.cn
 * Email 593979591@QQ.com
 *
 */

package com.hqq.core.net.ok;

/**
 * $desc$
 * author  黄其强
 * Created by Administrator on 2016/12/14 15:42.
 */

public interface OkNetCallback {

    void onSuccess(String statusCode, String response);

    void onFailure(String statusCode, String errMsg, String response);

}
