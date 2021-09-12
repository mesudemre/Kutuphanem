package com.mesutemre.kutuphanem.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.FragmentKitapDetayDeepBinding
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.*
import com.mesutemre.kutuphanem.viewmodels.KitapDetayViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_kitap_detay.view.*

/**
 * @Author: mesutemre.celenk
 * @Date: 9.09.2021
 */
@AndroidEntryPoint
class KitapDetayDeepFragment: BaseFragment<FragmentKitapDetayDeepBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentKitapDetayDeepBinding
            = FragmentKitapDetayDeepBinding::inflate
    override val layoutName: String = "fragment_kitap_detay_deep.xml"

    private val args:KitapDetayDeepFragmentArgs by navArgs();
    private lateinit var selectedKitap: KitapModel;
    private val viewModel: KitapDetayViewModel by viewModels()
    private var sharedImageUri: Uri? = null;
    private var isFromArsiv:Boolean = false;
    private lateinit var kitapId:String;

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        kitapId = args.kitapId;
        if(!isFromArsiv){
            viewModel.getKitapByKitapId(Integer.parseInt(kitapId));
        }
    }

    override fun onCreateViewFragment(view: View) {
        super.onCreateViewFragment(view);
        if(!isFromArsiv){
            observeKitapDetay(view);
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = KitapDetayDeepFragmentDirections.actionKitapDetayDeepFragmentToAnasayfaFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    private fun observeKitapDetay(view: View){
        viewModel.selectedKitap.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled){
                it.hasBeenHandled = false;
                binding.kitapIslemProgressBar.showComponent();
                if(it.hasBeenError){
                    binding.surecLayoutId.showComponent();
                    binding.kitapResimLayoutId.hideComponent();
                    binding.aksiyonLayoutId.hideComponent();
                    binding.kitapDetayBilgiLayoutId.hideComponent();
                    binding.kitapDetayTextLayoutId.hideComponent();
                }else{
                    selectedKitap = it.peekContent();
                    viewModel.kitapArsivlenmisMi(selectedKitap.kitapId!!);
                    observeKitapArsivlenmisMi();
                    initializeValues(view);
                }
                binding.kitapIslemProgressBar.hideComponent();
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

        view.kitapAdTextViewId.setText(selectedKitap.kitapAd);
        view.yazarAdTextViewId.setText(selectedKitap.yazarAd);
        view.kitapTurTextViewId.setText(selectedKitap.kitapTur?.aciklama);
        view.yayinEviTextViewId.setText(selectedKitap.yayineviModel?.aciklama);
        view.kitapDetayAciklamaTextId.setText(selectedKitap.kitapAciklama);
        view.alinmaTarTextViewId.setText(formatDate(selectedKitap.alinmatarihi!!,"dd.Mm.yyyy"));
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
            binding.kitapDetayAciklamaTextId?.maxLines = Integer.MAX_VALUE;
            binding.viewMoreImageIdLayout?.hideComponent();
            binding.viewMoreImageId?.hideComponent();
            binding.viewLessImageId?.showComponent();

            val anim = AnimationUtils.loadAnimation(it.context, R.anim.focus_to_y_animation);
            binding.kitapDetayAciklamaTextId?.animation = anim;
            binding.kitapDetayAciklamaTextId?.parent?.
            requestChildFocus(binding.kitapDetayAciklamaTextId,binding.kitapDetayAciklamaTextId);
        }

        binding.viewLessImageId?.setOnClickListener {
            binding.kitapDetayAciklamaTextId?.maxLines = 4;
            binding.viewMoreImageIdLayout?.showComponent();
            binding.viewMoreImageId?.showComponent();
            binding.viewLessImageId?.hideComponent();
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
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    //shareIntent.putExtra(Intent.EXTRA_TEXT,selectedKitap.kitapAd+" "+requireContext().resources.getString(R.string.paylasimKitapAdText));
                    shareIntent.setType("*/*");
                    //htmlBuilder.append(selectedKitap.kitapAd+" "+requireContext().resources.getString(R.string.paylasimKitapAdText));
                    shareIntent.putExtra(Intent.EXTRA_TEXT,requireContext().resources.getString(R.string.kitapDetayDeepLinkUrl)+"${selectedKitap.kitapId}");
                    //shareIntent.setType("image/png");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, sharedImageUri);
                    resultLauncher.launch(
                        Intent.createChooser(shareIntent, requireContext().resources.getString(
                            R.string.shareLabel)))
                }
            }
        });
    }

    private fun observeKitapArsiv(view: View){
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
    private fun observeKitapBegenme(view: View){
        viewModel.kitapBegenme.observe(viewLifecycleOwner,Observer{
            if(it.hasBeenHandled){
                binding.kitapIslemProgressBar.showComponent();
                if(it.hasBeenError){
                    showSnackBar(view,it.peekContent().statusMessage, ERROR);
                }else{
                    showSnackBar(view,it.peekContent().statusMessage, SUCCESS);
                    if(selectedKitap.kitapBegenilmis == 0){
                        binding.likeImageViewId.setTint(view.context.getColor(R.color.fistikYesil));
                        selectedKitap.kitapBegenilmis = 1;
                    }else{
                        binding.likeImageViewId.setTint(view.context.getColor(R.color.transparent));
                        selectedKitap.kitapBegenilmis = 0;
                    }

                }
                binding.kitapIslemProgressBar.hideComponent();
                it.hasBeenHandled = false;
            }
        });
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    }


}