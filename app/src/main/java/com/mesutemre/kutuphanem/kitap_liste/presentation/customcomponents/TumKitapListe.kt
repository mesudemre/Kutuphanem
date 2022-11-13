package com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapListeItem
import com.mesutemre.kutuphanem.kitap_liste.presentation.KitapListViewModel
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.util.customcomponents.error.KutuphanemErrorView
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemLoader
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.card.KitapCardItem
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun TumKitapListe(
    kitapServiceListeSource: LazyPagingItems<KitapListeItem>,
    kitapArsivleSource: BaseResourceEvent<ResponseStatusModel>,
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    viewModel: KitapListViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp)
    ) {
        kitapServiceListeSource.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    KitapListeLoading()
                }
            }
        }
        when (kitapArsivleSource) {
            is BaseResourceEvent.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    KutuphanemLoader(modifier = Modifier.size(180.sdp))
                }
            }
            is BaseResourceEvent.Success -> {
                showSnackbar(
                    kitapArsivleSource.data?.statusMessage ?: "",
                    SnackbarDuration.Long,
                    SUCCESS
                )
                viewModel.setDefaultStateForKitapArsiv()
            }
            is BaseResourceEvent.Error -> {
                showSnackbar(
                    kitapArsivleSource.data?.statusMessage ?: "",
                    SnackbarDuration.Long,
                    ERROR
                )
                viewModel.setDefaultStateForKitapArsiv()
            }
        }
        LazyColumn(modifier = Modifier.padding(bottom = 20.sdp)) {
            items(kitapServiceListeSource) { kitapModel ->
                val kitapArsiv: (Int, String, String, String) -> Unit =
                    { kitapId, kitapAd, yazarAd, aciklama ->
                        viewModel.kitapArsivle(
                            KitapArsivItem(
                                kitapId = kitapModel?.kitapId ?: 0,
                                kitapAd = kitapModel?.kitapAd ?: "",
                                yazarAd = kitapModel?.yazarAd ?: "",
                                kitapAciklama = kitapModel?.kitapAciklama ?: "",
                                kitapResim = kitapModel?.kitapResim ?: ""
                            )
                        )
                    }

                KitapCardItem(
                    kitapId = kitapModel?.kitapId ?: 0,
                    kitapAd = kitapModel?.kitapAd ?: "",
                    yazarAd = kitapModel?.yazarAd ?: "",
                    aciklama = kitapModel?.kitapAciklama ?: "",
                    kitapResim = kitapModel?.kitapResim ?: "",
                    onClickArchive = kitapArsiv
                )
                Spacer(modifier = Modifier.padding(top = 12.sdp))
            }
            kitapServiceListeSource.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.sdp),
                                contentAlignment = Alignment.Center
                            ) {
                                KutuphanemLoader(
                                    modifier = Modifier
                                        .size(200.sdp)
                                        .padding(bottom = 12.sdp)
                                )
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item {
                            KutuphanemErrorView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(vertical = 16.sdp, horizontal = 16.sdp),
                                errorText = stringResource(
                                    id = R.string.kitapListeEmpty
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}