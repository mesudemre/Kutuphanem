package com.mesutemre.kutuphanem.adapters

import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.fragments.KitapListeFragmentDirections
import com.mesutemre.kutuphanem.model.KitapListeState
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.viewholder.KitapListeViewHolder

class KitapListeAdapter(private val retry: () -> Unit):
    PagedListAdapter<KitapModel,  RecyclerView.ViewHolder>(KitapListeDiffCallBack) {

    private var state = KitapListeState.LOADING;

    companion object{
        val KitapListeDiffCallBack = object :DiffUtil.ItemCallback<KitapModel>(){
            override fun areItemsTheSame(oldItem: KitapModel, newItem: KitapModel): Boolean {
                return oldItem.kitapId==newItem.kitapId;
            }

            override fun areContentsTheSame(oldItem: KitapModel, newItem: KitapModel): Boolean {
                return oldItem == newItem;
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return KitapListeViewHolder.create(parent);
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as KitapListeViewHolder).bind(getItem(position));
        holder.view.itemLayoutId.setOnClickListener {
            val kitap:KitapModel? = holder.view.kitap;
            val action = KitapListeFragmentDirections.actionKitapListeFragmentToKitapDetayFragment(kitap!!,false);
            Navigation.findNavController(holder.view.root).navigate(action);
        }
    }

    fun setState(state: KitapListeState) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

}