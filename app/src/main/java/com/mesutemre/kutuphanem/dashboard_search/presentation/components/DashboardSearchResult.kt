package com.mesutemre.kutuphanem.dashboard_search.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.*
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.dashboard_search.domain.model.KitapSearchItem
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp

@Composable
fun DashboardSearchResult(
    resultListResource: BaseResourceEvent<List<KitapSearchItem>>,
    onSelectResultItem: (KitapSearchItem) -> Unit
) {
    when (resultListResource) {
        is BaseResourceEvent.Loading -> {
            val compositionResult: LottieCompositionResult = rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(
                    R.raw.search_loading
                )
            )
            val progress by animateLottieCompositionAsState(
                composition = compositionResult.value,
                isPlaying = true,
                iterations = LottieConstants.IterateForever,
                speed = 1.0f
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.sdp), contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = compositionResult.value,
                    progress,
                    Modifier.fillMaxWidth()
                )
            }
        }
        is BaseResourceEvent.Success -> {
            if (resultListResource.data.isNullOrEmpty()) {
                NoSearchDoneYet(stringResource(id = R.string.search_dashboard_not_found))
            } else {
                LazyColumn(modifier = Modifier.padding(top = 8.sdp)) {
                    items(resultListResource.data!!) { item ->
                        DashBoardSearchHistoryRowItem(
                            kitapAd = item.kitapAd,
                            yazarAd = item.yazarAd
                        ) {
                            onSelectResultItem(item)
                        }
                        Divider(
                            modifier = Modifier.padding(vertical = 4.sdp, horizontal = 16.sdp),
                            thickness = 1.sdp,
                            color = MaterialTheme.colorPalette.otherGrayLight
                        )
                    }
                }
            }
        }
        is BaseResourceEvent.Error -> {
            NoSearchDoneYet(resultListResource.message ?: "")
        }
    }
}