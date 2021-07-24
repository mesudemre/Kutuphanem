package com.mesutemre.kutuphanem.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.mesutemre.kutuphanem.databinding.ItemKitapSearchBinding
import com.mesutemre.kutuphanem.model.KitapModel

class KitapSearchResultAdapter(context: Context,
                               @LayoutRes private val layoutRes:Int,
                               val kitapListe:ArrayList<KitapModel>):ArrayAdapter<KitapModel>(context,layoutRes,kitapListe) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemKitapSearchBinding.inflate(inflater,parent,false);
        binding.kitapSearch = getItem(position);
        return binding.root;
    }

    override fun getItem(position: Int): KitapModel? {
        return kitapListe.get(position);
    }

    override fun getCount(): Int {
        return kitapListe.size;
    }

    fun updateKitapSearchListe(liste:List<KitapModel>){
        kitapListe.clear();
        kitapListe.addAll(liste);
        notifyDataSetChanged();
    }
}