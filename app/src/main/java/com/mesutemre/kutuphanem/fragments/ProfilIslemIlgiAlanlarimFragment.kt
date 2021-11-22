package com.mesutemre.kutuphanem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.ProfilIslemIlgiAlanlarBinding
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.util.hideComponent
import com.mesutemre.kutuphanem.util.showComponent
import com.mesutemre.kutuphanem.viewmodels.ParametreKitapturViewModel
import com.mesutemre.kutuphanem.viewmodels.ProfilIslemViewModel
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
    private val parametreViewModel:ParametreKitapturViewModel by viewModels();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        kullanici = args.kullaniciObj;
        parametreViewModel.kitapTurListeGetir(false);
    }

    override fun onStartFragment() {
        binding.textInputProfilKullaniciAd.editText?.setText(kullanici.username);
        observeParametreKitapTurListe();
        binding.ilgiAlanlarimBackImageId.setOnClickListener {
            requireActivity().onBackPressed();
        }
    }

    private fun observeParametreKitapTurListe() {
        parametreViewModel.kitapTurLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.profilIlgiAlanlarimProgressBar.showComponent();
            }else {
                binding.profilIlgiAlanlarimProgressBar.hideComponent();
            }
        });
        parametreViewModel.kitapturListe.observe(viewLifecycleOwner, Observer {
            if(it != null && it.size>0) {
                binding.ilgiAlanlarimInfoCardId.showComponent();
                binding.ilgiAlanlarimInfoErrorCardId.hideComponent();
                profilIslemViewModel.getKullaniciIlgiAlanlarFromDB(kullanici.username);
                observeKullaniciIlgiAlanlari(it);
            }
        });
        parametreViewModel.kitapTurError.observe(viewLifecycleOwner, Observer {
            binding.profilIlgiAlanlarimProgressBar.hideComponent();
           if (it) {
               binding.ilgiAlanlarimInfoCardId.hideComponent();
               binding.ilgiAlanlarimInfoErrorCardId.showComponent();
           }
        });
    }

    private fun observeKullaniciIlgiAlanlari(kitapTurListe:List<KitapturModel>){
        val ilgiAlanSecili: (List<KitapturModel>,KitapturModel) ->Boolean = {ilgiAlanListe,ilgiliAlan ->
            for (ia in ilgiAlanListe){
                if(ia.kitapTurId == ilgiliAlan.kitapTurId){
                     true;
                }
            }
            false;
        }

        profilIslemViewModel.kullaniciIlgiAlanlar.observe(viewLifecycleOwner, Observer {kullaniciIlgiAlanlar->
            if(kitapTurListe != null && kitapTurListe.size>0) {
                for (ia in kitapTurListe){
                    val ilgiAlan: Chip = Chip(requireContext());
                    ilgiAlan.text = ia.aciklama;
                    ilgiAlan.setChipBackgroundColorResource(R.color.white);
                    ilgiAlan.setChipStrokeColorResource(R.color.black);
                    ilgiAlan.chipStrokeWidth = 1f;
                    ilgiAlan.setTextColor(requireContext().getColor(R.color.black));
                    ilgiAlan.isCheckable = true;
                    ilgiAlan.setCheckedIconResource(R.drawable.ic_baseline_check_circle_outline_24);

                    if(kullaniciIlgiAlanlar != null && kullaniciIlgiAlanlar.size>0){
                        if(ilgiAlanSecili(kullaniciIlgiAlanlar,ia)){
                            ilgiAlan.isChecked = true;
                        }
                    }
                    binding.ilgiAlanChips.addView(ilgiAlan);
                }
            }
        });
    }
}