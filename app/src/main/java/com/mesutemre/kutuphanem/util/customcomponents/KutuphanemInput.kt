package com.mesutemre.kutuphanem.util.customcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.constraintlayout.compose.ConstraintLayout
import com.mesutemre.kutuphanem.ui.theme.*

@Composable
fun KutuphanemBaseInput(
    text: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    maxLine: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = Typography.smallUbuntuBlack,
    isError: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = MaterialTheme.colorPalette.transparent,
        unfocusedIndicatorColor = MaterialTheme.colorPalette.transparent,
        trailingIconColor = MaterialTheme.colorPalette.transparent,
        errorIndicatorColor = MaterialTheme.colorPalette.kirmizi,
        cursorColor = MaterialTheme.colorPalette.transparent,
        errorCursorColor = MaterialTheme.colorPalette.kirmizi
    ),
    hint: String? = null,
    singleLine: Boolean = false,
    hintStyle: TextStyle = Typography.smallUbuntuTransparent,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    errorMessage: String = ""
) {
    var textState by remember {
        mutableStateOf(TextFieldValue(text))
    }
    Column {
        TextField(
            value = textState,
            modifier = modifier,
            textStyle = textStyle,
            maxLines = maxLine,
            singleLine = singleLine,
            onValueChange = {
                textState = it
                onChange.invoke(textState.text)
            },
            trailingIcon = {
                when (isError) {
                    true -> {
                        Icon(
                            Icons.Filled.Error, contentDescription = errorMessage,
                            tint = MaterialTheme.colorPalette.kirmizi
                        )
                    }
                    false -> {
                        trailingIcon?.let {
                            trailingIcon()
                        }
                    }
                }
            },
            leadingIcon = leadingIcon,
            colors = colors,
            placeholder = {
                if (hint != null)
                    Text(
                        text = hint,
                        style = hintStyle
                    )
            },
            isError = isError,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            enabled = enabled,
            readOnly = readOnly
        )
        if (isError) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(top = 4.sdp)
                    .fillMaxWidth()
            ) {
                val (errorText) = createRefs()
                Text(
                    text = errorMessage!!,
                    style = textStyle,
                    color = MaterialTheme.colorPalette.kirmizi,
                    modifier = Modifier.constrainAs(errorText) {
                        start.linkTo(parent.start)
                    }
                )
            }
        }
    }
}