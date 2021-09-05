package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.adapters.ParametreTabViewPagerAdapter
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ParametreFragmentBinding
import kotlinx.android.synthetic.main.parametre_fragment.*

class ParametreFragment:BaseFragment<ParametreFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ParametreFragmentBinding
            = ParametreFragmentBinding::inflate
    override val layoutName = "parametre_fragment.xml";

    private val fragmentListe:ArrayList<Fragment> = ArrayList<Fragment>();
    private val fragmentBasliklar:ArrayList<String> = ArrayList<String>();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        addFragments();
    }

    override fun onStartFragment() {
        val fragmentAdapter = ParametreTabViewPagerAdapter(childFragmentManager,lifecycle,fragmentListe);
        binding.parametreViewPager.adapter = fragmentAdapter;

        fragmentBasliklar.clear();
        fragmentBasliklar.add(resources.getString(R.string.yayinEviLabel));
        fragmentBasliklar.add(resources.getString(R.string.kitapTurLabel));

        TabLayoutMediator(parametreTabId,parametreViewPager){tab, position ->
            tab.setText(fragmentBasliklar.get(position));
        }.attach();
    }

    private fun addFragments():Unit{
        fragmentListe.clear();
        fragmentListe.add(ParametreYayineviFragment());
        fragmentListe.add(ParametreKitapturFragment());
    }
}