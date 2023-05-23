package com.hqq.example.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.hqq.album.activity.PreviewActivity;
import com.hqq.album.annotation.LocalMediaType;
import com.hqq.album.common.Album;
import com.hqq.album.dialog.PhotoDialog;
import com.hqq.album.entity.LocalMedia;
import com.hqq.album.utils.AlbumFileUtils;
import com.hqq.core.ui.base.BaseActivity;
import com.hqq.example.R;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @Date : 16:59
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class AlbumIndexActivity  extends BaseActivity {

    TextView mTvInfo;
    TextView tv_file;
    @Override
    public int getLayoutViewId() {
        return R.layout.activity_album_index;
    }

    @Override
    public void initView() {
        mTvInfo = findViewById(R.id.tv_info);
        tv_file = findViewById(R.id.tv_file);

        findViewById(R.id.button).setOnClickListener(this::previewUrl);
        findViewById(R.id.button2).setOnClickListener(this::openPhotoSelectDialog);
        findViewById(R.id.button21).setOnClickListener(this::openPhotoSelectDialog4Activity);
        findViewById(R.id.button3).setOnClickListener(this::openCamera);
        findViewById(R.id.button4).setOnClickListener(this::openAlbum);
        findViewById(R.id.button7).setOnClickListener(this::openCameraAndAlbum);
        findViewById(R.id.button8).setOnClickListener(this::openVideoAlbum);
        findViewById(R.id.button9).setOnClickListener(this::openFolderAlbum);
        findViewById(R.id.button5).setOnClickListener(this::httpsTest);
        findViewById(R.id.button5).setOnClickListener(this::httpsTest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<LocalMedia> list = data.getParcelableArrayListExtra("data");
            Log.e("---------------------", "onActivityResult: ");
            StringBuilder stringBuilder = new StringBuilder();
            if (list != null) {
                for (LocalMedia localMedia : list) {
                    stringBuilder.append(localMedia.getPath() + "\n");
                    ImageView imageView = findViewById(R.id.imageView);
                    tv_file.setText(new File(localMedia.getPath()).getName());
                    Glide.with(imageView).load(AlbumFileUtils.getFile2Uri(this, localMedia.getPath())).into(imageView);
                }
            }

            mTvInfo.setText(stringBuilder.toString());
        }

    }

    /**
     * @param context
     * @param filePath relative path in Q, such as: "DCIM/" or "DCIM/dir_name/"
     *                 absolute path before Q
     * @return
     */
    private static Cursor searchTxtFromPublic(Context context, String filePath, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        if (!filePath.endsWith("/")) {
            filePath = filePath + "/";
        }

        String queryPathKey = MediaStore.Files.FileColumns.RELATIVE_PATH;
        String selection = queryPathKey + "=? and " + MediaStore.Files.FileColumns.DISPLAY_NAME + "=?";
        Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                new String[]{MediaStore.Files.FileColumns._ID, queryPathKey, MediaStore.Files.FileColumns.DISPLAY_NAME},
                selection,
                new String[]{filePath, fileName},
                null);

        return cursor;
    }


    /**
     * 测试http预览
     *
     * @param view
     */
    private void httpsTest(View view) {
        handleSSLHandshake();
        String gif = "https://images.shangwenwan.com/mall/6d392bfd-6273-4992-a24d-74f4b39b19d3?imageMogr2/size-limit/54.7k!/crop/!485x485a6a8";
        PreviewActivity.openPreviewActivity(activity, gif);
    }

    /**
     * 打开相册 带摄像头
     *
     * @param view
     */
    private void openCameraAndAlbum(View view) {
        Album.from(activity)
                .choose(LocalMediaType.VALUE_TYPE_IMAGE)
                .setDisplayCamera(true)
                .forResult(0x1)
        ;
    }

    /**
     * 打开相册
     *
     * @param view
     */
    private void openAlbum(View view) {
        Album.from(activity)
                .choose(LocalMediaType.VALUE_TYPE_IMAGE)
                .forResult(0x1)
        ;
    }

    /**
     * 打开指定文件夹
     *
     * @param view
     */
    private void openFolderAlbum(View view) {
        Album.from(activity)
                .choose(LocalMediaType.VALUE_TYPE_IMAGE)
                .setChooseFolder("爱饰拍")
                .forFolderResult(0x1);
    }

    /**
     * 打开摄像头
     *
     * @param view
     */
    private void openCamera(View view) {
        Album.from(activity)
                .choose(LocalMediaType.VALUE_TYPE_IMAGE)
                .setStartUpCamera(true)
                .forResult(0x1)
        ;
    }

    /**
     * 通用的选择Dialog
     *
     * @param view
     */
    private void openPhotoSelectDialog(View view) {
        PhotoDialog.getSelectPhotoDialog(1, new PhotoDialog.PhotoDialogCallBack() {
            @Override
            public void onSelectLocalMedia(ArrayList<LocalMedia> arrayList) {
                if (arrayList != null && arrayList.size() > 0) {
                    Toast.makeText(activity, arrayList.get(0).getPath(), Toast.LENGTH_SHORT).show();
                }
            }

        }).show(getSupportFragmentManager());

    }

    /**
     * 通用的选择Dialog 结果用Activity接收
     *
     * @param view
     */
    private void openPhotoSelectDialog4Activity(View view) {

        PhotoDialog.getSelectPhotoDialog().show(getSupportFragmentManager());
    }


    /**
     * 视频 入口
     *
     * @param view
     */
    public void openVideoAlbum(View view) {
        Album.from(activity)
                .choose(LocalMediaType.VALUE_TYPE_VIDEO)
                .forResult(0x1);
    }

    /**
     * 预览图片
     *
     * @param view
     */
    private void previewUrl(View view) {
        ArrayList<Object> list = new ArrayList<>();
        list.add("http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg");
        list.add("http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg");
        list.add("http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg");
        list.add("http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg");
        PreviewActivity.openPreviewActivity(activity, list, 0);
    }

    /**
     * https 图片加载
     */
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }


}
