package com.hqq.core.utils.file

import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.hqq.core.CoreConfig
import com.hqq.core.utils.log.LogUtils
import java.io.*
import java.math.BigDecimal


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @Date : 上午 9:33
 * @Email : qiqiang213@gmail.com
 * @Describe : A
 *
 */
object FileUtils {
    //region 文件名

    /**
     * 随机名称
     *
     * @return
     */
    @JvmStatic
    val defFileName: String
        get() = System.currentTimeMillis().toString() + ""

    /**
     * 带后缀的文件名称
     *
     * @param suffix
     * @return
     */
    @JvmStatic
    fun getDefFileName(suffix: String): String {
        return defFileName + suffix
    }
    //endregion

    //region  Path 相关
    /**
     * Android 10 区分存储
     * 1. 内部存储package下cache  database  等
     * 2. 外部公共存储 Download  登录
     * 3. 外部私有存储 Android/data/package
     */
    //region  内部私有存储
    /**
     * @param context
     * @return /data/user/0/com.hqq.core/cache
     */
    @JvmStatic
    fun getCacheDir(context: Context): String {
        return context.cacheDir.path
    }

    /**
     *
     * @param context Context
     * @return String /data/user/0/com.hqq.core/cache
     */
    @JvmStatic
    fun getCodeCacheDir(context: Context): String {
        return context.codeCacheDir.path
    }

    /**
     * @param context
     * @return /data/user/0/com.hqq.core/files
     */
    @JvmStatic
    fun getFilesDir(context: Context): String {
        return context.filesDir.path
    }

    /**
     * 同上
     *
     * @param context
     * @param fileName
     * @return /data/data/package/files/fileName
     */
    @JvmStatic
    fun getFileStreamPath(context: Context, fileName: String = ""): String {
        return context.getFileStreamPath(fileName).path
    }

    /**
     *
     * 内部存储下的私有目录
     * @return String
     */
    @JvmStatic
    fun getDataBaseDir(fileName: String): String {
        return CoreConfig.getApplicationContext().getDatabasePath(fileName)?.path + ""
    }
    //endregion

    //region 外部存储
    /**
     * /storage/emulated/0/Android/data/package/cache
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getExternalCacheDir(context: Context): String {
        return context.externalCacheDir!!.path
    }

    /**
     * @param context
     * @return /storage/emulated/0/Android/data/package/files
     */
    @JvmStatic
    fun getExternalFilesDir(context: Context): String {
        return context.getExternalFilesDir("")!!.path
    }

    /**
     * @param context
     * @param fileName DIRECTORY_MUSIC
     * DIRECTORY_ALARMS
     * DIRECTORY_NOTIFICATIONS
     * Environment.DIRECTORY_PICTURES
     * DIRECTORY_MOVIES
     * DIRECTORY_DOWNLOADS
     * DIRECTORY_DCIM
     * DIRECTORY_DOCUMENTS
     * @return /storage/emulated/0/Android/data/package/files/fileName
     */
    @kotlin.jvm.JvmStatic
    fun getExternalFilesDir(context: Context, fileName: String?): String {
        return context.getExternalFilesDir(fileName)!!.path
    }


    //endregion

    //region 公共存储
    //Android 10 以下可以直接访问  以上需要通过  MediaStore
    /**
     * /storage/emulated/0/Pictures
     */
    @kotlin.jvm.JvmStatic
    open fun getExternalPicturesPath(): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path
    }

    /**
     *  下载地址地址
     * @return String
     */
    open fun getExternalDownloadsPath(): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
    }

    //endregion

    //endregion

    //region Cache 相关

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    @kotlin.jvm.JvmStatic
    fun getTotalCacheSize(context: Context): String {
        var cacheSize: Long = 0
        try {
            cacheSize = getFolderSize(context.cacheDir)
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                cacheSize += getFolderSize(context.externalCacheDir)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return getFormatSize(cacheSize.toDouble())
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    @JvmStatic
    fun clearAllCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.externalCacheDir)
        }
    }

    /**
     * 删除文件夹
     *
     * @param dir
     * @return
     */
    @JvmStatic
    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            if (children != null) {
                for (i in children.indices) {
                    val success = deleteDir(File(dir, children[i]))
                    if (!success) {
                        return false
                    }
                }
            }
        }
        return dir!!.delete()
    }

    /**
     * 获取文件夹大小
     * @param file
     * @return
     */
    @JvmStatic
    fun getFolderSize(file: File?): Long {
        var size: Long = 0
        try {
            val fileList = file!!.listFiles()
            for (i in fileList.indices) {
                // 如果下面还有文件
                size = if (fileList[i].isDirectory) {
                    size + getFolderSize(fileList[i])
                } else {
                    size + fileList[i].length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    @JvmStatic
    fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return (result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB")
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    @kotlin.jvm.JvmStatic
    fun getCacheSize(context: Context): String {
        try {
            return getFormatSize(getFolderSize(File(context.cacheDir.toString() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)).toDouble())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    @JvmStatic
    fun getFileDatabaseSize(fileName: String): Long {
        return File(getDataBaseDir(fileName)).length()
    }

    /**
     * 获取系统分配内存大小
     *
     * @param context
     * @return
     */
    @kotlin.jvm.JvmStatic
    fun getAppCacheSize(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.memoryClass
    }


    //endregion

    //region File 2 Uri
    /**
     * file 2 Uri
     * @param filePath
     * @return
     */
    @JvmStatic
    fun getFile2Uri(filePath: String?): Uri? {
        return getFile2Uri(File(filePath))
    }

    /**
     * file 2 Uri
     * @param file File
     * @return Uri?
     */
    @JvmStatic
    fun getFile2Uri(file: File): Uri? {
        val context = CoreConfig.getApplicationContext()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果在Android7.0以上,使用FileProvider获取Uri
            try {
                return FileProvider.getUriForFile(context, context.packageName + ".provider", file)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            return Uri.fromFile(file)
        }
        return null
    }
    //endregion

    //region Android Q   10以上文件操作
    /**
     *  保存数据到 download 中
     * @param context Context
     * @param fileName String
     * @param data String
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun saveFile2Download(context: Context, fileName: String, data: String) {
        //使用ContentResolver创建需要操作的文件
        val outputStream =
                getDownloadInstallUri(fileName, context)?.let { context.contentResolver.openOutputStream(it) }
        outputStream?.write(data.toByteArray())
        outputStream?.close()
    }

    /**
     *   获取Download  uri
     * @param fileName String
     * @param context Context
     * @return Uri?
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun getDownloadInstallUri(fileName: String, context: Context): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
        //MediaStore对应类型名
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, "*/*")
        values.put(MediaStore.Files.FileColumns.TITLE, fileName)
        //公共目录下目录名
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Download")
        //内部存储的Download路径
        val external = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        // 插入Uri
        val insertUri = context.contentResolver.insert(external, values)
        insertUri?.let { context.contentResolver.openOutputStream(it) }
        return insertUri
    }

    /**
     * 删除公有目录的文件。(自己应用创建的文件才有权限删除)
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun deleteFile(context: Context, fileUri: Uri) {
        try {
            context.contentResolver?.delete(fileUri, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取公有Download目录下的文件
     * @param dirName Download目录下的下一级文件夹的名字
     * @return 文件的uri集合
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun listFiles(context: Context, dirName: String?): List<Uri> {
        val resultList = ArrayList<Uri>()
        try {
            val resolver = context.contentResolver
            val downloadUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            var selection: String? = null
            var selectionArgs: Array<String>? = null
            if (dirName != null && dirName.isNotEmpty()) {
                selection = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME + "=?"
                selectionArgs = arrayOf(dirName)
            }
            val resultCursor = resolver?.query(downloadUri, null,
                    selection, selectionArgs, null)
            if (resultCursor != null) {
                val fileIdIndex = resultCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                while (resultCursor.moveToNext()) {
                    val fileId = resultCursor.getLong(fileIdIndex)
                    val pathUri = downloadUri.buildUpon().appendPath("$fileId").build()
                    resultList.add(pathUri)
                }
                resultCursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resultList
    }

    /**
     * 复制私有目录的文件到公有Download目录
     * @param context 上下文
     * @param oldPath 私有目录的文件路径
     * @param targetDirName 公有目录下的目标文件夹名字。比如传test，则会复制到Download/test目录下。另外如果Download目录下test文件夹不存在，会自动创建。
     * @return 公有目录的uri，为空则代表复制失败
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun copyFileToDownloadDir(context: Context, oldPath: String, targetDirName: String): Uri? {
        try {
            val oldFile = File(oldPath)
            //设置目标文件的信息
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DESCRIPTION, "This is a file.")
            values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, oldFile.name)
            values.put(MediaStore.Files.FileColumns.TITLE, oldFile.name)
            values.put(MediaStore.Files.FileColumns.MIME_TYPE, getMimeType(oldPath))
            val relativePath = Environment.DIRECTORY_DOWNLOADS + File.separator + targetDirName
            values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath)
            val downloadUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            val resolver = context.contentResolver
            val insertUri = resolver.insert(downloadUri, values)
            if (insertUri != null) {
                val fos = resolver.openOutputStream(insertUri)
                if (fos != null) {
                    val fis = FileInputStream(oldFile)
                    fis.copyTo(fos)
                    fis.close()
                    fos.close()
                    return insertUri
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 保存图片到 相册与 Pictures
     * @param context Context
     * @param bitmap Bitmap
     * @param folderName String  文件夹名称
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun saveBitmap2Picture(context: Context = CoreConfig.getApplicationContext(),
                           bitmap: Bitmap, folderName: String = "", fileName: String) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            // 指定文件夹  image 默认是存放  DCIM 与Pictures中
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + folderName)
        }
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.let { it ->
            context.contentResolver.openOutputStream(it)?.let { it1 ->
                saveBitmap(it1, bitmap)
            }
        }

    }

    /**
     *  保存图片到Download
     * @param context Context
     * @param fileName String
     * @param bitmap Bitmap
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun saveBitmap2Download(context: Context, fileName: String, bitmap: Bitmap) {
        //使用ContentResolver创建需要操作的文件
        val os = getDownloadInstallUri(fileName, context)?.let { context.contentResolver.openOutputStream(it) }
        os?.let { saveBitmap(it, bitmap) }
    }
    //endregion

    //region String  读写
    /**
     *   创建文件
     * @param s String
     */
    @JvmStatic
    fun createNewFile(fileName: String): File {
        val path = getCacheDir(CoreConfig.getApplicationContext()) + "/" + fileName
        val file = File(path)
        file.createNewFile()
        return file
    }

    /**
     * 覆盖
     * 写如字符串 到指定文件中
     * @param file File
     * @param str String
     */
    @JvmStatic
    fun writerFile(file: File, str: String) {
        if (!file.exists()) {
            file.createNewFile()
        }
        val bufferedWriter = BufferedWriter(FileWriter(file));
        bufferedWriter.write(str)
        bufferedWriter.flush()
        bufferedWriter.close()
    }

    /**
     *  读取文件数据
     * @param filePathName String
     * @return String
     */
    @JvmStatic
    fun readFile(filePathName: String): String {
        val stringBuffer = StringBuffer()
        // 打开文件输入流
        val fileInputStream = FileInputStream(filePathName)
        val buffer = ByteArray(1024)
        var len: Int = fileInputStream.read(buffer)
        // 读取文件内容
        while (len > 0) {
            stringBuffer.append(String(buffer, 0, len))
            // 继续将数据放到buffer中
            len = fileInputStream.read(buffer)
        }
        // 关闭输入流
        fileInputStream.close()
        return stringBuffer.toString()
    }
    //endregion

    //region Bitmap 操作
    /**
     *  Android  10 一下的 图片存储
     * @param bm Bitmap
     * @param filePath String
     * @throws IOException
     */
    @Throws(IOException::class)
    fun saveBitmap(bm: Bitmap?, filePath: String = getExternalPicturesPath() + getDefFileName(".png")) {
        if (bm == null) {
            LogUtils.d(" saveBitmap   is  null  ")
            return
        }
        val myCaptureFile = File(filePath)
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile()
        }
        val bos = BufferedOutputStream(FileOutputStream(filePath).apply {
            saveBitmap(this, bm)
        })
        bos.flush()
        bos.close()
    }

    /**
     * 保存图片
     * @param os OutputStream
     * @param bitmap Bitmap
     */
    @JvmStatic
    fun saveBitmap(os: OutputStream, bitmap: Bitmap) {
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            os.close()
        }
    }
    //endregion

    /**
     * 获取文件类型
     * @param path String?
     * @return String
     */
    @JvmStatic
    fun getMimeType(path: String?): String {
        var mime = "*/*"
        path ?: return mime
        val mmr = MediaMetadataRetriever()
        try {
            mmr.setDataSource(path)
            mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE) ?: mime
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mmr.release()
        }
        return mime
    }

    /**
     * 公有目录文件复制到私有目录  需要测试
     * @param fileUri 公有目录文件的uri
     * @param privatePath 私有目录的路径
     */
    @JvmStatic
    fun copyToPrivateDir(context: Context, fileUri: Uri, privatePath: String) {
        try {
            val fis = FileInputStream(context.contentResolver.openFileDescriptor(fileUri, "r")?.fileDescriptor)
            fis.copyTo(FileOutputStream(privatePath))
            fis.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}