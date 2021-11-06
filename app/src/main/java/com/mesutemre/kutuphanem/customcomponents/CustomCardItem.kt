package com.mesutemre.kutuphanem.customcomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.setTint
import kotlinx.android.synthetic.main.custom_card_item.view.*

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

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_card_item,this,true);

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,R.styleable.custom_card_item_attributes,0,0);

            val cardTitle = resources.getText(typedArray.getResourceId(R.styleable.custom_card_item_attributes_custom_card_title,R.string.evet));
            customCardTitleTextView.setText(cardTitle);

            val cardArrowable = typedArray.getBoolean(R.styleable.custom_card_item_attributes_custom_card_isArrowable,true);
            if(!cardArrowable){
                customCardArrowImage.hideComponent();
            }

            val titleTextColor = resources.getColor(typedArray.getResourceId(R.styleable.custom_card_item_attributes_custom_card_titleColor,R.color.transparent))
            customCardTitleTextView.setTextColor(titleTextColor);

            val arrowImageTint = resources.getColor(typedArray.getResourceId(R.styleable.custom_card_item_attributes_custom_card_arrowTint,R.color.lacivert));
            customCardArrowImage.setTint(arrowImageTint);

            val cardBackgroundColor = resources.getColor(typedArray.getResourceId(R.styleable.custom_card_item_attributes_custom_card_backgroundColor,R.color.white));
            customMaterialCardId.setCardBackgroundColor(cardBackgroundColor);

            typedArray.recycle()
        }
    }
}