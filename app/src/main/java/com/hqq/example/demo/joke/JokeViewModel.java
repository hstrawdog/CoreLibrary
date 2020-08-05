package com.hqq.example.demo.joke;

import com.hqq.core.ui.vm.BaseListViewModel;
import com.hqq.example.demo.net.HttpManager;
import com.hqq.example.demo.net.NetCallback;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.joke
 * @FileName :   JokeViewModel
 * @Date : 2020/8/5 0005  下午 2:29
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class JokeViewModel extends BaseListViewModel {
    @Override
    public void onCrete() {
        super.onCrete();

        getJokeList();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();

        getJokeList();

    }

    private void getJokeList() {
        HttpManager.getJoke(mPageCount, new NetCallback<Joke>() {
            @Override
            public void onSuccess(Joke response) {
                setDate(response.getData());
            }
        });
    }
}
