package com.mesutemre.kutuphanem.util.customcomponents.input

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.constraintlayout.compose.ConstraintLayout
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.ui.theme.*
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.smallUbuntuError

@OptIn(ExperimentalComposeUiApi::class)
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
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    errorMessage: String = "",
    maxCharacter: Int? = null,
) {
    var textState by remember {
        mutableStateOf(TextFieldValue(text))
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        TextField(
            value = textState,
            modifier = modifier,
            textStyle = textStyle,
            maxLines = maxLine,
            singleLine = singleLine,
            onValueChange = { newText ->
                maxCharacter?.let {
                    if (newText.text.length <= maxCharacter) {
                        textState = newText
                        onChange.invoke(textState.text)
                    }
                } ?: run {
                    textState = newText
                    onChange.invoke(textState.text)
                }
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
            visualTransformation = visualTransformation,
            enabled = enabled,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions

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

@Composable
fun KutuphanemSearchInput(
    text: String,
    modifier: Modifier,
    @DrawableRes leadingIconId: Int = R.drawable.ic_baseline_search_grey_24,
    leadingIconColor: Color = Color.Unspecified,
    @DrawableRes trailingIconId: Int? = null,
    trailingIconColor: Color = Color.Unspecified,
    onLeadingIconClick: (() -> Unit) = {},
    onTrailingIconClick: (() -> Unit) = {},
    maxCharacter: Int? = null,
    placeholderText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.smallUbuntuBlack
) {
    var text by remember {
        mutableStateOf(TextFieldValue(text))
    }
    var placeHolder by remember {
        mutableStateOf(placeholderText)
    }
    BasicTextField(value = text,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorPalette.white)
            .border(
                BorderStroke((1 / 2).sdp, MaterialTheme.colorPalette.secondaryGray),
                shape = MaterialTheme.shapes.medium
            )
            .height(32.sdp),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = leadingIconId),
                    contentDescription = "",
                    tint = leadingIconColor,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(horizontal = 4.sdp)
                        .clickable {
                            onLeadingIconClick.invoke()
                        }
                )
                Box(Modifier.weight(1f)) {
                    if (text.text.isEmpty()) Text(
                        placeHolder,
                        color = MaterialTheme.colorPalette.otherGrayLight,
                        style = textStyle
                    )
                    innerTextField()
                }
                if (trailingIconId != null) {
                    Icon(
                        painter = painterResource(id = trailingIconId),
                        contentDescription = "",
                        tint = trailingIconColor,
                        modifier = Modifier
                            .padding(horizontal = 4.sdp)
                            .clip(CircleShape)
                            .clickable {
                                onTrailingIconClick.invoke()
                            }
                    )
                }
            }
        },
        onValueChange = { newText ->
            maxCharacter?.let {
                if (newText.text.length <= maxCharacter) {
                    text = newText
                    onValueChange(newText.text)
                }
            } ?: run {
                text = newText
                onValueChange(newText.text)
            }
        })
}

@Composable
fun KutuphanemFormInput(
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
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    errorMessage: String = "",
    maxCharacter: Int? = null,
    label: String? = null,
    labelStyle: TextStyle = Typography.smallUbuntuTransparent
) {
    var textState by remember {
        mutableStateOf(TextFieldValue(text))
    }

    Column(modifier = modifier) {
        TextField(
            value = textState,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(
                    color = MaterialTheme.colorPalette.white,
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    BorderStroke((1).sdp, MaterialTheme.colorPalette.otherGrayLight),
                    shape = MaterialTheme.shapes.medium
                ),
            textStyle = textStyle,
            maxLines = maxLine,
            singleLine = singleLine,
            onValueChange = { newText ->
                maxCharacter?.let {
                    if (newText.text.length <= maxCharacter) {
                        textState = newText
                        onChange.invoke(textState.text)
                    }
                } ?: run {
                    textState = newText
                    onChange.invoke(textState.text)
                }
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
            label = {
                if (label != null) {
                    Text(
                        text = label,
                        style = labelStyle
                    )
                }
            },
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
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

@Composable
fun KutuphanemOutlinedFormTextField(
    text: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    maxLine: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = Typography.smallUbuntuBlack,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = MaterialTheme.colorPalette.secondaryGray,
        unfocusedIndicatorColor = MaterialTheme.colorPalette.secondaryGray,
        trailingIconColor = MaterialTheme.colorPalette.secondaryGray,
        errorIndicatorColor = MaterialTheme.colorPalette.kirmizi,
        cursorColor = MaterialTheme.colorPalette.secondaryGray,
        errorCursorColor = MaterialTheme.colorPalette.kirmizi,
        backgroundColor = MaterialTheme.colorPalette.white
    ),
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    errorMessage: String? = null,
    maxCharacter: Int? = null,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.smallUbuntuTransparent,
    placeHolder: String,
    placeHolderStyle: TextStyle = MaterialTheme.typography.smallUbuntuTransparent
) {
    Column {
        val isError by remember {
            mutableStateOf(errorMessage.isNullOrEmpty().not())
        }
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = { str ->
                maxCharacter?.let {
                    if (str.length <= it) {
                        onChange(str)
                    }
                } ?: run {
                    onChange(str)
                }
            },
            isError = isError,
            readOnly = readOnly,
            enabled = enabled,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            maxLines = maxLine,
            label = {
                Text(text = label, style = labelStyle)
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
            placeholder = {
                Text(text = placeHolder, style = placeHolderStyle)
            },
            textStyle = textStyle,
            colors = colors,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )

        if (isError) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.sdp),
                text = errorMessage ?: "",
                style = MaterialTheme.typography.smallUbuntuError
            )
        }
    }
}

