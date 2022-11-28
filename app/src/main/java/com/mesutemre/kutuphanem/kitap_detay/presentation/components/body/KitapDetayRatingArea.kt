package com.mesutemre.kutuphanem.kitap_detay.presentation.components.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuTransparentBold
import com.mesutemre.kutuphanem_ui.theme.*

@Composable
fun KitapDetayRatingArea(kitapPuan: Float) {
    var rating by remember {
        mutableStateOf(kitapPuan)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = com.mesutemre.kutuphanem.R.string.kitap_detay_puan_label),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.smallUbuntuBlackBold
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.sdp), verticalAlignment = Alignment.CenterVertically) {
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
                text = if (kitapPuan>0) "$kitapPuan" else stringResource(id = R.string.noComentKitapYet),
                modifier = Modifier.padding(start = 4.sdp),
                style = if (kitapPuan>0) {
                    MaterialTheme.typography.smallUbuntuTransparentBold
                }else {
                    MaterialTheme.typography.smallUbuntuErrorBold.copy(lineHeight = 14.ssp)
                }
            )
        }
    }

}