package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ProfilIslemFragmentBinding
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.util.getCircleImageFromUrl
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.viewmodels.ProfilIslemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilIslemFragment() :BaseFragment<ProfilIslemFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfilIslemFragmentBinding
            = ProfilIslemFragmentBinding::inflate;
    override val layoutName = "profil_islem_fragment.xml";

    private val viewModel: ProfilIslemViewModel by viewModels();
    private lateinit var kullanici:Kullanici;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        viewModel.getKullaniciInfo();
    }

    override fun onStartFragment() {
        observeKullaniciBilgi();
        binding.adSoyadEpostaCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemAdSoyadEpostaFragment(kullanici);
            Navigation.findNavController(it).navigate(action);
        }
    }

    private fun observeKullaniciBilgi(){
        viewModel.kullaniciLoading.observe(viewLifecycleOwner, Observer {
           if(it) {
               binding.profilBilgiProgressBar.showComponent();
               binding.profilBilgiLayoutId.hideComponent();
               binding.profilResimLayout.hideComponent();
               binding.profilIslemlerItems.hideComponent();
           }else{
               binding.profilBilgiProgressBar.hideComponent();
               binding.profilBilgiLayoutId.showComponent();
               binding.profilResimLayout.showComponent();
               binding.profilIslemlerItems.showComponent();
           }
        });

        viewModel.kullanici.observe(viewLifecycleOwner, Observer {
            kullanici = it;
            binding.profilAdSoyadTextViewId.setText(kullanici.ad+" "+kullanici.soyad);
            binding.profilResimImage.getCircleImageFromUrl(kullanici.resim,
                binding.profilResimImage);
        });
    }
}