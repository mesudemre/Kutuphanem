package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.tabs.TabLayoutMediator
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.adapters.ParametreTabViewPagerAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.KitapListeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapListeFragment:BaseFragment<KitapListeFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapListeFragmentBinding
            = KitapListeFragmentBinding::inflate;
    override val layoutName: String = "kitap_liste_fragment.xml";

    private val fragmentListe = mutableListOf<Fragment>();
    private val fragmentBasliklar = mutableListOf<String>();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        addFragments();
    }

    override fun onStartFragment() {
        val fragmentAdapter = ParametreTabViewPagerAdapter(childFragmentManager,lifecycle,
            ArrayList(fragmentListe)
        );
        binding.kitapListeViewPager.adapter = fragmentAdapter;
        binding.kitapListeViewPager.isUserInputEnabled = false;

        addLabels();

        TabLayoutMediator(binding.kitapListeTabId,binding.kitapListeViewPager){tab, position ->
            tab.setText(fragmentBasliklar.get(position));
        }.attach();
    }

    private fun addFragments():Unit{
        fragmentListe.clear();
        fragmentListe.add(KitapListeAPIFragment());
        fragmentListe.add(KitapListeArsivFragment());
        fragmentListe.add(KitapListeBegendiklerimFragment());
    }

    private fun addLabels(){
        fragmentBasliklar.clear();
        fragmentBasliklar.add(resources.getString(R.string.kitapListeAPI));
        fragmentBasliklar.add(resources.getString(R.string.kitapListeArsiv));
        fragmentBasliklar.add(resources.getString(R.string.kitapListeBegendiklerim));
    }
}