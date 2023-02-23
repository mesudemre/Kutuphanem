package com.mesutemre.kutuphanem.kitap_ekleme.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun ColumnScope.CroppedKitapResimArea(
    croppedImage: ImageBitmap,
    onRemoveImage: () -> Unit
) {
    Row(
        modifier = Modifier
            .align(alignment = Alignment.CenterHorizontally)
    ) {
        Image(
            modifier = Modifier
                .height(150.sdp)
                .width(100.sdp)
                .alpha(0.6f),
            bitmap = croppedImage,
            contentDescription = stringResource(id = R.string.kitap_ekleme_croppedImageContentDescription)
        )
        Icon(
            Icons.Filled.RemoveCircle,
            modifier = Modifier
                .offset(x = (-8).sdp)
                .rippleClick {
                    onRemoveImage()
                },
            contentDescription = stringResource(id = R.string.kitap_ekleme_removeCroppedImageContentDescription),
            tint = MaterialTheme.colorPalette.secondaryGray
        )
    }
}