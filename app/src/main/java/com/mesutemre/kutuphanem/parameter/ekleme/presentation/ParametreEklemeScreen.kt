package com.mesutemre.kutuphanem.parameter.ekleme.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem.model.ERROR
import com.mesutemre.kutuphanem.model.SUCCESS
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.ParameterTypeSelection
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuWhiteBold
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemFormInput
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemLoader
import com.mesutemre.kutuphanem_ui.button.KutuphanemMainMaterialButton
import com.mesutemre.kutuphanem.util.customcomponents.menuitem.KutuphanemMenuInfo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ParametreEklemeScreen(
    viewModel: ParametreEklemeViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarDuration, Int) -> Unit
) {

    val state: ParametreEklemeState = viewModel.parametreEklemeState.collectAsState().value

    when (state.parametreKayit) {
        is BaseResourceEvent.Success -> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.parametreKayit.data?.statusMessage ?: "",
                    SnackbarDuration.Long,
                    SUCCESS
                )
            }
        }
        is BaseResourceEvent.Error -> {
            LaunchedEffect(key1 = Unit) {
                showSnackbar(
                    state.parametreKayit.message ?: "",
                    SnackbarDuration.Long,
                    ERROR
                )
            }
        }

        is BaseResourceEvent.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                KutuphanemLoader(
                    modifier = Modifier
                        .width(220.sdp)
                        .height(220.sdp)
                        .align(Alignment.Center)
                )
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (state.parametreKayit is BaseResourceEvent.Loading) 0.4f else 1f)
            .background(color = MaterialTheme.colorPalette.loginBackColor)
            .padding(bottom = 24.sdp, top = 16.sdp)
    ) {
        val (formContent, saveButton) = createRefs()
        val keyboardController = LocalSoftwareKeyboardController.current

        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(formContent) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }) {
            KutuphanemMenuInfo(info = stringResource(id = R.string.parametreEklemeInfo))
            ParameterTypeSelection(
                modifier = Modifier
                    .padding(top = 16.sdp, start = 16.sdp, end = 16.sdp)
                    .fillMaxWidth(),
                selectedParameterType = state.selectedParameterType,
                onSelectType = {
                    viewModel.onChangeEvent(ParametreEklemeValidationEvent.ParametreTypeChanged(it))
                }
            )
            KutuphanemFormInput(
                text = state.parametreText,
                singleLine = true,
                onChange = {
                    viewModel.onChangeEvent(ParametreEklemeValidationEvent.ParametreTextChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.sdp, start = 16.sdp, end = 16.sdp)
                    .onFocusChanged {
                        viewModel.onChangeEvent(ParametreEklemeValidationEvent.ParametreFocusChanged)
                    },
                isError = state.parametreTextErrorMessage != null,
                errorMessage = if (state.parametreTextErrorMessage != null) stringResource(id = state.parametreTextErrorMessage) else "",
                label = if (state.selectedParameterType == SelectedParameterType.YAYINEVI) stringResource(
                    id = R.string.yayinEviLabel
                ) else stringResource(id = R.string.kitapTurLabel),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }
                ),
                enabled = if (state.parametreKayit is BaseResourceEvent.Loading) false else true
            )
        }

        KutuphanemMainMaterialButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.sdp)
                .constrainAs(saveButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            text = stringResource(id = R.string.kaydet),
            iconId = R.drawable.ic_baseline_login_24,
            textStyle = MaterialTheme.typography.smallUbuntuWhiteBold,
            isEnabled = if (state.parametreKayit is BaseResourceEvent.Loading) false else true
        ) {
            viewModel.onChangeEvent(ParametreEklemeValidationEvent.Submit)
        }
    }
}