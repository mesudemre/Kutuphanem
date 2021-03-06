package com.mesutemre.kutuphanem.auth.profil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ProfilIslemDogumtarCinsiyetBinding
import com.mesutemre.kutuphanem.auth.profil.ui.dialog.DogumTarihiDialogFragment
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.util.formatDate
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: mesutemre.celenk
 * @Date: 14.11.2021
 */
@AndroidEntryPoint
class ProfilIslemDogumTarCinsiyetFragment:BaseFragment<ProfilIslemDogumtarCinsiyetBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfilIslemDogumtarCinsiyetBinding
            = ProfilIslemDogumtarCinsiyetBinding::inflate;
    override val layoutName = "profil_islem_dogumtar_cinsiyet.xml";

    private val args:ProfilIslemDogumTarCinsiyetFragmentArgs by navArgs();
    private lateinit var kullanici: Kullanici;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        kullanici = args.kullaniciObj;
    }

    override fun onStartFragment() {
        binding.textInputProfilKullaniciAd.editText?.setText(kullanici.username);
        binding.textInputProfilDogumtarih.editText?.setText(formatDate(kullanici.dogumTarihi,"dd.MM.yyyy"));

        binding.dogumTarihiCinsiyetBackImageId.setOnClickListener {
            onBackPressed();
        }

        binding.editTextProfilDogumtarih.setOnClickListener {
            DogumTarihiDialogFragment(binding.editTextProfilDogumtarih,
                kullanici.dogumTarihi)
                .show(requireFragmentManager(),null)
        }

        binding.cinsiyetRadioGroupId.check(binding.erkekRB.id);
        if(kullanici.cinsiyet.equals(getString(R.string.cinsiyetKadinValue))) {
            binding.cinsiyetRadioGroupId.check(binding.kadinRB.id);
        }
    }
}