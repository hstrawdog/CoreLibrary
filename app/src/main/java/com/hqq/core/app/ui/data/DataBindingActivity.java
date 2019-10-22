package com.hqq.core.app.ui.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.hqq.core.app.R;
import com.hqq.core.app.databinding.ActivityDataBindingBinding;
import com.hqq.core.toolbar.BaseDefToolBarImpl;
import com.hqq.core.toolbar.IToolBarBuilder;
import com.hqq.core.ui.BaseActivity;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.databing
 * @FileName :   DataBindingActivity
 * @Date : 2019/10/22 0022  下午 1:37
 * @Email :  qiqiang213@gmail.com
 * @Descrive : DataBinding 与现在的设计冲突
 */
public class DataBindingActivity extends AppCompatActivity {

    @BindView(R.id.textView2)
    TextView mTextView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data_binding);
        initView();
    }

//    @Override
//    public int setViewId() {
//        return R.layout.activity_data_binding;
//    }

//    @Override
    public void initView() {
        ActivityDataBindingBinding
                binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setUser(User.newInstance());


        Toolbar toolbar = (Toolbar)
                LayoutInflater.from(this).inflate(com.hqq.core.R.layout.layout_def_toolbar, null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


    }


}
