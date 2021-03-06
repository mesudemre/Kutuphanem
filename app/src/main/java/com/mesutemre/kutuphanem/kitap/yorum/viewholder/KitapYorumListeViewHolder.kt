package com.mesutemre.kutuphanem.kitap.yorum.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ItemEmptyListBinding
import com.mesutemre.kutuphanem.databinding.ItemYorumKitapBinding
import com.mesutemre.kutuphanem.kitap.yorum.listener.KitapYorumClickListener
import com.mesutemre.kutuphanem.kitap.yorum.model.KitapYorumModel

/**
 * @Author: mesutemre.celenk
 * @Date: 9.10.2021
 */
class KitapYorumListeViewHolder(val view: ViewDataBinding): RecyclerView.ViewHolder(view.root) {

    fun bind(kitapYorum: KitapYorumModel, clickListener: KitapYorumClickListener){
        (this.view as ItemYorumKitapBinding).yorum = kitapYorum;
        (this.view as ItemYorumKitapBinding).listener = clickListener;
    }

    fun bindErrorAndEmpty(message:String){
        (this.view as ItemEmptyListBinding).hataText = message;
    }

    companion object{
        fun create(parent:ViewGroup): KitapYorumListeViewHolder {
            val inflater = LayoutInflater.from(parent.context);
            val view = DataBindingUtil.inflate<ItemYorumKitapBinding>(inflater,
                R.layout.item_yorum_kitap, parent, false);
            return KitapYorumListeViewHolder(view);
        }

        fun createErrorEmpty(parent:ViewGroup): KitapYorumListeViewHolder {
            val inflater = LayoutInflater.from(parent.context);
            val view = DataBindingUtil.inflate<ItemEmptyListBinding>(inflater,
                R.layout.item_empty_list, parent, false);
            return KitapYorumListeViewHolder(view);
        }
    }
}