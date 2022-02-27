package com.mesutemre.kutuphanem.auth.profil.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.auth.profil.ui.dialog.ResimSecBottomSheetDialogFragment
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.ProfilIslemFragmentBinding
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfilIslemFragment() :BaseFragment<ProfilIslemFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfilIslemFragmentBinding
            = ProfilIslemFragmentBinding::inflate
    override val layoutName = "profil_islem_fragment.xml"

    private val viewModel: ProfilIslemViewModel by viewModels()
    private lateinit var kullanici: Kullanici

    @Inject
    lateinit var customSharedPreferences: CustomSharedPreferences

    override fun onCreateFragment(savedInstanceState: Bundle?) {
    }

    override fun onStartFragment() {
        viewModel.getKullaniciInfo()
        observeKullaniciBilgi()

        binding.adSoyadEpostaCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemAdSoyadEpostaFragment(kullanici)
        }

        binding.cikisCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToExitFromApplicationDialogFragment()
        }
        binding.dogumTarCinsiyetCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemDogumTarCinsiyetFragment(kullanici)
        }

        binding.ilgiAlanlarimCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemIlgiAlanlarimFragment(kullanici)
        }

        binding.uygulamaTercihlerimCardId.setOnClickListener {
            val action = ProfilIslemFragmentDirections.actionProfilIslemFragmentToProfilIslemIletisiTercihlerimFragment(kullanici)
        }

        binding.profilResimChangeId.setOnClickListener {
            ResimSecBottomSheetDialogFragment(::loadProfilResim).show(requireFragmentManager(),null)
        }
    }

    val loadProfilResimLambda: (Uri) ->Unit = { imageUri ->
        if (imageUri != null) {
            binding.profilResimImage.getCircleImageFromUri(imageUri,binding.profilResimImage)
            val ad = AlertDialog.Builder(requireContext())
            ad.setMessage(requireContext().getString(R.string.profilResimDegisiklik))
            ad.setPositiveButton(requireContext().getString(R.string.evet)){ dialogInterface, i ->
                viewModel.kullaniciResimGuncelle(imageUri,kullanici.username)
                observeProfilResimDegistirme()
            }
            ad.setNegativeButton(requireContext().getString(R.string.hayir)){ dialogInterface, i ->
                binding.profilResimImage.getCircleImageFromUrl(kullanici.resim,binding.profilResimImage)
            }
            ad.create().show()
        }
    }

    private fun loadProfilResim(imageUri:Uri) {
        if (imageUri != null) {
            binding.profilResimImage.getCircleImageFromUri(imageUri,binding.profilResimImage)
            val ad = AlertDialog.Builder(requireContext())
            ad.setMessage(requireContext().getString(R.string.profilResimDegisiklik))
            ad.setPositiveButton(requireContext().getString(R.string.evet)){ dialogInterface, i ->
                viewModel.kullaniciResimGuncelle(imageUri,kullanici.username)
                observeProfilResimDegistirme()
            }
            ad.setNegativeButton(requireContext().getString(R.string.hayir)){ dialogInterface, i ->
                binding.profilResimImage.getCircleImageFromUrl(kullanici.resim,binding.profilResimImage)
            }
            ad.create().show()
        }
    }

    private fun observeProfilResimDegistirme() {
        viewModel.kullaniciBilgiGuncelleResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.profilBilgiProgressBar.showComponent()
                    getFragmentView().hideComponents(binding.profilBilgiLayoutId,binding.profilResimLayout,binding.profilIslemlerItems)
                }
                is BaseResourceEvent.Error->{
                    binding.profilBilgiProgressBar.hideComponent()
                    showSnackBar(getFragmentView(),it.message!!, ERROR)
                }
                is BaseResourceEvent.Success->{
                    binding.profilBilgiProgressBar.hideComponent()
                    getFragmentView().showComponents(binding.profilBilgiLayoutId,binding.profilResimLayout,binding.profilIslemlerItems)
                    showSnackBar(getFragmentView(),it.data!!.statusMessage,SUCCESS)
                }
            }
        })
    }

    private fun observeKullaniciBilgi(){
        viewModel.kullaniciBilgiResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.profilBilgiProgressBar.showComponent()
                    getFragmentView().hideComponents(binding.profilBilgiLayoutId,binding.profilResimLayout,binding.profilIslemlerItems)
                }
                is BaseResourceEvent.Error->{
                    binding.profilBilgiProgressBar.hideComponent()
                }
                is BaseResourceEvent.Success->{
                    kullanici = it.data!!;
                    binding.profilBilgiProgressBar.hideComponent()
                    getFragmentView().showComponents(binding.profilBilgiLayoutId,binding.profilResimLayout,binding.profilIslemlerItems)
                    binding.profilAdSoyadTextViewId.setText(kullanici.ad+" "+kullanici.soyad)
                    binding.profilResimImage.getCircleImageFromUrl(kullanici.resim,
                        binding.profilResimImage)
                }
            }
        })
    }
}