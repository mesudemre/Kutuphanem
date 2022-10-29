package com.mesutemre.kutuphanem_ui.card

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import com.mesutemre.kutuphanem_ui.R
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.*

@Composable
fun KutuphanemCardTitle(
    @StringRes title: Int
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.sdp)
    ) {
        val (labelText, iconItem) = createRefs()
        Text(text = stringResource(id = title), modifier = Modifier
            .constrainAs(labelText) {
                top.linkTo(parent.top, 8.sdp)
                bottom.linkTo(parent.bottom, 8.sdp)
            }
            .padding(horizontal = 8.sdp),
            style = MaterialTheme.typography.normalUbuntuTransparentBold)

        Icon(
            Icons.Filled.ArrowForward,
            modifier = Modifier.constrainAs(iconItem) {
                top.linkTo(parent.top, 8.sdp)
                bottom.linkTo(parent.bottom, 8.sdp)
                end.linkTo(parent.end, 8.sdp)
            },
            contentDescription = stringResource(id = title),
            tint = MaterialTheme.colorPalette.transparent
        )
    }
}

@Composable
fun KitapCardItem(
    kitapId: Int,
    kitapAd: String,
    yazarAd: String,
    aciklama: String,
    kitapResim: String,
    isLiked: Boolean = false,
    //onClickLike: (Int) -> Unit,
    //onClickShare: (String, String, String) -> Unit,
    //onClickArchive: (Int, String, String, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.sdp),
        backgroundColor = MaterialTheme.colorPalette.white,
        shape = MaterialTheme.shapes.medium,
        elevation = 4.sdp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            KitapInfo(
                kitapImage = kitapResim,
                kitapAd = kitapAd,
                yazarAd = yazarAd,
                aciklama = aciklama
            )
            Divider(
                modifier = Modifier.padding(vertical = 4.sdp),
                thickness = 1.sdp, color = MaterialTheme.colorPalette.otherGrayLight
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.sdp), horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = null,
                    tint = MaterialTheme.colorPalette.iconGrayTint,
                    modifier = Modifier
                        .size(24.sdp)
                        .rippleClick {
                            //onClickShare(kitapAd, yazarAd, kitapResim)
                        })
                Icon(imageVector = Icons.Filled.Download, contentDescription = null,
                    tint = MaterialTheme.colorPalette.iconGrayTint,
                    modifier = Modifier
                        .size(24.sdp)
                        .padding(end = 8.sdp)
                        .rippleClick {
                            //onClickArchive(kitapId, kitapAd, yazarAd, aciklama, kitapResim)
                        })
                Icon(painter = painterResource(id = if (isLiked) R.drawable.red_heart else R.drawable.outline_heart),
                    contentDescription = null, modifier = Modifier
                        .size(24.sdp)
                        .padding(end = 8.sdp)
                        .rippleClick {
                            //onClickLike(kitapId)
                        })
            }
        }
    }
}

@Composable
private fun KitapInfo(kitapImage: String, kitapAd: String, yazarAd: String, aciklama: String) {
    Row(modifier = Modifier.padding(top = 8.sdp, start = 8.sdp, end = 8.sdp)) {
        SubcomposeAsyncImage(model = kitapImage,
            modifier = Modifier
                .width(100.sdp)
                .height(150.sdp)
                .border(
                    2.sdp,
                    MaterialTheme.colorPalette.secondaryGray,
                    MaterialTheme.shapes.medium
                ),
            contentDescription = kitapAd,
            loading = {
                if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                    KutuphanemImageIndeedLoading(
                        200.sdp, 300.sdp
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            })
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.sdp)
        ) {
            Text(
                text = kitapAd,
                modifier = Modifier.padding(top = 2.sdp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.smallUbuntuBlackBold
            )
            Text(
                text = kitapAd,
                modifier = Modifier.padding(top = 2.sdp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.smallUbuntuBlack
            )
            Text(
                text = aciklama,
                modifier = Modifier.padding(top = 2.sdp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.smallUbuntuTransparent
            )
        }
    }
}

@Composable
private fun KutuphanemImageIndeedLoading(
    width: Dp,
    height: Dp
) {
    Spacer(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(shape = MaterialTheme.shapes.medium)
            .placeholder(
                visible = true,
                color = MaterialTheme.colorPalette.placeHolderColor,
                highlight = PlaceholderHighlight.shimmer(highlightColor = MaterialTheme.colorPalette.otherGrayLight)
            )
    )
}