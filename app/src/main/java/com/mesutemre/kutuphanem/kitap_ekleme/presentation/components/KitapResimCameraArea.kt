package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.size
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuWhiteBold

@Composable
fun KitapResimCameraArea(
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onUseCase: (UseCase) -> Unit = { },
    onTakePicture: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Transparent,
    ) {
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
                onUseCase(
                    Preview.Builder()
                        .build()
                        .also {
                            Log.d("COORDINATES", "PreviewView Size : " + previewView.size)
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                )
                previewView
            })
        SoftwareLayerComposable(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                //.background(MaterialTheme.colorPalette.black.copy(alpha = 0.4f))
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.sdp)
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.kitap_ekleme_cameraTitle),
                        style = MaterialTheme.typography.smallUbuntuWhiteBold
                    )
                    /*Row(
                        modifier = Modifier
                            .padding(top = 12.sdp)
                            .height(300.sdp)
                            .width(200.sdp)
                            .border(
                                width = 1.sdp,
                                color = MaterialTheme.colorPalette.turuncu,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Canvas(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            drawRect(
                                color = Color.Transparent,
                                size = size,
                                blendMode = BlendMode.Clear
                            )
                        }
                    }*/
                }

                Icon(
                    Icons.Filled.Circle,
                    modifier = Modifier
                        .size(100.sdp)
                        .padding(bottom = 48.sdp)
                        .align(Alignment.BottomCenter)
                        .rippleClick {
                            onTakePicture()
                        },
                    contentDescription = stringResource(id = R.string.kitapEklemeResimArea),
                    tint = MaterialTheme.colorPalette.white
                )
                Icon(
                    Icons.Filled.Close,
                    modifier = Modifier
                        .size(64.sdp)
                        .padding(start = 16.sdp, bottom = 24.sdp)
                        .align(Alignment.BottomStart)
                        .rippleClick {

                        },
                    contentDescription = stringResource(id = R.string.kitapEklemeResimArea),
                    tint = MaterialTheme.colorPalette.white
                )

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_front_camera),
                    modifier = Modifier
                        .size(64.sdp)
                        .padding(bottom = 24.sdp, end = 16.sdp)
                        .align(Alignment.BottomEnd),
                    contentDescription = stringResource(id = R.string.kitap_ekleme_onKameraLabel),
                    tint = MaterialTheme.colorPalette.white
                )
            }
        }

    }
}

@Composable
private fun SoftwareLayerComposable(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AndroidView(
        factory = { context ->
            ComposeView(context).apply {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }
        },
        update = { composeView ->
            composeView.setContent(content)
        },
        modifier = modifier
    )
}