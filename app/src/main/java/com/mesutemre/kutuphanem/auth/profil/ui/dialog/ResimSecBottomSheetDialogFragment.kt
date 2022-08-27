package com.mesutemre.kutuphanem.auth.profil.ui.dialog

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.databinding.KitapAciklamaBottomSheetDialogFragmentBinding
import com.mesutemre.kutuphanem.databinding.ResimSecBottomSheetDialogFragmentBinding
import com.mesutemre.kutuphanem.model.PROFIL_RESIM_EKLEME_PHOTO
import com.mesutemre.kutuphanem.util.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

class ResimSecBottomSheetDialogFragment(
    val onSelectProfilePicture:(uri:Uri)->Unit
):BottomSheetDialogFragment() {

    private lateinit var binding: ResimSecBottomSheetDialogFragmentBinding

    private var kameraIzin:Int = 0
    private var readExternalStorageIzin:Int = 0
    private lateinit var camContext: Context
    private lateinit var readExtContext: Context
    private var selectedImageUri:Uri = Uri.EMPTY
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageCapture: ImageCapture? = null
    private var cameraSelector:CameraSelector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        outputDirectory = requireContext().createOutputDirectory(PROFIL_RESIM_EKLEME_PHOTO)
        if(checkDeviceHasCamera(requireParentFragment().requireContext()) && checkDeviceHasFronCamera(requireParentFragment().requireContext())){
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        }else{
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        }
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ResimSecBottomSheetDialogFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cameraLayoutId.setOnClickListener {
            camContext = requireParentFragment().requireContext()
            kameraIzin = ContextCompat.checkSelfPermission(requireParentFragment().requireContext(), Manifest.permission.CAMERA)
            if(kameraIzin != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
            }else{
                startCameraViews()
                startCamera()
            }
        }

        binding.galeriLayoutId.setOnClickListener {
            readExtContext = requireParentFragment().requireContext()
            readExternalStorageIzin = ContextCompat.checkSelfPermission(view.context,Manifest.permission.READ_EXTERNAL_STORAGE)
            if(readExternalStorageIzin != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),READ_EXTERNAL_STORAGE_REQUEST_CODE)
            }else{
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, READ_EXTERNAL_STORAGE_REQUEST_CODE)
            }
        }

        binding.removeImageLayoutId.setOnClickListener {
            //profilImageView.getCircleImageFromResource(R.drawable.ic_baseline_person_24,profilImageView)
        }

        binding.switchCameraImageId.setOnClickListener {
            if(cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA){
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            }else{
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            }
            startCamera()
        }

        binding.profilPhotoIptalImageId.setOnClickListener {
            cameraProvider?.unbindAll()
            stopCameraViews()
        }

        binding.profilPhotoCekImageId.setOnClickListener {
            takeProfilPhoto()
        }
    }

    private fun takeProfilPhoto(){
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,"profil_resim.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("HATA","Hata oldu!!! : "+exc.toString())
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    selectedImageUri = Uri.fromFile(photoFile)
                    onSelectProfilePicture(selectedImageUri)
                    stopCameraViews()
                    cameraProvider?.unbindAll()
                    dismiss()
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_REQUEST_CODE){
            kameraIzin = ContextCompat.checkSelfPermission(camContext,Manifest.permission.CAMERA)
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startCameraViews()
                startCamera()
            }
        }else if(requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE){
            readExternalStorageIzin = ContextCompat.checkSelfPermission(readExtContext,Manifest.permission.READ_EXTERNAL_STORAGE)
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, READ_EXTERNAL_STORAGE_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val uri: Uri = data?.data!!
            onSelectProfilePicture(uri)
            selectedImageUri = uri
        }
        dismiss()
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    //it.setSurfaceProvider(profilResimImageViewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .build()


            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    this, cameraSelector!!, preview,imageCapture)

            } catch(exc: Exception) {

            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun startCameraViews(){
        /*profilResimCekLayoutId.showComponent()
        profilFotografTitleTextViewId.hideComponent()
        seceneklerLayoutId.hideComponent()
        (dialog as? BottomSheetDialog)?.let {
            it.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }*/
    }

    private fun stopCameraViews(){
        /*profilResimCekLayoutId.hideComponent()
        profilFotografTitleTextViewId.showComponent()
        seceneklerLayoutId.showComponent()*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(cameraProvider != null ){
            cameraProvider?.shutdown()
        }
    }
}