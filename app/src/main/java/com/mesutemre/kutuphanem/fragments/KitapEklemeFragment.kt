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
import android.widget.ArrayAdapter
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.KitapEklemeFragmentBinding
import com.mesutemre.kutuphanem.fragments.dialogs.DogumTarihiDialogFragment
import com.mesutemre.kutuphanem.listener.TextInputErrorClearListener
import com.mesutemre.kutuphanem.model.KitapturModel
import com.mesutemre.kutuphanem.model.SnackTypeEnum
import com.mesutemre.kutuphanem.model.YayineviModel
import com.mesutemre.kutuphanem.util.CAMERA_REQUEST_CODE
import com.mesutemre.kutuphanem.util.clearContent
import com.mesutemre.kutuphanem.util.createOutputDirectory
import com.mesutemre.kutuphanem.util.showSnackBar
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
class KitapEklemeFragment:Fragment() {

    private var kitapEklemeBinding:KitapEklemeFragmentBinding? = null
    private val viewModel: KitapEklemeViewModel by viewModels()
    private var imageCapture:ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProvider: ProcessCameraProvider
    private var savedKitapUri:Uri? = null
    private var kameraIzin:Int = 0
    private var selectedKitapTur:Int = 0
    private var selectedYayinevi:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null){
            savedKitapUri = savedInstanceState.getParcelable("kitapImage")
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
        outputDirectory = createOutputDirectory(requireContext());
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        kitapEklemeBinding = KitapEklemeFragmentBinding.inflate(inflater)
        return kitapEklemeBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initKitapEklemeSpinnerListe()
        observeSpinnerList()

        kitapEklemeBinding!!.kitapEklemeBackImageId.setOnClickListener {
            requireActivity().onBackPressed()
        }

        kitapEklemeBinding!!.editTextAlinmaTar.setOnClickListener {
            val editText = it as TextInputEditText
            DogumTarihiDialogFragment(editText, Date()).show(requireFragmentManager(),null)
        }

        kitapEklemeBinding!!.kitapImageCapId.setOnClickListener {
            kameraIzin = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            if(kameraIzin != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
            }else{
                kitapEklemeBinding!!.photoCekLayoutId.visibility = View.VISIBLE
                startCamera()
            }
        }

        kitapEklemeBinding!!.fotoCekButtonId.setOnClickListener {
            takeKitapPhoto()
        }

        kitapEklemeBinding!!.fotoCekIptalButtonId.setOnClickListener {
            kitapEklemeBinding!!.photoCekLayoutId.visibility = View.GONE
            cameraProvider.unbindAll()
        }

        kitapEklemeBinding!!.kitapTurlerSpinnerId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedKT = parent!!.getItemAtPosition(position) as KitapturModel;
                selectedKitapTur = selectedKT.kitapTurId!!;
            }

        }

        kitapEklemeBinding!!.yayinEviSpinnerId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedYE = parent!!.getItemAtPosition(position) as YayineviModel;
                selectedYayinevi = selectedYE.yayinEviId!!;
            }
        }

        if(savedKitapUri != null){
            kitapEklemeBinding!!.kitapImageCapId.setImageURI(savedKitapUri)
        }

        kitapEklemeBinding!!.textInputKitapAd.editText!!.addTextChangedListener(TextInputErrorClearListener(kitapEklemeBinding!!.textInputKitapAd))
        kitapEklemeBinding!!.textInputYazarAd.editText!!.addTextChangedListener(TextInputErrorClearListener(kitapEklemeBinding!!.textInputYazarAd))
        kitapEklemeBinding!!.textInputAlinmaTar.editText!!.addTextChangedListener(TextInputErrorClearListener(kitapEklemeBinding!!.textInputAlinmaTar))
        kitapEklemeBinding!!.textInputKitapAciklama.editText!!.addTextChangedListener(TextInputErrorClearListener(kitapEklemeBinding!!.textInputKitapAciklama))

        kitapEklemeBinding!!.kitapKaydetButtonId.setOnClickListener {
            val kitapAd = kitapEklemeBinding!!.textInputKitapAd.editText?.text.toString()
            val yazarAd = kitapEklemeBinding!!.textInputYazarAd.editText?.text.toString()
            val alinmaTar = kitapEklemeBinding!!.textInputAlinmaTar.editText?.text.toString()
            val kitapAciklama = kitapEklemeBinding!!.textInputKitapAciklama.editText?.text.toString()

            if(savedKitapUri == null){
                showSnackBar(it,it.context.getString(R.string.kitapResimErrorText),SnackTypeEnum.WARNING)
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(kitapAd)){
                kitapEklemeBinding!!.textInputKitapAd.error = it.context.getString(R.string.kitapAdErrorText)
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(yazarAd)){
                kitapEklemeBinding!!.textInputYazarAd.error = it.context.getString(R.string.yazarAdErrorText)
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(alinmaTar)){
                kitapEklemeBinding!!.textInputAlinmaTar.error = it.context.getString(R.string.alinmaTarErrorText)
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(kitapAciklama)){
                kitapEklemeBinding!!.textInputKitapAciklama.error = it.context.getString(R.string.kitapAciklamaErrorText)
                return@setOnClickListener
            }

            if(selectedKitapTur == 0){
                showSnackBar(it,it.context.getString(R.string.kitapTurErrorText),SnackTypeEnum.WARNING)
                return@setOnClickListener
            }

            if(selectedYayinevi == 0){
                showSnackBar(it,it.context.getString(R.string.yayinEviErrorText),SnackTypeEnum.WARNING)
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

    private fun observeKitapKaydi(view:View){
        viewModel.kitapKaydet.observe(viewLifecycleOwner, Observer { kitapKaydet->
            kitapEklemeBinding!!.kitapKayitProgressLayoutId.visibility = View.GONE
            kitapKaydet.let {
                if(kitapKaydet){
                    showSnackBar(view,view.context.resources.getString(R.string.kitapKaydiBasarli),SnackTypeEnum.SUCCESS)
                }else{
                    showSnackBar(view,view.context.resources.getString(R.string.kitapKaydiHataText),SnackTypeEnum.ERROR)
                }
            }
        })

        viewModel.kitapKaydetLoading.observe(viewLifecycleOwner,Observer{it->
            if(it){
                kitapEklemeBinding!!.kitapKayitProgressLayoutId.visibility = View.VISIBLE
                requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }else{
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                kitapEklemeBinding!!.kitapKayitProgressLayoutId.visibility = View.GONE
            }
        })

        viewModel.kitapKaydetHata.observe(viewLifecycleOwner,Observer{it->
            kitapEklemeBinding!!.kitapKayitProgressLayoutId.visibility = View.GONE
            if(it){
                showSnackBar(view,view.context.resources.getString(R.string.kitapKaydiHataText),SnackTypeEnum.ERROR)
            }
        })
    }

    private fun observeKitapResimYukleme(view:View){
        viewModel.kitapResimYukle.observe(viewLifecycleOwner, Observer { response->
           if(response.statusCode.equals("200")){
               showSnackBar(view,response.statusMessage,SnackTypeEnum.SUCCESS);
               formTemizle();
           }else{
               showSnackBar(view,view.context.resources.getString(R.string.kitapResimErrorText),SnackTypeEnum.ERROR)
           }
        })

        viewModel.kitapResimYukleLoading.observe(viewLifecycleOwner,Observer{it->
           if(it) {
               kitapEklemeBinding!!.kitapResimYukleProgressLayoutId.visibility = View.VISIBLE
               requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                   WindowManager.LayoutParams.FLAG_DIM_BEHIND);
           }else{
               requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
               kitapEklemeBinding!!.kitapResimYukleProgressLayoutId.visibility = View.GONE
           }
        })

        viewModel.kitapResimYukleHata.observe(viewLifecycleOwner,Observer{it->
            if(it){
                showSnackBar(view,view.context.resources.getString(R.string.kitapResmiYuklemeHata),SnackTypeEnum.ERROR)
            }
        })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(kitapEklemeBinding!!.kitapImageViewFinder.surfaceProvider)
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
                    kitapEklemeBinding!!.kitapImageCapId.setImageURI(savedKitapUri)
                    kitapEklemeBinding!!.photoCekLayoutId.visibility = View.GONE
                    cameraProvider.unbindAll()
                }
            })
    }

    private fun observeSpinnerList(){
        viewModel.kitapEklemeKitapTurListe.observe(viewLifecycleOwner,Observer{kitapTurListe->
            val adapter = ArrayAdapter(kitapEklemeBinding!!.kitapTurlerSpinnerId.context,
                android.R.layout.simple_spinner_dropdown_item,
                kitapTurListe)
            kitapEklemeBinding!!.kitapTurlerSpinnerId.adapter = adapter
        })

        viewModel.kitapEklemeKitapTurLoading.observe(viewLifecycleOwner,Observer{it->
            if(it){
                kitapEklemeBinding!!.kitapEklemeKitapTurProgressBarId.visibility = View.VISIBLE
                kitapEklemeBinding!!.kitapEklemeKitapTurHataTextView.visibility = View.GONE
            }else{
                kitapEklemeBinding!!.kitapEklemeKitapTurProgressBarId.visibility = View.GONE
            }
        })

        viewModel.kitapEklemeKitapTurError.observe(viewLifecycleOwner,Observer{it->
            if(it){
                kitapEklemeBinding!!.kitapEklemeKitapTurHataTextView.visibility = View.VISIBLE
                kitapEklemeBinding!!.kitapTurlerSpinnerId.visibility = View.GONE
            }else{
                kitapEklemeBinding!!.kitapEklemeKitapTurHataTextView.visibility = View.GONE
            }
        })

        viewModel.kitapEklemeYayinEviListe.observe(viewLifecycleOwner,Observer{yayinEviListe->
            val adapter = ArrayAdapter(kitapEklemeBinding!!.yayinEviSpinnerId.context,
                android.R.layout.simple_spinner_dropdown_item,
                yayinEviListe)
            kitapEklemeBinding!!.yayinEviSpinnerId.adapter = adapter
        })

        viewModel.kitapEklemeYayinEviLoading.observe(viewLifecycleOwner,Observer{it->
            if(it){
                kitapEklemeBinding!!.kitapEklemeYayinEviProgressBarId.visibility = View.VISIBLE
                kitapEklemeBinding!!.kitapEklemeYayinEviHataTextView.visibility = View.GONE
            }else{
                kitapEklemeBinding!!.kitapEklemeYayinEviProgressBarId.visibility = View.GONE
            }
        })

        viewModel.kitapEklemeYayinEviError.observe(viewLifecycleOwner,Observer{it->
            if(it){
                kitapEklemeBinding!!.kitapEklemeYayinEviHataTextView.visibility = View.VISIBLE
                kitapEklemeBinding!!.yayinEviSpinnerId.visibility = View.GONE
            }else{
                kitapEklemeBinding!!.kitapEklemeYayinEviHataTextView.visibility = View.GONE
            }
        })
    }

    private fun formTemizle(){
        savedKitapUri = null;
        kitapEklemeBinding!!.kitapImageCapId.setImageDrawable(kitapEklemeBinding!!.kitapImageCapId.context.getDrawable(R.drawable.ic_baseline_photo_camera_48));
        kitapEklemeBinding!!.editTextKitapAd.clearContent(kitapEklemeBinding!!.editTextKitapAd);
        kitapEklemeBinding!!.editTextYazarAd.clearContent(kitapEklemeBinding!!.editTextYazarAd);
        kitapEklemeBinding!!.editTextAlinmaTar.clearContent(kitapEklemeBinding!!.editTextAlinmaTar);
        kitapEklemeBinding!!.editTextKitapAciklama.clearContent(kitapEklemeBinding!!.editTextKitapAciklama);
        selectedKitapTur = 0;
        kitapEklemeBinding!!.kitapTurlerSpinnerId.setSelection(selectedKitapTur);
        selectedYayinevi = 0;
        kitapEklemeBinding!!.yayinEviSpinnerId.setSelection(selectedYayinevi);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
        this.kitapEklemeBinding = null
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
                kitapEklemeBinding!!.photoCekLayoutId.visibility = View.VISIBLE
                startCamera()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if(savedKitapUri != null){
            outState.putParcelable("kitapImage",savedKitapUri)
        }
        super.onSaveInstanceState(outState)
    }
}