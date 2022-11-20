package com.mesutemre.kutuphanem_ui.card

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
    onClickLike: (Int) -> Unit,
    onClickShare: (Int, String, String, String) -> Unit,
    onClickArchive: (Int, String, String, String) -> Unit,
    onClickCardItem: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.sdp)
            .background(color = MaterialTheme.colorPalette.loginBackColor)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.sdp)
                .clickable {
                    onClickCardItem(kitapId)
                },
            backgroundColor = MaterialTheme.colorPalette.white,
            shape = MaterialTheme.shapes.medium,
            elevation = 8.sdp
        ) {
            KitapInfo(
                kitapImage = kitapResim,
                kitapAd = kitapAd,
                yazarAd = yazarAd,
                aciklama = aciklama
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-10).sdp, y = 152.sdp), horizontalArrangement = Arrangement.End
        ) {
            KitapCardItemTransactionBox(
                modifier = Modifier.size(32.sdp),
                backgroundColor = MaterialTheme.colorPalette.turuncu,
                title = "",
                icon = Icons.Filled.Download,
                iconSize = 16.sdp
            ) {
                onClickArchive(kitapId, kitapAd, yazarAd, aciklama)
            }
            Spacer(modifier = Modifier.width(8.sdp))
            KitapCardItemTransactionBox(
                modifier = Modifier.size(32.sdp),
                backgroundColor = MaterialTheme.colorPalette.fistikYesil,
                title = "",
                icon = ImageVector.vectorResource(id = R.drawable.icon_like),
                iconSize = 16.sdp
            ) {
                onClickLike(kitapId)
            }
            Spacer(modifier = Modifier.width(8.sdp))
            KitapCardItemTransactionBox(
                modifier = Modifier.size(32.sdp),
                backgroundColor = MaterialTheme.colorPalette.primaryTextColor,
                title = "",
                icon = Icons.Filled.Share,
                iconSize = 16.sdp
            ) {
                onClickShare(kitapId, kitapAd, yazarAd, kitapResim)
            }
        }
    }
}

@Composable
fun KitapArsivItemCard(
    kitapAd: String,
    yazarAd: String,
    aciklama: String,
    kitapResim: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.sdp),
        backgroundColor = MaterialTheme.colorPalette.white,
        shape = MaterialTheme.shapes.medium,
        elevation = 8.sdp
    ) {
        KitapInfo(
            kitapImage = kitapResim,
            kitapAd = kitapAd,
            yazarAd = yazarAd,
            aciklama = aciklama
        )
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
                    1.sdp,
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
                style = MaterialTheme.typography.smallUbuntuBlackBold.copy(
                    lineHeight = 14.ssp
                )
            )
            Text(
                text = yazarAd,
                modifier = Modifier.padding(top = 4.sdp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.smallAllegraBlack.copy(
                    lineHeight = 14.ssp
                )
            )
            Text(
                text = aciklama,
                modifier = Modifier.padding(top = 6.sdp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.smallUbuntuTransparent.copy(
                    lineHeight = 14.ssp
                )
            )
        }
        Icon(
            Icons.Filled.ChevronRight,
            modifier = Modifier
                .size(32.sdp)
                .align(alignment = Alignment.CenterVertically),
            contentDescription = null,
            tint = MaterialTheme.colorPalette.secondaryGray
        )
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

@Composable
private fun KitapCardItemTransactionBox(
    modifier: Modifier,
    backgroundColor: Color,
    title: String,
    icon: ImageVector,
    iconSize: Dp,
    iconTintColor: Color = MaterialTheme.colorPalette.white,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .rippleClick {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = icon,
            contentDescription = title,
            tint = iconTintColor
        )
    }
}