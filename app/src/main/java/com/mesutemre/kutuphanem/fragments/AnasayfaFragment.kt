package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import com.mesutemre.kutuphanem.databinding.AnasayfaFragmentBinding
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.viewmodels.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnasayfaFragment:Fragment() {

    private var anasayfaBinding:AnasayfaFragmentBinding? = null
    private val viewModel:AnasayfaViewModel by viewModels()
    private lateinit var dashKategoriAdapter: DashKategoriAdapter
    private lateinit var tanitimPagerAdapter:TanitimTabViewPagerAdapter
    private lateinit var kitapSearchResultAdapter: KitapSearchResultAdapter
    private lateinit var handler:Handler

    companion object{
        const val TRIGGER_AUTO_COMPLETE:Int = 100
        const val AUTO_COMPLETE_DELAY:Long = 300
    }

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        anasayfaBinding = AnasayfaFragmentBinding.inflate(inflater)
        val view = anasayfaBinding!!.root

        kitapSearchResultAdapter = KitapSearchResultAdapter(requireActivity(),R.layout.item_kitap_search, arrayListOf());

        anasayfaBinding!!.searchInputEditText.threshold = 2;
        anasayfaBinding!!.searchInputEditText.setAdapter(kitapSearchResultAdapter);

        anasayfaBinding!!.searchInputEditText.addTextChangedListener(object:TextWatcher{

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

        prepareProggressForSearch();

        handler = Handler(object:Handler.Callback{
            override fun handleMessage(message: Message): Boolean {
                if(!TextUtils.isEmpty(anasayfaBinding!!.searchInputEditText.text) && anasayfaBinding!!.searchInputEditText.text.length>2){
                    viewModel.searchKitapYazar(anasayfaBinding!!.searchInputEditText.text.toString())
                    observeSearchKitap()
                }
                return false
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashKategoriAdapter = DashKategoriAdapter(arrayListOf())

        viewModel.getAnasayfaDashListe()

        val dashKategoriLayout = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL, false)
        anasayfaBinding!!.dashKategoriRecyclerView.setHasFixedSize(true)
        anasayfaBinding!!.dashKategoriRecyclerView.layoutManager = dashKategoriLayout
        anasayfaBinding!!.dashKategoriRecyclerView.adapter = dashKategoriAdapter;

        observeLiveData()

        tanitimPagerAdapter = TanitimTabViewPagerAdapter(this.requireActivity())
        anasayfaBinding!!.tanitimViewPager.adapter = tanitimPagerAdapter
        TabLayoutMediator(anasayfaBinding!!.tanitimTabLayout,anasayfaBinding!!.tanitimViewPager){tab,position->

        }.attach()


    }

    private fun observeSearchKitap(){
        viewModel.kitapSearchResult.observe(viewLifecycleOwner, Observer { kitapListe->
            kitapSearchResultAdapter.updateKitapSearchListe(kitapListe);
        });

        viewModel.kitapSearchLoading.observe(viewLifecycleOwner, Observer { it->
            if(it){
                anasayfaBinding!!.searchInputLayout.isEndIconVisible = true;
            }else{
                anasayfaBinding!!.searchInputLayout.isEndIconVisible = false;
            }
        })
    }

    private fun observeLiveData(){
        observeDashKategoriListe()
    }

    private fun observeDashKategoriListe(){
        viewModel.dashKategoriListe.observe(viewLifecycleOwner, Observer { kitapTurListe->
            kitapTurListe?.let {
                dashKategoriAdapter.updateKategorListe(kitapTurListe)
            }
        })

        viewModel.dashKategoriListeLoading.observe(viewLifecycleOwner, Observer { loading->
            if(loading){
                anasayfaBinding!!.dashKategoriProgressBarId.visibility = View.VISIBLE;
                anasayfaBinding!!.dashKategoriHataTextView.visibility = View.GONE;
            }else{
                anasayfaBinding!!.dashKategoriProgressBarId.visibility = View.GONE;
            }
        })

        viewModel.dashKategoriListeError.observe(viewLifecycleOwner,Observer{error->
            if(error){
                anasayfaBinding!!.dashKategoriHataTextView.visibility = View.VISIBLE;
                anasayfaBinding!!.dashKategoriRecyclerView.visibility = View.GONE;
            }else{
                anasayfaBinding!!.dashKategoriHataTextView.visibility = View.GONE;
            }
        })
    }

    private fun prepareProggressForSearch(){
        val progressIndicatorSpec:ProgressIndicatorSpec = ProgressIndicatorSpec();
        progressIndicatorSpec.loadFromAttributes(
            anasayfaBinding!!.searchInputEditText.context,
            null,
            R.style.Widget_MaterialComponents_ProgressIndicator_Circular_Indeterminate);
        progressIndicatorSpec.circularInset = 0
        progressIndicatorSpec.circularRadius = 20;
        val progressIndicatorDrawable:IndeterminateDrawable = IndeterminateDrawable(anasayfaBinding!!.searchInputEditText.context,
            progressIndicatorSpec,
            CircularDrawingDelegate(),
            CircularIndeterminateAnimatorDelegate());
        anasayfaBinding!!.searchInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM;
        anasayfaBinding!!.searchInputLayout.endIconDrawable = progressIndicatorDrawable;
        anasayfaBinding!!.searchInputLayout.isEndIconVisible = false;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.anasayfaBinding = null
    }

}