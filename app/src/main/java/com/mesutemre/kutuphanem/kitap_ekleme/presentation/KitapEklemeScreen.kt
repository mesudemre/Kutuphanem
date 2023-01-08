package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.mesutemre.kutuphanem.BuildConfig
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_detay.presentation.KitapEklemeEvent
import com.mesutemre.kutuphanem.kitap_ekleme.presentation.components.KitapResimEklemeArea
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuWhiteBold
import com.mesutemre.kutuphanem.util.customcomponents.dialog.CustomKutuphanemDialog
import com.mesutemre.kutuphanem.util.customcomponents.input.KutuphanemOutlinedFormTextField
import com.mesutemre.kutuphanem_base.util.MaskVisualTransformation
import com.mesutemre.kutuphanem_ui.button.KutuphanemMainMaterialButton
import com.mesutemre.kutuphanem_ui.card.KutuphanemSelectableCard
import com.mesutemre.kutuphanem_ui.dialog.WARNING_DLG

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
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
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val context = LocalContext.current

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

        LaunchedEffect(cameraPermissionState.status) {
            if (state.value.isCameraPermissionClicked) {
                when (cameraPermissionState.status) {
                    PermissionStatus.Granted -> {
                        //Kamera açılacak...
                        Log.d("KUTUPHANEM_PERMISSION", "Kamera izni verildi...")
                    }
                    PermissionStatus.Denied(false) -> { //Kalıcı olarak red
                        Log.d("KUTUPHANEM_PERMISSION", "Kamera izni yok...")
                    }
                    PermissionStatus.Denied(true) -> { //Ekrandan denied yaptık.
                        Log.d("KUTUPHANEM_PERMISSION", "Store true Kamera izni yok..")
                    }
                }
            }
        }

        if (state.value.showSettingsDialog) {
            CustomKutuphanemDialog(
                modifier = Modifier
                    .height(220.sdp)
                    .width(400.sdp),
                type = WARNING_DLG,
                title = stringResource(id = R.string.kitap_ekleme_kameraIzinDialogTitle),
                description = stringResource(id = R.string.kitap_ekleme_kameraIzinDialogDescription),
                onDismissDialog = {
                    viewModel.dismissSettingsDialog()
                }) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                viewModel.dismissSettingsDialog()
                context.startActivity(intent)
            }
        }

        val onClickResimEkleme = remember<() -> Unit> {
            {
                if (cameraPermissionState.status.isGranted) {
                    //Direkt kamera açılacak...
                    Log.d("KUTUPHANEM_PERMISSION", "Kamera izni var...")
                } else if (cameraPermissionState.status == PermissionStatus.Denied(true)) {
                    viewModel.showSettingsDialog()
                } else {
                    viewModel.clickCameraPermission()
                    cameraPermissionState.launchPermissionRequest()
                }
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
                KitapResimEklemeArea(
                    onClickResimEkleme = onClickResimEkleme
                )
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
                KutuphanemSelectableCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.sdp),
                    title = state.value.selectedKitapTur?.let {
                        it.kitapTurAciklama
                    } ?: run {
                        stringResource(id = R.string.kitapTurLabel)
                    },
                    errorStr = state.value.kitapTurError?.let {
                        stringResource(id = it)
                    }
                )

                KutuphanemSelectableCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.sdp),
                    title = state.value.selectedYayinEvi?.let {
                        it.yayinEviAciklama
                    } ?: run {
                        stringResource(id = R.string.yayinEviLabel)
                    },
                    errorStr = state.value.yayinEviError?.let {
                        stringResource(id = it)
                    }
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