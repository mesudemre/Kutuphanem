package com.mesutemre.kutuphanem.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.MarkerEdgeTreatment
import com.google.android.material.shape.OffsetEdgeTreatment
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.FragmentKitapDetayBinding
import com.mesutemre.kutuphanem.fragments.dialogs.KitapAciklamaBottomSheetDialogFragment
import com.mesutemre.kutuphanem.fragments.dialogs.KitapYorumBottomSheetDialogFragment
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.model.Kullanici
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.*
import com.mesutemre.kutuphanem.viewmodels.KitapDetayViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_kitap_detay.view.*

@AndroidEntryPoint
class KitapDetayFragment:BaseFragment<FragmentKitapDetayBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentKitapDetayBinding
            = FragmentKitapDetayBinding::inflate
    override val layoutName: String = "fragment_kitap_detay.xml"

    private val args:KitapDetayFragmentArgs by navArgs();
    private lateinit var selectedKitap:KitapModel;
    private val viewModel: KitapDetayViewModel by viewModels()
    private var sharedImageUri: Uri? = null;
    private var isFromArsiv:Boolean = false;
    private lateinit var kullanici:Kullanici;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        isFromArsiv = args.fromArsiv;
        selectedKitap = args.kitapObj;
        if(!isFromArsiv){
            viewModel.getKitapBilgiler(selectedKitap.kitapId!!);
        }
    }

    override fun onCreateViewFragment(view: View) {
        super.onCreateViewFragment(view);
        viewModel.kitapArsivlenmisMi(selectedKitap.kitapId!!);
        observeKitapArsivlenmisMi();
        if(!isFromArsiv){
            observeKitapDetay(view);
            observeYorumYapanKullanici(view);
        }else{
            initializeValues(view);
        }
    }

    private fun observeYorumYapanKullanici(view:View){
        viewModel.yorumYapanKullanici.observe(viewLifecycleOwner,Observer{
           if(it.hasBeenHandled) {
               it.hasBeenHandled = false;
               if(it.hasBeenError){
                   showSnackBar(requireView(),
                       requireView().context.getString(R.string.profilSayfaHata),
                       ERROR
                   );
               }else{
                   kullanici = it.peekContent();
                   binding.kitapYorumYazanImageViewId.let {
                       it.getCircleImageFromUrl(kullanici.resim,it);
                   }
               }

           }
        });
    }

    private fun observeKitapDetay(view:View){
        viewModel.selectedKitap.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled){
                it.hasBeenHandled = false;
                binding.kitapDetayProgresBar.showComponent();
                if(it.hasBeenError){
                    binding.kitapDetayErrorLayoutId.showComponent();
                    binding.kitapDetayPanelLayoutId.hideComponent();
                    binding.kitapDetayGenelBilgilerCardId.hideComponent();
                }else{
                    selectedKitap = it.peekContent();
                    viewModel.kitapArsivlenmisMi(selectedKitap.kitapId!!);
                    observeKitapArsivlenmisMi();
                    initializeValues(view);
                }
                binding.kitapDetayProgresBar.hideComponent();
            }
        });
    }

    private fun initializeValues(view: View) {
        if(isFromArsiv){
            view.kitapDetayImageId.getImageFromLocal(selectedKitap.kitapId!!,view.kitapDetayImageId)
        }else{
            view.kitapDetayImageId.getImageFromUrl(selectedKitap.kitapResimPath,view.kitapDetayImageId);
        }

        if(selectedKitap.kitapBegenilmis>0){
            view.likeImageViewId.setTint(view.context.getColor(R.color.fistikYesil));
        }

        view.ratingBarKitapPuan.rating = selectedKitap.kitapPuan;
        view.yorumPuanTextViewId.text = "${selectedKitap.kitapPuan}";

        if(selectedKitap.kitapPuan == 0.0F){
            view.yorumPuanTextViewId.hideComponent();
            view.ratingBarKitapPuan.hideComponent();
            view.yorumNoPuanCardViewId.showComponent();
            addTriangleTreatment(view.noCommentCard);
        }
        view.kitapAdTextViewId.setText(selectedKitap.kitapAd);
        view.yazarAdTextViewId.setText(selectedKitap.yazarAd);
        view.kitapTurTextViewId.setText(selectedKitap.kitapTur?.aciklama);
        view.yayinEviTextViewId.setText(selectedKitap.yayineviModel?.aciklama);
        view.kitapDetayAciklamaTextId.setText(selectedKitap.kitapAciklama);
        view.alinmaTarTextViewId.setText(formatDate(selectedKitap.alinmatarihi!!,"dd.MM.yyyy"));
        view.kitapDetayAciklamaTextId.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                view.kitapDetayAciklamaTextId.viewTreeObserver.removeOnPreDrawListener(this);
                if(view.kitapDetayAciklamaTextId.lineCount>4){
                    view.kitapDetayAciklamaTextId.maxLines = 4;
                    view.viewMoreImageIdLayout.showComponent();
                    view.viewMoreImageId.showComponent();
                }
                return true;
            }
        });
    }

    override fun onStartFragment() {
        binding.viewMoreImageId?.setOnClickListener {
            KitapAciklamaBottomSheetDialogFragment(binding.kitapDetayAciklamaTextId.text.toString())
                .show(requireFragmentManager(),null);
        }

        binding.shareImageViewId?.setOnClickListener {
            viewModel.prepareShareKitap(selectedKitap,requireContext());
            observeShareUri();
        }

        binding.kitapArsivleImageViewId.setOnClickListener {
            viewModel.kitapArsivle(selectedKitap,it.context);
            observeKitapArsiv(it);
        }

        binding.kitapArsivCikarImageViewId.setOnClickListener {
            viewModel.kitapArsivdenCikar(selectedKitap,it.context);
            observeKitapArsivSilme(it);
        }

        if(isFromArsiv){
            binding.likeImageViewId.hideComponent();
        }
        binding.likeImageViewId.setOnClickListener {
            viewModel.kitapBegenmeIslem(selectedKitap.kitapId!!,selectedKitap.kitapBegenilmis);
            observeKitapBegenme(it);
        }

        binding.kitapDetayBackImage.setOnClickListener {
            activity?.onBackPressed();
        }

        binding.kitapYorumPanel?.setOnClickListener {
            KitapYorumBottomSheetDialogFragment(kullanici,selectedKitap,viewModel)
                .show(requireFragmentManager(),null);
        }
    }

    private fun observeShareUri() {
        viewModel.shareUri.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled){
                it.hasBeenHandled = false;
                if(it.hasBeenError){
                    showSnackBar(requireView(),
                        requireView().context.getString(R.string.kitapShareError),
                        ERROR
                    );
                }else{
                    sharedImageUri = it.peekContent();
                    val shareIntent = Intent(Intent.ACTION_SEND);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.setType("image/png");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,selectedKitap?.kitapAd+" "+requireContext().resources.getString(R.string.paylasimKitapAdText));
                    shareIntent.putExtra(Intent.EXTRA_STREAM, sharedImageUri);
                    resultLauncher.launch(Intent.createChooser(shareIntent, requireContext().resources.getString(R.string.shareLabel)))
                }
            }
        });
    }

    private fun observeKitapArsiv(view:View){
        viewModel.arsivKitap.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled) {
                showSnackBar(view,it.peekContent(), SUCCESS);
                binding.kitapArsivleImageViewId.hideComponent();
                binding.kitapArsivCikarImageViewId.showComponent();
                it.hasBeenHandled = false;
            }
        });

        viewModel.kitapResimDownload.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled) {
                it.hasBeenHandled = false;
            }
        });
    }

    private fun observeKitapArsivlenmisMi(){
        viewModel.kitapArsivMevcut.observe(viewLifecycleOwner,Observer{
            it.hasBeenHandled = true;
            if(!it.hasBeenError) {
                binding.kitapArsivCikarImageViewId.showComponent();
                binding.kitapArsivleImageViewId.hideComponent();
            }
        });
    }

    private fun observeKitapArsivSilme(view: View) {
        viewModel.arsivKitapSil.observe(viewLifecycleOwner,Observer{
            it.hasBeenHandled = true;
            showSnackBar(view,it.peekContent(), SUCCESS);
            binding.kitapArsivleImageViewId.showComponent();
            binding.kitapArsivCikarImageViewId.hideComponent();
            it.hasBeenHandled = false;
        });
    }
    private fun observeKitapBegenme(view:View){
        viewModel.kitapBegenme.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled){
                binding.kitapDetayProgresBar.showComponent();
                if(it.hasBeenError){
                    showSnackBar(view,it.peekContent().statusMessage, ERROR);
                    binding.kitapDetayProgresBar.hideComponent();
                }else{
                    showSnackBar(view,it.peekContent().statusMessage, SUCCESS);
                    if(selectedKitap.kitapBegenilmis == 0){
                        binding.likeImageViewId.setTint(view.context.getColor(R.color.fistikYesil));
                        selectedKitap.kitapBegenilmis = 1;
                    }else{
                        binding.likeImageViewId.setTint(view.context.getColor(R.color.white));
                        selectedKitap.kitapBegenilmis = 0;
                    }
                    binding.kitapDetayProgresBar.hideComponent();
                }
                it.hasBeenHandled = false;
            }
        });
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
    }

    private fun addTriangleTreatment(card:MaterialCardView){
        val triangleSize = getResources().getDimension(R.dimen.triangle_size);
        val markerEdgeTreatment = MarkerEdgeTreatment(triangleSize);
        val offsetEdgeTreatment = OffsetEdgeTreatment(markerEdgeTreatment,triangleSize);
        card.shapeAppearanceModel = card.shapeAppearanceModel.toBuilder()
            .setBottomEdge(offsetEdgeTreatment)
            .build();
    }
}