package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ProfilIslemAdSoyadEpostaBinding
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.util.hideKeyboard

/**
 * @Author: mesutemre.celenk
 * @Date: 31.10.2021
 */
class ProfilIslemAdSoyadEpostaFragment:BaseFragment<ProfilIslemAdSoyadEpostaBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfilIslemAdSoyadEpostaBinding
            = ProfilIslemAdSoyadEpostaBinding::inflate;
    override val layoutName = "profil_islem_ad_soyad_eposta.xml";

    private val args:ProfilIslemAdSoyadEpostaFragmentArgs by navArgs();
    private lateinit var kullanici: Kullanici;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        kullanici = args.kullaniciObj;
    }

    override fun onStartFragment() {
        binding.textInputProfilKullaniciAd.editText?.setText(kullanici.username);
        binding.textInputProfilAd.editText?.setText(kullanici.ad);
        binding.textInputProfilSoyad.editText?.setText(kullanici.soyad);
        binding.textInputProfilEposta.editText?.setText(kullanici.eposta);

        addTextChangeListeners();

        binding.adSoyadEPostaBackImageId.setOnClickListener {
            requireActivity().onBackPressed();
        }

        binding.adSoyadGuncelleButtonId.setOnClickListener {
            val ad:String = binding.textInputProfilAd.editText!!.text.toString();
            val soyad:String = binding.textInputProfilSoyad.editText!!.text.toString();
            val eposta:String = binding.textInputProfilEposta.editText!!.text.toString();

            binding.editTextProfilAd.hideKeyboard(binding.editTextProfilAd);

            if (TextUtils.isEmpty(ad)) {
                binding.textInputProfilAd.error = it.resources.getString(R.string.adValidationErr);
                return@setOnClickListener;
            }
            if (TextUtils.isEmpty(soyad)) {
                binding.textInputProfilSoyad.error = it.resources.getString(R.string.soyadValidationErr);
                return@setOnClickListener;
            }
            if (TextUtils.isEmpty(eposta)) {
                binding.textInputProfilEposta.error = it.resources.getString(R.string.epostaValidationErr);
                return@setOnClickListener;
            }
            if (!eposta.contains("@")) {
                binding.textInputProfilEposta.error = it.resources.getString(R.string.epostaFormatValidationErr);
                return@setOnClickListener;
            }
        }
    }

    private fun addTextChangeListeners() {
        binding.textInputProfilAd.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputProfilAd));
        binding.textInputProfilSoyad.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputProfilSoyad));
        binding.textInputProfilEposta.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputProfilEposta));
    }
}