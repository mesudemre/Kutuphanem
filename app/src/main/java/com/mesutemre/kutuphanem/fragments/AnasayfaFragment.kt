package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.progressindicator.CircularDrawingDelegate
import com.google.android.material.progressindicator.CircularIndeterminateAnimatorDelegate
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.progressindicator.ProgressIndicatorSpec
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.adapters.DashKategoriAdapter
import com.mesutemre.kutuphanem.adapters.KitapSearchResultAdapter
import com.mesutemre.kutuphanem.adapters.TanitimTabViewPagerAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.AnasayfaFragmentBinding
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.viewmodels.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnasayfaFragment:BaseFragment<AnasayfaFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AnasayfaFragmentBinding
            = AnasayfaFragmentBinding::inflate;
    override val layoutName: String = "anasayfa_fragment.xml";

    private val viewModel:AnasayfaViewModel by viewModels()
    private var dashKategoriAdapter: DashKategoriAdapter? = null;
    private var tanitimPagerAdapter:TanitimTabViewPagerAdapter? = null;
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

    override fun onCreateViewFragment(view: View) {
        super.onCreateViewFragment(view);
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
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                    AUTO_COMPLETE_DELAY)
            }
        });

        binding.searchInputEditText.setOnItemClickListener { parent, view, position, id ->
            val selectedKitap = kitapSearchResultAdapter?.getItem(position);
            binding.searchInputEditText.setText("");
            val action = AnasayfaFragmentDirections.actionAnasayfaFragmentToKitapDetayFragment(selectedKitap!!);
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

    override fun onStartFragment() {
        dashKategoriAdapter = DashKategoriAdapter(arrayListOf())

        viewModel.getAnasayfaDashListe()

        val dashKategoriLayout = LinearLayoutManager(getFragmentView().context,LinearLayoutManager.HORIZONTAL, false)
        binding.dashKategoriRecyclerView.setHasFixedSize(true)
        binding.dashKategoriRecyclerView.layoutManager = dashKategoriLayout
        binding.dashKategoriRecyclerView.adapter = dashKategoriAdapter;

        observeLiveData()

        tanitimPagerAdapter = TanitimTabViewPagerAdapter(this.requireActivity())
        binding.tanitimViewPager.adapter = tanitimPagerAdapter
        TabLayoutMediator(binding.tanitimTabLayout,binding.tanitimViewPager){tab,position->

        }.attach()
    }

    private fun observeSearchKitap(){
        viewModel.kitapSearchResult.observe(viewLifecycleOwner, Observer { kitapListe->
            kitapSearchResultAdapter?.updateKitapSearchListe(kitapListe);
        });

        viewModel.kitapSearchLoading.observe(viewLifecycleOwner, Observer { it->
            if(it){
                binding.searchInputLayout.isEndIconVisible = true;
            }else{
                binding.searchInputLayout.isEndIconVisible = false;
            }
        })
    }

    private fun observeLiveData(){
        observeDashKategoriListe()
    }

    private fun observeDashKategoriListe(){
        viewModel.dashKategoriListe.observe(viewLifecycleOwner, Observer { kitapTurListe->
            kitapTurListe?.let {
                dashKategoriAdapter?.updateKategorListe(kitapTurListe)
            }
        })

        viewModel.dashKategoriListeLoading.observe(viewLifecycleOwner, Observer { loading->
            if(loading){
                binding.dashKategoriProgressBarId.showComponent();
                binding.dashKategoriHataTextView.hideComponent();
            }else{
                binding.dashKategoriProgressBarId.hideComponent();
            }
        })

        viewModel.dashKategoriListeError.observe(viewLifecycleOwner,Observer{error->
            if(error){
                binding.dashKategoriHataTextView.showComponent();
                binding.dashKategoriRecyclerView.hideComponent();
            }else{
                binding.dashKategoriHataTextView.hideComponent();
            }
        })
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