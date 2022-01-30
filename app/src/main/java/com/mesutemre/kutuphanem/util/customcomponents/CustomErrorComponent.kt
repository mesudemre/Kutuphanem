package com.mesutemre.kutuphanem.util.customcomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ComponentCustomErrorBinding

/**
 * @Author: mesutemre.celenk
 * @Date: 24.01.2022
 */
class CustomErrorComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
): RelativeLayout(context, attrs, defStyle, defStyleRes) {


    var binding: ComponentCustomErrorBinding

    init {
        binding = ComponentCustomErrorBinding.inflate(LayoutInflater.from(context), this)
        binding.customeErrorImageView.animate().apply {
            duration = 1000
            alpha(.5f)
            scaleXBy(.5f)
            scaleYBy(.5f)
            rotationYBy(360f)
            translationYBy(200f)
        }.withEndAction {
            binding.customeErrorImageView.animate().apply {
                duration = 1000
                alpha(1f)
                scaleXBy(-.5f)
                scaleYBy(-.5f)
                rotationYBy(360f)
                translationYBy(-200f)
            }
        }.start()
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                R.styleable.custom_error_component_attributes,0,0)

            binding.customErrorTextId.apply {
                text = typedArray.getText(R.styleable.custom_error_component_attributes_custom_error_message)
            }
            typedArray.recycle()
        }
    }
}