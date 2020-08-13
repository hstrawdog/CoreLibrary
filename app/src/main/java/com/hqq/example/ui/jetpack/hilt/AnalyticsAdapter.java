package com.hqq.example.ui.jetpack.hilt;

import javax.inject.Inject;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.hilt
 * @FileName :   AnalyticsAdapter
 * @Date : 2020/8/13 0013  下午 4:18
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class AnalyticsAdapter {

    public final AnalyticsService service;

    @Inject
    AnalyticsAdapter(AnalyticsService service) {
        this.service = service;
    }

    public AnalyticsService getService() {
        return service;
    }
}
