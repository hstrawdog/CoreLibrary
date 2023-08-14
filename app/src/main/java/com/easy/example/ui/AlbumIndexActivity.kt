package com.easy.example.ui

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.easy.album.activity.AlbumPreviewActivity
import com.easy.album.annotation.LocalMediaType
import com.easy.album.common.Album
import com.easy.album.common.AlbumPhotoCallBack
import com.easy.album.dialog.PhotoDialog.Companion.getSelectPhotoDialog
import com.easy.album.entity.LocalMedia
import com.easy.album.utils.AlbumFileUtils.getFile2Uri
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R
import java.io.File

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @Date : 16:59
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class AlbumIndexActivity : BaseActivity() {
    var mTvInfo: TextView? = null
    var tv_file: TextView? = null
    override val layoutViewId: Int
        get() = R.layout.activity_album_index

    var call = object : AlbumPhotoCallBack {
        override fun onSelectLocalMedia(arrayList: ArrayList<com.easy.album.entity.LocalMedia>?) {
            val list = arrayList
            Log.e("---------------------", "onActivityResult: ")
            val stringBuilder = StringBuilder()
            if (list != null) {
                for (localMedia in list) {
                    stringBuilder.append("${localMedia.path}".trimIndent())
                    val imageView = findViewById<ImageView>(R.id.imageView)
                    tv_file!!.text = File(localMedia.path).name
                    Glide.with(imageView)
                        .load(getFile2Uri(activity, localMedia.path))
                        .into(imageView)
                }
            }
            mTvInfo!!.text = stringBuilder.toString()
        }

    }


    override fun initView() {
        mTvInfo = findViewById(R.id.tv_info)
        tv_file = findViewById(R.id.tv_file)
        findViewById<View>(R.id.button).setOnClickListener { view: View -> previewUrl(view) }
        findViewById<View>(R.id.button2).setOnClickListener { view: View -> openPhotoSelectDialog(view) }
        findViewById<View>(R.id.button21).setOnClickListener { view: View -> openPhotoSelectDialog4Activity(view) }
        findViewById<View>(R.id.button3).setOnClickListener { view: View -> openCamera(view) }
        findViewById<View>(R.id.button4).setOnClickListener { view: View -> openAlbum(view) }
        findViewById<View>(R.id.button7).setOnClickListener { view: View -> openCameraAndAlbum(view) }
        findViewById<View>(R.id.button8).setOnClickListener { view: View? -> openVideoAlbum(view) }
        findViewById<View>(R.id.button9).setOnClickListener { view: View -> openFolderAlbum(view) }
        findViewById<View>(R.id.button5).setOnClickListener { view: View -> httpsTest(view) }
        findViewById<View>(R.id.button5).setOnClickListener { view: View -> httpsTest(view) }
    }


    /**
     * 打开相册 带摄像头
     *
     * @param view
     */
    private fun openCameraAndAlbum(view: View) {
        Album.from(com.easy.album.annotation.LocalMediaType.VALUE_TYPE_IMAGE)
            .setDisplayCamera(true)
            .forResult(call)
    }

    /**
     * 打开相册
     *
     * @param view
     */
    private fun openAlbum(view: View) {

        Album.from(com.easy.album.annotation.LocalMediaType.VALUE_TYPE_IMAGE)
            .forResult(call)

    }

    /**
     * 打开指定文件夹
     *
     * @param view
     */
    private fun openFolderAlbum(view: View) {
        Album.from(com.easy.album.annotation.LocalMediaType.VALUE_TYPE_IMAGE)
            .setChooseFolder("爱饰拍")
            .forFolderResult(call)
    }

    /**
     * 打开摄像头
     *
     * @param view
     */
    private fun openCamera(view: View) {
        Album.from(com.easy.album.annotation.LocalMediaType.VALUE_TYPE_IMAGE)
            .setStartUpCamera(true)
            .forResult(call)
    }

    /**
     * 通用的选择Dialog
     *
     * @param view
     */
    private fun openPhotoSelectDialog(view: View) {
        getSelectPhotoDialog(photoDialogClick = call).show(supportFragmentManager)
    }

    /**
     * 通用的选择Dialog 结果用Activity接收
     *
     * @param view
     */
    private fun openPhotoSelectDialog4Activity(view: View) {
        getSelectPhotoDialog(1, call).show(supportFragmentManager)
    }

    /**
     * 视频 入口
     *
     * @param view
     */
    fun openVideoAlbum(view: View?) {
        Album.from(com.easy.album.annotation.LocalMediaType.VALUE_TYPE_VIDEO)
            .forResult(call)
    }

    /**
     * 测试http预览
     *
     * @param view
     */
    private fun httpsTest(view: View) {
//        handleSSLHandshake()
        val gif =
            "https://images.shangwenwan.com/mall/6d392bfd-6273-4992-a24d-74f4b39b19d3?imageMogr2/size-limit/54.7k!/crop/!485x485a6a8"
        val list = ArrayList<String>()
        list.add(gif)
        AlbumPreviewActivity.openPreview(activity as BaseActivity, list)
    }

    /**
     * 预览图片
     *
     * @param view
     */
    private fun previewUrl(view: View) {
        val list = ArrayList<String>()
        list.add("http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg")
        list.add("http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg")
        list.add("http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg")
        list.add("http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg")
//        openPreviewActivity(activity, list, 0)

        AlbumPreviewActivity.openPreview(activity as BaseActivity, list)
    }

    companion object {
        /**
         * @param context
         * @param filePath relative path in Q, such as: "DCIM/" or "DCIM/dir_name/"
         * absolute path before Q
         * @return
         */
        private fun searchTxtFromPublic(context: Context, filePath: String, fileName: String): Cursor? {
            var filePath = filePath
            if (TextUtils.isEmpty(fileName)) {
                return null
            }
            if (!filePath.endsWith("/")) {
                filePath = "$filePath/"
            }
            val queryPathKey = MediaStore.Files.FileColumns.RELATIVE_PATH
            val selection = queryPathKey + "=? and " + MediaStore.Files.FileColumns.DISPLAY_NAME + "=?"
            return context.contentResolver.query(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL), arrayOf(MediaStore.Files.FileColumns._ID, queryPathKey, MediaStore.Files.FileColumns.DISPLAY_NAME), selection, arrayOf(filePath, fileName), null)
        }

        /**
         * https 图片加载
         */
//        fun handleSSLHandshake() {
//            try {
//                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//                    override fun getAcceptedIssuers(): Array<X509Certificate> {
//                        return arrayOfNulls(0)
//                    }
//
//                    override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
//                    override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
//                })
//                val sc = SSLContext.getInstance("TLS")
//                // trustAllCerts信任所有的证书
//                sc.init(null, trustAllCerts, SecureRandom())
//                HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
//                HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }
//            } catch (ignored: Exception) {
//            }
//        }
    }
}