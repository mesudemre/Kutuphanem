package com.mesutemre.kutuphanem.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ItemArsivKitapBinding
import com.mesutemre.kutuphanem.model.KitapModel

/**
 * @Author: mesutemre.celenk
 * @Date: 7.09.2021
 */
class KitapArsivListeViewHolder(var view: ItemArsivKitapBinding): RecyclerView.ViewHolder(view.root) {

    fun bind(model: KitapModel?){
        if(model != null){
            this.view.kitap = model;
        }
    }

    companion object {
        fun create(parent: ViewGroup): KitapArsivListeViewHolder {
            val inflater = LayoutInflater.from(parent.context);
            val view = DataBindingUtil.inflate<ItemArsivKitapBinding>(inflater,
                R.layout.item_arsiv_kitap, parent, false);
            return KitapArsivListeViewHolder(view);
        }
    }
}