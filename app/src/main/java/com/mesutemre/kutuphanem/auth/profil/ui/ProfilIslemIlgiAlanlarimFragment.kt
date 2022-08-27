package com.mesutemre.kutuphanem.auth.profil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.ProfilIslemIlgiAlanlarBinding
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.hideComponents
import com.mesutemre.kutuphanem.util.showComponent
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: mesutemre.celenk
 * @Date: 21.11.2021
 */
@AndroidEntryPoint
class ProfilIslemIlgiAlanlarimFragment:BaseFragment<ProfilIslemIlgiAlanlarBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfilIslemIlgiAlanlarBinding
            = ProfilIslemIlgiAlanlarBinding::inflate;
    override val layoutName = "profil_islem_ilgi_alanlar.xml";

    private val args:ProfilIslemIlgiAlanlarimFragmentArgs by navArgs();
    private lateinit var kullanici: Kullanici;

    private val profilIslemViewModel: ProfilIslemViewModel by viewModels();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        kullanici = args.kullaniciObj;
    }

    override fun onStartFragment() {
        binding.textInputProfilKullaniciAd.editText?.setText(kullanici.username);
        observeParametreKitapTurListe();
        binding.ilgiAlanlarimBackImageId.setOnClickListener {
            onBackPressed();
        }
    }

    private fun observeParametreKitapTurListe() {

    }


}