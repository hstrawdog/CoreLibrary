package com.hqq.example.ui.jetpack.hilt;

import javax.inject.Inject;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.hilt
 * @FileName :   AnalyticsServiceImpl
 * @Date : 2020/8/13 0013  下午 4:19
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class AnalyticsServiceImpl implements AnalyticsService {

    @Inject
    AnalyticsServiceImpl() {
    }

    @Override
    public String analyticsMethods() {
        return "AnalyticsServiceImpl";
    }


}