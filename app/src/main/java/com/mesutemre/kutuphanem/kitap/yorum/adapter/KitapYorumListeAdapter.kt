package com.mesutemre.kutuphanem.kitap.yorum.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.kitap.yorum.listener.KitapYorumClickListener
import com.mesutemre.kutuphanem.kitap.yorum.model.KitapYorumModel
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.kitap.yorum.viewholder.KitapYorumListeViewHolder

/**
 * @Author: mesutemre.celenk
 * @Date: 9.10.2021
 */
class KitapYorumListeAdapter(val yorumListe:List<KitapYorumModel>, val message:String?)
    : RecyclerView.Adapter<KitapYorumListeViewHolder>(), KitapYorumClickListener {

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
            holder.bind(yorumListe.get(position),this);
        }
    }

    override fun getItemCount(): Int {
        if(yorumListe != null && yorumListe.size>0){
            return yorumListe.size;
        }else{
            return 1;
        }
    }

    override fun yorumunHepsiniGoster(view: View, kismiText: View, fullTextView: View) {
        view.hideComponent();
        kismiText.hideComponent();
        fullTextView.showComponent();
    }
}