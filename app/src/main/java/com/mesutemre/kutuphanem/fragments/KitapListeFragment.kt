package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.adapters.KitapListeAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.customcomponents.LinearSpacingDecoration
import com.mesutemre.kutuphanem.databinding.KitapListeFragmentBinding
import com.mesutemre.kutuphanem.model.KitapListeState
import com.mesutemre.kutuphanem.viewmodels.KitapListeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.kitap_liste_fragment.*

@AndroidEntryPoint
class KitapListeFragment:BaseFragment<KitapListeFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapListeFragmentBinding
            = KitapListeFragmentBinding::inflate;
    override val layoutName: String = "kitap_liste_fragment.xml";

    private var adapter:KitapListeAdapter? = null;
    private val viewModel: KitapListeViewModel by viewModels();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        binding.kitapListeRw.layoutManager = LinearLayoutManager(context);
        binding.kitapListeRw.addItemDecoration(LinearSpacingDecoration(itemSpacing = 20, edgeSpacing = 30));

        this.initAdapter();
        this.initState();

        binding.kitapListeSwipeRefreshLayout.setOnRefreshListener {
            binding.kitapListeSwipeRefreshLayout.isRefreshing = false;
            this.initAdapter();
            this.initState();
        }
    }

    private fun initAdapter(){
        adapter = KitapListeAdapter{viewModel.retry()};
        binding.kitapListeRw.adapter = adapter;
        viewModel.kitapListe.observe(viewLifecycleOwner, Observer { kitapListe->
            adapter?.submitList(kitapListe);
            adapter?.notifyDataSetChanged();
        });
    }

    private fun initState(){
        kitapListeErrorTextId.setOnClickListener { viewModel.retry() };
        viewModel.getKitapListeState().observe(viewLifecycleOwner, Observer { kitapState->
            binding.kitapListeProgressBar.visibility =
                if(viewModel.listIsEmpty() && kitapState == KitapListeState.LOADING) View.VISIBLE else View.GONE;
            binding.kitapListeErrorTextId.visibility =
                if(viewModel.listIsEmpty() && kitapState == KitapListeState.ERROR) View.VISIBLE else View.GONE;
            if(!viewModel.listIsEmpty()){
                adapter?.setState(KitapListeState.DONE);
            }
        });
    }

    override fun destroyOthers() {
        super.destroyOthers();
        adapter = null;
    }
}