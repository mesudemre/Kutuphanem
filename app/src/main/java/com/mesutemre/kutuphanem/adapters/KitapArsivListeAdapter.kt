package com.mesutemre.kutuphanem.adapters

import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mesutemre.kutuphanem.fragments.KitapListeFragmentDirections
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.viewholder.KitapArsivListeViewHolder

/**
 * @Author: mesutemre.celenk
 * @Date: 7.09.2021
 */
class KitapArsivListeAdapter():PagingDataAdapter<KitapModel,KitapArsivListeViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object:
            DiffUtil.ItemCallback<KitapModel>(){
                override fun areItemsTheSame(oldItem: KitapModel, newItem: KitapModel) = oldItem.kitapId == newItem.kitapId;

                override fun areContentsTheSame(oldItem: KitapModel, newItem: KitapModel) = oldItem == newItem
            }
    }

    override fun onBindViewHolder(holder: KitapArsivListeViewHolder, position: Int) {
        holder.bind(getItem(position));
        holder.view.itemLayoutId.setOnClickListener {
            val kitap:KitapModel? = holder.view.kitap;
            val action = KitapListeFragmentDirections.actionKitapListeFragmentToKitapDetayFragment(kitap!!,true);
            Navigation.findNavController(holder.view.root).navigate(action);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitapArsivListeViewHolder {
        return KitapArsivListeViewHolder.create(parent);
    }
}