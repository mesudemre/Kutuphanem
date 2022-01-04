package com.mesutemre.kutuphanem.auth.profil.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ProfilIslemAdSoyadEpostaBinding
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.hideKeyboard
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: mesutemre.celenk
 * @Date: 31.10.2021
 */
@AndroidEntryPoint
class ProfilIslemAdSoyadEpostaFragment:BaseFragment<ProfilIslemAdSoyadEpostaBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfilIslemAdSoyadEpostaBinding
            = ProfilIslemAdSoyadEpostaBinding::inflate;
    override val layoutName = "profil_islem_ad_soyad_eposta.xml";

    private val args:ProfilIslemAdSoyadEpostaFragmentArgs by navArgs();
    private lateinit var kullanici: Kullanici;
    private val profilIslemViewModel: ProfilIslemViewModel by viewModels();

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
            onBackPressed();
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

            kullanici.apply {
                this.ad = ad;
                this.soyad = soyad;
                this.eposta = eposta;
            }
            profilIslemViewModel.kullaniciBilgiGuncelle(Gson().toJson(kullanici));
            observeKullaniciBilgiGuncelle();
        }
    }

    private fun observeKullaniciBilgiGuncelle(){
        profilIslemViewModel.kullaniciGuncelleLoading.observe(viewLifecycleOwner, Observer {
           if (it) {
               binding.profilIslemAdSoyadEpostaProggressBar.showComponent();
               binding.profilIslemAdSoyadEpostaMainLayoutId.hideComponent();
           }
        });

        profilIslemViewModel.kullaniciGuncelleError.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.profilIslemAdSoyadEpostaProggressBar.hideComponent();
                binding.profilIslemAdSoyadEpostaMainLayoutId.showComponent();
                showSnackBar(getFragmentView(),getFragmentView().context.resources.getString(R.string.profilGuncellemeSunucuHata), ERROR);
            }
        });
        profilIslemViewModel.kullaniciGuncelleSonuc.observe(viewLifecycleOwner, Observer {
            binding.profilIslemAdSoyadEpostaProggressBar.hideComponent();
            binding.profilIslemAdSoyadEpostaMainLayoutId.showComponent();
            showSnackBar(getFragmentView(),it.statusMessage, SUCCESS);
        });
        /*kullaniciGuncelleError.value = false;
                            kullaniciGuncelleLoading.value = false;
                            kullaniciGuncelleSonuc.value = response;*/
    }

    private fun addTextChangeListeners() {
        binding.textInputProfilAd.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputProfilAd));
        binding.textInputProfilSoyad.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputProfilSoyad));
        binding.textInputProfilEposta.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputProfilEposta));
    }
}