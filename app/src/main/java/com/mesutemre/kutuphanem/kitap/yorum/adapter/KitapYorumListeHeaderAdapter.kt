package com.mesutemre.kutuphanem.kitap.yorum.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.kitap.yorum.listener.KitapYorumHeaderClickListener
import com.mesutemre.kutuphanem.kitap.yorum.viewholder.KitapYorumListeHeaderViewHolder
import com.mesutemre.kutuphanem.util.clearAndHideKeyboard

/**
 * @Author: mesutemre.celenk
 * @Date: 9.10.2021
 */
class KitapYorumListeHeaderAdapter(val kullaniciResim:String,
                                   val kitapYorumKaydet:(yorum:String)->Unit,
                                   val kitapPuanKaydet:(puan:Float)->Unit)
    : RecyclerView.Adapter<KitapYorumListeHeaderViewHolder>() , KitapYorumHeaderClickListener {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KitapYorumListeHeaderViewHolder {
        return KitapYorumListeHeaderViewHolder.create(parent);
    }

    override fun onBindViewHolder(
        holder: KitapYorumListeHeaderViewHolder,
        position: Int
    ) {
        holder.bind(kullaniciResim,this);
        holder.view.kybKitapYorumTextInput.setEndIconOnClickListener {
            kitapYorumKaydet(holder.view.kybKitapYorumTextInput.editText?.text.toString());
            holder.view.kybKitapYorumEditText.clearAndHideKeyboard();
        }
    }

    override fun getItemCount(): Int {
        return 1;
    }

    override fun kitapPuanla(view: View,puan:Float,isUser:Boolean) {
        this.kitapPuanKaydet(puan);
    }
}