package com.mesutemre.kutuphanem.kitap.liste.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.KitapListeFragmentBinding
import com.mesutemre.kutuphanem.kitap.liste.adapter.KitapArsivListeAdapter
import com.mesutemre.kutuphanem.kitap.liste.adapter.KitapBegeniListeAdapter
import com.mesutemre.kutuphanem.kitap.liste.adapter.KitapListeAdapter
import com.mesutemre.kutuphanem.kitap.liste.adapter.PagingLoadStateAdapter
import com.mesutemre.kutuphanem.kitap.liste.model.API_LISTE
import com.mesutemre.kutuphanem.kitap.liste.model.ARSIV
import com.mesutemre.kutuphanem.kitap.liste.model.BEGENILENLER
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.liste.viewholder.KitapListeViewHolder
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.model.WARNING
import com.mesutemre.kutuphanem.util.*
import com.mesutemre.kutuphanem.util.customcomponents.LinearSpacingDecoration
import com.mesutemre.kutuphanem.util.customcomponents.SwipeToArchiveCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KitapListeFragment:BaseFragment<KitapListeFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapListeFragmentBinding
            = KitapListeFragmentBinding::inflate;
    override val layoutName: String = "kitap_liste_fragment.xml";

    private var listeAdapter: KitapListeAdapter? = null;
    private var arsivAdapter: KitapArsivListeAdapter? = null;
    private var begeniAdapter: KitapBegeniListeAdapter? = null;

    private val viewModel: KitapListeViewModel by viewModels();
    private var selectedType = API_LISTE;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        binding.kitapListeRw.layoutManager = LinearLayoutManager(context);
        binding.kitapListeRw.addItemDecoration(LinearSpacingDecoration(itemSpacing = 20, edgeSpacing = 30));

        setSelectPropertiesForCheckedChip(binding.chipListeApi,true);

        when(selectedType) {
            API_LISTE -> {
                initKitapAPIListe()
            }
            ARSIV -> {
                initKitapArsivListe();
            }
            BEGENILENLER -> {
                initKitapBegenilenListe();
            }
        }

        binding.kitapListeSwipeRefreshLayout.setOnRefreshListener {
            binding.kitapListeSwipeRefreshLayout.isRefreshing = false;
            when(selectedType) {
                API_LISTE -> {
                    listeAdapter?.refresh();
                }
                ARSIV -> {
                    arsivAdapter?.refresh();
                }
                BEGENILENLER -> {
                    begeniAdapter?.refresh();
                }
            }
        }

        addSwipeEvents();

        binding.kitapListeButtonGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.chipListeApi->{
                    setSelectPropertiesForCheckedChip(binding.chipListeApi,true);
                    setSelectPropertiesForCheckedChip(binding.chipListeArsiv,false);
                    setSelectPropertiesForCheckedChip(binding.chipListeBegendiklerim,false);
                    selectedType = API_LISTE;
                    addSwipeEvents();
                    initKitapAPIListe();
                }
                R.id.chipListeArsiv->{
                    setSelectPropertiesForCheckedChip(binding.chipListeApi,false);
                    setSelectPropertiesForCheckedChip(binding.chipListeArsiv,true);
                    setSelectPropertiesForCheckedChip(binding.chipListeBegendiklerim,false);
                    selectedType = ARSIV;
                    addSwipeEvents();
                    initKitapArsivListe();
                }
                R.id.chipListeBegendiklerim->{
                    setSelectPropertiesForCheckedChip(binding.chipListeApi,false);
                    setSelectPropertiesForCheckedChip(binding.chipListeArsiv,false);
                    setSelectPropertiesForCheckedChip(binding.chipListeBegendiklerim,true);
                    selectedType = BEGENILENLER;
                    addSwipeEvents();
                    initKitapBegenilenListe();
                }
            }
        }
    }

    private fun initKitapAPIListe() {
        listeAdapter = KitapListeAdapter();
        binding.kitapListeRw.adapter = listeAdapter;
        lifecycleScope.launch {
            viewModel.getKitapListe().collectLatest {
                listeAdapter?.submitData(it);
            }
        }

        listeAdapter?.withLoadStateFooter(
            footer = PagingLoadStateAdapter(listeAdapter!!)
        )
        listeAdapter?.addLoadStateListener { combinedLoadStates ->
            val errorState =
                when {
                    combinedLoadStates.append is LoadState.Error -> combinedLoadStates.append as LoadState.Error
                    combinedLoadStates.prepend is LoadState.Error ->  combinedLoadStates.prepend as LoadState.Error
                    combinedLoadStates.refresh is LoadState.Error -> combinedLoadStates.refresh as LoadState.Error
                    else -> null
                }

            when (val loadState = combinedLoadStates.source.refresh) {
                is LoadState.NotLoading -> {
                    getFragmentView().hideComponents(binding.kitapListeProgressBar,binding.kitapListeErrorTextId,binding.kitapArsivListeErrorTextId,binding.kitapArsivListeNotTextId);
                    binding.kitapListeRw.showComponent();
                }
                is LoadState.Loading -> {
                    binding.kitapListeProgressBar.showComponent();
                    getFragmentView().hideComponents(binding.kitapArsivListeErrorTextId,binding.kitapArsivListeNotTextId,binding.kitapListeErrorTextId)
                }
                is LoadState.Error -> {
                    binding.kitapListeRw.hideComponent();
                    binding.kitapListeProgressBar.hideComponent();

                    errorState?.let{
                        binding.kitapListeErrorTextId.text = it.error.localizedMessage;
                        binding.kitapListeErrorTextId.showComponent();
                    }
                }
            }
        }
    }

    private fun addSwipeEvents() {
        val archiveSwipeHandler = object: SwipeToArchiveCallback(requireContext(),
            ItemTouchHelper.LEFT){

            override fun isItemViewSwipeEnabled(): Boolean {
                return selectedType == API_LISTE
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val kitapItemHolder: KitapListeViewHolder = viewHolder as KitapListeViewHolder;
                listeAdapter?.notifyItemChanged(viewHolder.adapterPosition);
                viewModel.kitapArsivlenmisMi(kitapItemHolder.view.kitap!!.kitapId!!);
                observeKitapArsiv(kitapItemHolder.view.kitap!!);
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }

        }
        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(archiveSwipeHandler);
        itemTouchHelper.attachToRecyclerView(binding.kitapListeRw);

        val shareSwipeHandler = object: SwipeToArchiveCallback(requireContext(),
            ItemTouchHelper.RIGHT){

            override fun isItemViewSwipeEnabled(): Boolean {
                return selectedType == API_LISTE
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listeAdapter?.notifyItemChanged(viewHolder.adapterPosition);
                val kitapItemHolder: KitapListeViewHolder = viewHolder as KitapListeViewHolder;
                viewModel.shareKitap(kitapItemHolder.view.kitap!!);
                observeShareKitap(kitapItemHolder.view.kitap!!);
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }
        }

        val shareTouchHelper: ItemTouchHelper = ItemTouchHelper(shareSwipeHandler);
        shareTouchHelper.attachToRecyclerView(binding.kitapListeRw);
    }

    private fun initKitapArsivListe() {
        arsivAdapter = KitapArsivListeAdapter();
        binding.kitapListeRw.adapter = arsivAdapter;
        viewModel.initKitapArsivListe();
        observeKitapArsivListe();
    }

    private fun observeKitapArsivListe() {
        viewModel.kitapArsivListeBaseResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapListeProgressBar.showComponent();
                    getFragmentView().hideComponents(binding.kitapListeErrorTextId,binding.kitapArsivListeErrorTextId,binding.kitapArsivListeNotTextId,binding.kitapListeRw);
                }
                is BaseResourceEvent.Success->{
                    arsivAdapter!!.addLoadStateListener {cls->
                        if(cls.append.endOfPaginationReached){
                            if(arsivAdapter!!.itemCount<1){
                                getFragmentView().showComponents(binding.kitapArsivListeErrorTextId,binding.kitapArsivListeNotTextId)
                                getFragmentView().hideComponents(binding.kitapListeProgressBar,binding.kitapListeRw,binding.kitapListeErrorTextId);
                            }else{
                                binding.kitapListeRw.showComponent();
                                getFragmentView().hideComponents(binding.kitapArsivListeErrorTextId,binding.kitapListeErrorTextId,binding.kitapArsivListeNotTextId,binding.kitapListeProgressBar);
                            }
                        }
                    }
                    viewModel.viewModelScope.launch {
                        arsivAdapter!!.submitData(it.data!!);
                    }
                }
            }
        })
    }

    private fun initKitapBegenilenListe() {
        begeniAdapter = KitapBegeniListeAdapter();
        binding.kitapListeRw.adapter = begeniAdapter;
        lifecycleScope.launch {
            viewModel.getKitapBegeniListe().collectLatest {
                begeniAdapter?.submitData(it);
            }
        }

        begeniAdapter?.withLoadStateFooter(
            footer = PagingLoadStateAdapter(begeniAdapter!!)
        )
        begeniAdapter?.addLoadStateListener { combinedLoadStates ->
            val errorState =
                when {
                    combinedLoadStates.append is LoadState.Error -> combinedLoadStates.append as LoadState.Error
                    combinedLoadStates.prepend is LoadState.Error ->  combinedLoadStates.prepend as LoadState.Error
                    combinedLoadStates.refresh is LoadState.Error -> combinedLoadStates.refresh as LoadState.Error
                    else -> null
                }

            when (val loadState = combinedLoadStates.source.refresh) {
                is LoadState.NotLoading -> {
                    getFragmentView().hideComponents(binding.kitapListeProgressBar,binding.kitapListeErrorTextId,binding.kitapArsivListeErrorTextId,binding.kitapArsivListeNotTextId);
                    binding.kitapListeRw.showComponent();
                }
                is LoadState.Loading -> {
                    binding.kitapListeProgressBar.showComponent();
                    getFragmentView().hideComponents(binding.kitapArsivListeErrorTextId,binding.kitapArsivListeNotTextId,binding.kitapListeErrorTextId)
                }
                is LoadState.Error -> {
                    binding.kitapListeRw.hideComponent();
                    binding.kitapListeProgressBar.hideComponent();

                    errorState?.let{
                        binding.kitapListeErrorTextId.text = it.error.localizedMessage;
                        binding.kitapListeErrorTextId.showComponent();
                    }
                }
            }
        }
    }

    private fun observeShareKitap(selectedKitap:KitapModel) {
        viewModel.shareKitapUri.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapIslemProgresBar.showComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.kitapIslemProgresBar.hideComponent();
                    showSnackBar(requireView(),
                        requireView().context.getString(R.string.kitapShareError),
                        ERROR
                    );
                }
                is BaseResourceEvent.Success->{
                    val shareIntent = Intent(Intent.ACTION_SEND);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.setType("image/png");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,selectedKitap?.kitapAd+" "+requireContext().resources.getString(R.string.paylasimKitapAdText));
                    val sharedImageUri = FileProvider.getUriForFile(requireContext(),
                        requireContext().applicationContext.packageName+".provider",it.data!!)
                    shareIntent.putExtra(Intent.EXTRA_STREAM, sharedImageUri);
                    binding.kitapIslemProgresBar.hideComponent();
                    resultLauncher.launch(Intent.createChooser(shareIntent, requireContext().resources.getString(R.string.shareLabel)))
                }
            }
        });
    }

    val setSelectPropertiesForCheckedChip = {it:Chip,isChecked:Boolean->
        if (isChecked) {
            it.setChipStrokeColorResource(R.color.white);
            it.setChipBackgroundColorResource(R.color.lacivert);
            it.setTextColor(it.context.getColor(R.color.whiteTextColor));
            it.setChipIconTintResource(R.color.white);
            it.setCheckedIconVisible(false);
        }else {
            it.setChipStrokeColorResource(R.color.lacivert);
            it.setChipBackgroundColorResource(R.color.white);
            it.setTextColor(it.context.getColor(R.color.transparent));
            it.setChipIconTintResource(R.color.transparent);
        }
    }

    private fun observeKitapArsiv(kitap: KitapModel) {
        viewModel.kitapArsivlenmisResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapIslemProgresBar.showComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.kitapIslemProgresBar.hideComponent();
                    viewModel.kitapArsivle(kitap);
                    observeKitapArsivKayit();
                }
                is BaseResourceEvent.Success->{
                    binding.kitapIslemProgresBar.hideComponent();
                    showSnackBar(getFragmentView(),getFragmentView().context.getString(R.string.kitapArsivdeMevcut), WARNING);
                }
            }
        });
    }

    private fun observeKitapArsivKayit() {
        viewModel.arsivKitap.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapIslemProgresBar.showComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.kitapIslemProgresBar.hideComponent();
                    showSnackBar(getFragmentView(),it.message!!, ERROR);
                }
                is BaseResourceEvent.Success->{
                    binding.kitapIslemProgresBar.hideComponent();
                    showSnackBar(getFragmentView(),it.data!!, SUCCESS);
                }
            }
        });
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_CANCELED) {
        }
    }

    override fun destroyOthers() {
        super.destroyOthers();
        listeAdapter = null;
        arsivAdapter = null;
        begeniAdapter = null;
    }
}