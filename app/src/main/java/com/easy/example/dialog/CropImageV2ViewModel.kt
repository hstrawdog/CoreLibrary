package com.easy.example.dialog

import androidx.lifecycle.MutableLiveData
import com.easy.core.ui.base.BaseViewModel
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.patelf.ui.user
 * @Date : 10:48
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class CropImageV2ViewModel : BaseViewModel() {
    var originalImage = MutableLiveData(R.mipmap.ic_crop_1_selected)

    var select_item = MutableLiveData(1)

    /**
     *
     * @param type Int 0  比例 1: 尺寸 2 选装
     *
     */
    fun onClickOperationType(type: Int) {

    }

}