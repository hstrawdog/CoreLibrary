package com.hqq.example.ui.system.info

import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.NetTool
import com.hqq.core.utils.NetTool.getActiveNetworkInfo
import com.hqq.core.utils.NetTool.getNetWorkType
import com.hqq.core.utils.NetTool.getNetWorkTypeName
import com.hqq.core.utils.NetTool.getNetworkOperatorName
import com.hqq.core.utils.NetTool.is3rd
import com.hqq.core.utils.NetTool.is4G
import com.hqq.core.utils.NetTool.isAvailable
import com.hqq.core.utils.NetTool.isConnected
import com.hqq.core.utils.NetTool.isNetworkAvailable
import com.hqq.core.utils.NetTool.isWifiConnected
import com.hqq.core.utils.NetTool.isWifiEnabled
import com.hqq.core.utils.NetTool.ping
import com.hqq.core.utils.TextSpannableBuilder
import com.hqq.example.databinding.ActivityNetInfoBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.system.info
 * @Date : 14:17
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class NetInfoActivity : BaseViewBindingActivity<ActivityNetInfoBinding>() {

    override fun initView() {

        var phoneType = when (NetTool.getPhoneType(activity)) {
            0 -> "手机制式未知"
            1 -> "手机制式为GSM，移动和联通"
            2 -> "手机制式为CDMA，电信"
            3 -> "SIP"
            else -> "其他"
        }

        binding.tvInfo.setText(TextSpannableBuilder().addTextPart("包名    ")
            .addTextPart(phoneType)
            .addTextPart("\n移动网络运营商名称    ")
            .addTextPart(getNetworkOperatorName(activity))
            .addTextPart("\n活动网络信息    ")
            .addTextPart(getActiveNetworkInfo(activity).toString())
            .addTextPart("\n网络是否是4G    ")
            .addTextPart(is4G(activity).toString())
            .addTextPart("\n网络是否是3G    ")
            .addTextPart(is3rd(activity).toString())
            .addTextPart("\nWifi是否连接状态    ")
            .addTextPart(isWifiConnected(activity).toString())
            .addTextPart("\nWIFI是否打开    ")
            .addTextPart(isWifiEnabled(activity).toString())
            .addTextPart("\n是否有外网连接    ")
            .addTextPart(ping().toString())
            .addTextPart("\n判断网络是否连接    ")
            .addTextPart(isConnected(activity).toString())
            .addTextPart("\n判断网络是否可用    ")
            .addTextPart(isAvailable(activity).toString())
            .addTextPart("\n断网络连接是否可用    ")
            .addTextPart(isNetworkAvailable(activity).toString())
            .addTextPart("\n当前的网络类型    ")
            .addTextPart(getNetWorkTypeName(activity).toString())
            .addTextPart("\nnetTyped 的结果    ")
            .addTextPart(getNetWorkType(activity).toString())
            .build())
    }

}