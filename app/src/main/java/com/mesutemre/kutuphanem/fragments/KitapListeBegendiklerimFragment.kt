package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.adapters.KitapBegeniListeAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.customcomponents.LinearSpacingDecoration
import com.mesutemre.kutuphanem.databinding.KitapListeBegendiklerimFragmentBinding
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.viewmodels.KitapBegeniListeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @Author: mesutemre.celenk
 * @Date: 3.09.2021
 */
@AndroidEntryPoint
class KitapListeBegendiklerimFragment:BaseFragment<KitapListeBegendiklerimFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapListeBegendiklerimFragmentBinding
            = KitapListeBegendiklerimFragmentBinding::inflate;
    override val layoutName = "kitap_liste_begendiklerim_fragment.xml";

    private var adapter: KitapBegeniListeAdapter? = null;
    private val viewModel: KitapBegeniListeViewModel by viewModels();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        adapter = KitapBegeniListeAdapter();
    }

    override fun onStartFragment() {
        binding.kitapBegeniListeRw.layoutManager = LinearLayoutManager(context);
        binding.kitapBegeniListeRw.addItemDecoration(LinearSpacingDecoration(itemSpacing = 20, edgeSpacing = 30));
        initKitapBegeniListe();

        binding.kitapBegeniListeSwipeRefreshLayout.setOnRefreshListener {
            binding.kitapBegeniListeSwipeRefreshLayout.isRefreshing = false;
            adapter?.refresh();
        }
    }

    private fun initKitapBegeniListe(){
        binding.kitapBegeniListeRw.adapter = adapter;
        lifecycleScope.launch {
            viewModel.getKitapBegeniListe().collectLatest {
                adapter?.submitData(it);
            }
        }

        adapter?.addLoadStateListener { combinedLoadStates ->
            when (val loadState = combinedLoadStates.source.refresh) {
                is LoadState.NotLoading -> {
                    binding.kitapBegeniListeProgressBar.hideComponent();
                    binding.kitapBegeniListeErrorTextId.hideComponent();
                }
                is LoadState.Loading -> {
                    binding.kitapBegeniListeProgressBar.showComponent();
                    binding.kitapBegeniListeErrorTextId.hideComponent();
                }
                is LoadState.Error -> {
                    binding.kitapBegeniListeErrorTextId.showComponent();
                    binding.kitapBegeniListeProgressBar.hideComponent();
                }
            }
        }
    }
}