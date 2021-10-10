package com.mesutemre.kutuphanem.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.model.KitapYorumModel
import com.mesutemre.kutuphanem.viewholder.KitapYorumListeHeaderViewHolder
import com.mesutemre.kutuphanem.viewholder.KitapYorumListeViewHolder

/**
 * @Author: mesutemre.celenk
 * @Date: 9.10.2021
 */
class KitapYorumListeAdapter(val yorumListe:List<KitapYorumModel>,val message:String?)
    : RecyclerView.Adapter<KitapYorumListeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitapYorumListeViewHolder {
        if(yorumListe == null || yorumListe.isEmpty()){
            return KitapYorumListeViewHolder.createErrorEmpty(parent);
        }
        return KitapYorumListeViewHolder.create(parent);
    }

    override fun onBindViewHolder(holder: KitapYorumListeViewHolder, position: Int) {
        if(yorumListe == null || yorumListe.isEmpty()){
            return holder.bindErrorAndEmpty(message!!);
        }else{
            holder.bind(yorumListe.get(position));

        }
    }

    override fun getItemCount(): Int {
        if(yorumListe != null && yorumListe.size>0){
            return yorumListe.size;
        }else{
            return 1;
        }
    }

}