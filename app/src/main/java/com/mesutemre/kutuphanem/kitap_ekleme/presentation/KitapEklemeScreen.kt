package com.mesutemre.kutuphanem.kitap_ekleme.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageProxy
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mesutemre.kutuphanem.BuildConfig
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.kitap_detay.presentation.KitapEklemeEvent
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.CameraOpenType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeBottomsheetType
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeYayinEviItem
import com.mesutemre.kutuphanem.kitap_ekleme.presentation.components.*
import com.mesutemre.kutuphanem.kitap_ekleme.presentation.components.bottomsheet.KitapEklemeKitapTurBottomSheetArea
import com.mesutemre.kutuphanem.kitap_ekleme.presentation.components.bottomsheet.KitapEklemeYayinEviBottomSheetArea
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuBlack
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuWhiteBold
import com.mesutemre.kutuphanem.util.customcomponents.dialog.CustomKutuphanemDialog
import com.mesutemre.kutuphanem.util.customcomponents.input.KutuphanemOutlinedFormTextField
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.util.MaskVisualTransformation
import com.mesutemre.kutuphanem_ui.button.KutuphanemMainMaterialButton
import com.mesutemre.kutuphanem_ui.card.KutuphanemSelectableCard
import com.mesutemre.kutuphanem_ui.dialog.ERROR_DLG
import com.mesutemre.kutuphanem_ui.dialog.KutuphanemPermissionDialog
import com.mesutemre.kutuphanem_ui.extensions.findActivity
import com.mesutemre.kutuphanem_ui.extensions.rippleClick
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.ssp
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KitapEklemeScreen(
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    navController: NavController,
    viewModel: KitapEkleViewModel = hiltViewModel()
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val state = viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val localFocusManager = LocalFocusManager.current
    val systemUiController = rememberSystemUiController()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val camPermissionState =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                viewModel.onCameraPermissionResult(
                    isGranted = isGranted,
                    isPermanantlyDenied = context.findActivity()
                        .shouldShowRequestPermissionRationale(Manifest.permission.CAMERA),
                    cameraOpenType = CameraOpenType.KITAP_ACIKLAMA_TEXT_RECOGNIZE
                )
            })

    val kitapEklemePermissionState =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { perms ->
                perms.keys.forEach { permission ->

                    viewModel.onMultiplePermissionResult(
                        permission = permission,
                        isGranted = perms[permission] == true,
                        isPermanantlyDenied = context.findActivity()
                            .shouldShowRequestPermissionRationale(permission)
                    )
                }
            })

    systemUiController.isStatusBarVisible =
        (state.value.openCamera || state.value.showCropArea).not()


    val openCloseCamera = remember<(Boolean, CameraOpenType?) -> Unit> {
        { isOpen, cameraOpenType ->
            viewModel.onKitapEklemeEvent(
                KitapEklemeEvent.KitapResimEklemeOpenClose(
                    isOpen, cameraOpenType ?: CameraOpenType.KITAP_RESIM
                )
            )
        }
    }

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
    val onChangeKitapAciklama = remember<(String) -> Unit> {
        {
            viewModel.onKitapEklemeEvent(KitapEklemeEvent.KitapAciklamaTextChange(it))
        }
    }

    val popBack = remember<() -> Unit> {
        {
            navController.popBackStack()
        }
    }

    val onClickKitapTurYayinEvi = remember<(KitapEklemeBottomsheetType) -> Unit> {
        {
            viewModel.onKitapEklemeEvent(KitapEklemeEvent.OnChangeBottomSheetType(it))
        }
    }

    BackHandler {
        if (state.value.openCamera) {
            openCloseCamera(false, null)
        } else if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.animateTo(
                    BottomSheetValue.Collapsed,
                    tween(500)
                )
            }
        } else {
            popBack()
        }
    }

    val onSelectKitapTur = remember<(KitapEklemeKitapTurItem?) -> Unit> {
        {
            coroutineScope.launch {
                viewModel.onKitapEklemeEvent(KitapEklemeEvent.OnSelectKitapTur(it))
                bottomSheetScaffoldState.bottomSheetState.animateTo(
                    BottomSheetValue.Collapsed, tween(500)
                )
            }
        }
    }

    val onSelectYayinevi = remember<(KitapEklemeYayinEviItem?) -> Unit> {
        {
            coroutineScope.launch {
                viewModel.onKitapEklemeEvent(KitapEklemeEvent.OnSelectYayinEvi(it))
                bottomSheetScaffoldState.bottomSheetState.animateTo(
                    BottomSheetValue.Collapsed, tween(500)
                )
            }
        }
    }

    when (state.value.kitapKaydetResourceEvent) {
        is BaseResourceEvent.Success -> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    "Kitap kaydı başarıyla yapıldı.", SnackbarDuration.Short, SUCCESS
                )
            }
        }
        is BaseResourceEvent.Error -> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.value.kitapKaydetResourceEvent.message ?: "",
                    SnackbarDuration.Short,
                    ERROR
                )
            }
        }
        else -> Unit
    }

    when (state.value.kitapResimYukleResourceEvent) {
        is BaseResourceEvent.Success -> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.value.kitapResimYukleResourceEvent.data?.statusMessage ?: "",
                    SnackbarDuration.Short,
                    SUCCESS
                )
            }
        }
        is BaseResourceEvent.Error -> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.value.kitapResimYukleResourceEvent.message ?: "",
                    SnackbarDuration.Short,
                    ERROR
                )
            }
        }
        else -> Unit
    }

    if (state.value.cameraPermissionDeniedDialog) {
        KutuphanemPermissionDialog(modifier = Modifier
            .height(220.sdp)
            .width(400.sdp),
            permission = stringResource(id = R.string.kitap_ekleme_kameraIzinDialogTitle),
            description = stringResource(id = if (state.value.cameraPermissionDeniedPermanently) R.string.kutuphanem_camera_permission_transaction else R.string.kutuphanem_camera_permission_settings),
            confirmButtonText = stringResource(id = R.string.ayarlaLabel),
            onOkClick = {
                viewModel.onKitapEklemeEvent(KitapEklemeEvent.CloseCameraPermissionWarning)
                if (state.value.cameraPermissionDeniedPermanently) {
                    camPermissionState.launch(
                        Manifest.permission.CAMERA
                    )
                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                    context.startActivity(intent)
                }
            })
    }

    if (state.value.readExternalStoragePermissionDeniedDialog) {
        KutuphanemPermissionDialog(modifier = Modifier
            .height(220.sdp)
            .width(400.sdp),
            permission = stringResource(id = R.string.kitap_ekleme_writeReadIzinDialogTitle),
            description = stringResource(id = if (state.value.readExternalStoragePermissionDeniedPermanently) R.string.kutuphanem_read_permission_transaction else R.string.kutuphanem_read_permission_settings),
            confirmButtonText = stringResource(id = R.string.ayarlaLabel),
            onOkClick = {
                viewModel.onKitapEklemeEvent(KitapEklemeEvent.CloseReadExternalStoragePermissionWarning)
                if (state.value.readExternalStoragePermissionDeniedPermanently) {
                    kitapEklemePermissionState.launch(
                        viewModel.kitapResimEklemePermissionList.toTypedArray()
                    )
                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                    context.startActivity(intent)
                }
            })
    }

    if (state.value.kitapAciklamaTextRecognationErrorOccured) {
        CustomKutuphanemDialog(modifier = Modifier
            .height(220.sdp)
            .width(400.sdp),
            type = ERROR_DLG,
            title = stringResource(id = R.string.kitap_ekleme_text_read_title),
            description = stringResource(id = R.string.kitap_ekleme_text_read_errorDescription),
            onDismissDialog = {
                viewModel.dismissTextRecognationErrorDialog()
            }) {
            viewModel.dismissTextRecognationErrorDialog()
        }
    }

    val onClickResimEkleme = remember<() -> Unit> {
        {
            kitapEklemePermissionState.launch(
                viewModel.kitapResimEklemePermissionList.toTypedArray()
            )
        }
    }

    val onClickKitapAciklama = remember<() -> Unit> {
        {
            camPermissionState.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    val onCompleteKitapResimCrop = remember<(ImageBitmap, File?) -> Unit> {
        { croppedImage, croppedFile ->
            viewModel.onKitapEklemeEvent(
                KitapEklemeEvent.OnKitapResimCropped(
                    croppedImage, croppedFile
                )
            )
        }
    }

    val onCloseKitapResimCrop = remember<() -> Unit> {
        {
            viewModel.onKitapEklemeEvent(
                KitapEklemeEvent.OnKitapResimCropClose
            )
        }
    }

    val onRemoveCroppedResim = remember<() -> Unit> {
        {
            viewModel.onKitapEklemeEvent(
                KitapEklemeEvent.OnRemoveCroppedKitapResim
            )
        }
    }

    val onSuccessCaptured = remember<(File) -> Unit> {
        {
            viewModel.setCapturedImage(it)
        }
    }

    val onSuccessImageInfo = remember<(ImageProxy) -> Unit> {
        {
            viewModel.setCapturedForImageTextRecognize(it)
        }
    }

    val onClickKaydetButton = remember<() -> Unit> {
        {
            viewModel.onKitapEklemeEvent(KitapEklemeEvent.OnSaveKitap)
        }
    }

    SideEffect {
        if (state.value.mustScrollForValidation) {
            coroutineScope.launch {
                scrollState.animateScrollTo(scrollState.maxValue)
                viewModel.onKitapEklemeEvent(KitapEklemeEvent.SetDefaultMustScroll)
            }
        }
    }


    BottomSheetScaffold(scaffoldState = bottomSheetScaffoldState,
        topBar = if ((state.value.openCamera || state.value.showCropArea).not()) {
            {
                KitapEklemeScreenTopBar(pageTitle = stringResource(id = R.string.kitapEklemeTitle),
                    onBackPress = {
                        navController.popBackStack()
                    })
            }
        } else null,
        sheetContent = {
            if (state.value.bottomsheetScreen == KitapEklemeBottomsheetType.KITAP_TUR) {
                KitapEklemeKitapTurBottomSheetArea(
                    kitapTurListResponse = state.value.kitapTurListResponse,
                    onSelectKitapTur = onSelectKitapTur
                )
            } else if (state.value.bottomsheetScreen == KitapEklemeBottomsheetType.YAYIN_EVI) {
                KitapEklemeYayinEviBottomSheetArea(
                    yayinEviListResponse = state.value.yayinEviListResponse,
                    onSelectYayinEvi = onSelectYayinevi
                )
            }
        },
        sheetBackgroundColor = MaterialTheme.colorPalette.white,
        sheetContentColor = MaterialTheme.colorPalette.loginBackColor,
        sheetPeekHeight = (-50).sdp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorPalette.loginBackColor)
        ) {

            if (state.value.openCamera) {
                KitapResimCameraCaptureArea(
                    cameraType = state.value.cameraOpenType,
                    onSuccessCaptured = onSuccessCaptured,
                    onSuccessImageInfo = onSuccessImageInfo
                )
            }
            if (state.value.showCropArea) {
                KitapResimCropArea(
                    capturedImage = state.value.captureImageBitmap!!.asImageBitmap(),
                    onCloseCrop = onCloseKitapResimCrop,
                    onCompleteCrop = onCompleteKitapResimCrop
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.sdp, vertical = 24.sdp)
                    .verticalScroll(scrollState)
            ) {
                state.value.croppedImageBitMap?.let {
                    CroppedKitapResimArea(croppedImage = it, onRemoveImage = onRemoveCroppedResim)
                } ?: run {
                    KitapResimEklemeArea(
                        errorValidationMessage = state.value.kitapResimError?.let {
                            stringResource(id = it)
                        },
                        onClickResimEkleme = onClickResimEkleme
                    )
                }

                KutuphanemOutlinedFormTextField(text = state.value.kitapAd,
                    onChange = onChangeKitapAd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.sdp),
                    errorMessage = state.value.kitapAdError?.let {
                        stringResource(id = it)
                    } ?: null,
                    label = stringResource(id = R.string.kitapAdLabelText),
                    placeHolder = stringResource(id = R.string.kitap_ekleme_kitapAdPlaceholder))

                KutuphanemOutlinedFormTextField(text = state.value.yazarAd,
                    onChange = onChangeYazarAd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.sdp),
                    errorMessage = state.value.yazarAdError?.let {
                        stringResource(id = it)
                    } ?: null,
                    label = stringResource(id = R.string.yazarAdLabelText),
                    placeHolder = stringResource(id = R.string.kitap_ekleme_yazarAdPlaceholder))

                KutuphanemOutlinedFormTextField(text = state.value.alinmaTar,
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
                    placeHolder = stringResource(id = R.string.kitap_ekleme_alinmaTarPlaceholder))

                KutuphanemOutlinedFormTextField(text = state.value.kitapAciklama,
                    onChange = onChangeKitapAciklama,
                    textStyle = MaterialTheme.typography.smallUbuntuBlack.copy(
                        lineHeight = 14.ssp
                    ),
                    maxLine = 4,
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.sdp)
                        .padding(top = 12.sdp),
                    errorMessage = state.value.kitapAciklamaError?.let {
                        stringResource(id = it)
                    } ?: null,
                    label = stringResource(id = R.string.kitap_ekleme_kitapAciklamaLabel),
                    placeHolder = stringResource(id = R.string.kitap_ekleme_kitapAciklamaPlaceHolder),
                    trailingIcon = {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_text_recognizer),
                            contentDescription = stringResource(id = R.string.kitap_ekleme_kitapAciklamaPlaceHolder),
                            modifier = Modifier
                                .padding(end = 8.sdp)
                                .size(24.sdp)
                                .rippleClick {
                                    onClickKitapAciklama()
                                })
                    })


                KutuphanemSelectableCard(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.sdp),
                    titleStyle = state.value.selectedKitapTur?.let {
                        MaterialTheme.typography.smallUbuntuBlack
                    },
                    title = state.value.selectedKitapTur?.let {
                        it.kitapTurAciklama
                    } ?: run {
                        stringResource(id = R.string.kitapTurLabel)
                    }, errorStr = state.value.kitapTurError?.let {
                        stringResource(id = it)
                    },
                    onClick = {
                        coroutineScope
                            .launch {
                                onClickKitapTurYayinEvi(KitapEklemeBottomsheetType.KITAP_TUR)
                                bottomSheetScaffoldState.bottomSheetState.animateTo(
                                    BottomSheetValue.Expanded, tween(500)
                                )
                            }
                            .invokeOnCompletion {
                                viewModel.initKitapTurList()
                            }
                    })

                KutuphanemSelectableCard(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.sdp),
                    onClick = {
                        coroutineScope
                            .launch {
                                onClickKitapTurYayinEvi(KitapEklemeBottomsheetType.YAYIN_EVI)
                                bottomSheetScaffoldState.bottomSheetState.animateTo(
                                    BottomSheetValue.Expanded, tween(500)
                                )
                            }
                            .invokeOnCompletion {
                                viewModel.initYayinEviList()
                            }
                    },
                    titleStyle = state.value.selectedYayinEvi?.let {
                        MaterialTheme.typography.smallUbuntuBlack
                    },
                    title = state.value.selectedYayinEvi?.let {
                        it.yayinEviAciklama
                    } ?: run {
                        stringResource(id = R.string.yayinEviLabel)
                    }, errorStr = state.value.yayinEviError?.let {
                        stringResource(id = it)
                    })
            }
            KutuphanemMainMaterialButton(
                modifier = Modifier
                    .padding(start = 16.sdp, end = 16.sdp, bottom = 24.sdp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.kaydet),
                textStyle = MaterialTheme.typography.smallUbuntuWhiteBold,
                onClick = onClickKaydetButton
            )
        }
    }
}