package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.KitapListeArsivFragmentBinding

/**
 * @Author: mesutemre.celenk
 * @Date: 3.09.2021
 */
class KitapListeArsivFragment:BaseFragment<KitapListeArsivFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapListeArsivFragmentBinding
            = KitapListeArsivFragmentBinding::inflate;
    override val layoutName = "kitap_liste_arsiv_fragment.xml";

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
    }
}