package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.mesutemre.kutuphanem.adapters.DashKategoriAdapter
import com.mesutemre.kutuphanem.adapters.TanitimTabViewPagerAdapter
import com.mesutemre.kutuphanem.databinding.AnasayfaFragmentBinding
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import com.mesutemre.kutuphanem.viewmodels.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnasayfaFragment:Fragment() {

    private var anasayfaBinding:AnasayfaFragmentBinding? = null;
    private val viewModel:AnasayfaViewModel by viewModels();
    private lateinit var dashKategoriAdapter: DashKategoriAdapter;
    private lateinit var tanitimPagerAdapter:TanitimTabViewPagerAdapter;

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        anasayfaBinding = AnasayfaFragmentBinding.inflate(inflater);
        val view = anasayfaBinding!!.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        dashKategoriAdapter = DashKategoriAdapter(arrayListOf());

        viewModel.getAnasayfaDashListe();

        val dashKategoriLayout = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL, false);
        anasayfaBinding!!.dashKategoriRecyclerView.setHasFixedSize(true);
        anasayfaBinding!!.dashKategoriRecyclerView.layoutManager = dashKategoriLayout;
        anasayfaBinding!!.dashKategoriRecyclerView.adapter = dashKategoriAdapter;

        observeLiveData();

        tanitimPagerAdapter = TanitimTabViewPagerAdapter(this.requireActivity());
        anasayfaBinding!!.tanitimViewPager.adapter = tanitimPagerAdapter;
        TabLayoutMediator(anasayfaBinding!!.tanitimTabLayout,anasayfaBinding!!.tanitimViewPager){tab,position->

        }.attach();

    }

    private fun observeLiveData(){
        observeDashKategoriListe();
    }

    private fun observeDashKategoriListe(){
        viewModel.dashKategoriListe.observe(viewLifecycleOwner, Observer { kitapTurListe->
            kitapTurListe?.let {
                dashKategoriAdapter.updateKategorListe(kitapTurListe);
            }
        });

        viewModel.dashKategoriListeLoading.observe(viewLifecycleOwner, Observer { loading->
           if(loading){
               anasayfaBinding!!.dashKategoriProgressBarId.visibility = View.VISIBLE;
               anasayfaBinding!!.dashKategoriHataTextView.visibility = View.GONE;
           }else{
               anasayfaBinding!!.dashKategoriProgressBarId.visibility = View.GONE;
           }
        });

        viewModel.dashKategoriListeError.observe(viewLifecycleOwner,Observer{error->
            if(error){
                anasayfaBinding!!.dashKategoriHataTextView.visibility = View.VISIBLE;
                anasayfaBinding!!.dashKategoriRecyclerView.visibility = View.GONE;
            }else{
                anasayfaBinding!!.dashKategoriHataTextView.visibility = View.GONE;
            }
        });
    }

    override fun onDestroyView() {
        super.onDestroyView();
        this.anasayfaBinding = null;
    }

}