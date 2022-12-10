package com.mesutemre.kutuphanem.kitap_detay.presentation.components.body

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayIlkYorumModel
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.*

@Composable
fun KitapIlkYorumItem(
    kitapDetayIlkYorumModel: KitapDetayIlkYorumModel
) {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.sdp), verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            SubcomposeAsyncImage(model = kitapDetayIlkYorumModel.yorumYazanResim,
                modifier = Modifier
                    .size(32.sdp)
                    .clip(CircleShape),
                contentDescription = kitapDetayIlkYorumModel.yorumAciklama,
                loading = {
                    if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {

                    } else {
                        SubcomposeAsyncImageContent()
                    }
                })
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.sdp, end = 24.sdp)) {
                Text(
                    text = kitapDetayIlkYorumModel.yorumYazanAdSoyad,
                    style = MaterialTheme.typography.smallUbuntuTransparentBold
                )
                Text(
                    modifier = Modifier.padding(top = 4.sdp),
                    text = kitapDetayIlkYorumModel.yorumAciklama,
                    style = MaterialTheme.typography.smallUbuntuBlack.copy(
                        lineHeight = 14.ssp
                    )
                )
            }
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorPalette.transparent,
            modifier = Modifier.padding(end = 8.sdp)
        )
    }
}