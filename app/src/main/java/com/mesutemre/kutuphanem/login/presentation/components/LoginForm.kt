package com.mesutemre.kutuphanem.login.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.login.presentation.LoginFormState
import com.mesutemre.kutuphanem.login.presentation.LoginViewModel
import com.mesutemre.kutuphanem.ui.theme.Typography
import com.mesutemre.kutuphanem.ui.theme.colorPalette
import com.mesutemre.kutuphanem.ui.theme.largeAllegraPrimaryBold
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.customcomponents.KutuphanemBaseInput

@Composable
fun LoginForm(loginViewModel: LoginViewModel = hiltViewModel()) {
    val loginState by loginViewModel.loginState.observeAsState(initial = LoginFormState())
    //val loginPasswordState by loginViewModel.loginPasswordState.observeAsState(initial = PasswordState())
    Card(
        shape = RoundedCornerShape(20.sdp), elevation = 20.sdp,
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
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.sdp), onClick = {
                loginViewModel.validateUsername()
            }) {
                Text(text = "Tıkla")
            }

        }

    }
}

@Composable
private fun UserName (loginUserState:LoginFormState,loginViewModel: LoginViewModel) {

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
        trailingIcon = {
            Icon(
                Icons.Filled.Person, contentDescription = stringResource(
                    id = R.string.login_email_hint
                )
            )
        }
    )
}

@Composable
private fun UserPassword (loginPasswordState:LoginFormState,loginViewModel: LoginViewModel) {
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
        trailingIcon = {
            Icon(
                Icons.Filled.Password, contentDescription = stringResource(
                    id = R.string.login_password_hint
                )
            )
        }
    )
}