package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.mesutemre.kutuphanem_ui.extensions.executor
import com.mesutemre.kutuphanem_ui.extensions.getCameraProvider
import com.mesutemre.kutuphanem_ui.extensions.kutuphanemTakePicture
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun KitapResimCameraCaptureArea(
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onSuccessCaptured: (File) -> Unit
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
                    imageCaptureUseCase.kutuphanemTakePicture(
                        context.executor,
                        imageName = SimpleDateFormat(
                            "yyyy-MM-dd-HH-mm-ss-SSS", Locale.US
                        ).format(System.currentTimeMillis()) + ".jpg"
                    ).let {
                        onSuccessCaptured(it)
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