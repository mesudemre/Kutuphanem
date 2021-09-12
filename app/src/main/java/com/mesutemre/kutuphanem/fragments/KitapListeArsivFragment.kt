package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.adapters.KitapArsivListeAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.customcomponents.LinearSpacingDecoration
import com.mesutemre.kutuphanem.databinding.KitapListeArsivFragmentBinding
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.viewmodels.KitapArsivListeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @Author: mesutemre.celenk
 * @Date: 3.09.2021
 */
@AndroidEntryPoint
class KitapListeArsivFragment:BaseFragment<KitapListeArsivFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapListeArsivFragmentBinding
            = KitapListeArsivFragmentBinding::inflate;
    override val layoutName = "kitap_liste_arsiv_fragment.xml";

    private lateinit var adapter:KitapArsivListeAdapter;
    private val viewModel: KitapArsivListeViewModel by viewModels();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        adapter = KitapArsivListeAdapter();
        binding.kitapArsivListeRw.layoutManager = LinearLayoutManager(context);
        binding.kitapArsivListeRw.addItemDecoration(LinearSpacingDecoration(itemSpacing = 20, edgeSpacing = 30));

        viewModel.initKitapArsivListe();
        observeKitapArsivListe();

        binding.kitapArsivListeSwipeRefreshLayout.setOnRefreshListener {
            adapter?.refresh();
        }
    }

    private fun observeKitapArsivListe() {
        viewModel.kitapListe.observe(viewLifecycleOwner, Observer {base->
            if(!base.hasBeenHandled) {
                binding.kitapArsivListeRw.adapter = adapter;
                binding.kitapArsivListeProgressBar.showComponent();
                adapter.addLoadStateListener {
                    if(it.append.endOfPaginationReached){
                        if(adapter.itemCount<1){
                            binding.kitapArsivListeErrorTextId.showComponent();
                            binding.kitapArsivListeNotTextId.showComponent();
                        }else{
                            binding.kitapArsivListeErrorTextId.hideComponent();
                            binding.kitapArsivListeNotTextId.hideComponent();
                        }
                    }
                }
                viewModel.launch {
                    adapter.submitData(base.peekContent());
                }
                base.hasBeenHandled = true;
            }
            if(base.hasBeenHandled){
                binding.kitapArsivListeProgressBar.hideComponent();
            }
        });
    }
}