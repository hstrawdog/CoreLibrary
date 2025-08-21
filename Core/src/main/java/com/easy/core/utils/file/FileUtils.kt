package com.easy.core.utils.file

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.content.*
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.os.StatFs
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.easy.core.CoreConfig
import com.easy.core.utils.data.DataUtils
import com.easy.core.utils.file.PhotoUtils
import com.easy.core.utils.encrypt.EncryptTool
import com.easy.core.utils.image.BitmapUtils
import com.easy.core.utils.log.LogUtils
import com.easy.core.utils.log.LogUtils.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.math.BigDecimal
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util.Arrays
import java.util.Collections
import java.util.Vector


/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils
 * @Date : 上午 9:33
 * @Email : qiqiang213@gmail.com
 * @Describe :
 *      拆分文件操作   图片  与其他文件如 视频  pdf 等
 */
object FileUtils {
    /**
     * KB与Byte的倍数
     */
    const val KB = 1024
    const val BUFSIZE = 1024 * 8

    const val TAG = "RxFileTool"// 取得sdcard文件路径

    //region 文件名
    /**
     *
     * @return 以时间毫秒为名称的名称 但未添加后缀
     */
    @JvmStatic
    val defFileName:String
        get() = System.currentTimeMillis().toString()

    /**
     * 带后缀的文件名称
     *
     * @param suffix  ".png" 之类的  默认使用  png
     * @return
     */
    @JvmStatic
    fun getDefFileName(suffix:String = ".png"):String {
        return defFileName + suffix
    }

    @JvmStatic
    fun getDefFileName(fileName:String=defFileName ,  compressFormat:CompressFormat = CompressFormat.PNG):String {
        return "$fileName.${compressFormat.name.lowercase()}"
    }

    /**
     * 获取文件图片 对应的媒体类型
     * @param path String?
     * @return String
     */
    @JvmStatic
    private fun String.getMimeType():String? {
        val fileName = this.toLowerCase()
        return when {
            fileName.endsWith(".png") -> "image/png"
            fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") -> "image/jpeg"
            fileName.endsWith(".webp") -> "image/webp"
            fileName.endsWith(".gif") -> "image/gif"
            else -> null
        }
    }
    //endregion

    //region Cache 相关

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    @kotlin.jvm.JvmStatic
    fun getTotalCacheSize(context:Context):String {
        var cacheSize:Long = 0
        try {
            cacheSize = getFolderSize(context.cacheDir)
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                cacheSize += getFolderSize(context.externalCacheDir)
            }
        } catch (e:Exception) {
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
    fun clearAllCache(context:Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            context.externalCacheDir?.let { deleteDir(it) }
        }
    }

    /**
     * 获取文件夹大小
     * @param file
     * @return
     */
    @JvmStatic
    fun getFolderSize(file:File?):Long {
        var size:Long = 0
        try {
            val fileList = file!!.listFiles()
            if (fileList != null) {
                for (i in fileList.indices) {
                    // 如果下面还有文件
                    size = if (fileList[i].isDirectory) {
                        size + getFolderSize(fileList[i])
                    } else {
                        size + fileList[i].length()
                    }
                }
            }
        } catch (e:Exception) {
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
    fun getFormatSize(size:Double):String {
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
    fun getCacheSize(context:Context):String {
        try {
            return getFormatSize(getFolderSize(File(context.cacheDir.toString() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)).toDouble())
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return ""
    }

    @JvmStatic
    fun getFileDatabaseSize(fileName:String):Long {
        return File(FilePathTools.getDataBaseDir(fileName)).length()
    }

    /**
     * 获取系统分配内存大小
     *
     * @param context
     * @return
     */
    @kotlin.jvm.JvmStatic
    fun getAppCacheSize(context:Context):Int {
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
    fun getFile2Uri(filePath:String?):Uri? {
        return getFile2Uri(File(filePath))
    }

    /**
     * file 2 Uri
     * @param file File
     * @return Uri?
     */
    @JvmStatic
    fun getFile2Uri(file:File):Uri? {
        val context = CoreConfig.applicationContext
        //如果在Android7.0以上,使用FileProvider获取Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                return FileProvider.getUriForFile(context, context.packageName + ".FileProvider", file)
            } catch (e:java.lang.Exception) {
                e.printStackTrace()
            }
        } else {
            //否则使用Uri.fromFile(file)方法获取Uri
            return Uri.fromFile(file)
        }
        return null
    }
    //endregion

    //region Android Q   10以上文件操作
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
    @JvmStatic
    private fun createBitmapValues(data:String,
                                   _display_name:String,
                                   mime_type:String,
                                   relative_path:String,
                                   date_added:String,
                                   date_modified:String,
                                   date_taken:String,
                                   size:Int,
                                   width:Int,
                                   height:Int):ContentValues {
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
    fun saveFile2Download(context:Context, fileName:String, data:String) {
        //使用ContentResolver创建需要操作的文件
        val outputStream = getDownloadInstallUri(fileName, context)?.let { context.contentResolver.openOutputStream(it) }
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
    fun getDownloadInstallUri(fileName:String, context:Context):Uri? {
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
    fun deleteFile(context:Context, fileUri:Uri):Int {
        var  count =0
        try {
            count=context.contentResolver?.delete(fileUri, null, null)?:0
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return count
    }

    /**
     * 根据 删除图片
     * @param contentResolver ContentResolver
     * @param uri Uri?
     * @return Boolean
     */
    fun deleteImageByUri(contentResolver:ContentResolver, uri:Uri?):Boolean {
        try {
            // 检查 URI 是否是有效的
            if (uri != null && "content" == uri.scheme) {
                // 从 URI 中解析 ID
                val id = ContentUris.parseId(uri)
                // 构建图片的 URI
                val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                // 执行删除操作
                val rowsDeleted = contentResolver.delete(imageUri, null, null)
                return rowsDeleted > 0
            }
        } catch (e:java.lang.Exception) {
            e("ImageUtils Error deleting image", e)
        }
        return false
    }

    /**
     * 获取公有Download目录下的文件
     * @param dirName Download目录下的下一级文件夹的名字
     * @return 文件的uri集合
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    @JvmStatic
    fun listFiles(context:Context, dirName:String?):List<Uri> {
        val resultList = ArrayList<Uri>()
        try {
            val resolver = context.contentResolver
            val downloadUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            var selection:String? = null
            var selectionArgs:Array<String>? = null
            if (dirName != null && dirName.isNotEmpty()) {
                selection = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME + "=?"
                selectionArgs = arrayOf(dirName)
            }
            val resultCursor = resolver?.query(downloadUri, null, selection, selectionArgs, null)
            if (resultCursor != null) {
                val fileIdIndex = resultCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                while (resultCursor.moveToNext()) {
                    val fileId = resultCursor.getLong(fileIdIndex)
                    val pathUri = downloadUri.buildUpon().appendPath("$fileId").build()
                    resultList.add(pathUri)
                }
                resultCursor.close()
            }
        } catch (e:Exception) {
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
    fun copyFileToDownloadDir(context:Context, oldPath:String, targetDirName:String?, description:String?):Uri? {
        try {
            val oldFile = File(oldPath)
            //设置目标文件的信息
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DESCRIPTION, description)
            values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, oldFile.name)
            values.put(MediaStore.Files.FileColumns.TITLE, oldFile.name)
            values.put(MediaStore.Files.FileColumns.MIME_TYPE, oldPath.getMimeType())
            val relativePath = if (targetDirName.isNullOrEmpty()) {
                Environment.DIRECTORY_DOWNLOADS
            } else {
                Environment.DIRECTORY_DOWNLOADS + File.separator + targetDirName
            }
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
        } catch (e:Exception) {
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
    fun saveBitmap2Public(context:Context = CoreConfig.applicationContext, bitmap:Bitmap, filePath:String) {
        if (!File(filePath).parentFile.exists()) {
            File(filePath).parentFile?.mkdirs()
        }
        val file = File(filePath)
        val current = System.currentTimeMillis()

        val values = createBitmapValues(file.absolutePath,
            file.name,
            "image/jpg",
            file.parent,
            current.toString(),
            current.toString(),
            current.toString(),
            BitmapUtils.getBitmapSize(bitmap),
            bitmap.width,
            bitmap.height)

        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.let { it ->
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
    @JvmStatic
    fun saveBitmap2Pictures(relativePath:String, fileName:String, bitmap:Bitmap):Uri? {
        getContentResolverUri(relativePath, fileName)?.let { uri ->
            CoreConfig.applicationContext.contentResolver.openOutputStream(uri)?.let { outputStream ->
                saveBitmap(outputStream, bitmap)
                // 更新图片大小
                if (!isQ()) {
                    val imageValues = ContentValues()
                    imageValues.put(MediaStore.Images.Media.SIZE, bitmap.byteCount)
                    CoreConfig.applicationContext.contentResolver.update(uri, imageValues, null, null)
                    // 通知媒体库更新
                    val intent = Intent(@Suppress("DEPRECATION") Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
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
    @JvmStatic
    fun getContentResolverUri(relativePath:String, fileName:String):Uri? {
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
        val collection:Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val path = "${ALBUM_DIR}/${relativePath}"
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
            val pictures = @Suppress("DEPRECATION") Environment.getExternalStoragePublicDirectory(ALBUM_DIR)
            val saveDir = File(pictures, relativePath)
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
    @JvmStatic
    private fun queryMediaImage28(imagePath:String):Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) return null

        val imageFile = File(imagePath)
        if (imageFile.canRead() && imageFile.exists()) {
            // 文件已存在，返回一个file://xxx的uri
            return Uri.fromFile(imageFile)
        }
        // 保存的位置
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        // 查询是否已经存在相同图片
        val query = CoreConfig.applicationContext.contentResolver.query(collection,
            arrayOf(MediaStore.Images.Media._ID, @Suppress("DEPRECATION") MediaStore.Images.Media.DATA),
            "${@Suppress("DEPRECATION") MediaStore.Images.Media.DATA} == ?",
            arrayOf(imagePath),
            null)
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
    fun copyBitmap2CustomPath(context:Context, oldPath:String, newPath:String):Uri? {

        val oldFile = File(oldPath)
        //设置目标文件的信息
        val options = BitmapUtils.getImageOptions(oldPath)

        val values = createBitmapValues(newPath,
            oldFile.name,
            "image/png",
            File(newPath).parentFile.path,
            oldFile.name,
            oldFile.name,
            oldFile.name,
            oldFile.length().toInt(),
            options.outHeight,
            options.outHeight)
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
    fun saveBitmap2Download(context:Context, fileName:String, bitmap:Bitmap) {
        //使用ContentResolver创建需要操作的文件
        val os = getDownloadInstallUri(fileName, context)?.let { context.contentResolver.openOutputStream(it) }
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
    fun saveBitmap(bm:Bitmap?, filePath:String = FilePathTools.getExternalPicturesPath() + File.separator + getDefFileName(".png")) {
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
    fun saveBitmap(os:OutputStream, bitmap:Bitmap) {
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        } catch (e:IOException) {
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
    fun createNewFile(fileName:String):File {
        val path = FilePathTools.getCacheDir(CoreConfig.applicationContext) + "/" + fileName
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
    fun writerFile(file:File, str:String) {
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
    fun readFile(filePathName:String):String {
        val stringBuffer = StringBuffer()
        // 打开文件输入流
        val fileInputStream = FileInputStream(filePathName)
        val buffer = ByteArray(1024)
        var len:Int = fileInputStream.read(buffer)
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
    fun copyToPrivateDir(context:Context, fileUri:Uri, privatePath:String) {
        try {
            val fis = FileInputStream(context.contentResolver.openFileDescriptor(fileUri, "r")?.fileDescriptor)
            fis.copyTo(FileOutputStream(privatePath))
            fis.close()
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 查询 下载目录中的文件
     * 只能查询 APP安装后存储后的文件
     * @param context Context
     * @param dirName String?  子文件夹
     * @param fileName String 文件名称
     * @return List<Uri>
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun findDownloadFile(context:Context, dirName:String?, fileName:String):List<Uri> {
        val resultList = ArrayList<Uri>()
        try {
            val resolver = context.contentResolver
            val downloadUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            var selection:String? = null
            var selectionArgs:Array<String>? = null
            if (dirName != null && dirName.isNotEmpty()) {
                selection = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME + " = ? AND " + MediaStore.Downloads.DISPLAY_NAME + " = ? "
                selectionArgs = arrayOf(dirName, fileName)
            }
            val resultCursor = resolver?.query(downloadUri, null, selection, selectionArgs, null)
            if (resultCursor != null) {
                val fileIdIndex = resultCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                while (resultCursor.moveToNext()) {
                    val fileId = resultCursor.getLong(fileIdIndex)
                    val pathUri = downloadUri.buildUpon().appendPath("$fileId").build()
                    resultList.add(pathUri)
                }
                resultCursor.close()
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return resultList
    }

    /**
     *  通过描述 字段 查询 下载目录中的文件
     * @param context Context
     * @param dirName String?  子目录
     * @param description String  描述字段
     * @return ArrayList<Uri>
     */
    fun findDownloadsUri4Description(context:Context, dirName:String?, description:String):ArrayList<Uri> {
        val resultList = ArrayList<Uri>()
        try {
            val resolver = context.contentResolver
            val downloadUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            var selection:String? = null

            var selectionArgs = mutableListOf<String>()
            if (dirName != null && dirName.isNotEmpty()) {
                selection = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME + " = ? AND "
//                selectionArgs = arrayOf(dirName, fileName)
                selectionArgs.add(dirName)
            }
            selection = selection + MediaStore.Images.Media.DESCRIPTION + " = ? "
            selectionArgs.add(description)

            val resultCursor = resolver?.query(downloadUri, null, selection, selectionArgs.toTypedArray(), null)
            if (resultCursor != null) {
                val fileIdIndex = resultCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                while (resultCursor.moveToNext()) {
                    val fileId = resultCursor.getLong(fileIdIndex)
                    val pathUri = downloadUri.buildUpon().appendPath("$fileId").build()
                    resultList.add(pathUri)
                }
                resultCursor.close()
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return resultList
    }

    /**
     *  10 以上
     * @return Boolean
     */
    @JvmStatic
    fun isQ():Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    /**
     *  保存视频到相册
     * @param context Context
     * @param destFile File  全地址
     */
    @JvmStatic
    fun videoSaveToNotifyGalleryToRefreshWhenVersionGreaterQ(context:Context, destFile:File) {
        val values = ContentValues()
        val uriSavedVideo:Uri?
        uriSavedVideo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies")
            values.put(MediaStore.Video.Media.TITLE, destFile.name)
            values.put(MediaStore.Video.Media.DISPLAY_NAME, destFile.name)
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            val collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
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
        val pfd:ParcelFileDescriptor?
        try {
            pfd = context.contentResolver.openFileDescriptor(uriSavedVideo!!, "w")
            val out = FileOutputStream(pfd!!.fileDescriptor)
            val inputStream = FileInputStream(destFile)
            val buf = ByteArray(8192)
            var len:Int
            while (inputStream.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
            out.close()
            inputStream.close()
            pfd.close()
        } catch (e:Exception) {
            e.printStackTrace()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear()
            values.put(MediaStore.Video.Media.IS_PENDING, 0)
            context.contentResolver.update(uriSavedVideo!!, values, null, null)
        }
    }

//    ----------------------------------------------------------

    /**
     * 传入文件名以及字符串, 将字符串信息保存到文件中
     *
     * @param strFilePath
     * @param strBuffer
     */
    @JvmStatic
    fun Text2File(strFilePath:String, strBuffer:String) {
        var fileWriter:FileWriter? = null
        try {
            // 创建文件对象
            val fileText = File(strFilePath)
            // 向文件写入对象写入信息
            fileWriter = FileWriter(fileText)
            // 写文件
            fileWriter.write(strBuffer)
            // 关闭
        } catch (e:IOException) {
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (ex:IOException) {
                ex.printStackTrace()
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    @JvmStatic
    fun readFileByLines(fileName:String) {
        val file = File(fileName)
        var reader:BufferedReader? = null
        try {
            println("以行为单位读取文件内容，一次读一整行：")
            reader = BufferedReader(FileReader(file))
            var tempString:String? = null
            var line = 1
            // 一次读入一行，直到读入null为文件结束
            while (reader.readLine().also { tempString = it } != null) {
                // 显示行号
                println("line?????????????????????????????????? $line: $tempString")
                val content = tempString
                line++
            }
            reader.close()
        } catch (e:IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e1:IOException) {
                }
            }
        }
    }

    /**
     * 得到SD卡根目录.
     */
    @JvmStatic
    val rootPath:File?
        get() {
            var path:File? = null
            path = if (sdCardIsAvailable()) {
                Environment.getExternalStorageDirectory() // 取得sdcard文件路径
            } else {
                Environment.getDataDirectory()
            }
            return path
        }

    /**
     * 获取的目录默认没有最后的”/”,需要自己加上
     * 获取本应用图片缓存目录
     *
     * @return
     */
    @JvmStatic
    fun getCacheFolder(context:Context):File {
        val folder = File(context.cacheDir, "IMAGECACHE")
        if (!folder.exists()) {
            folder.mkdir()
        }
        return folder
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br></br>false : 不可用
     */
    @JvmStatic
    val isSDCardEnable:Boolean
        get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    /**
     * 获取SD卡路径
     *
     * 一般是/storage/emulated/0/
     *
     * @return SD卡路径
     */
    @JvmStatic
    val sDCardPath:String
        get() = if (!isSDCardEnable) {
            "sdcard unable!"
        } else Environment.getExternalStorageDirectory().path + File.separator

    /**
     * 获取SD卡Data路径
     *
     * @return SD卡Data路径
     */
    @JvmStatic
    val dataPath:String
        get() = if (!isSDCardEnable) {
            "sdcard unable!"
        } else Environment.getDataDirectory().path

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @get:TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    val freeSpace:String
        get() {
            if (!isSDCardEnable) {
                return "sdcard unable!"
            }
            val stat = StatFs(sDCardPath)
            val blockSize:Long
            val availableBlocks:Long
            availableBlocks = stat.availableBlocksLong
            blockSize = stat.blockSizeLong
            return DataUtils.byte2FitSize(availableBlocks * blockSize)
        }

    /**
     * SD卡是否可用.
     */
    @JvmStatic
    fun sdCardIsAvailable():Boolean {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val sd = File(Environment.getExternalStorageDirectory().path)
            sd.canWrite()
        } else {
            false
        }
    }

    /**
     * 文件或者文件夹是否存在.
     */
    @JvmStatic
    fun fileExists(filePath:String?):Boolean {
        val file = File(filePath)
        return file.exists()
    }

    /**
     * 删除指定文件夹下所有文件, 不保留文件夹.
     */
    @JvmStatic
    fun delAllFile(path:String?):Boolean {
        val flag = false
        val file = File(path)
        if (!file.exists()) {
            return flag
        }
        if (file.isFile) {
            file.delete()
            return true
        }
        val files = file.listFiles()
        for (i in files.indices) {
            val exeFile = files[i]
            if (exeFile.isDirectory) {
                delAllFile(exeFile.absolutePath)
            } else {
                exeFile.delete()
            }
        }
        file.delete()
        return flag
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dirPath 目录路径
     * @return `true`: 删除成功<br></br>`false`: 删除失败
     */
    @JvmStatic
    fun deleteFilesInDir(dirPath:String?):Boolean {
        return deleteFilesInDir(getFileByPath(dirPath))
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dir 目录
     * @return `true`: 删除成功<br></br>`false`: 删除失败
     */
    @JvmStatic
    fun deleteFilesInDir(dir:File?):Boolean {
        if (dir == null) {
            return false
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true
        }
        // 不是目录返回false
        if (!dir.isDirectory) {
            return false
        }
        // 现在文件存在且是文件夹
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (file.isFile) {
                    if (!deleteFile(file)) {
                        return false
                    }
                } else if (file.isDirectory) {
                    if (!deleteDir(file)) {
                        return false
                    }
                }
            }
        }
        return true
    }

    /**
     * 清除内部缓存
     *
     * /data/data/com.xxx.xxx/cache
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    @JvmStatic
    fun cleanInternalCache(context:Context):Boolean {
        return deleteFilesInDir(context.cacheDir)
    }

    /**
     * 清除内部文件
     *
     * /data/data/com.xxx.xxx/files
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    @JvmStatic
    fun cleanInternalFiles(context:Context):Boolean {
        return deleteFilesInDir(context.filesDir)
    }

    /**
     * 清除内部数据库
     *
     * /data/data/com.xxx.xxx/databases
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    @JvmStatic
    fun cleanInternalDbs(context:Context):Boolean {
        return deleteFilesInDir(context.filesDir.parent + File.separator + "databases")
    }

    /**
     * 根据名称清除数据库
     *
     * /data/data/com.xxx.xxx/databases/dbName
     *
     * @param dbName 数据库名称
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    @JvmStatic
    fun cleanInternalDbByName(context:Context, dbName:String?):Boolean {
        return context.deleteDatabase(dbName)
    }

    /**
     * 清除内部SP
     *
     * /data/data/com.xxx.xxx/shared_prefs
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    @JvmStatic
    fun cleanInternalSP(context:Context):Boolean {
        return deleteFilesInDir(context.filesDir.parent + File.separator + "shared_prefs")
    }

    /**
     * 清除外部缓存
     *
     * /storage/emulated/0/android/data/com.xxx.xxx/cache
     *
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    @JvmStatic
    fun cleanExternalCache(context:Context):Boolean {
        return isSDCardEnable && deleteFilesInDir(context.externalCacheDir)
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    @JvmStatic
    fun cleanCustomCache(dirPath:String?):Boolean {
        return deleteFilesInDir(dirPath)
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return `true`: 清除成功<br></br>`false`: 清除失败
     */
    @JvmStatic
    fun cleanCustomCache(dir:File?):Boolean {
        return deleteFilesInDir(dir)
    }

    /**
     * 文件复制.
     */
    @JvmStatic
    fun copy(srcFile:String?, destFile:String?):Boolean {
        try {
            val `in` = FileInputStream(srcFile)
            val out = FileOutputStream(destFile)
            val bytes = ByteArray(1024)
            var c:Int
            while (`in`.read(bytes).also { c = it } != -1) {
                out.write(bytes, 0, c)
            }
            `in`.close()
            out.flush()
            out.close()
        } catch (e:Exception) {
            return false
        }
        return true
    }

    /**
     * 复制整个文件夹内.
     *
     * @param oldPath string 原文件路径如：c:/fqf.
     * @param newPath string 复制后路径如：f:/fqf/ff.
     */
    @JvmStatic
    fun copyFolder(oldPath:String, newPath:String) {
        try {
            File(newPath).mkdirs() // 如果文件夹不存在 则建立新文件夹
            val a = File(oldPath)
            val file = a.list()
            var temp:File? = null
            for (i in file.indices) {
                temp = if (oldPath.endsWith(File.separator)) {
                    File(oldPath + file[i])
                } else {
                    File(oldPath + File.separator + file[i])
                }
                if (temp.isFile) {
                    val input = FileInputStream(temp)
                    val output = FileOutputStream(newPath + "/" + temp.name)
                    val b = ByteArray(1024 * 5)
                    var len:Int
                    while (input.read(b).also { len = it } != -1) {
                        output.write(b, 0, len)
                    }
                    output.flush()
                    output.close()
                    input.close()
                }
                if (temp.isDirectory) { // 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i])
                }
            }
        } catch (e:NullPointerException) {
        } catch (e:Exception) {
        }
    }

    /**
     * 重命名文件.
     */
    @JvmStatic
    fun renameFile(resFilePath:String?, newFilePath:String?):Boolean {
        val resFile = File(resFilePath)
        val newFile = File(newFilePath)
        return resFile.renameTo(newFile)
    }

    /**
     * 获取磁盘可用空间.
     */
    @get:SuppressLint("NewApi")
    val sDCardAvailaleSize:Long
        get() {
            val path = rootPath
            val stat = StatFs(path!!.path)
            val blockSize:Long
            val availableBlocks:Long
            if (Build.VERSION.SDK_INT >= 18) {
                blockSize = stat.blockSizeLong
                availableBlocks = stat.availableBlocksLong
            } else {
                blockSize = stat.blockSize.toLong()
                availableBlocks = stat.availableBlocks.toLong()
            }
            return availableBlocks * blockSize
        }

    /**
     * 获取某个目录可用大小.
     */
    @SuppressLint("NewApi")
    @JvmStatic
    fun getDirSize(path:String?):Long {
        val stat = StatFs(path)
        val blockSize:Long
        val availableBlocks:Long
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.blockSizeLong
            availableBlocks = stat.availableBlocksLong
        } else {
            blockSize = stat.blockSize.toLong()
            availableBlocks = stat.availableBlocks.toLong()
        }
        return availableBlocks * blockSize
    }

    /**
     * 获取文件或者文件夹大小.
     */
    @JvmStatic
    fun getFileAllSize(path:String?):Long {
        val file = File(path)
        return if (file.exists()) {
            if (file.isDirectory) {
                val childrens = file.listFiles()
                var size:Long = 0
                for (f in childrens) {
                    size += getFileAllSize(f.path)
                }
                size
            } else {
                file.length()
            }
        } else {
            0
        }
    }

    /**
     * 创建一个文件.
     */
    @JvmStatic
    fun initFile(path:String?):Boolean {
        var result = false
        try {
            val file = File(path)
            if (!file.exists()) {
                result = file.createNewFile()
            } else if (file.isDirectory) {
                file.delete()
                result = file.createNewFile()
            } else if (file.exists()) {
                result = true
            }
        } catch (e:IOException) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 创建一个文件夹.
     */
    @JvmStatic
    fun initDirectory(path:String?):Boolean {
        var result = false
        val file = File(path)
        if (!file.exists()) {
            result = file.mkdir()
        } else if (!file.isDirectory) {
            file.delete()
            result = file.mkdir()
        } else if (file.exists()) {
            result = true
        }
        return result
    }

    /**
     * 复制文件.
     */
    @Throws(IOException::class)
    @JvmStatic
    fun copyFile(from:File, to:File?) {
        if (!from.exists()) {
            throw IOException("The source file not exist: " + from.absolutePath)
        }
        val fis = FileInputStream(from)
        try {
            copyFile(fis, to)
        } finally {
            fis.close()
        }
    }

    /**
     * 从InputStream流复制文件.
     */
    @Throws(IOException::class)
    @JvmStatic
    fun copyFile(from:InputStream, to:File?):Long {
        var totalBytes:Long = 0
        val fos = FileOutputStream(to, false)
        try {
            val data = ByteArray(1024)
            var len:Int
            while (from.read(data).also { len = it } > -1) {
                fos.write(data, 0, len)
                totalBytes += len.toLong()
            }
            fos.flush()
        } finally {
            fos.close()
        }
        return totalBytes
    }

    /**
     * 保存InputStream流到文件.
     */
    @JvmStatic
    fun saveFile(inputStream:InputStream, filePath:String?) {
        try {
            val outputStream:OutputStream = FileOutputStream(File(filePath), false)
            var len:Int
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer).also { len = it } != -1) {
                outputStream.write(buffer, 0, len)
            }
            outputStream.flush()
            outputStream.close()
        } catch (e:IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 用UTF8保存一个文件.
     */
    @Throws(IOException::class)
    @JvmStatic
    fun saveFileUTF8(path:String?, content:String?, append:Boolean?) {
        val fos = FileOutputStream(path, append!!)
        val out:Writer = OutputStreamWriter(fos, StandardCharsets.UTF_8)
        out.write(content)
        out.flush()
        out.close()
        fos.flush()
        fos.close()
    }

    /**
     * 用UTF8读取一个文件.
     */
    @JvmStatic
    fun getFileUTF8(path:String?):String {
        var result = ""
        var fin:InputStream? = null
        try {
            fin = FileInputStream(path)
            val length = fin.available()
            val buffer = ByteArray(length)
            fin.read(buffer)
            fin.close()
            result = String(buffer, StandardCharsets.UTF_8)
        } catch (e:Exception) {
        }
        return result
    }

    /**
     * 得到一个文件Intent.
     */
    @JvmStatic
    fun getFileIntent(path:String?, mimeType:String?):Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.fromFile(File(path)), mimeType)
        return intent
    }

    /**
     * 获取缓存目录
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getDiskCacheDir(context:Context):String? {
        var cachePath:String? = null
        cachePath = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            context.externalCacheDir!!.path
        } else {
            context.cacheDir.path
        }
        return cachePath
    }

    /**
     * 获取缓存视频文件目录
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getDiskFileDir(context:Context):String? {
        var cachePath:String? = null
        cachePath = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)!!.path
        } else {
            context.filesDir.path
        }
        return cachePath
    }

    /**
     * 多个文件合并
     *
     * @param outFile
     * @param files
     */
    @JvmStatic
    fun mergeFiles(context:Context?, outFile:File?, files:List<File?>) {
        var outChannel:FileChannel? = null
        try {
            outChannel = FileOutputStream(outFile).channel
            for (f in files) {
                val fc = FileInputStream(f).channel
                val bb = ByteBuffer.allocate(BUFSIZE)
                while (fc.read(bb) != -1) {
                    bb.flip()
                    outChannel.write(bb)
                    bb.clear()
                }
                fc.close()
            }
            LogUtils.d("拼接完成")
        } catch (ioe:IOException) {
            ioe.printStackTrace()
        } finally {
            try {
                outChannel?.close()
            } catch (ignore:IOException) {
            }
        }
    }

    /**
     * 将在线的m3u8替换成本地的m3u8
     *
     * @param context  实体
     * @param file     在线的m3u8
     * @param pathList 本地的ts文件
     * @return
     */
    @JvmStatic
    fun getNativeM3u(context:Context?, file:File?, pathList:List<File>):String {
        var `in`:InputStream? = null
        var num = 0
        //需要生成的目标buff
        val buf = StringBuffer()
        try {
            if (file != null) {
                `in` = FileInputStream(file)
            }
            val reader = BufferedReader(InputStreamReader(`in`))
            var line = ""
            while (reader.readLine().also { line = it } != null) {
                if (line.length > 0 && line.startsWith("http://")) {
                    //replce 这行的内容
//                    RxLogTool.d("ts替换", line + "  replce  " + pathList.get(num).getAbsolutePath());
                    buf.append("""
    file:${pathList[num].absolutePath}

    """.trimIndent())
                    num++
                } else {
                    buf.append("""
    $line

    """.trimIndent())
                }
            }
            `in`!!.close()
            write(file!!.absolutePath, buf.toString())
            LogUtils.d("ts替换", "ts替换完成")
        } catch (e1:FileNotFoundException) {
            e1.printStackTrace()
        } catch (e1:IOException) {
            e1.printStackTrace()
        }
        return buf.toString()
    }

    /**
     * 将字符串 保存成 文件
     *
     * @param filePath
     * @param content
     */
    @JvmStatic
    fun write(filePath:String?, content:String?) {
        var bw:BufferedWriter? = null
        try {
            //根据文件路径创建缓冲输出流
            bw = BufferedWriter(FileWriter(filePath))
            // 将内容写入文件中
            bw.write(content)
            //            RxLogTool.d("M3U8替换", "替换完成");
        } catch (e:Exception) {
            e.printStackTrace()
        } finally {
            // 关闭流
            if (bw != null) {
                try {
                    bw.close()
                } catch (e:IOException) {
                    bw = null
                }
            }
        }
    }

    /**
     * 获取 搜索的路径 下的 所有 后缀 的文件
     *
     * @param fileAbsolutePath 搜索的路径
     * @param suffix           文件后缀
     * @return
     */
    @JvmStatic
    fun GetAllFileName(fileAbsolutePath:String?, suffix:String?):Vector<String> {
        val vecFile = Vector<String>()
        val file = File(fileAbsolutePath)
        val subFile = file.listFiles()
        for (iFileLength in subFile.indices) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory) {
                val filename = subFile[iFileLength].name
                // 判断是否为suffix结尾
                if (filename.trim { it <= ' ' }.toLowerCase().endsWith(suffix!!)) {
                    vecFile.add(filename)
                }
            }
        }
        return vecFile
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    @JvmStatic
    fun getFileByPath(filePath:String?):File? {
        return if (DataUtils.isNullString(filePath)) null else File(filePath)
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    @JvmStatic
    fun isFileExists(filePath:String?):Boolean {
        return isFileExists(getFileByPath(filePath))
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    @JvmStatic
    fun isFileExists(file:File?):Boolean {
        return file != null && file.exists()
    }

    /**
     * 判断是否是目录
     *
     * @param dirPath 目录路径
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isDir(dirPath:String?):Boolean {
        return isDir(getFileByPath(dirPath))
    }

    /**
     * 判断是否是目录
     *
     * @param file 文件
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isDir(file:File?):Boolean {
        return isFileExists(file) && file!!.isDirectory
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isFile(filePath:String?):Boolean {
        return isFile(getFileByPath(filePath))
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isFile(file:File?):Boolean {
        return isFileExists(file) && file!!.isFile
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 文件路径
     * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
     */
    @JvmStatic
    fun createOrExistsDir(dirPath:String?):Boolean {
        return createOrExistsDir(getFileByPath(dirPath))
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
     */
    @JvmStatic
    fun createOrExistsDir(file:File?):Boolean {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
     */
    @JvmStatic
    fun createOrExistsFile(filePath:String?):Boolean {
        return createOrExistsFile(getFileByPath(filePath))
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
     */
    @JvmStatic
    fun createOrExistsFile(file:File?):Boolean {
        if (file == null) {
            return false
        }
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile
        }
        return if (!createOrExistsDir(file.parentFile)) {
            false
        } else try {
            file.createNewFile()
        } catch (e:IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return `true`: 创建成功<br></br>`false`: 创建失败
     */
    @JvmStatic
    fun createFileByDeleteOldFile(filePath:String?):Boolean {
        return createFileByDeleteOldFile(getFileByPath(filePath))
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return `true`: 创建成功<br></br>`false`: 创建失败
     */
    @JvmStatic
    fun createFileByDeleteOldFile(file:File?):Boolean {
        if (file == null) {
            return false
        }
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile && !file.delete()) {
            return false
        }
        // 创建目录失败返回false
        return if (!createOrExistsDir(file.parentFile)) {
            false
        } else try {
            file.createNewFile()
        } catch (e:IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 复制或移动目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @param isMove      是否移动
     * @return `true`: 复制或移动成功<br></br>`false`: 复制或移动失败
     */
    @JvmStatic
    fun copyOrMoveDir(srcDirPath:String?, destDirPath:String?, isMove:Boolean):Boolean {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), isMove)
    }

    /**
     * 复制或移动目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @param isMove  是否移动
     * @return `true`: 复制或移动成功<br></br>`false`: 复制或移动失败
     */
    @JvmStatic
    fun copyOrMoveDir(srcDir:File?, destDir:File?, isMove:Boolean):Boolean {
        if (srcDir == null || destDir == null) {
            return false
        }
        // 如果目标目录在源目录中则返回false，看不懂的话好好想想递归怎么结束
        // srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
        // destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
        // 为防止以上这种情况出现出现误判，须分别在后面加个路径分隔符
        val srcPath = srcDir.path + File.separator
        val destPath = destDir.path + File.separator
        if (destPath.contains(srcPath)) {
            return false
        }
        // 源文件不存在或者不是目录则返回false
        if (!srcDir.exists() || !srcDir.isDirectory) {
            return false
        }
        // 目标目录不存在返回false
        if (!createOrExistsDir(destDir)) {
            return false
        }
        val files = srcDir.listFiles()
        for (file in files) {
            val oneDestFile = File(destPath + file.name)
            if (file.isFile) {
                // 如果操作失败返回false
                if (!copyOrMoveFile(file, oneDestFile, isMove)) {
                    return false
                }
            } else if (file.isDirectory) {
                // 如果操作失败返回false
                if (!copyOrMoveDir(file, oneDestFile, isMove)) {
                    return false
                }
            }
        }
        return !isMove || deleteDir(srcDir)
    }

    /**
     * 复制或移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @param isMove       是否移动
     * @return `true`: 复制或移动成功<br></br>`false`: 复制或移动失败
     */
    @JvmStatic
    fun copyOrMoveFile(srcFilePath:String?, destFilePath:String?, isMove:Boolean):Boolean {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove)
    }

    /**
     * 复制或移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param isMove   是否移动
     * @return `true`: 复制或移动成功<br></br>`false`: 复制或移动失败
     */
    @JvmStatic
    fun copyOrMoveFile(srcFile:File?, destFile:File?, isMove:Boolean):Boolean {
        if (srcFile == null || destFile == null) {
            return false
        }
        // 源文件不存在或者不是文件则返回false
        if (!srcFile.exists() || !srcFile.isFile) {
            return false
        }
        // 目标文件存在且是文件则返回false
        if (destFile.exists() && destFile.isFile) {
            return false
        }
        // 目标目录不存在返回false
        return if (!createOrExistsDir(destFile.parentFile)) {
            false
        } else try {
            (writeFileFromIS(destFile, FileInputStream(srcFile), false) && !(isMove && !deleteFile(srcFile)))
        } catch (e:FileNotFoundException) {
            e.printStackTrace()
            LogUtils.e4Mark(e)
            false
        }
    }

    /**
     * 复制目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @return `true`: 复制成功<br></br>`false`: 复制失败
     */
    @JvmStatic
    fun copyDir(srcDirPath:String?, destDirPath:String?):Boolean {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath))
    }

    /**
     * 复制目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @return `true`: 复制成功<br></br>`false`: 复制失败
     */
    @JvmStatic
    fun copyDir(srcDir:File?, destDir:File?):Boolean {
        return copyOrMoveDir(srcDir, destDir, false)
    }

    /**
     * 复制文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @return `true`: 复制成功<br></br>`false`: 复制失败
     */
    @JvmStatic
    fun copyFile(srcFilePath:String?, destFilePath:String?):Boolean {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), false)
    }

    /**
     * 复制文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return `true`: 复制成功<br></br>`false`: 复制失败
     */
    @JvmStatic
    fun copyFile(srcFile:File?, destFile:File?, isCopy:Boolean):Boolean {
        return copyOrMoveFile(srcFile, destFile, false)
    }

    /**
     * 移动目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @return `true`: 移动成功<br></br>`false`: 移动失败
     */
    @JvmStatic
    fun moveDir(srcDirPath:String?, destDirPath:String?):Boolean {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath))
    }

    /**
     * 移动目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @return `true`: 移动成功<br></br>`false`: 移动失败
     */
    @JvmStatic
    fun moveDir(srcDir:File?, destDir:File?):Boolean {
        return copyOrMoveDir(srcDir, destDir, true)
    }

    /**
     * 移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @return `true`: 移动成功<br></br>`false`: 移动失败
     */
    @JvmStatic
    fun moveFile(srcFilePath:String?, destFilePath:String?):Boolean {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath))
    }

    /**
     * 移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return `true`: 移动成功<br></br>`false`: 移动失败
     */
    @JvmStatic
    fun moveFile(srcFile:File?, destFile:File?):Boolean {
        return copyOrMoveFile(srcFile, destFile, true)
    }

    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @return `true`: 删除成功<br></br>`false`: 删除失败
     */
    @JvmStatic
    fun deleteDir(dirPath:String):Boolean? {
        return getFileByPath(dirPath)?.let { deleteDir(it) }
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return `true`: 删除成功<br></br>`false`: 删除失败
     */
    @JvmStatic
    fun deleteDir(dir:File):Boolean {
        if (dir == null) {
            return false
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true
        }
        // 不是目录返回false
        if (!dir.isDirectory) {
            return false
        }
        // 现在文件存在且是文件夹
        val files = dir.listFiles()
        for (file in files) {
            if (file.isFile) {
                if (!deleteFile(file)) {
                    return false
                }
            } else if (file.isDirectory) {
                if (!deleteDir(file)) {
                    return false
                }
            }
        }
        return dir.delete()
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return `true`: 删除成功<br></br>`false`: 删除失败
     */
    @JvmStatic
    fun deleteFile(srcFilePath:String?):Boolean {
        return deleteFile(getFileByPath(srcFilePath))
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return `true`: 删除成功<br></br>`false`: 删除失败
     */
    @JvmStatic
    fun deleteFile(file:File?):Boolean {
        return file != null && (!file.exists() || file.isFile && file.delete())
    }

    /**
     * 获取目录下所有文件
     *
     * @param dirPath     目录路径
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDir(dirPath:String?, isRecursive:Boolean):List<File>? {
        return listFilesInDir(getFileByPath(dirPath), isRecursive)
    }

    /**
     * 获取目录下所有文件
     *
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDir(dir:File?, isRecursive:Boolean):List<File>? {
        if (isRecursive) {
            return listFilesInDir(dir)
        }
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list:MutableList<File> = java.util.ArrayList()
        Collections.addAll(list, *dir.listFiles())
        return list
    }

    /**
     * 获取目录下所有文件包括子目录
     *
     * @param dirPath 目录路径
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDir(dirPath:String?):List<File>? {
        return listFilesInDir(getFileByPath(dirPath))
    }

    /**
     * 获取目录下所有文件包括子目录
     *
     * @param dir 目录
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDir(dir:File?):List<File>? {
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list:MutableList<File> = java.util.ArrayList()
        val files = dir.listFiles()
        for (file in files) {
            list.add(file)
            if (file.isDirectory) {
                list.addAll(listFilesInDir(file)!!)
            }
        }
        return list
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     *
     * 大小写忽略
     *
     * @param dirPath     目录路径
     * @param suffix      后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDirWithFilter(dirPath:String?, suffix:String, isRecursive:Boolean):List<File>? {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix, isRecursive)
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     *
     * 大小写忽略
     *
     * @param dir         目录
     * @param suffix      后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDirWithFilter(dir:File?, suffix:String, isRecursive:Boolean):List<File>? {
        if (isRecursive) {
            return listFilesInDirWithFilter(dir, suffix)
        }
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list:MutableList<File> = java.util.ArrayList()
        val files = dir.listFiles()
        for (file in files) {
            if (file.name.toUpperCase().endsWith(suffix.toUpperCase())) {
                list.add(file)
            }
        }
        return list
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     *
     * 大小写忽略
     *
     * @param dirPath 目录路径
     * @param suffix  后缀名
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDirWithFilter(dirPath:String?, suffix:String):List<File>? {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix)
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     *
     * 大小写忽略
     *
     * @param dir    目录
     * @param suffix 后缀名
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDirWithFilter(dir:File?, suffix:String):List<File>? {
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list:MutableList<File> = java.util.ArrayList()
        val files = dir.listFiles()
        for (file in files) {
            if (file.name.toUpperCase().endsWith(suffix.toUpperCase())) {
                list.add(file)
            }
            if (file.isDirectory) {
                list.addAll(listFilesInDirWithFilter(file, suffix)!!)
            }
        }
        return list
    }

    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dirPath     目录路径
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDirWithFilter(dirPath:String?, filter:FilenameFilter, isRecursive:Boolean):List<File>? {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive)
    }

    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dir         目录
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDirWithFilter(dir:File?, filter:FilenameFilter, isRecursive:Boolean):List<File>? {
        if (isRecursive) {
            return listFilesInDirWithFilter(dir, filter)
        }
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list:MutableList<File> = java.util.ArrayList()
        val files = dir.listFiles()
        for (file in files) {
            if (filter.accept(file.parentFile, file.name)) {
                list.add(file)
            }
        }
        return list
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dirPath 目录路径
     * @param filter  过滤器
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDirWithFilter(dirPath:String?, filter:FilenameFilter):List<File>? {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter)
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dir    目录
     * @param filter 过滤器
     * @return 文件链表
     */
    @JvmStatic
    fun listFilesInDirWithFilter(dir:File?, filter:FilenameFilter):List<File>? {
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list:MutableList<File> = java.util.ArrayList()
        val files = dir.listFiles()
        for (file in files) {
            if (filter.accept(file.parentFile, file.name)) {
                list.add(file)
            }
            if (file.isDirectory) {
                list.addAll(listFilesInDirWithFilter(file, filter)!!)
            }
        }
        return list
    }

    /**
     * 获取目录下指定文件名的文件包括子目录
     *
     * 大小写忽略
     *
     * @param dirPath  目录路径
     * @param fileName 文件名
     * @return 文件链表
     */
    @JvmStatic
    fun searchFileInDir(dirPath:String?, fileName:String):List<File>? {
        return searchFileInDir(getFileByPath(dirPath), fileName)
    }

    /**
     * 获取目录下指定文件名的文件包括子目录
     *
     * 大小写忽略
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return 文件链表
     */
    @JvmStatic
    fun searchFileInDir(dir:File?, fileName:String):List<File>? {
        if (dir == null || !isDir(dir)) {
            return null
        }
        val list:MutableList<File> = java.util.ArrayList()
        val files = dir.listFiles()
        for (file in files) {
            if (file.name.toUpperCase() == fileName.toUpperCase()) {
                list.add(file)
            }
            if (file.isDirectory) {
                list.addAll(listFilesInDirWithFilter(file, fileName)!!)
            }
        }
        return list
    }

    /**
     * 将输入流写入文件
     *
     * @param filePath 路径
     * @param is       输入流
     * @param append   是否追加在文件末
     * @return `true`: 写入成功<br></br>`false`: 写入失败
     */
    @JvmStatic
    fun writeFileFromIS(filePath:String?, `is`:InputStream?, append:Boolean):Boolean {
        return writeFileFromIS(getFileByPath(filePath), `is`, append)
    }

    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param is     输入流
     * @param append 是否追加在文件末
     * @return `true`: 写入成功<br></br>`false`: 写入失败
     */
    @JvmStatic
    fun writeFileFromIS(file:File?, `is`:InputStream?, append:Boolean):Boolean {
        if (file == null || `is` == null) return false
        if (!createOrExistsFile(file)) return false
        var os:OutputStream? = null
        return try {
            os = BufferedOutputStream(FileOutputStream(file, append))
            val data = ByteArray(KB)
            var len:Int
            while (`is`.read(data, 0, KB).also { len = it } != -1) {
                os.write(data, 0, len)
            }
            true
        } catch (e:IOException) {
            e.printStackTrace()
            false
        } finally {
            closeIO(`is`, os)
        }
    }

    /**
     * 将字符串写入文件
     *
     * @param filePath 文件路径
     * @param content  写入内容
     * @param append   是否追加在文件末
     * @return `true`: 写入成功<br></br>`false`: 写入失败
     */
    @JvmStatic
    fun writeFileFromString(filePath:String?, content:String?, append:Boolean):Boolean {
        return writeFileFromString(getFileByPath(filePath), content, append)
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @param append  是否追加在文件末
     * @return `true`: 写入成功<br></br>`false`: 写入失败
     */
    @JvmStatic
    fun writeFileFromString(file:File?, content:String?, append:Boolean):Boolean {
        if (file == null || content == null) return false
        if (!createOrExistsFile(file)) return false
        var fileWriter:FileWriter? = null
        return try {
            fileWriter = FileWriter(file, append)
            fileWriter.write(content)
            true
        } catch (e:IOException) {
            e.printStackTrace()
            false
        } finally {
            closeIO(fileWriter)
        }
    }

    /**
     * 指定编码按行读取文件到List
     *
     * @param filePath    文件路径
     * @param charsetName 编码格式
     * @return 文件行链表
     */
    @JvmStatic
    fun readFile2List(filePath:String?, charsetName:String?):List<String>? {
        return readFile2List(getFileByPath(filePath), charsetName)
    }

    /**
     * 指定编码按行读取文件到List
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 文件行链表
     */
    @JvmStatic
    fun readFile2List(file:File?, charsetName:String?):List<String>? {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName)
    }

    /**
     * 指定编码按行读取文件到List
     *
     * @param filePath    文件路径
     * @param st          需要读取的开始行数
     * @param end         需要读取的结束行数
     * @param charsetName 编码格式
     * @return 包含制定行的list
     */
    @JvmStatic
    fun readFile2List(filePath:String?, st:Int, end:Int, charsetName:String?):List<String>? {
        return readFile2List(getFileByPath(filePath), st, end, charsetName)
    }

    /**
     * 指定编码按行读取文件到List
     *
     * @param file        文件
     * @param st          需要读取的开始行数
     * @param end         需要读取的结束行数
     * @param charsetName 编码格式
     * @return 包含从start行到end行的list
     */
    @JvmStatic
    fun readFile2List(file:File?, st:Int, end:Int, charsetName:String?):List<String>? {
        if (file == null) {
            return null
        }
        if (st > end) {
            return null
        }
        var reader:BufferedReader? = null
        return try {
            var line:String?
            var curLine = 1
            val list:MutableList<String> = java.util.ArrayList()
            reader = if (DataUtils.isNullString(charsetName)) {
                BufferedReader(FileReader(file))
            } else {
                BufferedReader(InputStreamReader(FileInputStream(file), charsetName))
            }
            while (reader.readLine().also { line = it } != null) {
                if (curLine > end) {
                    break
                }
                if (st <= curLine && curLine <= end) {
                    list.add(line!!)
                }
                ++curLine
            }
            list
        } catch (e:IOException) {
            e.printStackTrace()
            null
        } finally {
            closeIO(reader)
        }
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param filePath    文件路径
     * @param charsetName 编码格式
     * @return 字符串
     */
    @JvmStatic
    fun readFile2String(filePath:String?, charsetName:String?):String? {
        return readFile2String(getFileByPath(filePath), charsetName)
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 字符串
     */
    @JvmStatic
    fun readFile2String(file:File?, charsetName:String?):String? {
        if (file == null) {
            return null
        }
        var reader:BufferedReader? = null
        return try {
            val sb = StringBuilder()
            reader = if (DataUtils.isNullString(charsetName)) {
                BufferedReader(InputStreamReader(FileInputStream(file)))
            } else {
                BufferedReader(InputStreamReader(FileInputStream(file), charsetName))
            }
            var line:String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\r\n") // windows系统换行为\r\n，Linux为\n
            }
            // 要去除最后的换行符
            sb.delete(sb.length - 2, sb.length).toString()
        } catch (e:IOException) {
            e.printStackTrace()
            null
        } finally {
            closeIO(reader)
        }
    }

    /**
     * 指定编码按行读取文件到字符数组中
     *
     * @param filePath 文件路径
     * @return StringBuilder对象
     */
    @JvmStatic
    fun readFile2Bytes(filePath:String?):ByteArray? {
        return readFile2Bytes(getFileByPath(filePath))
    }

    /**
     * 指定编码按行读取文件到字符数组中
     *
     * @param file 文件
     * @return StringBuilder对象
     */
    @JvmStatic
    fun readFile2Bytes(file:File?):ByteArray? {
        return if (file == null) {
            null
        } else try {
            DataUtils.inputStream2Bytes(FileInputStream(file))
        } catch (e:FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 简单获取文件编码格式
     *
     * @param filePath 文件路径
     * @return 文件编码
     */
    @JvmStatic
    fun getFileCharsetSimple(filePath:String?):String {
        return getFileCharsetSimple(getFileByPath(filePath))
    }

    /**
     * 简单获取文件编码格式
     *
     * @param file 文件
     * @return 文件编码
     */
    @JvmStatic
    fun getFileCharsetSimple(file:File?):String {
        var p = 0
        var `is`:InputStream? = null
        try {
            `is` = BufferedInputStream(FileInputStream(file))
            p = (`is`.read() shl 8) + `is`.read()
        } catch (e:IOException) {
            e.printStackTrace()
        } finally {
            closeIO(`is`)
        }
        return when (p) {
            0xefbb -> "UTF-8"
            0xfffe -> "Unicode"
            0xfeff -> "UTF-16BE"
            else -> "GBK"
        }
    }

    /**
     * 获取文件行数
     *
     * @param filePath 文件路径
     * @return 文件行数
     */
    @JvmStatic
    fun getFileLines(filePath:String?):Int {
        return getFileLines(getFileByPath(filePath))
    }

    /**
     * 获取文件行数
     *
     * @param file 文件
     * @return 文件行数
     */
    @JvmStatic
    fun getFileLines(file:File?):Int {
        var count = 1
        var `is`:InputStream? = null
        try {
            `is` = BufferedInputStream(FileInputStream(file))
            val buffer = ByteArray(KB)
            var readChars:Int
            while (`is`.read(buffer, 0, KB).also { readChars = it } != -1) {
                for (i in 0 until readChars) {
                    if (buffer[i].equals('\n')) {
                        ++count
                    }
                }
            }
        } catch (e:IOException) {
            e.printStackTrace()
        } finally {
            closeIO(`is`)
        }
        return count
    }

    /**
     * 获取文件大小
     *
     * @param filePath 文件路径
     * @return 文件大小
     */
    @JvmStatic
    fun getFileSize(filePath:String?):String {
        return getFileSize(getFileByPath(filePath))
    }

    /**
     * 获取文件大小
     *
     * 例如：getFileSize(file, RxConstTool.MB); 返回文件大小单位为MB
     *
     * @param file 文件
     * @return 文件大小
     */
    @JvmStatic
    fun getFileSize(file:File?):String {
        return if (!isFileExists(file)) {
            ""
        } else DataUtils.byte2FitSize(file!!.length())
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param filePath 文件
     * @return 文件的MD5校验码
     */
    @JvmStatic
    fun getFileMD5(filePath:String?):String {
        return getFileMD5(getFileByPath(filePath))
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param file 文件
     * @return 文件的MD5校验码
     */
    @JvmStatic
    fun getFileMD5(file:File?):String {
        return EncryptTool.encryptMD5File2String(file!!)
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    @JvmStatic
    fun closeIO(vararg closeables:Closeable?) {
        if (closeables == null) {
            return
        }
        try {
            for (closeable in closeables) {
                closeable?.close()
            }
        } catch (e:IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 获取全路径中的最长目录
     *
     * @param file 文件
     * @return filePath最长目录
     */
    @JvmStatic
    fun getDirName(file:File?):String? {
        return if (file == null) {
            null
        } else getDirName(file.path)
    }

    /**
     * 获取全路径中的最长目录
     *
     * @param filePath 文件路径
     * @return filePath最长目录
     */
    @JvmStatic
    fun getDirName(filePath:String):String {
        if (DataUtils.isNullString(filePath)) {
            return filePath
        }
        val lastSep = filePath.lastIndexOf(File.separator)
        return if (lastSep == -1) "" else filePath.substring(0, lastSep + 1)
    }

    /**
     * 获取全路径中的文件名
     *
     * @param file 文件
     * @return 文件名
     */
    @JvmStatic
    fun getFileName(file:File?):String? {
        return if (file == null) {
            null
        } else getFileName(file.path)
    }

    /**
     * 获取全路径中的文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    @JvmStatic
    fun getFileName(filePath:String):String {
        if (DataUtils.isNullString(filePath)) {
            return filePath
        }
        val lastSep = filePath.lastIndexOf(File.separator)
        return if (lastSep == -1) filePath else filePath.substring(lastSep + 1)
    }

    /**
     * 获取全路径中的不带拓展名的文件名
     *
     * @param file 文件
     * @return 不带拓展名的文件名
     */
    @JvmStatic
    fun getFileNameNoExtension(file:File?):String? {
        return if (file == null) {
            null
        } else getFileNameNoExtension(file.path)
    }

    /**
     * 获取全路径中的不带拓展名的文件名
     *
     * @param filePath 文件路径
     * @return 不带拓展名的文件名
     */
    @JvmStatic
    fun getFileNameNoExtension(filePath:String):String {
        if (DataUtils.isNullString(filePath)) {
            return filePath
        }
        val lastPoi = filePath.lastIndexOf('.')
        val lastSep = filePath.lastIndexOf(File.separator)
        if (lastSep == -1) {
            return if (lastPoi == -1) filePath else filePath.substring(0, lastPoi)
        }
        return if (lastPoi == -1 || lastSep > lastPoi) {
            filePath.substring(lastSep + 1)
        } else filePath.substring(lastSep + 1, lastPoi)
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param file 文件
     * @return 文件拓展名
     */
    @JvmStatic
    fun getFileExtension(file:File?):String? {
        return if (file == null) {
            null
        } else getFileExtension(file.path)
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    @JvmStatic
    fun getFileExtension(filePath:String):String {
        if (DataUtils.isNullString(filePath)) {
            return filePath
        }
        val lastPoi = filePath.lastIndexOf('.')
        val lastSep = filePath.lastIndexOf(File.separator)
        return if (lastPoi == -1 || lastSep >= lastPoi) {
            ""
        } else filePath.substring(lastPoi)
    }

    /**
     * 将文件转换成uri(支持7.0)
     *
     * @param mContext
     * @param file
     * @return
     */
    @JvmStatic
    fun getUriForFile(mContext:Context, file:File?):Uri? {
        var fileUri:Uri? = null
        fileUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(mContext, mContext.packageName + ".FileProvider", file!!)
        } else {
            Uri.fromFile(file)
        }
        return fileUri
    }

    /**
     * 将图片文件转换成uri
     *
     * @param context
     * @param imageFile
     * @return
     */
    @SuppressLint("Range")
    @JvmStatic
    fun getImageContentUri(context:Context?, imageFile:File?):Uri? {
        val filePath = imageFile?.absolutePath
        val cursor = context?.contentResolver?.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.Media._ID), MediaStore.Images.Media.DATA + "=? ", arrayOf(filePath), null)
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (imageFile!!.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                context!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
        }
    }

    /**
     * 将Uri转换成File
     *
     * @param context
     * @param uri
     * @return
     */
    @JvmStatic
    fun getFilePhotoFromUri(context:Activity?, uri:Uri?):File {
        return File(PhotoUtils.getImageAbsolutePath(context, uri))
    }

    @TargetApi(19)
    @JvmStatic
    fun getPathFromUri(context:Context, uri:Uri):String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri:Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) {
                uri.lastPathSegment
            } else getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return ""
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    @JvmStatic
    fun isExternalStorageDocument(uri:Uri):Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    @JvmStatic
    fun isDownloadsDocument(uri:Uri):Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    @JvmStatic
    fun isMediaDocument(uri:Uri):Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    @JvmStatic
    fun isGooglePhotosUri(uri:Uri):Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    @JvmStatic
    fun getDataColumn(context:Context, uri:Uri?, selection:String?, selectionArgs:Array<String>?):String? {
        var cursor:Cursor? = null
        val column = MediaStore.Images.Media.DATA
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    @JvmStatic
    fun closeIOQuietly(vararg closeables:Closeable?) {
        if (closeables == null) {
            return
        }
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (ignored:IOException) {
                }
            }
        }
    }

    @JvmStatic
    fun file2Base64(filePath:String?):String {
        var fis:FileInputStream? = null
        var base64String = ""
        val bos = ByteArrayOutputStream()
        try {
            fis = FileInputStream(filePath)
            val buffer = ByteArray(1024 * 100)
            var count = 0
            while (fis.read(buffer).also { count = it } != -1) {
                bos.write(buffer, 0, count)
            }
            fis.close()
        } catch (e:Exception) {
            e.printStackTrace()
        }
        base64String = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT)
        return base64String
    }

    /**
     *   是否是图片结尾
     * @param name String
     * @return Boolean
     */
    fun hasImageSuffix(name:String):Boolean {
        return name.lowercase().endsWith(".png") || name.lowercase().endsWith(".jpg") || name.lowercase().endsWith(".jpeg") || name.lowercase().endsWith(".webp")
    }
    @JvmStatic
    fun exportDb2Sdcard(context:Context, path:String, realDBName:String?, exportDBName:String) {
        val filePath = context.getDatabasePath(realDBName).absolutePath
        val buffer = ByteArray(1024)
        try {
            val input = FileInputStream(File(filePath))
            val output = FileOutputStream(path + File.separator + exportDBName)
            var length:Int
            while (input.read(buffer).also { length = it } > 0) {
                output.write(buffer, 0, length)
            }
            output.flush()
            output.close()
            input.close()
            LogUtils.i("mv success!")
        } catch (var8:IOException) {
            LogUtils.e("TAG", var8.toString())
        }
    }

}