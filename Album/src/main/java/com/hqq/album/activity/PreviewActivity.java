/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
 * Create on 2017-1-22 下午2:10
 * FileName: PreviewActivity.java
 * Author: huang qiqiang
 * Contact: http://www.huangqiqiang.cn
 * Email 593979591@QQ.com
 *
 */

package com.hqq.album.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.hqq.album.AppManager;
import com.hqq.album.R;
import com.hqq.album.activity.base.BaseActivity;
import com.hqq.album.common.FunctionKey;
import com.hqq.album.customize.FilterImageView;
import com.hqq.album.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : cn.hqq.halbum.activity
 * @FileName :   PreviewUrlActivity
 * @Date : 2018/9/3 0003
 * @Descrive :
 * @Email :
 */
public class PreviewActivity extends BaseActivity implements View.OnClickListener {
    List<Object> mLocalMediaList;
    private RecyclerView mRcAlbumList;
    private ConstraintLayout mAlbumTitleBar;
    private FilterImageView mAlbumBack;
    private TextView mAlbumTitle;
    private LinearLayout mLlCheck;
    private ImageView mTvCheck;
    private LinearLayout mLlComplete;
    private TextView mAlbumFinish;
    int mPosition = 0;


    public static void openPreviewActivity2LocalMedia(Activity context, List<LocalMedia> data, int position) {
        ArrayList<Object> list = new ArrayList<>();
        for (LocalMedia localMedia : data) {
            if (localMedia.getUri() != null) {
                list.add(localMedia.getUri());
            } else if (!localMedia.getPath().isEmpty()) {
                list.add(localMedia.getPath());
            }
        }
        Intent intent = new Intent(context, PreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FunctionKey.KEY_DATA, list);
        intent.putExtras(bundle);
        intent.putExtra(FunctionKey.KEY_POSITION, position + 1);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void openPreviewActivity(Activity context, ArrayList<Object> list, int mPosition) {
        Intent intent = new Intent(context, PreviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FunctionKey.KEY_DATA, list);
        intent.putExtras(bundle);
        intent.putExtra(FunctionKey.KEY_POSITION, mPosition + 1);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void openPreviewActivity(Activity context, Object url) {
        Intent intent = new Intent(context, PreviewActivity.class);
        ArrayList<Object> list = new ArrayList<>();
        list.add(url);
        intent.putExtra(FunctionKey.KEY_POSITION, 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FunctionKey.KEY_DATA, list);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_preview_v2);
        mLocalMediaList = (List<Object>) getIntent().getSerializableExtra(FunctionKey.KEY_DATA);
        initView();
        mLlCheck.setVisibility(View.GONE);
        PreviewAdapter previewAdapter = new PreviewAdapter();
        mRcAlbumList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(mRcAlbumList);
        mRcAlbumList.setAdapter(previewAdapter);
        mRcAlbumList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (first == last && mPosition != last) {
                        mPosition = last;
                        updateSelectMenu();
                    }
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mPosition = getIntent().getIntExtra(FunctionKey.KEY_POSITION, 1) - 1;
        mRcAlbumList.scrollToPosition(mPosition);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.album_back) {
            AppManager.getAppManager().finishActivity();
        }
    }

    private void initView() {
        mRcAlbumList = (RecyclerView) findViewById(R.id.rc_album_list);
        mAlbumTitleBar = (ConstraintLayout) findViewById(R.id.album_title_bar);
        mAlbumBack = (FilterImageView) findViewById(R.id.album_back);
        mAlbumTitle = (TextView) findViewById(R.id.album_title);
        mLlCheck = (LinearLayout) findViewById(R.id.ll_check);
        mTvCheck = (ImageView) findViewById(R.id.tv_check);
        mLlComplete = (LinearLayout) findViewById(R.id.ll_complete);
        mAlbumFinish = (TextView) findViewById(R.id.album_finish);

//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mAlbumTitleBar.getLayoutParams();
//        layoutParams.setMargins(0, AlbumUtils.getStatusBarHeight(this), 0, 0);
//        mAlbumTitleBar.setLayoutParams(layoutParams);

        mAlbumBack.setOnClickListener(this);
        mLlCheck.setOnClickListener(this);
        mAlbumFinish.setOnClickListener(this);

        mAlbumTitle.setText(getIntent().getIntExtra(FunctionKey.KEY_POSITION, 1) + "/" + mLocalMediaList.size());

    }

    private void updateSelectMenu() {

        mAlbumTitle.setText(mPosition + 1 + "/" + mLocalMediaList.size());

    }


    class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_album_preview, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            initData(PreviewActivity.this, viewHolder, mLocalMediaList.get(i));
        }

        @Override
        public int getItemCount() {
            return mLocalMediaList.size();
        }


        private void initData(final Context context, final ViewHolder viewHolder, Object localMedia) {
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.photoDraweeView.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.GONE);
            Glide.with(context)
                    .asBitmap()
                    .load(localMedia)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            // 创建一个新的Bitmap
                            Bitmap bitmap = Bitmap.createBitmap(resource);
                            viewHolder.photoDraweeView.setImage(ImageSource.bitmap(bitmap));
                            viewHolder.progressBar.setVisibility(View.GONE);


                        }

                    })
            ;

        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            VideoView videoView;
            ProgressBar progressBar;
            SubsamplingScaleImageView photoDraweeView;

            public ViewHolder(View itemView) {
                super(itemView);
                videoView = itemView.findViewById(R.id.vv_view);
                progressBar = itemView.findViewById(R.id.pb_bar);
                photoDraweeView = itemView.findViewById(R.id.image_item);

            }
        }


    }


}
