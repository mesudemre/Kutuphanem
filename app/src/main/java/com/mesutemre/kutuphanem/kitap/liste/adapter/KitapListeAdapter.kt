package com.mesutemre.kutuphanem.kitap.liste.adapter

import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.liste.ui.KitapListeFragmentDirections
import com.mesutemre.kutuphanem.kitap.liste.viewholder.KitapListeViewHolder

class KitapListeAdapter():
    PagingDataAdapter<KitapModel, KitapListeViewHolder>(KitapBegeniListeAdapter.KitapModelDiffCallback) {

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
            val kitap: KitapModel? = holder.view.kitap;
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