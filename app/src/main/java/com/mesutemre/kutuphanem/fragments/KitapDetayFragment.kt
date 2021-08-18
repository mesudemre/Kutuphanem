package com.mesutemre.kutuphanem.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.databinding.FragmentKitapDetayBinding
import com.mesutemre.kutuphanem.model.KitapModel
import com.mesutemre.kutuphanem.util.formatDate
import com.mesutemre.kutuphanem.util.getImageFromUrl
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

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        selectedKitap = args.kitapObj;
    }

    override fun onCreateViewFragment(view: View) {
        super.onCreateViewFragment(view);
        initializeValues(view);
    }

    private fun initializeValues(view: View) {
        view.kitapDetayImageId.getImageFromUrl(selectedKitap.kitapResimPath,view.kitapDetayImageId);
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
                    view.viewMoreImageIdLayout.visibility = View.VISIBLE;
                    view.viewMoreImageId.visibility = View.VISIBLE;
                }
                return true;
            }
        });
    }

    override fun onStartFragment() {
        binding.viewMoreImageId?.setOnClickListener {
            binding.kitapDetayAciklamaTextId?.maxLines = Integer.MAX_VALUE;
            binding.viewMoreImageIdLayout?.visibility = View.GONE;
            binding.viewMoreImageId?.visibility = View.GONE;
            binding.viewLessImageId?.visibility = View.VISIBLE;

            val anim = AnimationUtils.loadAnimation(it.context,R.anim.focus_to_y_animation);
            binding.kitapDetayAciklamaTextId?.animation = anim;
            binding.kitapDetayAciklamaTextId?.parent?.
            requestChildFocus(binding.kitapDetayAciklamaTextId,binding.kitapDetayAciklamaTextId);
        }

        binding.viewLessImageId?.setOnClickListener {
            binding.kitapDetayAciklamaTextId?.maxLines = 4;
            binding.viewMoreImageIdLayout?.visibility = View.VISIBLE;
            binding.viewMoreImageId?.visibility = View.VISIBLE;
            binding.viewLessImageId?.visibility = View.GONE;
        }

        binding.shareImageViewId?.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND);
            viewModel.prepareShareKitap(shareIntent,selectedKitap,requireContext());
            observeShareIntent(it);
        }

        binding.kitapArsivleImageViewId.setOnClickListener {
            //Bu kısımda kitap room db ye kaydedilecektir...
            /* TODO
            1.Resim indirilip local de tutulacak ->Yukarıda ki metod ProjectUtil classına taşınacak
            2.Kitap bilgileri room dbye kaydedilecek.
            3.Tüm bu işlemler viewmodel tarafında yapılacaktır...
            4.Yukarıdaki share kısmıda dahil olmak üzere buradaki işlemde viewmodel tarafında yapılacaktır...
             */
        }
    }

    private fun observeShareIntent(view:View) {
        viewModel.shareIntent.observe(viewLifecycleOwner,Observer{
            startActivity(Intent.createChooser(it, view.context.resources.getString(R.string.shareLabel)))
        });
    }
}