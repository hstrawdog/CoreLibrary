package com.hqq.core.utils

import java.lang.Exception
import java.util.regex.Pattern

/**
 * @Author : huangqiqiang
 * @Package : com..common.utils
 * @FileName :   RegexUtils
 * @Date : 2018/12/24 0024  下午 4:53
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  验证工具类 包含空判断 正则判断
 */
object RegexUtils {


    /**
     * 检查 null
     * 支持类型
     * String
     * List  有待 验证
     *
     * @param any Object
     * @return boolean
     */
    fun checkIsNull(any: Any?): Boolean {
        return when (any) {
            null -> {
                true
            }
            is String -> {
                any.isEmpty()
            }
            is List<*> -> {
                any.isEmpty()
            }
            is Array<*> -> {
                any.isEmpty()
            }
            is Map<*, *> -> {
                any.isEmpty()
            }
            else -> false
        }
    }

    /**
     * 非空判断
     * @param any Any?
     * @return Boolean
     */
    fun checkUnNull(any: Any?): Boolean {
        return !checkIsNull(any)
    }

    /**
     *  两个都不为空
     * @param value1 T1?
     * @param value2 T2?
     * @param bothNotNull Function2<T1, T2, Unit>
     */
    fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
        if (value1 != null && value2 != null) {
            bothNotNull(value1, value2)
        }
    }

    //region String  相关
    /**
     * 判断string是否是空的   过滤null
     *
     * @param str
     * @return
     */
    fun checkStringIsNull(str: String?): Boolean {
        if (checkIsNull(str)) {
            return true
        } else if ("null" == str) {
            return true
        }
        return false
    }

    /**
     *  判断String  不为空
     * @param str String?
     * @return Boolean
     */
    fun checkStringUnEmpty(str: String?): Boolean {
        return !checkStringIsNull(str)
    }


    /**
     *  判断String 是否是空的 并给出提示
     * @param str String?
     * @param tip String
     * @return Boolean
     */
    fun checkStringIsNullAndShowHint(str: String?, hint: String): Boolean {
        if (checkIsNull(str)) {
            ToastUtils.showToast(hint)
            return true
        }
        return false
    }
    //endregion判断


    /**
     * 判断手机号码
     *
     * @param contactPhone
     * @return
     */
    fun checkPhone(contactPhone: String): Boolean {
        if (checkIsNull(contactPhone)) {
            ToastUtils.showToast("请输入手机号码")
            return true
        } else if (contactPhone.trim { it <= ' ' }.length != 11) {
            ToastUtils.showToast("请输入正确的手机号码")
            return true
        }
        return false
    }

    /**
     *  判断是否是手机号码
     * @param phone String
     * @return Boolean
     */
    fun isPhone(phone: String): Boolean {
        val regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$"
        return if (checkIsNull(phone)) {
            ToastUtils.showToast("请输入手机号码")
            false
        } else if (phone.length != 11) {
            ToastUtils.showToast("请输入正确的手机号码")
            true
        } else {
            val p = Pattern.compile(regex)
            val m = p.matcher(phone)
            val isMatch = m.matches()
            if (!isMatch) {
                ToastUtils.showToast("请输入正确的手机号码")
            }
            !isMatch
        }
    }

    /**
     * 判断姓名
     *
     * @param name
     * @param activity
     * @return
     */
    fun checkName(name: String?): Boolean {
        if (checkIsNull(name)) {
            ToastUtils.showToast("请输入姓名")
            return true
        }
        return false
    }

    /**
     * 检查 是否是中文 英文  与数字
     *
     * @param sellerName
     * @return
     */
    fun checkWhetherChineseAndNum(sellerName: String?): Boolean {
        val chat = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$"
        return Pattern.matches(chat, sellerName)
    }

    /**
     * 检查 是否是中文 英文
     *
     * @param sellerName
     * @return
     */
    fun checkWhetherChinese(sellerName: String?): Boolean {
        val chat = "^[\\u4e00-\\u9fa5_a-zA-Z]+$"
        return Pattern.matches(chat, sellerName)
    }

    /**
     * 检查 emoji
     *
     * @param sellerName
     * @return
     */
    fun checkEmoji(sellerName: String?): Boolean {
        val checkEmoji =
            "(?:[\\uD83C\\uDF00-\\uD83D\\uDDFF]|[\\uD83E\\uDD00-\\uD83E\\uDDFF]|[\\uD83D\\uDE00-\\uD83D\\uDE4F]|[\\uD83D\\uDE80-\\uD83D\\uDEFF]|[\\u2600-\\u26FF]\\uFE0F?|[\\u2700-\\u27BF]\\uFE0F?|\\u24C2\\uFE0F?|[\\uD83C\\uDDE6-\\uD83C\\uDDFF]{1,2}|[\\uD83C\\uDD70\\uD83C\\uDD71\\uD83C\\uDD7E\\uD83C\\uDD7F\\uD83C\\uDD8E\\uD83C\\uDD91-\\uD83C\\uDD9A]\\uFE0F?|[\\u0023\\u002A\\u0030-\\u0039]\\uFE0F?\\u20E3|[\\u2194-\\u2199\\u21A9-\\u21AA]\\uFE0F?|[\\u2B05-\\u2B07\\u2B1B\\u2B1C\\u2B50\\u2B55]\\uFE0F?|[\\u2934\\u2935]\\uFE0F?|[\\u3030\\u303D]\\uFE0F?|[\\u3297\\u3299]\\uFE0F?|[\\uD83C\\uDE01\\uD83C\\uDE02\\uD83C\\uDE1A\\uD83C\\uDE2F\\uD83C\\uDE32-\\uD83C\\uDE3A\\uD83C\\uDE50\\uD83C\\uDE51]\\uFE0F?|[\\u203C\\u2049]\\uFE0F?|[\\u25AA\\u25AB\\u25B6\\u25C0\\u25FB-\\u25FE]\\uFE0F?|[\\u00A9\\u00AE]\\uFE0F?|[\\u2122\\u2139]\\uFE0F?|\\uD83C\\uDC04\\uFE0F?|\\uD83C\\uDCCF\\uFE0F?|[\\u231A\\u231B\\u2328\\u23CF\\u23E9-\\u23F3\\u23F8-\\u23FA]\\uFE0F?)"
        return Pattern.matches(checkEmoji, sellerName)
    }

    /**
     * 检查 身份证号码
     *
     * @param idCardNum
     * @return
     */
    fun checkIdCard(idCardNum: String?): Boolean {
        val chat = "^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$"
        return Pattern.matches(chat, idCardNum)
    }

    /**
     * 校验银行卡号
     *
     * @param cardId
     * @return
     */
    fun checkBankNum(cardId: String): Boolean {
        return if (checkIsNull(cardId)) {
            false
        } else cardId.matches(Regex("^([0-9]{15}|[0-9]{16}|[0-9]{17}|[0-9]{18}|[0-9]{19})$"))
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    fun getBankCardCheckCode(nonCheckCodeCardId: String): Char {


        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim { it <= ' ' }.length == 0 || !nonCheckCodeCardId.matches(Regex("\\d+"))) {
            //如果传的不是数据返回N
            return 'N'
        }
        val chs = nonCheckCodeCardId.trim { it <= ' ' }.toCharArray()
        var luhmSum = 0
        var i = chs.size - 1
        var j = 0
        while (i >= 0) {
            var k = chs[i] - '0'
            if (j % 2 == 0) {
                k *= 2
                k = k / 10 + k % 10
            }
            luhmSum += k
            i--
            j++
        }
        return if (luhmSum % 10 == 0) '0' else (10 - luhmSum % 10 + '0'.toInt()).toChar()
    }


    /**
     * 替换空格 等
     *
     * @param str
     * @return
     */
    fun replaceBlank(str: String?): String {
        var dest = ""
        if (str != null) {
            val chat = "\\s*|\t|\r|\n"
            val m = Pattern.compile(chat).matcher(str)
            dest = m.replaceAll("")
        }
        return dest
    }

    fun isNumeric(str: String): Boolean {
        for (i in 0 until str.length) {
            if (str.indexOf(str[i]) == -1) {
                return false
            }
        }
        return true
    }

    /**
     * 格式化 html  替换字符串中图片大小
     *
     * @param html
     * @return
     */
    fun htmlFormatImg(html: String): String {
        return if (checkIsNull(html)) {
            ""
        } else html.replace("<img", "<img style='max-width:100%;height:auto;'")
    }

    /**
     * 截取 正则中的字符串 取1
     *
     * @param regex
     * @param source
     * @return
     */
    fun getMatcher(regex: String?, source: String?): String {
        var result = ""
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(source)
        while (matcher.find()) {
            result = matcher.group(1)
        }
        return result
    }


    /**
     * 格式化数据  ***
     *
     * @param userName
     * @return
     */
    fun format2xxx(userName: String): String {
        if (checkUnNull(userName)) {
            val stringBuilder = StringBuilder(userName.substring(0, 1))
            stringBuilder.append("***")
            stringBuilder.append(userName.substring(userName.length - 1, userName.length))
            return stringBuilder.toString()
        }
        return "***"
    }


    fun isZero(string: String?): Boolean {
        if (RegexUtils.checkStringUnEmpty(string)) {
            try {
                if (java.lang.Double.valueOf(string) == 0.0) {
                    return true
                }
            } catch (e: Exception) {
            }
            return false
        }
        return true
    }


    @JvmStatic
    fun main(args: Array<String>) {

//        println("null: " + checkIsNull(null))
//
//        println("String: " + checkIsNull(""))
//        println("String:123  " + checkIsNull("123"))
//        var str: String? = null
//        println("str: " + checkIsNull(str))
//
//        ArrayList<String>().isNullOrEmpty()
//
//        var list: ArrayList<String>? = null
//        println("list: " + checkIsNull(list))
//        println("ArrayList<String>: " + checkIsNull(ArrayList<String>()))
//        var map: HashMap<String, String>? = null
//        println("map: " + checkIsNull(map))
//        println("HashMap<String, String>: " + checkIsNull(HashMap<String, String>()))


//        println("" + checkBankNum("6227001823770993846"))
//        println("" + checkBankNum("6221386102180111123"))
//        println("" + checkBankNum("6222600260001072321"))

        var list = ArrayList<String>()
//        println(list.indexOf(null))

        var map = HashMap<String, String>()

        println(checkUnNull(map))


    }
}