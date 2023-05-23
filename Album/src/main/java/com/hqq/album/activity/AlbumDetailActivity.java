package com.hqq.album.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hqq.album.Adapter.AlbumDetailAdapter;
import com.hqq.album.AppManager;
import com.hqq.album.R;
import com.hqq.album.activity.base.BaseActivity;
import com.hqq.album.common.FunctionKey;
import com.hqq.album.common.FunctionOptions;
import com.hqq.album.common.SelectOptions;
import com.hqq.album.decoration.GridSpacingItemDecoration;
import com.hqq.album.entity.LocalMedia;
import com.hqq.album.utils.AlbumUtils;

import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : cn.hqq.halbum.activity
 * @FileName :   AlbumDetailActivity
 * @Date : 2018/10/8 0008
 * @Descrive :
 * @Email :
 */
public class AlbumDetailActivity extends BaseActivity implements View.OnClickListener, AlbumDetailAdapter.OnPhotoSelectChangedListener {


    private static final int CODE_CLOSE = 0x9910;
    private RecyclerView mRecyclerView;
    TextView mTvFinish;
    TextView mTvTile;
    AlbumDetailAdapter mAlbumDetailAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        List<LocalMedia> list = SelectOptions.getInstance().getFolderLocalMedia();
        mAlbumDetailAdapter.bindImagesData(list);
        mTvFinish.setText("完成(" + SelectOptions.getInstance().getSelectLocalMedia().size() + "/" + FunctionOptions.getInstance().getMaxSelectNum() + ")");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        initViews();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_album_detail);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, AlbumUtils.dip2px(this, 2), false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mAlbumDetailAdapter = new AlbumDetailAdapter(this, FunctionOptions.getInstance().getMaxSelectNum());
        mRecyclerView.setAdapter(mAlbumDetailAdapter);
        mAlbumDetailAdapter.setOnPhotoSelectChangedListener(this);
        findViewById(R.id.album_back).setOnClickListener(this);
        findViewById(R.id.album_finish).setOnClickListener(this);
        mTvFinish = (TextView) findViewById(R.id.album_finish);
        mTvTile = (TextView) findViewById(R.id.album_title);
        mTvTile.setText(getIntent().getStringExtra(FunctionKey.KEY_FOLDER_NAME));
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.album_back) {
            AppManager.getAppManager().finishActivity();
        }
        if (i == R.id.album_finish) {
            AppManager.getAppManager().finishAllActivityAndCallBack();
        }
    }

    @Override
    public void onTakePhoto() {// 打开相机

    }

    @Override
    public void onChange(List<LocalMedia> selectImages) {
        mTvFinish.setText("完成(" + selectImages.size() + "/" +  FunctionOptions.getInstance().getMaxSelectNum() + ")");
    }

    @Override
    public void onPictureClick(LocalMedia media, int position) {
        startActivityForResult(new Intent(this, AlbumPreviewActivity.class)
                        .putExtra(FunctionKey.KEY_POSITION, position + 1)
                        .putExtra(FunctionKey.KEY_FOLDER_NAME, getIntent().getStringExtra(FunctionKey.KEY_FOLDER_NAME))
                , CODE_CLOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_CLOSE && resultCode == Activity.RESULT_OK) {
            finish();
        }

    }
}
