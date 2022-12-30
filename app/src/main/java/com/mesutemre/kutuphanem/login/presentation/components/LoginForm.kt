package com.mesutemre.kutuphanem.login.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.login.presentation.LoginFormState
import com.mesutemre.kutuphanem.login.presentation.LoginValidationEvent
import com.mesutemre.kutuphanem.login.presentation.LoginViewModel
import com.mesutemre.kutuphanem.navigation.KutuphanemNavigationItem
import com.mesutemre.kutuphanem.navigation.popUpToTop
import com.mesutemre.kutuphanem.ui.theme.*
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemBaseInput
import com.mesutemre.kutuphanem.util.customcomponents.progressbar.KutuphanemLoader
import com.mesutemre.kutuphanem_ui.button.KutuphanemMainMaterialButton

@Composable
fun LoginForm(
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val loginState = loginViewModel.state.collectAsState().value
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        loginViewModel.loginErrorMessage.collect {
            showSnackbar(
                it.message
                    ?: context.getString(
                        it.messageId ?: R.string.app_name
                    ),
                it.duration,
                it.type
            )
        }
    }
    Card(
        shape = RoundedCornerShape(20.sdp),
        modifier = Modifier
            .padding(start = 30.sdp, end = 30.sdp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colorPalette.white
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.sdp, end = 20.sdp)
        ) {

            if (loginState.isLoading) {
                KutuphanemLoader(
                    modifier = Modifier
                        .width(220.sdp)
                        .height(220.sdp)
                        .align(Alignment.Center)
                )
            }
            if (loginState.isSuccess) {
                navController.navigate(KutuphanemNavigationItem.DashboardScreen.screenRoute) {
                    popUpToTop(navController)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (loginState.isLoading) 0.4f else 1f)
            ) {
                Text(
                    text = stringResource(id = R.string.girisLabel),
                    style = Typography.largeAllegraPrimaryBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.sdp)
                )

                UserName(loginUserState = loginState, loginViewModel = loginViewModel)
                UserPassword(loginPasswordState = loginState, loginViewModel = loginViewModel)
                ForgotPasswordArea()
                KutuphanemMainMaterialButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.sdp),
                    text = stringResource(id = R.string.girisButtonLabel),
                    iconId = R.drawable.ic_baseline_login_24,
                    textStyle = MaterialTheme.typography.smallUbuntuWhiteBold,
                    isEnabled = !loginState.isLoading
                ) {
                    localFocusManager.clearFocus()
                    loginViewModel.onLoginFormEvent(LoginValidationEvent.Submit)
                }
                Text(
                    text = stringResource(id = R.string.hesapYokKayitLabel),
                    style = MaterialTheme.typography.thinyAllegraPrimary,
                    modifier = Modifier
                        .padding(bottom = 20.sdp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UserName(loginUserState: LoginFormState, loginViewModel: LoginViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    KutuphanemBaseInput(
        text = loginUserState.username,
        singleLine = true,
        onChange = {
            loginViewModel.onLoginFormEvent(LoginValidationEvent.UsernameChanged(it))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.sdp)
            .onFocusChanged {
                loginViewModel.onUsernameFocusedChange()
            },
        isError = loginUserState.usernameError,
        errorMessage = if (loginUserState.usernameErrorMessage != null) stringResource(id = loginUserState.usernameErrorMessage!!) else "",
        hint = stringResource(id = R.string.login_email_hint),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }
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
private fun UserPassword(loginPasswordState: LoginFormState, loginViewModel: LoginViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    KutuphanemBaseInput(
        text = loginPasswordState.password,
        singleLine = true,
        onChange = {
            loginViewModel.onLoginFormEvent(LoginValidationEvent.PasswordChanged(it))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.sdp)
            .onFocusChanged {
                loginViewModel.onPasswordFocusedChange()
            },
        isError = loginPasswordState.passwordError,
        errorMessage = if (loginPasswordState.passwordErrorMessage != null) stringResource(id = loginPasswordState.passwordErrorMessage!!) else "",
        hint = stringResource(id = R.string.login_password_hint),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }
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