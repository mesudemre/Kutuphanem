package com.mesutemre.kutuphanem.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.util.clearAndHideKeyboard
import com.mesutemre.kutuphanem.viewholder.KitapYorumListeHeaderViewHolder

/**
 * @Author: mesutemre.celenk
 * @Date: 9.10.2021
 */
class KitapYorumListeHeaderAdapter(val kullaniciResim:String,
                                   val kitapYorumKaydet:(yorum:String)->Unit )
    : RecyclerView.Adapter<KitapYorumListeHeaderViewHolder>()  {

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
        holder.bind(kullaniciResim);
        holder.view.kybKitapYorumTextInput.setEndIconOnClickListener {
            kitapYorumKaydet(holder.view.kybKitapYorumTextInput.editText?.text.toString());
            holder.view.kybKitapYorumEditText.clearAndHideKeyboard();
        }
    }

    override fun getItemCount(): Int {
        return 1;
    }
}