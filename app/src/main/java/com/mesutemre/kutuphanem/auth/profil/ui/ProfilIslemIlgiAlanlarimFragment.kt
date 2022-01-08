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
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.parametre.kitaptur.ui.ParametreKitapturViewModel
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
    private val parametreViewModel: ParametreKitapturViewModel by viewModels();

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        kullanici = args.kullaniciObj;
        parametreViewModel.kitapTurListeGetir(false);
    }

    override fun onStartFragment() {
        binding.textInputProfilKullaniciAd.editText?.setText(kullanici.username);
        observeParametreKitapTurListe();
        binding.ilgiAlanlarimBackImageId.setOnClickListener {
            onBackPressed();
        }
    }

    private fun observeParametreKitapTurListe() {
        parametreViewModel.kitapTurListeResourceEvent.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.profilIlgiAlanlarimProgressBar.showComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.profilIlgiAlanlarimProgressBar.hideComponent();
                    binding.ilgiAlanlarimInfoCardId.hideComponent();
                    binding.ilgiAlanlarimInfoErrorCardId.showComponent();
                }
                is BaseResourceEvent.Success->{
                    binding.profilIlgiAlanlarimProgressBar.hideComponent();
                    binding.ilgiAlanlarimInfoCardId.showComponent();
                    binding.ilgiAlanlarimInfoErrorCardId.hideComponent();
                    profilIslemViewModel.getKullaniciIlgiAlanlarFromDB(kullanici.username);
                    observeKullaniciIlgiAlanlari(it.data!!);
                }
            }
        });
    }

    private fun observeKullaniciIlgiAlanlari(kitapTurListe:List<KitapturModel>){
        profilIslemViewModel.kullaniciIlgiAlanlar.observe(viewLifecycleOwner, Observer {kullaniciIlgiAlanlar->
            when(kullaniciIlgiAlanlar){
                is BaseResourceEvent.Loading->{
                    binding.profilIlgiAlanlarimProgressBar.showComponent();
                    getFragmentView().hideComponents(binding.ilgiAlanlarimInfoErrorCardId, binding.ilgiAlanlarimInfoCardId);
                }
                is BaseResourceEvent.Error->{
                    binding.ilgiAlanlarimInfoErrorCardId.showComponent();
                    getFragmentView().hideComponents(binding.profilIlgiAlanlarimProgressBar, binding.ilgiAlanlarimInfoCardId);
                }
                is BaseResourceEvent.Success->{
                    /*val ilgiAlanSecili: (List<KitapturModel>,KitapturModel) ->Boolean = ilgiAlanSecili@ {ilgiAlanListe,ilgiliAlan ->
                for (ia in ilgiAlanListe){
                    if(ia.kitapTurId == ilgiliAlan.kitapTurId){
                        return@ilgiAlanSecili true;
                    }
                }
                false;
            }*/
                    getFragmentView().hideComponents(binding.ilgiAlanlarimInfoErrorCardId,binding.profilIlgiAlanlarimProgressBar)
                    binding.ilgiAlanlarimInfoCardId.showComponent();
                    val ilgiAlanSeciliFixed : (List<KitapturModel>, KitapturModel) ->Boolean = { ilgiAlanListe, ilgiliAlan ->
                        ilgiAlanListe.any {
                            it.kitapTurId == ilgiliAlan.kitapTurId
                        }
                    }
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

                            if(kullaniciIlgiAlanlar.data != null && kullaniciIlgiAlanlar.data.size>0){
                                if(ilgiAlanSeciliFixed(kullaniciIlgiAlanlar.data,ia)){
                                    ilgiAlan.isChecked = true;
                                }
                            }
                            binding.ilgiAlanChips.addView(ilgiAlan);
                        }
                    }
                }
            }


        });
    }
}