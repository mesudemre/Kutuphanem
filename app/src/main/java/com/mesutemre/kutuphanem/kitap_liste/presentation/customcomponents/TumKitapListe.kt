package com.mesutemre.kutuphanem.kitap_liste.presentation.customcomponents

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapArsivItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapListeItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapShareModel
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
    listState: LazyListState,
    kitapServiceListeSource: LazyPagingItems<KitapListeItem>,
    kitapIslemSource: BaseResourceEvent<ResponseStatusModel?>,
    kitapShareSource: BaseResourceEvent<KitapShareModel>,
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    viewModel: KitapListViewModel = hiltViewModel(),
    onClickKitapLike: (Int) -> Unit,
    onClickKitapShare: (Int, String, String, String) -> Unit
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
        when (kitapIslemSource) {
            is BaseResourceEvent.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    KutuphanemLoader(modifier = Modifier.size(180.sdp))
                }
            }
            is BaseResourceEvent.Success -> {
                showSnackbar(
                    kitapIslemSource.data?.statusMessage ?: "",
                    SnackbarDuration.Short,
                    SUCCESS
                )
                viewModel.setDefaultStateForKitapIslem()
            }
            is BaseResourceEvent.Error -> {
                showSnackbar(
                    kitapIslemSource.data?.statusMessage ?: "",
                    SnackbarDuration.Short,
                    ERROR
                )
                viewModel.setDefaultStateForKitapIslem()
            }
        }

        when (kitapShareSource) {
            is BaseResourceEvent.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    KutuphanemLoader(modifier = Modifier.size(180.sdp))
                }
            }
            is BaseResourceEvent.Success -> {
                val context = LocalContext.current
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "image/png"
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    putExtra(Intent.EXTRA_TEXT, kitapShareSource.data?.content)
                    putExtra(Intent.EXTRA_STREAM, kitapShareSource.data?.imageUri)
                }
                viewModel.setDefaultStateForKitapShare()
                context.startActivity(Intent.createChooser(shareIntent, "Paylaş"))
            }
            is BaseResourceEvent.Error -> {
                showSnackbar(
                    kitapShareSource.message ?: "",
                    SnackbarDuration.Short,
                    ERROR
                )
                viewModel.setDefaultStateForKitapShare()
            }
        }


        LazyColumn(modifier = Modifier.padding(bottom = 20.sdp), state = listState) {
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
                val kitapShare: (Int, String, String, String) -> Unit =
                    { kitapId, kitapAd, yazarAd, kitapResim ->
                        onClickKitapShare(kitapId, kitapAd, yazarAd, kitapResim)
                    }
                KitapCardItem(
                    kitapId = kitapModel?.kitapId ?: 0,
                    kitapAd = kitapModel?.kitapAd ?: "",
                    yazarAd = kitapModel?.yazarAd ?: "",
                    aciklama = kitapModel?.kitapAciklama ?: "",
                    kitapResim = kitapModel?.kitapResim ?: "",
                    onClickLike = {
                        onClickKitapLike(kitapModel?.kitapId ?: 0)
                    },
                    onClickArchive = kitapArsiv,
                    onClickShare = kitapShare
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