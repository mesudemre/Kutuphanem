package com.mesutemre.kutuphanem.auth.profil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.auth.profil.ui.dialog.ExitFromApplicationDialogFragment
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.ProfilIslemFragmentBinding
import com.mesutemre.kutuphanem.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfilIslemFragment() :BaseFragment<ProfilIslemFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfilIslemFragmentBinding
            = ProfilIslemFragmentBinding::inflate;
    override val layoutName = "profil_islem_fragment.xml";

    private val viewModel: ProfilIslemViewModel by viewModels();
    private lateinit var kullanici: Kullanici;

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        viewModel.getKullaniciInfo();
        observeKullaniciBilgi();

        binding.adSoyadEpostaCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemAdSoyadEpostaFragment(kullanici);
            findNavController(this).navigate(action);
        }

        binding.cikisCardId.setOnClickListener {
            ExitFromApplicationDialogFragment(customSharedPreferences,this)
                .show(requireFragmentManager(),null);
        }
        binding.dogumTarCinsiyetCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemDogumTarCinsiyetFragment(kullanici);
           findNavController(this).navigate(action);
        }

        binding.ilgiAlanlarimCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemIlgiAlanlarimFragment(kullanici);
            findNavController(this).navigate(action);
        }

        binding.uygulamaTercihlerimCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemIletisiTercihlerimFragment(kullanici);
            findNavController(this).navigate(action);
        }
    }

    private fun observeKullaniciBilgi(){
        viewModel.kullaniciBilgiResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.profilBilgiProgressBar.showComponent();
                    getFragmentView().hideComponents(binding.profilBilgiLayoutId,binding.profilResimLayout,binding.profilIslemlerItems)
                }
                is BaseResourceEvent.Error->{
                }
                is BaseResourceEvent.Success->{
                    kullanici = it.data!!;
                    binding.profilBilgiProgressBar.hideComponent();
                    getFragmentView().showComponents(binding.profilBilgiLayoutId,binding.profilResimLayout,binding.profilIslemlerItems)
                    binding.profilAdSoyadTextViewId.setText(kullanici.ad+" "+kullanici.soyad);
                    binding.profilResimImage.getCircleImageFromUrl(kullanici.resim,
                        binding.profilResimImage);
                }
            }
        });
    }
}