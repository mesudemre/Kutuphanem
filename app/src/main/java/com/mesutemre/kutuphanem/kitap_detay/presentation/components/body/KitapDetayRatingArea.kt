package com.mesutemre.kutuphanem.kitap_detay.presentation.components.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.mediumUbuntuTransparentBold
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuBlackBold
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuErrorBold
import com.mesutemre.kutuphanem_ui.theme.ssp

@Composable
fun KitapDetayRatingArea(kitapPuan: Float) {
    var rating by remember {
        mutableStateOf(kitapPuan)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.sdp),
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colorPalette.white,
        elevation = 4.sdp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.sdp, vertical = 8.sdp)
        ) {
            Text(
                text = stringResource(id = R.string.kitap_detay_puan_label),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.smallUbuntuBlackBold
            )
            if (kitapPuan > 0f) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        modifier = Modifier.weight(1f),
                        value = rating,
                        config = RatingBarConfig()
                            .activeColor(MaterialTheme.colorPalette.ratingYellow)
                            .inactiveBorderColor(MaterialTheme.colorPalette.transparent)
                            .inactiveColor(MaterialTheme.colorPalette.white)
                            .hideInactiveStars(false)
                            .stepSize(StepSize.HALF)
                            .numStars(5)
                            .isIndicator(true)
                            .size(24.sdp)
                            .padding(2.sdp)
                            .style(RatingBarStyle.HighLighted),
                        onValueChange = {
                            rating = it
                        },
                        onRatingChanged = {

                        }
                    )
                    Text(
                        text = "$kitapPuan",
                        modifier = Modifier.padding(start = 4.sdp),
                        style = MaterialTheme.typography.mediumUbuntuTransparentBold
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.noComentKitapYet),
                    modifier = Modifier.padding(start = 4.sdp,top = 8.sdp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.smallUbuntuErrorBold.copy(lineHeight = 14.ssp)
                )
            }
        }
    }
}