package com.mesutemre.kutuphanem.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.KitapYorumHeaderItemBinding

/**
 * @Author: mesutemre.celenk
 * @Date: 9.10.2021
 */
class KitapYorumListeHeaderViewHolder(val view:KitapYorumHeaderItemBinding): RecyclerView.ViewHolder(view.root) {

    fun bind(kullaniciResim:String){
        this.view.kullaniciResim = kullaniciResim;
    }

    companion object{
        fun create(parent: ViewGroup): KitapYorumListeHeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context);
            val view = DataBindingUtil.inflate<KitapYorumHeaderItemBinding>(inflater,
                R.layout.kitap_yorum_header_item, parent, false);
            return KitapYorumListeHeaderViewHolder(view);
        }
    }
}