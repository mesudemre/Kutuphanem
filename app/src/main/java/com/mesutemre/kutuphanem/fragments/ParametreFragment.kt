package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.adapters.ParametreTabViewPagerAdapter
import com.mesutemre.kutuphanem.databinding.ParametreFragmentBinding
import kotlinx.android.synthetic.main.parametre_fragment.*

class ParametreFragment:Fragment() {

    private val fragmentListe:ArrayList<Fragment> = ArrayList<Fragment>();
    private val fragmentBasliklar:ArrayList<String> = ArrayList<String>();
    private var parametreBinding:ParametreFragmentBinding? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parametreBinding = ParametreFragmentBinding.inflate(inflater);
        return parametreBinding!!.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        addFragments();
        val fragmentAdapter = ParametreTabViewPagerAdapter(childFragmentManager,lifecycle,fragmentListe);
        parametreBinding!!.parametreViewPager.adapter = fragmentAdapter;

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

    override fun onDestroyView() {
        super.onDestroyView();
        parametreBinding = null;
    }
}