package com.hqq.core.ui.vm;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.hqq.core.BaseCommonsKey;

import java.util.ArrayList;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseListModel
 * @Date : 2020/7/29 0029  上午 10:28
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseListViewModel extends BaseViewModel {
    public MutableLiveData<ArrayList> mDate = new MediatorLiveData<>();
    protected int mPageCount = 1;
    protected int mPageSize = BaseCommonsKey.PAGE_SIZE;

    /**
     * 初始化数据
     */
    abstract public void initData();


    /**
     * 加载更多
     */
    public void onLoadMore() {

    }


    public BaseListViewModel setDate(MutableLiveData<ArrayList> date) {
        mDate = date;
        return this;
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

    public ArrayList getDate() {
        return mDate.getValue();
    }

    public BaseListViewModel setDate(ArrayList date) {
        mDate.setValue(date);
        return this;
    }

}
