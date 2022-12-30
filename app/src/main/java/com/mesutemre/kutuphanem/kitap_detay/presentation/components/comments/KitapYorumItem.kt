package com.mesutemre.kutuphanem.kitap_detay.presentation.components.comments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_detay.domain.model.KitapDetayBottomSheetYorumModel
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlack
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuTransparent
import com.mesutemre.kutuphanem_ui.theme.ssp

@Composable
fun KitapYorumItem(
    yorumItem: KitapDetayBottomSheetYorumModel
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        SubcomposeAsyncImage(model = yorumItem.yorumYazanResim,
            modifier = Modifier
                .size(24.sdp)
                .clip(CircleShape),
            contentDescription = yorumItem.yorumAciklama,
            loading = {
                if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                } else {
                    SubcomposeAsyncImageContent()
                }
            })
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.sdp)
        ) {
            KitapYorumItemHeader(
                yorumYazanAdSoyad = yorumItem.yorumYazanAdSoyad,
                yorumYazmaTar = yorumItem.yorumYazmaTarih
            )
            var lineCount by remember {
                mutableStateOf(1)
            }
            var readContinue by remember {
                mutableStateOf(false)
            }
            if (readContinue.not()) {
                Row(modifier = Modifier.padding(top = 4.sdp)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = yorumItem.yorumAciklama,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { textLayoutResult: TextLayoutResult ->
                            lineCount = textLayoutResult.lineCount
                        },
                        style = MaterialTheme.typography.smallUbuntuBlack.copy(
                            lineHeight = 14.ssp
                        )
                    )
                    if (lineCount > 2) {
                        Text(
                            text = stringResource(id = R.string.kitapYorumDevaminiOku),
                            style = MaterialTheme.typography.smallUbuntuTransparent,
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .padding(start = 2.sdp)
                                .rippleClick {
                                    readContinue = true
                                }
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = readContinue,
                enter = expandVertically(expandFrom = Alignment.Top) { 20 },
                exit = shrinkVertically(animationSpec = tween()) { fullHeight ->
                    fullHeight / 2
                },
            ) {
                Text(
                    modifier = Modifier.padding(top = 4.sdp),
                    text = yorumItem.yorumAciklama,
                    style = MaterialTheme.typography.smallUbuntuBlack.copy(
                        lineHeight = 14.ssp
                    )
                )
            }
        }
    }
}

@Composable
private fun KitapYorumItemHeader(
    yorumYazanAdSoyad: String,
    yorumYazmaTar: String
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = yorumYazanAdSoyad,
            style = MaterialTheme.typography.smallUbuntuTransparent
        )
        Canvas(
            modifier = Modifier
                .size(8.sdp)
                .padding(start = 4.sdp)
        ) {
            drawCircle(color = Color.Gray)
        }
        Text(
            modifier = Modifier.padding(start = 4.sdp),
            text = yorumYazmaTar,
            style = MaterialTheme.typography.smallUbuntuTransparent
        )
    }
}