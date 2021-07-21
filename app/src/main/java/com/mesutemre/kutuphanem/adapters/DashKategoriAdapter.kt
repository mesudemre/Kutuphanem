package com.mesutemre.kutuphanem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ItemDashKategoriBinding
import com.mesutemre.kutuphanem.model.KitapturModel

class DashKategoriAdapter(val kitapTurListe:ArrayList<KitapturModel>) :RecyclerView.Adapter<DashKategoriAdapter.KitapKategoriViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KitapKategoriViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val view = DataBindingUtil.inflate<ItemDashKategoriBinding>(inflater,R.layout.item_dash_kategori,parent,false);
        return KitapKategoriViewHolder(view);
    }

    override fun getItemCount(): Int {
        return kitapTurListe.size;
    }

    override fun onBindViewHolder(holder: KitapKategoriViewHolder, position: Int) {
        holder.view.kategori = kitapTurListe.get(position);
    }

    fun updateKategorListe(liste:List<KitapturModel>){
        kitapTurListe.clear();
        kitapTurListe.addAll(liste);
        notifyDataSetChanged();
    }

    class KitapKategoriViewHolder(var view: ItemDashKategoriBinding): RecyclerView.ViewHolder(view.root) {
    }
}