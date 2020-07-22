package com.hqq.example.ui.info;

import android.widget.TextView;

import com.hqq.core.ui.BaseActivity;
import com.hqq.core.utils.FilePathUtils;
import com.hqq.core.utils.TextSpannableBuilder;
import com.hqq.example.R;

import butterknife.BindView;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.info
 * @FileName :   FilePathActivity
 * @Date : 2019/11/29 0029  上午 10:23
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
public class FilePathActivity extends BaseActivity {


    @BindView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    public int getLayoutViewId() {
        return R.layout.activity_base_info;
    }

    @Override
    public void initView() {
        mTvInfo.setText(new TextSpannableBuilder()
                .addTextPart("\nEnvironment.getDataDirectory()    ")
                .addTextPart(FilePathUtils.getPath4Data())
                .addTextPart("\nContext.getCacheDir()  ")
                .addTextPart(FilePathUtils.getCacheDir(this))
                .addTextPart("\nContext.getFilesDir()  ")
                .addTextPart(FilePathUtils.getFilesDir(this))
                .addTextPart("\nContext.getFileStreamPath()  ")
                .addTextPart(FilePathUtils.getFileStreamPath(this))
                .addTextPart("\nContext.getFileStreamPath()  ")
                .addTextPart(FilePathUtils.getFileStreamPath(this, "fileName"))
                .addTextPart("\nContext.getExternalStorageDirectory()  ")
                .addTextPart(FilePathUtils.getExternalStorageDirectory())
                .addTextPart("\nContext.getExternalCacheDir()  ")
                .addTextPart(FilePathUtils.getExternalCacheDir(this))
                .addTextPart("\nContext.getExternalFilesDir()  ")
                .addTextPart(FilePathUtils.getExternalFilesDir(this))
                .addTextPart("\nContext.getExternalFilesDir()  ")
                .addTextPart(FilePathUtils.getExternalFilesDir(this, "fileName"))
                .addTextPart("\nContext.getSdCardExternalStorageDirectory()  ")
                .addTextPart(FilePathUtils.getSdCardExternalStorageDirectory())
                .addTextPart("\nContext.getSdCardExternalStoragePublicDirectory()  ")
                .addTextPart(FilePathUtils.getSdCardExternalStoragePublicDirectory())
                .addTextPart("\nContext.getSdCardExternalStoragePublicDirectory()  ")
                .addTextPart(FilePathUtils.getSdCardExternalStoragePublicDirectory("fileName"))
                .addTextPart("\n  ")
                .addTextPart("")

                .build());
    }

}
