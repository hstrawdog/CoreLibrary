package com.hqq.core.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : huangqiqiang
 * @Package : com..common.utils
 * @FileName :   RegexUtils
 * @Date : 2018/12/24 0024  下午 4:53
 * @Descrive :  验证工具类 包含空判断 正则判断
 * @Email :  qiqiang213@gmail.com
 */
public class RegexUtils {

    /**
     * 检查 null
     * 支持类型
     * String
     * List  有待 验证
     *
     * @param object Object
     * @return boolean
     */
    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return TextUtils.isEmpty(((String) object));
        } else if (object instanceof List) {
            return ((List) object).isEmpty();
        } else if (object instanceof String[]) {
            return ((String[]) object).length <= 0;
        }
//        else if (object.getClass().isArray()) {
//            if (((String[]) object).length <= 0) {
//                return true;
//            }
//        }
        return false;
    }
    /**
     * 检查 null
     * 支持类型
     * String
     * List  有待 验证
     *
     * @param object Object
     * @return boolean
     */
    public static boolean checkNull(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return TextUtils.isEmpty(((String) object));
            // return ((String) object).isEmpty();
        } else if (object instanceof List) {
            return ((List) object).isEmpty();
        } else if (object instanceof String[]) {
            return ((String[]) object).length <= 0;
        }
        return false;
    }
    /**
     * 非空判断
     *
     * @param object
     * @return
     */
    public static boolean unNull(Object object) {
        return !isNull(object);
    }

    /**
     * 判断string是否是空的   过滤null
     *
     * @param str
     * @return
     */
    public static boolean stringIsNull(String str) {
        if (isNull(str)) {
            return true;
        } else if ("null".equals(str)) {
            return true;
        }
        return false;


    }

    /**
     * 判断手机号码
     *
     * @param contactPhone
     * @return
     */
    public static boolean checkPhone(String contactPhone) {
        if (RegexUtils.isNull(contactPhone)) {
            ToastUtils.showToast("请输入手机号码");
            return true;
        } else if (contactPhone.trim().length() != 11) {
            ToastUtils.showToast("请输入正确的手机号码");
            return true;
        }
        return false;
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (RegexUtils.isNull(phone)) {
            ToastUtils.showToast("请输入手机号码");
            return false;
        } else if (phone.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号码");
            return true;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                ToastUtils.showToast("请输入正确的手机号码");
            }
            return !isMatch;
        }
    }

    /**
     * 判断姓名
     *
     * @param name
     * @param activity
     * @return
     */
    public static boolean checkName(String name, Activity activity) {
        if (RegexUtils.isNull(name)) {
            ToastUtils.showToast("请输入姓名");
            return true;
        }

        return false;
    }

    /**
     * 检查 是否是中文 英文  与数字
     *
     * @param sellerName
     * @return
     */
    public static boolean checkWhetherChineseAndNum(String sellerName) {
        String chat = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$";
        return Pattern.matches(chat, sellerName);

    }

    /**
     * 检查 是否是中文 英文
     *
     * @param sellerName
     * @return
     */
    public static boolean checkWhetherChinese(String sellerName) {
        String chat = "^[\\u4e00-\\u9fa5_a-zA-Z]+$";
        return Pattern.matches(chat, sellerName);

    }

    /**
     * 检查 emoji
     *
     * @param sellerName
     * @return
     */
    public static boolean checkEmoji(String sellerName) {
        return true;
        //        String checkEmoji = "(?:[\\uD83C\\uDF00-\\uD83D\\uDDFF]|[\\uD83E\\uDD00-\\uD83E\\uDDFF]|[\\uD83D\\uDE00-\\uD83D\\uDE4F]|[\\uD83D\\uDE80-\\uD83D\\uDEFF]|[\\u2600-\\u26FF]\\uFE0F?|[\\u2700-\\u27BF]\\uFE0F?|\\u24C2\\uFE0F?|[\\uD83C\\uDDE6-\\uD83C\\uDDFF]{1,2}|[\\uD83C\\uDD70\\uD83C\\uDD71\\uD83C\\uDD7E\\uD83C\\uDD7F\\uD83C\\uDD8E\\uD83C\\uDD91-\\uD83C\\uDD9A]\\uFE0F?|[\\u0023\\u002A\\u0030-\\u0039]\\uFE0F?\\u20E3|[\\u2194-\\u2199\\u21A9-\\u21AA]\\uFE0F?|[\\u2B05-\\u2B07\\u2B1B\\u2B1C\\u2B50\\u2B55]\\uFE0F?|[\\u2934\\u2935]\\uFE0F?|[\\u3030\\u303D]\\uFE0F?|[\\u3297\\u3299]\\uFE0F?|[\\uD83C\\uDE01\\uD83C\\uDE02\\uD83C\\uDE1A\\uD83C\\uDE2F\\uD83C\\uDE32-\\uD83C\\uDE3A\\uD83C\\uDE50\\uD83C\\uDE51]\\uFE0F?|[\\u203C\\u2049]\\uFE0F?|[\\u25AA\\u25AB\\u25B6\\u25C0\\u25FB-\\u25FE]\\uFE0F?|[\\u00A9\\u00AE]\\uFE0F?|[\\u2122\\u2139]\\uFE0F?|\\uD83C\\uDC04\\uFE0F?|\\uD83C\\uDCCF\\uFE0F?|[\\u231A\\u231B\\u2328\\u23CF\\u23E9-\\u23F3\\u23F8-\\u23FA]\\uFE0F?)" ;
//        return Pattern.matches(checkEmoji, sellerName);
    }

    /**
     * 检查 身份证号码
     *
     * @param idCardNum
     * @return
     */
    public static boolean checkIdCard(String idCardNum) {
        String chat = "^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
        return Pattern.matches(chat, idCardNum);
    }

    /**
     * 校验银行卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankNum(String cardId) {
        if (RegexUtils.checkNull(cardId)) {
            return false;
        }
        return cardId.matches("^([0-9]{15}|[0-9]{16}|[0-9]{17}|[0-9]{18}|[0-9]{19})$");
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 收货地址判断
     *
     * @param address
     * @return
     */
    public static boolean checkAddress(String address) {
        if (isNull(address)) {
            ToastUtils.showToast("收货地址不能为空");

        }
        if (!checkEmoji(address)) {
            ToastUtils.showToast("地址不支持emoji表情");
            return true;
        }
        return false;
    }

    /**
     * 替换空格 等
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            String chat = "\\s*|\t|\r|\n";
            Matcher m = Pattern.compile(chat).matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.indexOf(str.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 格式化 html  替换字符串中图片大小
     *
     * @param html
     * @return
     */
    public static String htmlFormatImg(String html) {
        if (RegexUtils.isNull(html)) {
            return "";
        }
        return html.replace("<img", "<img style='max-width:100%;height:auto;'");
    }

    /**
     * 截取 正则中的字符串 取1
     *
     * @param regex
     * @param source
     * @return
     */
    public static String getMatcher(String regex, String source) {
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }


    /**
     * 格式化数据  ***
     *
     * @param userName
     * @return
     */
    public static String format2xxx(String userName) {
        if (unNull(userName)) {
            StringBuilder stringBuilder = new StringBuilder(userName.substring(0, 1));
            stringBuilder.append("***");
            stringBuilder.append(userName.substring(userName.length() - 1, userName.length()));

            return stringBuilder.toString();
        }

        return "***";
    }

    public static void main(String[] args) {



        System.out.println("" + checkBankNum("6227001823770993846"));
        System.out.println("" + checkBankNum("6221386102180111123"));
        System.out.println("" + checkBankNum("6222600260001072321"));



    }


}
