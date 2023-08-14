package com.easy.example.dialog

import com.easy.core.ui.dialog.BaseBindingDialog
import com.easy.core.utils.ResourcesUtils
import com.easy.example.R
import com.easy.example.databinding.DialogCropImgeBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.dialog
 * @Date : 11:28
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class CropImageDialog : BaseBindingDialog<DialogCropImgeBinding>() {
    override fun initConfig() {
        rootViewBuild.rootViewImpl.iToolBarBuilder.showToolBar = true
        rootViewBuild.rootViewImpl.iToolBarBuilder.showStatusBar = true
        rootViewBuild.rootViewImpl.iToolBarBuilder.showLine = true
    }


    override fun initView() {
        rootViewBuild.rootViewImpl.iToolBar?.setToolbarTitle("12312312")



        initModel()
        initCrop()


    }

    private fun initCrop() {

        binding.tvAdjustCropOriginal.setOnClickListener {
            initCropView()
            binding.tvAdjustCropOriginal.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }
        binding.tvAdjustCropCustomize.setOnClickListener {
            initCropView()
            binding.tvAdjustCropCustomize.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }

        binding.tvAdjustCropFree.setOnClickListener {
            initCropView()
            binding.tvAdjustCropFree.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }
        binding.tvAdjustCrop11.setOnClickListener {
            initCropView()
            binding.tvAdjustCrop11.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }
        binding.tvAdjustCrop23.setOnClickListener {
            initCropView()
            binding.tvAdjustCrop23.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }

        binding.tvAdjustCrop32.setOnClickListener {
            initCropView()
            binding.tvAdjustCrop32.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }

        binding.tvAdjustCrop43.setOnClickListener {
            initCropView()
            binding.tvAdjustCrop43.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }
        binding.tvAdjustCrop916.setOnClickListener {
            initCropView()
            binding.tvAdjustCrop916.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }
        binding.tvAdjustCrop169.setOnClickListener {
            initCropView()
            binding.tvAdjustCrop169.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
            binding.tvAdjustCrop169.setCompoundDrawablesWithIntrinsicBounds(
                0, R.mipmap.ic_16_9, 0, 0
            )

        }


    }

    private fun initCropView() {
        binding.tvAdjustCrop169.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCrop169.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_16_9, 0, 0)

        binding.tvAdjustCrop916.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCrop916.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_9_16, 0, 0)

        binding.tvAdjustCrop43.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCrop43.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_4_3, 0, 0)

        binding.tvAdjustCrop32.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCrop32.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_3_2, 0, 0)

        binding.tvAdjustCrop23.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCrop23.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_2_3, 0, 0)

        binding.tvAdjustCrop11.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCrop11.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_1_1, 0, 0)

        binding.tvAdjustCropCustomize.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCropCustomize.setCompoundDrawablesWithIntrinsicBounds(
            0, R.mipmap.ic_customize, 0, 0
        )

        binding.tvAdjustCropFree.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCropFree.setCompoundDrawablesWithIntrinsicBounds(
            0, R.mipmap.ic_crop_free, 0, 0
        )

        binding.tvAdjustCropOriginal.setTextColor(ResourcesUtils.getColor(R.color.color_24))
        binding.tvAdjustCropOriginal.setCompoundDrawablesWithIntrinsicBounds(
            0, R.mipmap.ic_crop_1_selected, 0, 0
        )


    }

    private fun initModel() {
        binding.tvProportion.setOnClickListener {
            setModel()
            binding.tvProportion.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }
        binding.tvSize.setOnClickListener {
            setModel()
            binding.tvSize.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))

        }
        binding.tvRotate.setOnClickListener {
            setModel()
            binding.tvRotate.setTextColor(ResourcesUtils.getColor(R.color.color_fc4848))
        }
    }

    private fun setModel() {

        binding.tvProportion.setTextColor(ResourcesUtils.getColor(R.color.color_13))
        binding.tvSize.setTextColor(ResourcesUtils.getColor(R.color.color_13))
        binding.tvRotate.setTextColor(ResourcesUtils.getColor(R.color.color_13))
    }


}