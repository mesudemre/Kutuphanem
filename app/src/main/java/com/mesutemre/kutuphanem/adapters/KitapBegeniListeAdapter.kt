package com.mesutemre.kutuphanem.adapters

import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mesutemre.kutuphanem.fragments.KitapListeFragmentDirections
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.viewholder.KitapListeViewHolder

/**
 * @Author: mesutemre.celenk
 * @Date: 10.09.2021
 */
class KitapBegeniListeAdapter:PagingDataAdapter<KitapModel, KitapListeViewHolder>(KitapModelDiffCallback) {

    object KitapModelDiffCallback: DiffUtil.ItemCallback<KitapModel>() {
        override fun areItemsTheSame(oldItem: KitapModel, newItem: KitapModel): Boolean {
            return oldItem.kitapId == newItem.kitapId
        }

        override fun areContentsTheSame(oldItem: KitapModel, newItem: KitapModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: KitapListeViewHolder, position: Int) {
        holder.bind(getItem(position));
        holder.view.itemLayoutId.setOnClickListener {
            val kitap:KitapModel? = holder.view.kitap;
            val action = KitapListeFragmentDirections.actionKitapListeFragmentToKitapDetayFragment(kitap!!,false);
            Navigation.findNavController(holder.view.root).navigate(action);
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KitapListeViewHolder {
        return KitapListeViewHolder.create(parent);
    }
}