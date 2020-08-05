package com.hqq.example.demo.news;

import com.hqq.core.ui.vm.BaseListViewModel;
import com.hqq.example.demo.net.HttpManager;
import com.hqq.example.demo.net.NetCallback;

import java.util.ArrayList;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.news
 * @FileName :   NewViewModel
 * @Date : 2020/8/5 0005  上午 10:36
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class NewsViewModel extends BaseListViewModel {


    @Override
    public void onCrete() {
        super.onCrete();
        HttpManager.getNews(new NetCallback<News>() {
            @Override
            public void onSuccess(News response) {
                if (response != null) {
                    setDate(response.getData());
                }else {
                    setDate(new ArrayList());

                }
            }

            @Override
            public void onFail(int code, String message) {
                setShowToast(message);
            }
        });

    }
}
