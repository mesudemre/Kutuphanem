package com.mesutemre.kutuphanem.fragments;

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseFragment
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.databinding.KitapEklemeFragmentBinding
import com.mesutemre.kutuphanem.fragments.dialogs.DogumTarihiDialogFragment
import com.mesutemre.kutuphanem.fragments.dialogs.SelectionDialogFragment
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.*
import com.mesutemre.kutuphanem.util.*
import com.mesutemre.kutuphanem.viewmodels.KitapEklemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

@AndroidEntryPoint
class KitapEklemeFragment: BaseFragment<KitapEklemeFragmentBinding>() {

    private val viewModel: KitapEklemeViewModel by viewModels()
    private var imageCapture:ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProvider: ProcessCameraProvider
    private var savedKitapUri:Uri? = null
    private var kameraIzin:Int = 0
    private var selectedKitapTur:Int = 0
    private var selectedYayinevi:Int = 0
    private lateinit var kitapTurler:List<KitapturModel>;
    private lateinit var yayinEvler:List<YayineviModel>;

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> KitapEklemeFragmentBinding
            = KitapEklemeFragmentBinding::inflate
    override val layoutName = "kitap_ekleme_fragment.xml";

    override fun onCreateFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState != null){
            savedKitapUri = savedInstanceState.getParcelable("kitapImage")
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
        outputDirectory = createOutputDirectory(requireContext());
    }

    override fun onStartFragment() {
        viewModel.initKitapEklemeSpinnerListe()
        observeSpinnerList();

        binding.kitapEklemeBackImageId.setOnClickListener {
            onBackPressed();
        }

        binding.editTextAlinmaTar.setOnClickListener {
            val editText = it as TextInputEditText
            DogumTarihiDialogFragment(editText, Date()).show(requireFragmentManager(),null)
        }

        binding.kitapImageCapId.setOnClickListener {
            kameraIzin = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            if(kameraIzin != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
            }else{
                binding.photoCekLayoutId.showComponent();
                binding.kitapGenelBilgiLayoutId.hideComponent();
                startCamera();
            }
        }

        binding.fotoCekButtonId.setOnClickListener {
            takeKitapPhoto()
        }

        binding.fotoCekIptalButtonId.setOnClickListener {
            binding.photoCekLayoutId.hideComponent();
            binding.kitapGenelBilgiLayoutId.showComponent();
            cameraProvider.unbindAll()
        }

        binding.yayinEviSpinnerId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedYE = parent!!.getItemAtPosition(position) as YayineviModel;
                selectedYayinevi = selectedYE.yayinEviId!!;
            }
        }

        if(savedKitapUri != null){
            binding.kitapImageCapId.setImageURI(savedKitapUri)
        }

        binding.selectKitapTurCardId.setOnClickListener {
            var selectList = mutableListOf<SelectItemModel>();
            for (kt in kitapTurler) {
                selectList.add(SelectItemModel(kt.kitapTurId!!,kt.aciklama!!));
            }
            SelectionDialogFragment(
                it.context.getString(R.string.kitapSelectionTurLabel)
                ,true
                ,selectList
                ,::onSelectKitapTur)
                .show(requireFragmentManager(),null);
        }

        binding.selectYayineviCardId.setOnClickListener {
            var selectList = mutableListOf<SelectItemModel>();
            for (ye in yayinEvler) {
                selectList.add(SelectItemModel(ye.yayinEviId!!,ye.aciklama!!));
            }
            SelectionDialogFragment(
                it.context.getString(R.string.kitapSelectionYayineviLabel),
                true,
                selectList,
                ::onSelectYayinevi).show(requireFragmentManager(),null);
        }

        binding.textInputKitapAd.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputKitapAd))
        binding.textInputYazarAd.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputYazarAd))
        binding.textInputAlinmaTar.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputAlinmaTar))
        binding.textInputKitapAciklama.editText!!.addTextChangedListener(TextInputErrorClearListener(binding.textInputKitapAciklama))

        binding.kitapKaydetButtonId.setOnClickListener {
            val kitapAd = binding.textInputKitapAd.editText?.text.toString()
            val yazarAd = binding.textInputYazarAd.editText?.text.toString()
            val alinmaTar = binding.textInputAlinmaTar.editText?.text.toString()
            val kitapAciklama = binding.textInputKitapAciklama.editText?.text.toString()

            if(savedKitapUri == null){
                showSnackBar(it,it.context.getString(R.string.kitapResimErrorText), WARNING)
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(kitapAd)){
                binding.textInputKitapAd.error = it.context.getString(R.string.kitapAdErrorText)
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(yazarAd)){
                binding.textInputYazarAd.error = it.context.getString(R.string.yazarAdErrorText)
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(alinmaTar)){
                binding.textInputAlinmaTar.error = it.context.getString(R.string.alinmaTarErrorText)
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(kitapAciklama)){
                binding.textInputKitapAciklama.error = it.context.getString(R.string.kitapAciklamaErrorText)
                return@setOnClickListener
            }

            if(selectedKitapTur == 0){
                showSnackBar(it,it.context.getString(R.string.kitapTurErrorText),WARNING)
                return@setOnClickListener
            }

            if(selectedYayinevi == 0){
                showSnackBar(it,it.context.getString(R.string.yayinEviErrorText),WARNING)
                return@setOnClickListener
            }

            val jsonObj: JSONObject = JSONObject()
            jsonObj.put("kitapAd",kitapAd)
            jsonObj.put("yazarAd",yazarAd)

            val kitapTurObj:JSONObject = JSONObject()
            kitapTurObj.put("id",selectedKitapTur)
            jsonObj.put("kitapTur",kitapTurObj)

            val yayinEviObj:JSONObject = JSONObject()
            yayinEviObj.put("id",selectedYayinevi)
            jsonObj.put("yayinEvi",yayinEviObj)

            val alinmaTarSon = alinmaTar.split(".")[2]+"-"+alinmaTar.split(".")[1]+"-"+alinmaTar.split(".")[0];
            jsonObj.put("alinmatarihi",alinmaTarSon)
            jsonObj.put("kitapAciklama",kitapAciklama)

            viewModel.kitapKaydet(jsonObj.toString(),savedKitapUri!!,it.context);
            observeKitapKaydi(it)
            observeKitapResimYukleme(it)
        }
    }

    private fun onSelectKitapTur(selectedItem:SelectItemModel){
        binding.selectedKitapTurMaterialTextViewId.setText(selectedItem.selectedItemLabel);
        selectedKitapTur = selectedItem.selectedItemValue;
    }

    private fun onSelectYayinevi(selectedItem:SelectItemModel){
        binding.selectedYayineviMaterialTextViewId.setText(selectedItem.selectedItemLabel);
        selectedYayinevi = selectedItem.selectedItemValue;
    }

    private fun observeKitapKaydi(view:View){
        viewModel.kitapKaydetResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapKayitProgressLayoutId.showComponent();
                    requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
                is BaseResourceEvent.Error->{
                    binding.kitapKayitProgressLayoutId.hideComponent();
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    showSnackBar(view,view.context.resources.getString(R.string.kitapKaydiHataText),ERROR)
                }
                is BaseResourceEvent.Success->{
                    binding.kitapKayitProgressLayoutId.hideComponent();
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    showSnackBar(view,view.context.resources.getString(R.string.kitapKaydiBasarli),SUCCESS)
                }
            }
        });
    }

    private fun observeKitapResimYukleme(view:View){
        viewModel.kitapResimYukleResourceEvent.observe(viewLifecycleOwner, Observer {
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapResimYukleProgressLayoutId.showComponent();
                    requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                }
                is BaseResourceEvent.Error->{
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    binding.kitapResimYukleProgressLayoutId.hideComponent();
                    showSnackBar(view,view.context.resources.getString(R.string.kitapResmiYuklemeHata),ERROR)
                }
                is BaseResourceEvent.Success->{
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    binding.kitapResimYukleProgressLayoutId.hideComponent();
                    showSnackBar(view,it.data!!.statusMessage,SUCCESS);
                    formTemizle();
                }
            }
        });
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.kitapImageViewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview,imageCapture)

            } catch(exc: Exception) {

            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takeKitapPhoto(){
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss-SSS", Locale.US
            ).format(System.currentTimeMillis()) + ".jpg");
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("HATA","Hata oldu!!! : "+exc.toString())
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    savedKitapUri = Uri.fromFile(photoFile)
                    binding.kitapImageCapId.setImageURI(savedKitapUri)
                    binding.photoCekLayoutId.hideComponent();
                    binding.kitapGenelBilgiLayoutId.showComponent();
                    cameraProvider.unbindAll()
                }
            })
    }

    private fun formTemizle(){
        savedKitapUri = null;
        binding.kitapImageCapId.setImageDrawable(binding.kitapImageCapId.context.getDrawable(R.drawable.ic_baseline_photo_camera_48));
        binding.editTextKitapAd.clearContent(binding.editTextKitapAd);
        binding.editTextYazarAd.clearContent(binding.editTextYazarAd);
        binding.editTextAlinmaTar.clearContent(binding.editTextAlinmaTar);
        binding.editTextKitapAciklama.clearContent(binding.editTextKitapAciklama);
        selectedKitapTur = 0;
        //binding.kitapTurlerSpinnerId.setSelection(selectedKitapTur);
        selectedYayinevi = 0;
        //binding.yayinEviSpinnerId.setSelection(selectedYayinevi);
    }

    private fun observeSpinnerList(){
        viewModel.kitapTurListeResourceEvent.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapEklemeKitapTurProgressBarId.showComponent();
                    binding.kitapEklemeKitapTurHataTextView.hideComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.kitapEklemeKitapTurProgressBarId.hideComponent();
                    binding.kitapEklemeKitapTurHataTextView.showComponent();
                }
                is BaseResourceEvent.Success->{
                    getFragmentView().hideComponents(binding.kitapEklemeKitapTurProgressBarId,binding.kitapEklemeKitapTurHataTextView);
                    kitapTurler = it.data!!;
                }
            }
        });

        viewModel.yayinEviListeResourceEvent.observe(viewLifecycleOwner,Observer{
            when(it){
                is BaseResourceEvent.Loading->{
                    binding.kitapEklemeYayinEviProgressBarId.showComponent();
                    binding.kitapEklemeYayinEviHataTextView.hideComponent();
                }
                is BaseResourceEvent.Error->{
                    binding.kitapEklemeYayinEviProgressBarId.hideComponent();
                    binding.kitapEklemeYayinEviHataTextView.showComponent();
                }
                is BaseResourceEvent.Success->{
                    yayinEvler = it.data!!;
                    getFragmentView().hideComponents(binding.kitapEklemeYayinEviProgressBarId,binding.kitapEklemeYayinEviHataTextView);
                }
            }
        });
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_REQUEST_CODE){
            kameraIzin = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA)
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                binding.photoCekLayoutId.showComponent();
                binding.kitapGenelBilgiLayoutId.hideComponent();
                startCamera()
            }
        }
    }

    override fun destroyOthers() {
        cameraExecutor.shutdown()
    }
}