package com.hqq.example.ui.view

import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.compose.ui.graphics.Color
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.ResourcesUtils
import com.hqq.core.utils.ToastUtils
import com.hqq.core.utils.text.TextSpannableBuilder
import com.hqq.example.R
import com.hqq.example.databinding.ActivityTextViewBinding
import com.hqq.example.utils.ColorUtil

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.view
 * @FileName :   TextViewActivity
 * @Date : 2020/6/9 0009  下午 3:34
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class TextViewActivity : BaseViewBindingActivity<ActivityTextViewBinding>() {

    var _textSpannable = TextSpannableBuilder()


    override fun initView() {
        binding.textView10.text = _textSpannable.build()

        binding.button89.setOnClickListener {
            _textSpannable.addTextPart("添加文字")
            binding.textView10.text = _textSpannable.build()
        }

        binding.button90.setOnClickListener {
            _textSpannable.addTextPart("添加文字颜色", ResourcesUtils.getColor(R.color.color_main))
            binding.textView10.text = _textSpannable.build()
        }

        binding.button91.setOnClickListener {
            _textSpannable.addTextPart("添加文字颜色大小", ResourcesUtils.getColor(R.color.color_red), 1.2f, BackgroundColorSpan(ResourcesUtils.getColor(R.color.color_main)), object :
                TextSpannableBuilder.OnClickListener {
                override fun onClick(widget: View?, clickedText: CharSequence?) {
                    ToastUtils.showToast("12312312")
                }

            })
            binding.button91.highlightColor = ResourcesUtils.getColor(R.color.color_757575)
//            _textSpannable.addTextClick("123", ResourcesUtils.getColor(R.color.black),object  :
//                TextSpannableBuilder.OnClickListener{
//                override fun onClick(widget: View?, clickedText: CharSequence?) {
//                    ToastUtils.showToast("12312312")
//                }
//            }
//            )
            binding.textView10.text = _textSpannable.build()
            binding.textView10.setMovementMethod(LinkMovementMethod.getInstance())
        }



        binding.button92.setOnClickListener {

            _textSpannable.addTextPart("添加文字颜色大小", ResourcesUtils.getColor(R.color.color_red), 1.2f, null, object :
                TextSpannableBuilder.OnClickListener {
                override fun onClick(widget: View?, clickedText: CharSequence?) {
                    ToastUtils.showToast("12312312")
                }
            })
//            binding.button91.highlightColor = ResourcesUtils.getColor(R.color.color_757575)
            binding.textView10.setHighlightColor(ResourcesUtils.getColor(R.color.transparent))
            binding.textView10.text = _textSpannable.build()
            binding.textView10.setMovementMethod(LinkMovementMethod.getInstance())

        }

    }


}