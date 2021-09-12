package com.mesutemre.kutuphanem.adapters

import android.view.LayoutInflater
import android.view.View
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
        holder: PagingLoadStateAdapter.NetworkStateItemViewHolder,
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

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                if(loadState is LoadState.Loading){
                    progressBar.showComponent();
                }else{
                    progressBar.hide()
                }

                if(loadState is LoadState.Error){
                    retryButton.showComponent();
                }else{
                    retryButton.hideComponent();
                }
                if(!(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()){
                    errorMsg.showComponent();
                    errorMsg.text = (loadState as? LoadState.Error)?.error?.message
                }else{
                    errorMsg.hideComponent();
                }
            }
        }
    }

}