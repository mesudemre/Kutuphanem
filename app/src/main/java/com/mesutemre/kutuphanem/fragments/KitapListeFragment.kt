package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.adapters.KitapListeAdapter
import com.mesutemre.kutuphanem.customcomponents.LinearSpacingDecoration
import com.mesutemre.kutuphanem.databinding.KitapListeFragmentBinding
import com.mesutemre.kutuphanem.model.KitapListeState
import com.mesutemre.kutuphanem.viewmodels.KitapListeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.kitap_liste_fragment.*

@AndroidEntryPoint
class KitapListeFragment:Fragment() {

    private lateinit var adapter:KitapListeAdapter;
    private var kitapListeBinding:KitapListeFragmentBinding? = null
    private val viewModel: KitapListeViewModel by viewModels();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        kitapListeBinding = KitapListeFragmentBinding.inflate(inflater);
        return kitapListeBinding!!.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        kitapListeBinding!!.kitapListeRw.layoutManager = LinearLayoutManager(context);
        kitapListeBinding!!.kitapListeRw.addItemDecoration(LinearSpacingDecoration(itemSpacing = 20, edgeSpacing = 30))

        this.initAdapter();
        this.initState();

        kitapListeBinding!!.kitapListeSwipeRefreshLayout.setOnRefreshListener {
            kitapListeBinding!!.kitapListeSwipeRefreshLayout.isRefreshing = false;
            this.initAdapter();
            this.initState();
        }
    }

    private fun initAdapter(){
        adapter = KitapListeAdapter{viewModel.retry()};
        kitapListeBinding!!.kitapListeRw.adapter = adapter;
        viewModel.kitapListe.observe(viewLifecycleOwner, Observer { kitapListe->
            adapter.submitList(kitapListe);
            adapter.notifyDataSetChanged();
        });
    }

    private fun initState(){
        kitapListeErrorTextId.setOnClickListener { viewModel.retry() };
        viewModel.getKitapListeState().observe(viewLifecycleOwner, Observer { kitapState->
            kitapListeBinding!!.kitapListeProgressBar.visibility =
                   if(viewModel.listIsEmpty() && kitapState == KitapListeState.LOADING) View.VISIBLE else View.GONE;
            kitapListeBinding!!.kitapListeErrorTextId.visibility =
                    if(viewModel.listIsEmpty() && kitapState == KitapListeState.ERROR) View.VISIBLE else View.GONE;
            if(!viewModel.listIsEmpty()){
                adapter.setState(KitapListeState.DONE);
            }
        });
    }

    override fun onDestroyView() {
        super.onDestroyView();
        kitapListeBinding = null;
    }
}