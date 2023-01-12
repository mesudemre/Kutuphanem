package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCameraFront
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem_ui.extensions.getCameraProvider
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun KitapResimCameraArea(
    coroutineScope: CoroutineScope,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                // CameraX Preview UseCase
                val previewUseCase = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                coroutineScope.launch {
                    val cameraProvider = context.getCameraProvider()
                    try {
                        // Must unbind the use-cases before rebinding them.
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner, cameraSelector, previewUseCase
                        )
                    } catch (ex: Exception) {
                        Log.e("CameraPreview", "Use case binding failed", ex)
                    }
                }

                previewView
            })
        Row(
            modifier = Modifier
                .height(300.sdp)
                .width(200.sdp)
                .alpha(1f)
                .border(
                    width = 1.sdp,
                    color = MaterialTheme.colorPalette.turuncu,
                    shape = MaterialTheme.shapes.medium
                )
                .align(Alignment.Center)
        ) {}
        Icon(
            Icons.Filled.Circle,
            modifier = Modifier
                .size(100.sdp)
                .padding(bottom = 48.sdp)
                .align(Alignment.BottomCenter),
            contentDescription = stringResource(id = R.string.kitapEklemeResimArea),
            tint = MaterialTheme.colorPalette.white
        )
        Icon(
            Icons.Filled.Close,
            modifier = Modifier
                .size(64.sdp)
                .padding(start = 16.sdp, bottom = 24.sdp)
                .align(Alignment.BottomStart),
            contentDescription = stringResource(id = R.string.kitapEklemeResimArea),
            tint = MaterialTheme.colorPalette.white
        )

        Icon(
            Icons.Filled.PhotoCameraFront,
            modifier = Modifier
                .size(64.sdp)
                .padding(bottom = 24.sdp, end = 16.sdp)
                .align(Alignment.BottomEnd),
            contentDescription = stringResource(id = R.string.kitapEklemeResimArea),
            tint = MaterialTheme.colorPalette.white
        )
    }
}