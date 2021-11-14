package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ProfilIslemDogumtarCinsiyetBinding
import com.mesutemre.kutuphanem.fragments.dialogs.DogumTarihiDialogFragment
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.util.formatDate

/**
 * @Author: mesutemre.celenk
 * @Date: 14.11.2021
 */
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
            requireActivity().onBackPressed();
        }

        binding.editTextProfilDogumtarih.setOnClickListener {
            DogumTarihiDialogFragment(binding.editTextProfilDogumtarih,
                kullanici.dogumTarihi)
                .show(requireFragmentManager(),null)
        }
    }
}