package com.mesutemre.kutuphanem.login.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.login.presentation.LoginFormState
import com.mesutemre.kutuphanem.login.presentation.LoginViewModel
import com.mesutemre.kutuphanem.ui.theme.*
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemBaseInput
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemMainMaterialButton

@Composable
fun LoginForm(loginViewModel: LoginViewModel = hiltViewModel()) {
    val loginState = loginViewModel.state.value

    Card(
        shape = RoundedCornerShape(20.sdp),
        modifier = Modifier
            .padding(start = 30.sdp, end = 30.sdp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colorPalette.white
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.sdp, end = 20.sdp)
        ) {

            Text(
                text = stringResource(id = R.string.girisLabel),
                style = Typography.largeAllegraPrimaryBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.sdp)
            )

            UserName(loginUserState = loginState,loginViewModel = loginViewModel)
            UserPassword(loginPasswordState = loginState,loginViewModel = loginViewModel)
            ForgotPasswordArea()
            KutuphanemMainMaterialButton(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.sdp), text = stringResource(id = R.string.girisButtonLabel),
                iconId = R.drawable.ic_baseline_login_24,
            textStyle = MaterialTheme.typography.smallUbuntuWhiteBold) {
                loginViewModel.validateUsername()
            }
            Text(text = stringResource(id = R.string.hesapYokKayitLabel),
                style = MaterialTheme.typography.thinyAllegraPrimary,
            modifier = Modifier
                .padding(bottom = 20.sdp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center)

            when(loginState.loginResourceEvent) {
                is BaseResourceEvent.Loading ->{
                    CircularProgressIndicator(color = MaterialTheme.colorPalette.googleDarkGray)
                }
                is BaseResourceEvent.Success-> {
                    Log.d("Login Token",loginState.loginResourceEvent.data!!)
                }
                is BaseResourceEvent.Error -> {
                    Log.e("Login Error",loginState.loginResourceEvent.message!!)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UserName (loginUserState:LoginFormState,loginViewModel: LoginViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    KutuphanemBaseInput(
        text = loginUserState.username,
        singleLine = true,
        onChange = {
            loginViewModel.onChangeUsername(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.sdp)
            .onFocusChanged {
                loginViewModel.onUsernameFocusedChange()
            },
        isError = loginUserState.usernameError,
        errorMessage = loginUserState.usernameErrorMessage ?: "",
        hint = stringResource(id = R.string.login_email_hint),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions( onDone = {
            keyboardController?.hide()}
        ),
        trailingIcon = {
            Icon(
                Icons.Filled.Person, contentDescription = stringResource(
                    id = R.string.login_email_hint
                )
            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UserPassword (loginPasswordState:LoginFormState,loginViewModel: LoginViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    KutuphanemBaseInput(
        text = loginPasswordState.password,
        singleLine = true,
        onChange = {
            loginViewModel.onChangePassword(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.sdp)
            .onFocusChanged {
                loginViewModel.onPasswordFocusedChange()
            },
        isError = loginPasswordState.passwordError,
        errorMessage = loginPasswordState.passwordErrorMessage ?: "",
        hint = stringResource(id = R.string.login_password_hint),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions( onDone = {
            keyboardController?.hide()}
        ),
        trailingIcon = {
            Icon(
                Icons.Filled.Password, contentDescription = stringResource(
                    id = R.string.login_password_hint
                )
            )
        }
    )
}