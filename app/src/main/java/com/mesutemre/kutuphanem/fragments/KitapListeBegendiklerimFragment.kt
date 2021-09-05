package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.KitapListeBegendiklerimFragmentBinding

/**
 * @Author: mesutemre.celenk
 * @Date: 3.09.2021
 */
class KitapListeBegendiklerimFragment:BaseFragment<KitapListeBegendiklerimFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapListeBegendiklerimFragmentBinding
            = KitapListeBegendiklerimFragmentBinding::inflate;
    override val layoutName = "kitap_liste_begendiklerim_fragment.xml";

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
    }
}