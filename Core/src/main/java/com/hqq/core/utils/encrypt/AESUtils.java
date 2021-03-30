package com.hqq.core.utils.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils.encrypt
 * @FileName :   AESUtils
 * @Date : 2021/3/30 0030  上午 11:34
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 * 原作者：https://blog.csdn.net/u013718120/article/details/56486408
 */
public class AESUtils {

    private AESUtils() {
        throw new UnsupportedOperationException("constrontor cannot be init");
    }

    /**
     * 生成秘钥
     *
     * @return
     */
    public static byte[] generateKey() {

        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES"); // 秘钥生成器
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.init(256); // 初始秘钥生成器
        SecretKey secretKey = keyGen.generateKey(); // 生成秘钥
        return secretKey.getEncoded(); // 获取秘钥字节数组
    }

    /**
     * 加密
     *
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {

        SecretKey secretKey = new SecretKeySpec(key, "AES"); // 恢复秘钥
        Cipher cipher = null;
        byte[] cipherBytes = null;
        try {
            cipher = Cipher.getInstance("AES"); // 对Cipher完成加密或解密工作
            cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 对Cipher初始化,加密模式
            cipherBytes = cipher.doFinal(data); // 加密数据
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return cipherBytes;
    }

    /**
     * 解密
     *
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {

        SecretKey secretKey = new SecretKeySpec(key, "AES"); // 恢复秘钥
        Cipher cipher = null;
        byte[] plainBytes = null;

        try {
            cipher = Cipher.getInstance("AES"); // 对Cipher初始化,加密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey); // 对Cipher初始化,加密模式
            plainBytes = cipher.doFinal(data); // 解密数据
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return plainBytes;
    }

}
