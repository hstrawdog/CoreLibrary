package com.hqq.core.utils.file

import android.app.ActivityManager
import android.content.*
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.hqq.core.CoreConfig
import com.hqq.core.utils.image.BitmapUtils
import com.hqq.core.utils.log.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.math.BigDecimal
import java.nio.file.Path


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
    fun getCacheDir(context: Context = CoreConfig.applicationContext): String {
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
     *
     * @return String /data/user/0/com.hqq.core/code_cache
     */
    @JvmStatic
    fun getCodeCacheDir(): String {
        return getCodeCacheDir(CoreConfig.applicationContext)
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
        return CoreConfig.applicationContext.getDatabasePath(fileName)?.path + ""
    }

    /**
     *  获取包下的 指定目录文件夹
     * @param fileName String
     * @return File?
     */
    fun getPackageDir(fileName: String): String {
        return CoreConfig.applicationContext.getDir(fileName, Context.MODE_APPEND).path
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
     * @param fileName DIRECTORY_MUSIC
     * DIRECTORY_ALARMS
     * DIRECTORY_NOTIFICATIONS
     * Environment.DIRECTORY_PICTURES
     * DIRECTORY_MOVIES
     * DIRECTORY_DOWNLOADS
     * DIRECTORY_DCIM
     * DIRECTORY_DOCUMENTS
     * @return /storage/emulated/0/Android/data/package/files/fileName
     * app 卸载后 目录也会删除掉
     */
    @kotlin.jvm.JvmStatic
    fun getExternalFilesDir(context: Context = CoreConfig.applicationContext, fileName: String = ""): String {
        return context.getExternalFilesDir(fileName)!!.path
    }

    //endregion

    //region 公共存储
    //Android 10 以下可以直接访问  以上需要通过  MediaStore
    /**
     * /storage/emulated/0/Pictures
     */
    @kotlin.jvm.JvmStatic
    fun getExternalPicturesPath(): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path
    }

    /**
     *  下载地址地址
     * @return String
     */
    fun getExternalDownloadsPath(): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
    }

    /**
     *  获取根目录地址
     * @return String /storage/emulated/0/
     */
    fun getStorageDirectory(): String {
        return Environment.getExternalStorageDirectory().absolutePath
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
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return (result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB")
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
        val context = CoreConfig.applicationContext
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果在Android7.0以上,使用FileProvider获取Uri
            try {
                return FileProvider.getUriForFile(context, context.packageName + ".FileProvider", file)
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

    /**
     * 创建 pictures  Values
     * @param data String 原始地址 / 就版本地址  Q(10)以上没有这个字段
     * @param _display_name String 名称
     * @param mime_type String   类型
     * @param relative_path String  真实地址
     * @param date_added String  添加时间
     * @param date_modified String  修改时间
     * @param date_taken String token
     * @param size Int  大小
     * @param width Int 宽度
     * @param height Int 高度
     * @return ContentValues
     */
    private fun createBitmapValues(data: String, _display_name: String, mime_type: String, relative_path: String, date_added: String, date_modified: String, date_taken: String, size: Int, width: Int, height: Int): ContentValues {
        var values = ContentValues().apply {
            if (!isQ()) {
                put(MediaStore.Images.ImageColumns.DATA, data)
            }
            put(MediaStore.Images.ImageColumns.DISPLAY_NAME, _display_name)
            put(MediaStore.Images.ImageColumns.MIME_TYPE, mime_type)
            if (isQ()) {
                // 持久化的路径 也就是存储的路径  指定文件夹  只能存放在 image DCIM/Pictures
                put(MediaStore.Images.ImageColumns.RELATIVE_PATH, relative_path)
            }
            put(MediaStore.Images.ImageColumns.DATE_ADDED, date_added)
            put(MediaStore.Images.ImageColumns.DATE_MODIFIED, date_modified)
            put(MediaStore.Images.ImageColumns.DATE_TAKEN, date_taken)
            put(MediaStore.Images.ImageColumns.SIZE, size)
            put(MediaStore.Images.ImageColumns.WIDTH, width)
            put(MediaStore.Images.ImageColumns.HEIGHT, height)
        }
        return values
    }

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
            val resultCursor = resolver?.query(downloadUri, null, selection, selectionArgs, null)
            if (resultCursor != null) {
                val fileIdIndex =
                    resultCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
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
            values.put(MediaStore.Files.FileColumns.MIME_TYPE, oldPath.getMimeType())
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
     * android:requestLegacyExternalStorage="true"  才能保存至指定的目录中
     *  保存至 外部公有目录 否则都只能在pictures 目录下
     * @param context Context
     * @param bitmap Bitmap
     * @param filePath String
     */
    @JvmStatic
    fun saveBitmap2Public(context: Context = CoreConfig.applicationContext, bitmap: Bitmap, filePath: String) {
        if (!File(filePath).parentFile.exists()) {
            File(filePath).parentFile.mkdirs()
        }
        val file = File(filePath)
        val current = System.currentTimeMillis()

        val values =
            createBitmapValues(file.absolutePath, file.name, "image/jpg", file.parent, current.toString(), current.toString(), current.toString(), BitmapUtils.getBitmapSize(bitmap), bitmap.width, bitmap.height)

        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?.let { it ->
                context.contentResolver.openOutputStream(it)?.let { it1 ->
                    saveBitmap(it1, bitmap)
                }
            }


    }

    /**
     *
     * @param relativePath String
     * @param fileName String
     * @param bitmap Bitmap
     */
    fun saveBitmap2Pictures(relativePath: String, fileName: String, bitmap: Bitmap): Uri? {
        getContentResolverUri(relativePath, fileName)?.let { uri ->
            CoreConfig.applicationContext.contentResolver.openOutputStream(uri)
                ?.let { outputStream ->
                    saveBitmap(outputStream, bitmap)
                    // 更新图片大小
                    if (!isQ()) {
                        val imageValues = ContentValues()
                        imageValues.put(MediaStore.Images.Media.SIZE, bitmap.byteCount)
                        CoreConfig.applicationContext.contentResolver.update(uri, imageValues, null, null)
                        // 通知媒体库更新
                        val intent =
                            Intent(@Suppress("DEPRECATION") Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
                        CoreConfig.applicationContext.sendBroadcast(intent)
                    } else {
                        val imageValues = ContentValues()
                        // Android Q添加了IS_PENDING状态，为0时其他应用才可见
                        imageValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                        CoreConfig.applicationContext.contentResolver.update(uri, imageValues, null, null)
                    }
                    return uri
                }
        }
        return null
    }

    /**
     *
     * @param relativePath String   子目录地址
     * @param fileName String  文件名称
     * @return Uri?
     */
    fun getContentResolverUri(relativePath: String, fileName: String): Uri? {
        val ALBUM_DIR = Environment.DIRECTORY_PICTURES // 系统相册文件夹
        // 图片信息
        val imageValues = ContentValues().apply {
            val mimeType = fileName.getMimeType()
            if (mimeType != null) {
                put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            }
            val date = System.currentTimeMillis() / 1000
            put(MediaStore.Images.Media.DATE_ADDED, date)
            put(MediaStore.Images.Media.DATE_MODIFIED, date)
        }

        // 保存的位置
        val collection: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val path = if (relativePath != null) "${ALBUM_DIR}/${relativePath}" else ALBUM_DIR
            imageValues.apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.RELATIVE_PATH, path)
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
            collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            // 高版本不用查重直接插入，会自动重命名
            var uri = CoreConfig.applicationContext.contentResolver.insert(collection, imageValues)
            return uri
        } else {
            // 老版本
            val pictures =
                @Suppress("DEPRECATION") Environment.getExternalStoragePublicDirectory(ALBUM_DIR)
            val saveDir = if (relativePath != null) File(pictures, relativePath) else pictures
            if (!saveDir.exists() && !saveDir.mkdirs()) {
                return null
            }
            // 文件路径查重，重复的话在文件名后拼接数字
            var imageFile = File(saveDir, fileName)
            val fileNameWithoutExtension = imageFile.nameWithoutExtension
            val fileExtension = imageFile.extension
            var queryUri = queryMediaImage28(imageFile.absolutePath)
            var suffix = 1
            while (queryUri != null) {
                val newName = fileNameWithoutExtension + "(${suffix++})." + fileExtension
                imageFile = File(saveDir, newName)
                queryUri = queryMediaImage28(imageFile.absolutePath)
            }

            imageValues.apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.name)
                // 保存路径
                val imagePath = imageFile.absolutePath
                put(@Suppress("DEPRECATION") MediaStore.Images.Media.DATA, imagePath)
            }
            collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            // 插入图片信息
            var uri = CoreConfig.applicationContext.contentResolver.insert(collection, imageValues)
            return uri
        }

    }

    /**
     * Android Q以下版本，查询媒体库中当前路径是否存在
     * @return Uri 返回null时说明不存在，可以进行图片插入逻辑
     */
    private fun queryMediaImage28(imagePath: String): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) return null

        val imageFile = File(imagePath)
        if (imageFile.canRead() && imageFile.exists()) {
            // 文件已存在，返回一个file://xxx的uri
            return Uri.fromFile(imageFile)
        }
        // 保存的位置
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        // 查询是否已经存在相同图片
        val query =
            CoreConfig.applicationContext.contentResolver.query(collection, arrayOf(MediaStore.Images.Media._ID, @Suppress("DEPRECATION") MediaStore.Images.Media.DATA), "${@Suppress("DEPRECATION") MediaStore.Images.Media.DATA} == ?", arrayOf(imagePath), null)
        query?.use {
            while (it.moveToNext()) {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val id = it.getLong(idColumn)
                val existsUri = ContentUris.withAppendedId(collection, id)
                return existsUri
            }
        }
        return null
    }

    /**
     *  复制图片到指定目录上
     * @param context Context
     * @param oldPath String  旧的地址
     * @param newPath String
     * @return Uri?
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun copyFile2CustomPath(context: Context, oldPath: String, newPath: String): Uri? {

        val oldFile = File(oldPath)
        //设置目标文件的信息
        val options = BitmapUtils.getImageOptions(oldPath)

        val values =
            createBitmapValues(newPath, oldFile.name, "image/png", File(newPath).parentFile.path, oldFile.name, oldFile.name, oldFile.name, oldFile.length()
                .toInt(), options.outHeight, options.outHeight)
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        context.contentResolver.insert(collection, values)?.let { insertUri ->
            context.contentResolver.openOutputStream(insertUri)?.let { fos ->
                val fis = FileInputStream(oldFile)
                fis.copyTo(fos)
                fis.close()
                fos.close()
                return insertUri
            }
        }
        return null
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
        val os =
            getDownloadInstallUri(fileName, context)?.let { context.contentResolver.openOutputStream(it) }
        os?.let { saveBitmap(it, bitmap) }
    }
    //endregion

    //region Bitmap 操作
    /**
     *  Android  10 以下的 图片存储
     * @param bm Bitmap
     * @param filePath String
     * @throws IOException
     */
    @Throws(IOException::class)
    fun saveBitmap(bm: Bitmap?, filePath: String = getExternalPicturesPath() + File.separator + getDefFileName(".png")) {
        if (bm == null) {
            LogUtils.d(" saveBitmap   is  null  ")
            return
        }
        val myCaptureFile = File(filePath).parentFile
        if (!myCaptureFile.exists()) {
            myCaptureFile.mkdirs()
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

    //TODO  整理 文件复制  与其他操作

    //region   读写 String
    /**
     *   创建文件
     * @param s String
     */
    @JvmStatic
    fun createNewFile(fileName: String): File {
        val path = getCacheDir(CoreConfig.applicationContext) + "/" + fileName
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

    /**
     * 公有目录文件复制到私有目录  需要测试
     * @param fileUri 公有目录文件的uri
     * @param privatePath 私有目录的路径
     */
    @JvmStatic
    fun copyToPrivateDir(context: Context, fileUri: Uri, privatePath: String) {
        try {
            val fis =
                FileInputStream(context.contentResolver.openFileDescriptor(fileUri, "r")?.fileDescriptor)
            fis.copyTo(FileOutputStream(privatePath))
            fis.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *  10 以上
     * @return Boolean
     */
    fun isQ(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    /**
     * 获取文件图片 对应的媒体类型
     * @param path String?
     * @return String
     */
    private fun String.getMimeType(): String? {
        val fileName = this.toLowerCase()
        return when {
            fileName.endsWith(".png") -> "image/png"
            fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") -> "image/jpeg"
            fileName.endsWith(".webp") -> "image/webp"
            fileName.endsWith(".gif") -> "image/gif"
            else -> null
        }
    }

    /**
     *  保存视频到相册
     * @param context Context
     * @param destFile File  全地址
     */
    fun videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(context: Context, destFile: File) {
        val values = ContentValues()
        val uriSavedVideo: Uri?
        uriSavedVideo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies")
            values.put(MediaStore.Video.Media.TITLE, destFile.name)
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.name)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            val collection =
                MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            context.contentResolver.insert(collection, values)
        } else {
            values.put(MediaStore.Video.Media.TITLE, destFile.name)
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.name)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Video.Media.DATA, destFile.absolutePath)
            context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Video.Media.IS_PENDING, 1)
        }
        val pfd: ParcelFileDescriptor?
        try {
            pfd = context.contentResolver.openFileDescriptor(uriSavedVideo!!, "w")
            val out = FileOutputStream(pfd!!.fileDescriptor)
            val inputStream = FileInputStream(destFile)
            val buf = ByteArray(8192)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
            out.close()
            inputStream.close()
            pfd.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear()
            values.put(MediaStore.Video.Media.IS_PENDING, 0)
            context.contentResolver.update(uriSavedVideo!!, values, null, null)
        }
    }

}