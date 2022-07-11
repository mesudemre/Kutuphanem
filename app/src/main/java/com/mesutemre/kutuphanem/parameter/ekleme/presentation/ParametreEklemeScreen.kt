package com.mesutemre.kutuphanem.parameter.ekleme.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.ParameterTypeSelection
import com.mesutemre.kutuphanem.parameter.ekleme.presentation.components.SelectedParameterType
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.ui.theme.smallUbuntuWhiteBold
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemBaseInput
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemFormInput
import com.mesutemre.kutuphanem.util.customcomponents.button.KutuphanemMainMaterialButton
import com.mesutemre.kutuphanem.util.customcomponents.menuitem.KutuphanemMenuInfo

@Composable
fun ParametreEklemeScreen(
    viewModel: ParametreEklemeViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarDuration, Int) -> Unit
) {

    val state: ParametreEklemeState = viewModel.parametreEklemeState.value

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(bottom = 24.sdp,top = 16.sdp)) {
        val (formContent, saveButton) = createRefs()

        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(formContent) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }) {
            KutuphanemMenuInfo(info = stringResource(id = R.string.parametreEklemeInfo))
            ParameterTypeSelection(
                modifier = Modifier.padding(top = 16.sdp,start = 16.sdp,end = 16.sdp).fillMaxWidth(),
                selectedParameterType = state.selectedParameterType,
                onSelectType = {
                    viewModel.onChangeParameterType(it)
                }
            )
            KutuphanemFormInput(
                text = state.parametreText,
                singleLine = true,
                onChange = {
                    //loginViewModel.onLoginFormEvent(LoginValidationEvent.UsernameChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.sdp,start = 16.sdp,end = 16.sdp)
                    .onFocusChanged {
                        //loginViewModel.onUsernameFocusedChange()
                    },
                isError = state.parametreTextError,
                errorMessage = "",//if (loginUserState.usernameErrorMessage != null) stringResource(id = loginUserState.usernameErrorMessage!!) else "",
                hint = if (state.selectedParameterType == SelectedParameterType.YAYINEVI) stringResource(
                    id = R.string.yayinEviLabel
                ) else stringResource(id = R.string.kitapTurLabel),
                label = if (state.selectedParameterType == SelectedParameterType.YAYINEVI) stringResource(
                    id = R.string.yayinEviLabel
                ) else stringResource(id = R.string.kitapTurLabel),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    //keyboardController?.hide()
                }
                )
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
            //isEnabled = if (loginState.isLoading) false else true
        ) {
            //loginViewModel.onLoginFormEvent(LoginValidationEvent.Submit)
        }
    }
}