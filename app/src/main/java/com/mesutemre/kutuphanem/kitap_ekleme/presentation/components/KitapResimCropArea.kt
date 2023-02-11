package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.QA_DLG
import com.mesutemre.kutuphanem.util.customcomponents.dialog.CustomKutuphanemDialog
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.extensions.saveImageToFile
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.smarttoolfactory.cropper.ImageCropper
import com.smarttoolfactory.cropper.model.OutlineType
import com.smarttoolfactory.cropper.model.RectCropShape
import com.smarttoolfactory.cropper.settings.CropDefaults
import com.smarttoolfactory.cropper.settings.CropOutlineProperty
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun KitapResimCropArea(
    capturedImage: ImageBitmap,
    onCloseCrop: () -> Unit,
    onCompleteCrop: (ImageBitmap, File?) -> Unit
) {
    val context: Context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var cropProperties by remember {
        mutableStateOf(
            CropDefaults.properties(
                cropOutlineProperty = CropOutlineProperty(
                    OutlineType.Rect,
                    RectCropShape(0, "Rect")
                )
            )
        )
    }
    var croppedImage by remember { mutableStateOf<ImageBitmap?>(null) }
    var crop by remember { mutableStateOf(false) }
    var isCropping by remember { mutableStateOf(false) }
    var isDialogShow by remember {
        mutableStateOf(false)
    }

    if (isDialogShow) {
        CustomKutuphanemDialog(
            modifier = Modifier
                .height(200.sdp)
                .width(400.sdp),
            type = QA_DLG,
            title = stringResource(id = R.string.kitap_ekleme_cropDialogTitle),
            description = stringResource(id = R.string.kitap_ekleme_cropDialogDescription),
            onDismissDialog = {
                isDialogShow = false
            }) {
            croppedImage?.let {
                isDialogShow = false
                croppedImage = null
                val imageFile = context.saveImageToFile(it.asAndroidBitmap())
                imageFile?.let { file ->
                    coroutineScope.launch {
                        onCompleteCrop(it, Compressor.compress(
                            context, file
                        ) {
                            default()
                            destination(file)
                        })
                    }
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ImageCropper(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            imageBitmap = capturedImage,
            contentDescription = stringResource(id = R.string.kitap_ekleme_imageCropAreaContentDescription),
            crop = crop,
            cropProperties = cropProperties,
            onCropStart = {
                isCropping = true
            }
        ) {
            croppedImage = it
            isCropping = false
            crop = false
            isDialogShow = true
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorPalette.black)
                .padding(horizontal = 16.sdp)
        ) {
            Icon(
                Icons.Filled.Close,
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .size(48.sdp)
                    .rippleClick {
                        onCloseCrop()
                    },
                contentDescription = null,
                tint = MaterialTheme.colorPalette.white
            )

            Icon(
                Icons.Filled.Done,
                modifier = Modifier
                    .size(48.sdp)
                    .align(alignment = Alignment.CenterEnd)
                    .rippleClick {
                        crop = true
                    },
                contentDescription = null,
                tint = MaterialTheme.colorPalette.fistikYesil
            )
        }
    }
}