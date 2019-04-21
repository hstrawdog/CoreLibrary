package com.hqq.coreapp.bean;

import com.hqq.core.recycler.IBannerBean;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: com.hqq.coreapp.bean.BannerBean.java
 * @emain: 593979591@qq.com
 * @date: 2019-04-21 18:45
 */
public class BannerBean implements IBannerBean {
    int id;

    public BannerBean(int id) {
        this.id = id;
    }

    @Override
    public String getBannerUrl() {
        return id+"";
    }

    @Override
    public String getBannerTitle() {
        return "";
    }

    @Override
    public Object getBannerTag() {
        return "";
    }
}
