package com.hqq.core.ui.vm;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.hqq.core.BaseCommonsKey;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListModel
 * @Date : 2020/7/29 0029  上午 10:28
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseListViewModel extends BaseViewModel {
    public MutableLiveData<List> mDate = new MediatorLiveData<List>();
    protected int mPageCount = 1;
    protected int mPageSize = BaseCommonsKey.PAGE_SIZE;


    /**
     * 加载更多
     */
    public void onLoadMore() {

    }


    public int getPageCount() {
        return mPageCount;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public BaseListViewModel setPageSize(int pageSize) {
        mPageSize = pageSize;
        return this;
    }

    public BaseListViewModel setPageCount(int pageCount) {
        mPageCount = pageCount;
        return this;
    }

    public List getDate() {
        return mDate.getValue();
    }

    public BaseListViewModel setDate(List date) {
        mDate.setValue(date);
        return this;
    }

}
