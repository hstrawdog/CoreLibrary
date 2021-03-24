package com.qq.readbook.weight.flowlayout;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TagAdapter<T> {
    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    @Deprecated
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    private SparseArray<Integer> mForbidSelect = new SparseArray();

    public TagAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    @Deprecated
    public TagAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    @Deprecated
    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
        setSelectedList(set);
    }

    @Deprecated
    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    public void setSelectedList(List set) {
        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    @Deprecated
    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }


    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public List<T> getTagDatas() {
        return mTagDatas == null ? new ArrayList<T>() : mTagDatas;

    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onChanged();
        }
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);


    public void onSelected(int position, View view) {
        Log.d("zhy", "onSelected " + position);
    }

    public void unSelected(int position, View view) {
        Log.d("zhy", "unSelected " + position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }

    public void setSelected(int position) {
        if (mCheckedPosList != null) {
            mCheckedPosList.add(position);
        }
        notifyDataChanged();
    }

    public boolean checkSelect(int position) {
        if (mForbidSelect != null && mForbidSelect.get(position) != null) {
            return true;
        }
        return false;
    }


    public void setForbidSelect(SparseArray<Integer> forbidSelect) {
        mForbidSelect = forbidSelect;
        HashSet<Integer> checkedPosList = new HashSet<Integer>();

        for (Integer integer : mCheckedPosList) {
            if (forbidSelect.get(integer) != null) {

            } else {
                checkedPosList.add(integer);
            }
        }

        setSelectedList(checkedPosList);
    }

    /**
     * @param forbidSelect 禁用集合
     * @param selectedList 选中集合
     */
    public void setForbidSelect(SparseArray<Integer> forbidSelect, Set<Integer> selectedList) {
        mForbidSelect = forbidSelect;
        HashSet<Integer> checkedPosList = new HashSet<Integer>();

        for (Integer integer : selectedList) {
            if (forbidSelect != null && forbidSelect.get(integer) != null) {

            } else {
                checkedPosList.add(integer);
            }
        }

        setSelectedList(checkedPosList);
    }

    public interface OnDataChangedListener {
        void onChanged();
    }

}
