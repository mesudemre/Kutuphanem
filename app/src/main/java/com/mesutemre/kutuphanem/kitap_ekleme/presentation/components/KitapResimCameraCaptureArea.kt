package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components

import android.util.Log
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.mesutemre.kutuphanem.kitap_ekleme.data.CameraOpenType
import com.mesutemre.kutuphanem_ui.extensions.executor
import com.mesutemre.kutuphanem_ui.extensions.getCameraProvider
import com.mesutemre.kutuphanem_ui.extensions.kutuphanemTakePicture
import com.mesutemre.kutuphanem_ui.extensions.kutuphanemTakePictureForTextRecognation
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun KitapResimCameraCaptureArea(
    cameraType: CameraOpenType,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onSuccessCaptured: (File) -> Unit,
    onSuccessImageInfo: (ImageProxy) -> Unit
) {
    Box {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val lifecycleOwner = LocalLifecycleOwner.current
        var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
        val imageCaptureUseCase by remember {
            mutableStateOf(
                ImageCapture.Builder()
                    .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .build()
            )
        }
        KitapResimCameraArea(
            onUseCase = {
                previewUseCase = it
            },
            onTakePicture = {
                coroutineScope.launch {
                    when (cameraType.ordinal) {
                        CameraOpenType.KITAP_RESIM.ordinal -> {
                            imageCaptureUseCase.kutuphanemTakePicture(
                                context.executor,
                                imageName = SimpleDateFormat(
                                    "yyyy-MM-dd-HH-mm-ss-SSS", Locale.US
                                ).format(System.currentTimeMillis()) + ".jpg"
                            ).let {
                                onSuccessCaptured(it)
                            }
                        }
                        CameraOpenType.KITAP_ACIKLAMA_TEXT_RECOGNIZE.ordinal -> {
                            imageCaptureUseCase.kutuphanemTakePictureForTextRecognation(context.executor)
                                .let {
                                    onSuccessImageInfo(it)
                                }
                        }
                    }
                }
            }
        )
        LaunchedEffect(previewUseCase) {
            val cameraProvider = context.getCameraProvider()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                )
            } catch (ex: Exception) {
                Log.e("CameraCapture", "Failed to bind camera use cases", ex)
            }
        }
    }
}