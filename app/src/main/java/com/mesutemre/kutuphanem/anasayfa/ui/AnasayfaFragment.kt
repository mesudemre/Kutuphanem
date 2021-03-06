package com.mesutemre.kutuphanem.anasayfa.ui

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.progressindicator.CircularDrawingDelegate
import com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.progressindicator.ProgressIndicatorSpec
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.anasayfa.adapter.DashKategoriAdapter
import com.mesutemre.kutuphanem.anasayfa.adapter.KitapSearchResultAdapter
import com.mesutemre.kutuphanem.anasayfa.adapter.TanitimTabViewPagerAdapter
import com.mesutemre.kutuphanem.anasayfa.ui.dialog.CloseApplicationDialogFragment
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.AnasayfaFragmentBinding
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.hideComponents
import com.mesutemre.kutuphanem.util.showComponent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AnasayfaFragment:BaseFragment<AnasayfaFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AnasayfaFragmentBinding
            = AnasayfaFragmentBinding::inflate;
    override val layoutName: String = "anasayfa_fragment.xml";

    private val viewModel: AnasayfaViewModel by viewModels()
    private var dashKategoriAdapter: DashKategoriAdapter? = null;
    private var tanitimPagerAdapter: TanitimTabViewPagerAdapter? = null;
    private var kitapSearchResultAdapter: KitapSearchResultAdapter? = null;
    private lateinit var handler:Handler;

    companion object{
        const val TRIGGER_AUTO_COMPLETE:Int = 100
        const val AUTO_COMPLETE_DELAY:Long = 300
    }

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                CloseApplicationDialogFragment(parentFragment!!).show(requireFragmentManager(),null);
            }
        });
        viewLifecycleOwner.lifecycleScope.launch {
            async { prepareSearchProperties() }
            async { prepareAnasayfaDashListe() }
            async { prepareTanitimPageAdapter() }
        }
    }

    private fun prepareSearchProperties() {
        kitapSearchResultAdapter = KitapSearchResultAdapter(requireActivity(),R.layout.item_kitap_search, arrayListOf());
        binding.searchInputEditText.threshold = 2;
        binding.searchInputEditText.setAdapter(kitapSearchResultAdapter);
        binding.searchInputEditText.addTextChangedListener(object:TextWatcher{

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(searchText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE)
                handler.sendEmptyMessageDelayed(
                    TRIGGER_AUTO_COMPLETE,
                    AUTO_COMPLETE_DELAY
                )
            }
        });

        binding.searchInputEditText.setOnItemClickListener { parent, view, position, id ->
            val selectedKitap = kitapSearchResultAdapter?.getItem(position);
            binding.searchInputEditText.setText("");
            val action = AnasayfaFragmentDirections.actionAnasayfaFragmentToKitapDetayFragment(selectedKitap!!,false);
            Navigation.findNavController(binding.root).navigate(action);
        }

        prepareProggressForSearch();

        handler = Handler(object:Handler.Callback{
            override fun handleMessage(message: Message): Boolean {
                if(binding != null && !TextUtils.isEmpty(binding.searchInputEditText.text) && binding.searchInputEditText.text.length>2){
                    viewModel.searchKitapYazar(binding.searchInputEditText.text.toString())
                    observeSearchKitap()
                }
                return false
            }
        });
    }

    private fun prepareAnasayfaDashListe() {
        dashKategoriAdapter = DashKategoriAdapter(arrayListOf())

        viewModel.getAnasayfaDashListe()

        val dashKategoriLayout = LinearLayoutManager(getFragmentView().context,LinearLayoutManager.HORIZONTAL, false)
        binding.dashKategoriRecyclerView.setHasFixedSize(true)
        binding.dashKategoriRecyclerView.layoutManager = dashKategoriLayout
        binding.dashKategoriRecyclerView.adapter = dashKategoriAdapter;

        observeLiveData()
    }

    private fun prepareTanitimPageAdapter() {
        tanitimPagerAdapter = TanitimTabViewPagerAdapter(this.requireActivity())
        binding.tanitimViewPager.adapter = tanitimPagerAdapter
        TabLayoutMediator(binding.tanitimTabLayout,binding.tanitimViewPager){tab,position->

        }.attach()
    }

    private fun observeSearchKitap(){
        viewModel.kitapSearchResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it) {
                is BaseResourceEvent.Loading->{
                    binding.searchInputLayout.isEndIconVisible = true;
                }
                is BaseResourceEvent.Error->{
                    binding.searchInputLayout.isEndIconVisible = false;
                }
                is BaseResourceEvent.Success->{
                    binding.searchInputLayout.isEndIconVisible = false;
                    kitapSearchResultAdapter?.updateKitapSearchListe(it.data!!);
                }
            }
        });
    }

    private fun observeLiveData(){
        observeDashKategoriListe()
    }

    private fun observeDashKategoriListe(){
        viewModel.dashKategoriListeResourceEvent.observe(viewLifecycleOwner, Observer {
           when(it) {
               is BaseResourceEvent.Loading->{
                   binding.dashKategoriProgressBarId.showComponent();
                   getFragmentView().hideComponents(binding.dashKategoriHataTextView,binding.dashKategoriRecyclerView)
               }
               is BaseResourceEvent.Error->{
                   binding.dashKategoriHataTextView.showComponent();
                   getFragmentView().hideComponents(binding.dashKategoriProgressBarId,binding.dashKategoriRecyclerView)
               }
               is BaseResourceEvent.Success->{
                   getFragmentView().hideComponents(binding.dashKategoriProgressBarId,binding.dashKategoriHataTextView)
                   binding.dashKategoriRecyclerView.showComponent();
                   dashKategoriAdapter?.updateKategorListe(it.data!!)
               }
           }
        });
    }

    private fun prepareProggressForSearch(){
        val progressIndicatorSpec:ProgressIndicatorSpec = ProgressIndicatorSpec();
        progressIndicatorSpec.loadFromAttributes(
            binding.searchInputEditText.context,
            null,
            R.style.Widget_MaterialComponents_ProgressIndicator_Circular_Indeterminate);
        progressIndicatorSpec.circularInset = 0
        progressIndicatorSpec.circularRadius = 20;
        val progressIndicatorDrawable:IndeterminateDrawable = IndeterminateDrawable(binding.searchInputEditText.context,
            progressIndicatorSpec,
            CircularDrawingDelegate(),
            CircularIndeterminateAnimatorDelegate());
        binding.searchInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM;
        binding.searchInputLayout.endIconDrawable = progressIndicatorDrawable;
        binding.searchInputLayout.isEndIconVisible = false;
    }

    override fun destroyOthers() {
        super.destroyOthers();
        handler.removeCallbacksAndMessages(null);
        this.dashKategoriAdapter = null;
        this.tanitimPagerAdapter = null;
        this.kitapSearchResultAdapter = null;
    }
}