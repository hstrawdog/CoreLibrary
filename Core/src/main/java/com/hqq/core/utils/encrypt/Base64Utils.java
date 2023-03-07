package com.hqq.core.utils.encrypt;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils.encrypt
 * @FileName :   Base64Utils
 * @Date : 2021/3/30 0030  上午 11:34
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
public class Base64Utils {

    private Base64Utils() {
        throw new UnsupportedOperationException("constrontor cannot be init");
    }

    /**
     * 字符串编码
     *
     * @param data
     * @return
     */
    public static String encodedStr(String data) {

        return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
    }

    /**
     * 字符串解码
     *
     * @param data
     * @return
     */
    public static String decodedStr(String data) {

        return new String(Base64.decode(data, Base64.DEFAULT));
    }

    /**
     * 文件编码
     *
     * @param filePath
     * @return
     */
    public static String encodedFile(String filePath) {

        String result = "";
        File file = new File(filePath);
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
            result = Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 文件解码
     * 解码后保存到指定目录，并返回解码后的内容
     *
     * @param filePath
     * @param data
     * @return
     */
    public static String decodedFile(String filePath, String data) {

        String result = "";
        File file = new File(filePath);
        try {

            byte[] decodeBytes = Base64.decode(data.getBytes(), Base64.DEFAULT);
            result = new String(decodeBytes);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(decodeBytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
