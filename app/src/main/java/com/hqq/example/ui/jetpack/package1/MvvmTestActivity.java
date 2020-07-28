package com.hqq.example.ui.jetpack.package1;

import android.app.Activity;
import android.content.Intent;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableArrayMap;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hqq.core.ui.vm.BaseActivity;
import com.hqq.example.R;
import com.hqq.example.databinding.ActivityMvvmBinding;
import com.hqq.example.ui.jetpack.livedata.User;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.package1
 * @FileName :   Package1Activity
 * @Date : 2020/7/21 0021  下午 3:53
 * @Email :  qiqiang213@gmail.com
 * @Descrive : ViewBinding + ViewModel + liveData
 */
public class MvvmTestActivity extends BaseActivity<ActivityMvvmBinding,UserViewModel> {
    public static void open(Activity context) {
        Intent starter = new Intent(context, MvvmTestActivity.class);
        context.startActivityForResult(starter, -1);
    }

    @Override
    protected void addViewModel() {
//       mBinding.setVariable(BR.vm, new ViewModelProvider(this).get(UserViewModel.class));
        mBinding.setVm(new ViewModelProvider(this).get(UserViewModel.class));
        mBinding.setTitle("这是一个变量String");
        ObservableArrayMap<String, String> map = new ObservableArrayMap<>();
        map.put("key1", "ObservableArrayMap  value1");
        map.put("key2", "ObservableArrayMap value2");
        map.put("key3", "ObservableArrayMap value3");
        map.put("key4", "ObservableArrayMap value4");
        mBinding.setMap(map);

        ObservableArrayList observableArrayList = new ObservableArrayList<String>();
        observableArrayList.add("key1");
        observableArrayList.add("key2");
        mBinding.setList(observableArrayList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvvm;
    }

    @Override
    protected void initViews() {

        mBinding.getVm().getLiveData().observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });

    }

   public static final class Fields {
        public final static MutableLiveData<Integer> AGE = new MutableLiveData<>(1);
        public final  static int AGE2=1;
    }

}