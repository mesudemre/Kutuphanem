package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ProfilIslemIletisimTercihlerBinding
import com.mesutemre.kutuphanem.model.Kullanici

/**
 * @Author: mesutemre.celenk
 * @Date: 21.11.2021
 */
class ProfilIslemIletisimTercihlerimFragment:BaseFragment<ProfilIslemIletisimTercihlerBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfilIslemIletisimTercihlerBinding
            = ProfilIslemIletisimTercihlerBinding::inflate;
    override val layoutName = "profil_islem_iletisim_tercihler.xml";

    private val args:ProfilIslemIletisimTercihlerimFragmentArgs by navArgs();
    private lateinit var kullanici: Kullanici;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        kullanici = args.kullaniciObj;
    }

    override fun onStartFragment() {
        binding.textInputProfilKullaniciAd.editText?.setText(kullanici.username);

        binding.iletisimTercihBackImageId.setOnClickListener {
            requireActivity().onBackPressed();
        }
    }
}