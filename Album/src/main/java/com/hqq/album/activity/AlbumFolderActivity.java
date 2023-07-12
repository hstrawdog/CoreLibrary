package com.hqq.album.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hqq.album.Adapter.AlbumDetailAdapter;
import com.hqq.album.Adapter.AlbumDirectoryAdapter;
import com.hqq.album.AppManager;
import com.hqq.album.R;
import com.hqq.album.activity.base.BaseActivity;
import com.hqq.album.common.FunctionKey;
import com.hqq.album.common.FunctionOptions;
import com.hqq.album.common.LocalMediaLoader;
import com.hqq.album.common.SelectOptions;
import com.hqq.album.decoration.GridSpacingItemDecoration;
import com.hqq.album.decoration.RecycleViewDivider;
import com.hqq.album.entity.LocalMedia;
import com.hqq.album.entity.LocalMediaFolder;
import com.hqq.album.utils.AlbumUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.activity
 * @Date : 下午 4:51
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class AlbumFolderActivity extends BaseActivity implements AlbumDetailAdapter.OnPhotoSelectChangedListener, View.OnClickListener, AlbumDirectoryAdapter.OnItemClickListener {
    List<LocalMediaFolder> mFolderList = new ArrayList<>();
    private AlbumDirectoryAdapter mAdapter;
    private static final int CODE_CLOSE = 0x9910;
    private RecyclerView mRecyclerView;
    TextView mTvFinish;
    AlbumDetailAdapter mAlbumDetailAdapter;
    TextView mTvTile;


    int mSelectPosition = 0;
    String mFolderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_folder);
        mTvFinish = (TextView) findViewById(R.id.album_finish);
        mTvFinish.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rc_list);
        mTvTile = (TextView) findViewById(R.id.album_title);
        findViewById(R.id.album_back).setOnClickListener(this);
        findViewById(R.id.ll_title).setOnClickListener(this);
        mFolderName = FunctionOptions.getInstance().getChooseFolder();

        initViews();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, AlbumUtils.dip2px(this, 2), false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mAlbumDetailAdapter = new AlbumDetailAdapter(this, FunctionOptions.getInstance().getMaxSelectNum());
        mRecyclerView.setAdapter(mAlbumDetailAdapter);
        mAlbumDetailAdapter.setOnPhotoSelectChangedListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvFinish.setText("完成(" + SelectOptions.getInstance().getSelectLocalMedia().size() + "/" + FunctionOptions.getInstance().getMaxSelectNum() + ")");
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.album_back) {
            if (mRecyclerView.getAdapter() == mAdapter) {
                mRecyclerView.removeItemDecorationAt(0);
                mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, AlbumUtils.dip2px(this, 2), false));
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                mRecyclerView.setAdapter(mAlbumDetailAdapter);
                return;
            }

            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.album_finish) {
            AppManager.getAppManager().finishAllActivityAndCallBack();
        } else if (i == R.id.ll_title) {
            mAdapter = new AlbumDirectoryAdapter(this);
            mAdapter.bindFolderData(mFolderList);
            mRecyclerView.removeItemDecorationAt(0);
            mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, AlbumUtils.dip2px(this, 0.5f), ContextCompat.getColor(this, R.color.line_color)));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(this);
            mRecyclerView.scrollToPosition(mSelectPosition);

        }
    }

    private void initViews() {
        // 第一次启动ImageActivity，没有获取过相册列表
        // 先判断手机是否有读取权限，主要是针对6.0已上系统
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            initData();
        } else {
            requestPermission(FunctionKey.REQUEST_CODE_READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case FunctionKey.REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                } else {
                    Toast.makeText(mContext, "读取内存卡权限已被拒绝,请在系统设置中开启权限", Toast.LENGTH_SHORT).show();
                    //应用程序详情页面
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                }
                break;
            case FunctionKey.REQUEST_CODE_CAMERA:
                Toast.makeText(mContext, "拍照权限已被拒绝,请在系统设置中开启权限", Toast.LENGTH_SHORT).show();
                //应用程序详情页面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                break;
            default:

                break;
        }
    }

    private void initData() {
        LocalMediaLoader localMediaLoader = new LocalMediaLoader(this, FunctionOptions.getInstance().getAlbumType(), FunctionOptions.getInstance().isSupportGif());
        localMediaLoader.loadAllImage(new LocalMediaLoader.LocalMediaLoadListener() {
            @Override
            public void loadComplete(List<LocalMediaFolder> folders) {
                mFolderList = folders;
                setAlbum(folders);
                if (mAlbumDetailAdapter.getItemCount() <= 0) {
                    mFolderName = "最近照片";
                    setAlbum(folders);
                }
            }
        });
    }

    private void setAlbum(List<LocalMediaFolder> folders) {
        for (int i = 0; i < folders.size(); i++) {
            if (folders.get(i).getName().equals(mFolderName)) {
                mAlbumDetailAdapter.bindImagesData(folders.get(i).getImages());
                SelectOptions.getInstance().setFolderLocalMedia(folders.get(i).getImages());
                mTvTile.setText(mFolderName);
                mSelectPosition = i;
                break;
            }
        }
    }

    @Override
    public void onTakePhoto() {

    }

    @Override
    public void onChange(List<LocalMedia> selectImages) {
        mTvFinish.setText("完成(" + selectImages.size() + "/" + FunctionOptions.getInstance().getMaxSelectNum() + ")");
    }

    @Override
    public void onPictureClick(LocalMedia media, int position) {
        startActivityForResult(new Intent(this, AlbumPreviewActivity.class).putExtra(FunctionKey.KEY_POSITION, position + 1).putExtra(FunctionKey.KEY_FOLDER_NAME, mFolderName), CODE_CLOSE);
    }

    @Override
    public void onItemClick(String folderName, List<LocalMedia> images) {
        mRecyclerView.removeItemDecorationAt(0);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, AlbumUtils.dip2px(this, 2), false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mAlbumDetailAdapter.bindImagesData(images);
        mRecyclerView.setAdapter(mAlbumDetailAdapter);
        SelectOptions.getInstance().setFolderLocalMedia(images);

        mFolderName = folderName;
        for (int i = 0; i < mFolderList.size(); i++) {
            if (mFolderList.get(i).getName().equals(folderName)) {
                mTvTile.setText(mFolderName);
                mSelectPosition = i;
                break;
            }
        }


    }
}
