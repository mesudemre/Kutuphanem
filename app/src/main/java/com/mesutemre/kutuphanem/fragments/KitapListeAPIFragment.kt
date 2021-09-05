package com.mesutemre.kutuphanem.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.adapters.KitapListeAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.customcomponents.LinearSpacingDecoration
import com.mesutemre.kutuphanem.customcomponents.SwipeToArchiveCallback
import com.mesutemre.kutuphanem.databinding.KitapListeApiFragmentBinding
import com.mesutemre.kutuphanem.model.*
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.util.showSnackBar
import com.mesutemre.kutuphanem.viewholder.KitapListeViewHolder
import com.mesutemre.kutuphanem.viewmodels.KitapListeAPIViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: mesutemre.celenk
 * @Date: 3.09.2021
 */
@AndroidEntryPoint
class KitapListeAPIFragment:BaseFragment<KitapListeApiFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapListeApiFragmentBinding
            = KitapListeApiFragmentBinding::inflate;
    override val layoutName = "kitap_liste_api_fragment.xml";
    private var adapter: KitapListeAdapter? = null;
    private val viewModel: KitapListeAPIViewModel by viewModels();
    private var sharedImageUri: Uri? = null;
    private var selectedSharedKitap: KitapModel? = null;

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

        val archiveSwipeHandler = object:SwipeToArchiveCallback(requireContext(),
            ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val kitapItemHolder:KitapListeViewHolder = viewHolder as KitapListeViewHolder;
                viewModel.kitapArsivle(kitapItemHolder.view.kitap!!,requireContext());
                adapter?.notifyItemChanged(viewHolder.adapterPosition);
                observeKitapArsiv(view!!);
                observeArsivUri(view!!);
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }

        }
        val itemTouchHelper:ItemTouchHelper = ItemTouchHelper(archiveSwipeHandler);
        itemTouchHelper.attachToRecyclerView(binding.kitapListeRw);

        val shareSwipeHandler = object:SwipeToArchiveCallback(requireContext(),
            ItemTouchHelper.RIGHT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter?.notifyItemChanged(viewHolder.adapterPosition);
                val kitapItemHolder:KitapListeViewHolder = viewHolder as KitapListeViewHolder;
                viewModel.prepareShareKitap(kitapItemHolder.view.kitap!!,requireContext());
                observeShareUri();
                selectedSharedKitap = kitapItemHolder.view.kitap!!
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }

        }
        val shareTouchHelper:ItemTouchHelper = ItemTouchHelper(shareSwipeHandler);
        shareTouchHelper.attachToRecyclerView(binding.kitapListeRw);
    }

    private fun observeArsivUri(view: View){
        viewModel.arsivUri.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled){
                it.hasBeenHandled = false;
                binding.arsivPanelLayoutId.showComponent();
                if(it.hasBeenError){
                    binding.arsivPanelLayoutId.hideComponent();
                    showSnackBar(view,view.context.getString(R.string.kitapArsivResimHata), ERROR);
                }else{
                    binding.arsivPanelLayoutId.hideComponent();
                }
            }
        });
    }

    private fun observeKitapArsiv(view:View) {
        viewModel.arsivKitap.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled) {
                it.hasBeenHandled = false;
                if(it.hasBeenError){
                    showSnackBar(view,it.peekContent(), WARNING);
                }else{
                    showSnackBar(view,it.peekContent(), SUCCESS);
                }
            }
        });

    }

    private fun observeShareUri() {
        viewModel.shareUri.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled){
                it.hasBeenHandled = false;
                if(it.hasBeenError){
                    showSnackBar(requireView(),
                        requireView().context.getString(R.string.kitapShareError),
                        ERROR);
                }else{
                    sharedImageUri = it.peekContent();
                    val shareIntent = Intent(Intent.ACTION_SEND);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.setType("image/png");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,selectedSharedKitap?.kitapAd+" "+requireContext().resources.getString(R.string.paylasimKitapAdText));
                    shareIntent.putExtra(Intent.EXTRA_STREAM, sharedImageUri);
                    resultLauncher.launch(Intent.createChooser(shareIntent, requireContext().resources.getString(R.string.shareLabel)))
                    selectedSharedKitap = null;
                }
            }
        });
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_CANCELED) {
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
        binding.kitapListeErrorTextId.setOnClickListener { viewModel.retry() };
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