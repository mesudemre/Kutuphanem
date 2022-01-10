package com.mesutemre.kutuphanem.kitap.liste.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.ItemStateLoaderBinding
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent

/**
 * @Author: mesutemre.celenk
 * @Date: 12.09.2021
 */
class PagingLoadStateAdapter <T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onBindViewHolder(
        holder: NetworkStateItemViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            ItemStateLoaderBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_state_loader, parent, false)
            )
        ) {
            adapter.retry()
        }


    class NetworkStateItemViewHolder(
        private val binding: ItemStateLoaderBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(loadState: LoadState) {
            with(binding) {
                if(loadState is LoadState.Loading){
                    footerLoaderProgressBar.showComponent();
                }else{
                    footerLoaderProgressBar.hideComponent();
                }

                if(!(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()){
                    footerLoaderErrorText.showComponent();
                    footerLoaderErrorText.text = (loadState as? LoadState.Error)?.error?.message
                }else{
                    footerLoaderErrorText.hideComponent();
                }
            }
        }
    }

}