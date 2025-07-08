package com.easy.example.ui;
import android.util.Base64;
import com.easy.core.utils.log.LogUtils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public class EncryptUtil {
    private static String TAG = "EncryptUtil";
    private static String messageKey="UfrsbHzQ&kCBP%gY";

    public static String encrypt2aes(String str, String str2) {
        try {
            return Base64.encodeToString(aesEncrypt(str, str2), 2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encrypt2aes(String str) {
        return encrypt2aes(str,messageKey);
    }

    public static byte[] aesEncrypt(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            byte[] bytes = str.getBytes("UTF-8");
            cipher.init(1, secretKeySpec);
            return cipher.doFinal(bytes);
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] aesDecrypt(byte[] bArr, String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(2, secretKeySpec);
            return cipher.doFinal(bArr);
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String aesDecrypt(String str) {
        return aesDecrypt(str, "UfrsbHzQ&kCBP%gY");
    }

    public static String aesDecryptForMessageForDebug(String str) {
        String strAesDecrypt = aesDecrypt(str);
        return strAesDecrypt;
    }

    public static String aesDecrypt(String str, String str2) {
        if (str.startsWith("<")) {
            return str;
        }
        try {
            byte[] bArrAesDecrypt = aesDecrypt(Base64.decode(str, 2), str2);
            if (bArrAesDecrypt == null) {
                return str;
            }
            return new String(bArrAesDecrypt, "UTF-8");
        } catch (Exception e) {
            LogUtils.e(TAG, "aesDecrypt: bad Base64" + str);
            e.printStackTrace();
            return str;
        }
    }



}