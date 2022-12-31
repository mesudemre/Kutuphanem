package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_detay.presentation.KitapEklemeEvent
import com.mesutemre.kutuphanem.kitap_ekleme.presentation.components.KitapResimEklemeArea
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuWhiteBold
import com.mesutemre.kutuphanem.util.customcomponents.input.KutuphanemOutlinedFormTextField
import com.mesutemre.kutuphanem_base.util.MaskVisualTransformation
import com.mesutemre.kutuphanem_ui.button.KutuphanemMainMaterialButton

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KitapEklemeScreen(
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    navController: NavController,
    viewModel: KitapEkleViewModel = hiltViewModel()
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState =
        BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current
    val state = viewModel.state.collectAsState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {

        },
        sheetBackgroundColor = MaterialTheme.colorPalette.white,
        sheetContentColor = MaterialTheme.colorPalette.loginBackColor,
        sheetPeekHeight = (-50).sdp
    ) {
        val onChangeKitapAd = remember<(String) -> Unit> {
            {
                viewModel.onKitapEklemeEvent(KitapEklemeEvent.KitapAdTextChange(it))
            }
        }
        val onChangeYazarAd = remember<(String) -> Unit> {
            {
                viewModel.onKitapEklemeEvent(KitapEklemeEvent.YazarAdTextChange(it))
            }
        }
        val onChangeKitapAlinmaTar = remember<(String) -> Unit> {
            {
                viewModel.onKitapEklemeEvent(KitapEklemeEvent.KitapAlinmaTarTextChange(it))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorPalette.loginBackColor)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.sdp, vertical = 24.sdp)
                    .verticalScroll(rememberScrollState())
            ) {
                KitapResimEklemeArea()
                KutuphanemOutlinedFormTextField(
                    text = state.value.kitapAd,
                    onChange = onChangeKitapAd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.sdp),
                    errorMessage = state.value.kitapAdError?.let {
                        stringResource(id = it)
                    } ?: null,
                    label = stringResource(id = R.string.kitapAdLabelText),
                    placeHolder = stringResource(id = R.string.kitap_ekleme_kitapAdPlaceholder)
                )

                KutuphanemOutlinedFormTextField(
                    text = state.value.yazarAd,
                    onChange = onChangeYazarAd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.sdp),
                    errorMessage = state.value.yazarAdError?.let {
                        stringResource(id = it)
                    } ?: null,
                    label = stringResource(id = R.string.yazarAdLabelText),
                    placeHolder = stringResource(id = R.string.kitap_ekleme_yazarAdPlaceholder)
                )

                KutuphanemOutlinedFormTextField(
                    text = state.value.alinmaTar,
                    onChange = onChangeKitapAlinmaTar,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    visualTransformation = MaskVisualTransformation("##.##.####"),
                    maxCharacter = 8,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.sdp),
                    errorMessage = state.value.alinmaTarError?.let {
                        stringResource(id = it)
                    } ?: null,
                    label = stringResource(id = R.string.alinmaTarLabelText),
                    placeHolder = stringResource(id = R.string.kitap_ekleme_alinmaTarPlaceholder)
                )
            }
            KutuphanemMainMaterialButton(
                modifier = Modifier
                    .padding(start = 16.sdp, end = 16.sdp, bottom = 24.sdp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.kaydet),
                textStyle = MaterialTheme.typography.smallUbuntuWhiteBold
            ) {
            }
        }

    }
}