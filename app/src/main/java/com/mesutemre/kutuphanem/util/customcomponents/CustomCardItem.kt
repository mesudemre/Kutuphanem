package com.mesutemre.kutuphanem.util.customcomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.CustomCardItemBinding
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.setTint

/**
 * @Author: mesutemre.celenk
 * @Date: 30.10.2021
 */
class CustomCardItem  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
):RelativeLayout(context, attrs, defStyle, defStyleRes) {

    var binding:CustomCardItemBinding

    init {
        binding = CustomCardItemBinding.inflate(LayoutInflater.from(context), this)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,R.styleable.custom_card_item_attributes,0,0);

            val cardTitle = resources.getText(typedArray.getResourceId(R.styleable.custom_card_item_attributes_custom_card_title,R.string.evet));
            binding.customCardTitleTextView.setText(cardTitle);

            val cardArrowable = typedArray.getBoolean(R.styleable.custom_card_item_attributes_custom_card_isArrowable,true);
            if(!cardArrowable){
                binding.customCardArrowImage.hideComponent();
            }

            val titleTextColor = resources.getColor(typedArray.getResourceId(R.styleable.custom_card_item_attributes_custom_card_titleColor,R.color.transparent))
            binding.customCardTitleTextView.setTextColor(titleTextColor);

            val arrowImageTint = resources.getColor(typedArray.getResourceId(R.styleable.custom_card_item_attributes_custom_card_arrowTint,R.color.lacivert));
            binding.customCardArrowImage.setTint(arrowImageTint);

            val cardBackgroundColor = resources.getColor(typedArray.getResourceId(R.styleable.custom_card_item_attributes_custom_card_backgroundColor,R.color.white));
            binding.customMaterialCardId.setCardBackgroundColor(cardBackgroundColor);

            typedArray.recycle()
        }
    }
}