package com.mesutemre.kutuphanem.kitap.liste.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ItemKitapBinding
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel

class KitapListeViewHolder(var view: ItemKitapBinding): RecyclerView.ViewHolder(view.root) {

    fun bind(model: KitapModel?){
        if(model != null){
            this.view.kitap = model;
        }
    }

    companion object {
        fun create(parent: ViewGroup): KitapListeViewHolder {
            val inflater = LayoutInflater.from(parent.context);
            val view = DataBindingUtil.inflate<ItemKitapBinding>(inflater,R.layout.item_kitap, parent, false);
            return KitapListeViewHolder(view);
        }
    }
}