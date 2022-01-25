package com.mesutemre.kutuphanem.kitap.detay.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.MarkerEdgeTreatment
import com.google.android.material.shape.OffsetEdgeTreatment
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.auth.profil.model.Kullanici
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.FragmentKitapDetayBinding
import com.mesutemre.kutuphanem.kitap.detay.ui.dialog.KitapAciklamaBottomSheetDialogFragment
import com.mesutemre.kutuphanem.kitap.liste.model.KitapModel
import com.mesutemre.kutuphanem.kitap.yorum.ui.KitapYorumBottomSheetDialogFragment
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_kitap_detay.view.*

@AndroidEntryPoint
class KitapDetayFragment:BaseFragment<FragmentKitapDetayBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentKitapDetayBinding
            = FragmentKitapDetayBinding::inflate
    override val layoutName: String = "fragment_kitap_detay.xml"

    private val args:KitapDetayFragmentArgs by navArgs()
    private lateinit var selectedKitap: KitapModel
    private val viewModel: KitapDetayViewModel by viewModels()
    private var sharedImageUri: Uri? = null
    private var isFromArsiv:Boolean = false
    private lateinit var kullanici: Kullanici

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        isFromArsiv = args.fromArsiv
        selectedKitap = args.kitapObj
        if(!isFromArsiv){
            viewModel.getKitapBilgiler(selectedKitap.kitapId!!)
        }
    }

    override fun onCreateViewFragment(view: View) {
        super.onCreateViewFragment(view)
        viewModel.kitapArsivlenmisMi(selectedKitap.kitapId!!)
        observeKitapArsivlenmisMi()
        if(!isFromArsiv){
            observeKitapDetay(view)
            observeYorumYapanKullanici()
        }else{
            initializeValues(view)
        }
    }

    private fun observeYorumYapanKullanici(){
        viewModel.yorumYapanKullanici.observe(viewLifecycleOwner,Observer{
            when(it) {
                is BaseResourceEvent.Error->{
                    showSnackBar(requireView(),
                        requireView().context.getString(R.string.profilSayfaHata),
                        ERROR
                    )
                }
                is BaseResourceEvent.Success->{
                    kullanici = it.data!!
                    binding.kitapYorumYazanImageViewId.let {
                        it.getCircleImageFromUrl(kullanici.resim,it)
                    }
                }
            }
        })
    }

    private fun observeKitapDetay(view:View){
        viewModel.selectedKitap.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapDetayProgresBar.showComponent()
                }
                is BaseResourceEvent.Error->{
                    binding.kitapDetayErrorLayoutId.showComponent()
                    getFragmentView().hideComponents(binding.kitapDetayPanelLayoutId,binding.kitapDetayGenelBilgilerCardId,binding.kitapDetayProgresBar)
                }
                is BaseResourceEvent.Success->{
                    getFragmentView().showComponents(binding.kitapDetayPanelLayoutId,binding.kitapDetayGenelBilgilerCardId)
                    getFragmentView().hideComponents(binding.kitapDetayProgresBar,binding.kitapDetayErrorLayoutId)
                    selectedKitap = it.data!!
                    viewModel.kitapArsivlenmisMi(selectedKitap.kitapId!!)
                    observeKitapArsivlenmisMi()
                    initializeValues(view)
                }
            }
        })
    }

    private fun initializeValues(view: View) {
        if(isFromArsiv){
            view.kitapDetayImageId.getImageFromLocal(selectedKitap.kitapId!!,view.kitapDetayImageId)
        }else{
            view.kitapDetayImageId.getImageFromUrl(selectedKitap.kitapResimPath,view.kitapDetayImageId)
        }

        if(selectedKitap.kitapBegenilmis>0){
            view.likeImageViewId.setTint(view.context.getColor(R.color.fistikYesil))
        }

        view.ratingBarKitapPuan.rating = selectedKitap.kitapPuan
        view.yorumPuanTextViewId.text = "${selectedKitap.kitapPuan}"

        if(selectedKitap.kitapPuan == 0.0F){
            view.yorumPuanTextViewId.hideComponent()
            view.ratingBarKitapPuan.hideComponent()
            view.yorumNoPuanCardViewId.showComponent()
            addTriangleTreatment(view.noCommentCard)
        }
        view.kitapAdTextViewId.setText(selectedKitap.kitapAd)
        view.yazarAdTextViewId.setText(selectedKitap.yazarAd)
        view.kitapTurTextViewId.setText(selectedKitap.kitapTur?.aciklama)
        view.yayinEviTextViewId.setText(selectedKitap.yayineviModel?.aciklama)
        view.kitapDetayAciklamaTextId.setText(selectedKitap.kitapAciklama)
        view.alinmaTarTextViewId.setText(formatDate(selectedKitap.alinmatarihi!!,"dd.MM.yyyy"))
        view.kitapDetayAciklamaTextId.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                view.kitapDetayAciklamaTextId.viewTreeObserver.removeOnPreDrawListener(this)
                if(view.kitapDetayAciklamaTextId.lineCount>4){
                    view.kitapDetayAciklamaTextId.maxLines = 4
                    view.viewMoreImageIdLayout.showComponent()
                    view.viewMoreImageId.showComponent()
                }
                return true
            }
        })
    }

    override fun onStartFragment() {
        binding.viewMoreImageId?.setOnClickListener {
            val action = KitapDetayFragmentDirections.
            actionKitapDetayFragmentToKitapAciklamaBottomSheetDialogFragment(binding.kitapDetayAciklamaTextId.text.toString())
            findNavController().navigate(action)
        }

        binding.shareImageViewId?.setOnClickListener {
            viewModel.prepareShareKitap(selectedKitap)
            observeShareUri()
        }

        binding.kitapArsivleImageViewId.setOnClickListener {
            viewModel.kitapArsivle(selectedKitap)
            observeKitapArsiv(it)
        }

        binding.kitapArsivCikarImageViewId.setOnClickListener {
            viewModel.kitapArsivdenCikar(selectedKitap)
            observeKitapArsivSilme(it)
        }

        if(isFromArsiv){
            binding.likeImageViewId.hideComponent()
        }
        binding.likeImageViewId.setOnClickListener {
            viewModel.kitapBegenmeIslem(selectedKitap.kitapId!!,selectedKitap.kitapBegenilmis)
            observeKitapBegenme(it)
        }

        binding.kitapDetayBackImage.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.kitapYorumPanel?.setOnClickListener {
            KitapYorumBottomSheetDialogFragment(kullanici,selectedKitap,viewModel)
                .show(requireFragmentManager(),null)
        }

        binding.pdfGosterFoo.setOnClickListener {
            //findNavController().navigate(R.id.kotlinKitapViewFooFragment)
            viewModel.downloadKitapPdf()
            observeKitapPdfIndirFoo()
        }
    }

    private fun observeShareUri() {
        viewModel.shareKitapUri.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapDetayProgresBar.showComponent()
                }
                is BaseResourceEvent.Error->{
                    binding.kitapDetayProgresBar.hideComponent()
                    showSnackBar(requireView(),
                        requireView().context.getString(R.string.kitapShareError),
                        ERROR
                    )
                }
                is BaseResourceEvent.Success->{
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    shareIntent.setType("image/png")
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    shareIntent.putExtra(Intent.EXTRA_TEXT,selectedKitap?.kitapAd+" "+requireContext().resources.getString(R.string.paylasimKitapAdText))
                    sharedImageUri = FileProvider.getUriForFile(requireContext(),
                    requireContext().applicationContext.packageName+".provider",it.data!!)
                    shareIntent.putExtra(Intent.EXTRA_STREAM, sharedImageUri)
                    binding.kitapDetayProgresBar.hideComponent()
                    resultLauncher.launch(Intent.createChooser(shareIntent, requireContext().resources.getString(R.string.shareLabel)))
                }
            }
        })
    }

    private fun observeKitapArsiv(view:View){
        viewModel.arsivKitap.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Error->{
                    showSnackBar(view,it.message!!, ERROR)
                }
                is BaseResourceEvent.Success->{
                    showSnackBar(view,it.data!!, SUCCESS)
                    binding.kitapArsivleImageViewId.hideComponent()
                    binding.kitapArsivCikarImageViewId.showComponent()
                }
            }
        })
    }

    private fun observeKitapArsivlenmisMi(){
        viewModel.kitapArsivMevcut.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Error->{
                }
                is BaseResourceEvent.Success->{
                    binding.kitapArsivCikarImageViewId.showComponent()
                    binding.kitapArsivleImageViewId.hideComponent()
                }
            }
        })
    }

    private fun observeKitapArsivSilme(view: View) {
        viewModel.arsivKitapSil.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Error->{
                    showSnackBar(getFragmentView(),it.message!!, ERROR)
                }
                is BaseResourceEvent.Success->{
                    showSnackBar(view,it.data!!, SUCCESS)
                    binding.kitapArsivleImageViewId.showComponent()
                    binding.kitapArsivCikarImageViewId.hideComponent()
                }
            }
        })
    }

    private fun observeKitapBegenme(view:View){
        viewModel.kitapBegenme.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapDetayProgresBar.showComponent()
                }
                is BaseResourceEvent.Error->{
                    binding.kitapDetayProgresBar.hideComponent()
                    showSnackBar(view,it.data!!.statusMessage, ERROR)
                }
                is BaseResourceEvent.Success->{
                    binding.kitapDetayProgresBar.hideComponent()
                    showSnackBar(view,it.data!!.statusMessage, SUCCESS)
                    if(selectedKitap.kitapBegenilmis == 0){
                        binding.likeImageViewId.setTint(view.context.getColor(R.color.fistikYesil))
                        selectedKitap.kitapBegenilmis = 1
                    }else{
                        binding.likeImageViewId.setTint(view.context.getColor(R.color.white))
                        selectedKitap.kitapBegenilmis = 0
                    }
                }
            }
        })
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
    }

    private fun addTriangleTreatment(card:MaterialCardView){
        val triangleSize = getResources().getDimension(R.dimen.triangle_size)
        val markerEdgeTreatment = MarkerEdgeTreatment(triangleSize)
        val offsetEdgeTreatment = OffsetEdgeTreatment(markerEdgeTreatment,triangleSize)
        card.shapeAppearanceModel = card.shapeAppearanceModel.toBuilder()
            .setBottomEdge(offsetEdgeTreatment)
            .build()
    }

    private fun observeKitapPdfIndirFoo() {
        viewModel.kitapPdfFoo.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapDetayProgresBar.showComponent()
                }
                is BaseResourceEvent.Error->{

                }
                is BaseResourceEvent.Success->{
                    binding.kitapDetayProgresBar.hideComponent()
                    val action = KitapDetayFragmentDirections.actionKitapDetayFragmentToKotlinKitapViewFooFragment(
                        FileDataModel(it.data!!,"kotlininaction")
                    )
                    findNavController().navigate(action)
                }
            }
        })
    }
}