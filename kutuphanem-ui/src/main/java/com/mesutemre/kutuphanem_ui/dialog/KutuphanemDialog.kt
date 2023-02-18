package com.mesutemre.kutuphanem_ui.dialog

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mesutemre.kutuphanem_ui.R
import com.mesutemre.kutuphanem_ui.button.KutuphanemMainMaterialButton
import com.mesutemre.kutuphanem_ui.button.KutuphanemTerritaryButton
import com.mesutemre.kutuphanem_ui.theme.*

@Composable
fun CustomKutuphanemDialog(
    modifier: Modifier,
    @KutuphanemDialogType type: Int,
    title: String,
    description: String,
    dismissButtonText: String,
    confirmButtonText: String,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit
) {
    val animateShape = remember { Animatable(0f) }
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = 360f,
            animationSpec = repeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = FastOutLinearInEasing
                ),
                repeatMode = RepeatMode.Restart,
                iterations = 1
            )
        )
    }

    Dialog(
        onDismissRequest = { onDismissDialog.invoke() }
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = 4.sdp,
            modifier = modifier,
            backgroundColor = MaterialTheme.colorPalette.white
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (content, bottomButtons) = createRefs()

                Column(
                    modifier = Modifier
                        .constrainAs(content) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(bottomButtons.top)
                            height = Dimension.fillToConstraints
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        when (type) {
                            QA_DLG -> {
                                Icon(
                                    painterResource(id = R.drawable.ic_popup_question),
                                    contentDescription = title,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.rotate(animateShape.value)
                                )
                            }
                            WARNING_DLG -> {
                                Icon(
                                    painterResource(id = R.drawable.ic_popup_warning),
                                    contentDescription = title,
                                    tint = Color.Unspecified
                                )
                            }
                            INFO_DLG -> {
                                Icon(
                                    painterResource(id = R.drawable.ic_popup_info),
                                    contentDescription = title,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.rotate(animateShape.value)
                                )
                            }
                            SUCCESS_DLG -> {
                                Icon(
                                    painterResource(id = R.drawable.ic_popup_success),
                                    contentDescription = title,
                                    tint = Color.Unspecified
                                )
                            }
                            ERROR_DLG -> {
                                Icon(
                                    painterResource(id = R.drawable.ic_popup_error),
                                    contentDescription = title,
                                    tint = Color.Unspecified
                                )
                            }
                            else -> {
                                Icon(
                                    painterResource(id = R.drawable.ic_popup_info),
                                    contentDescription = title,
                                    tint = Color.Unspecified
                                )
                            }
                        }

                    }
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.sdp, vertical = 8.sdp),
                        style = MaterialTheme.typography.mediumAllegraBlackBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.sdp, end = 16.sdp, top = 8.sdp),
                        style = MaterialTheme.typography.normalUbuntuBlack.copy(
                            lineHeight = 16.ssp
                        ),
                        textAlign = TextAlign.Center
                    )

                }
                Row(modifier = Modifier
                    .constrainAs(bottomButtons) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.sdp, vertical = 8.sdp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        KutuphanemTerritaryButton(
                            buttonText = dismissButtonText,
                            modifier = Modifier.weight(0.5f)
                        ) {
                            onDismissDialog.invoke()
                        }
                        KutuphanemMainMaterialButton(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(start = 8.sdp),
                            text = confirmButtonText
                        ) {
                            onConfirmDialog.invoke()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KutuphanemPermissionDialog(
    modifier: Modifier,
    permission: String,
    description: String,
    confirmButtonText: String,
    onOkClick: () -> Unit
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = 4.sdp,
            modifier = modifier,
            backgroundColor = MaterialTheme.colorPalette.white
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (content, bottomButtons) = createRefs()

                Column(
                    modifier = Modifier
                        .constrainAs(content) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(bottomButtons.top)
                            height = Dimension.fillToConstraints
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_popup_warning),
                            contentDescription = permission,
                            tint = Color.Unspecified
                        )
                    }
                    Text(
                        text = permission,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.sdp, vertical = 8.sdp),
                        style = MaterialTheme.typography.mediumAllegraBlackBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.sdp, end = 16.sdp, top = 8.sdp),
                        style = MaterialTheme.typography.normalUbuntuBlack.copy(
                            lineHeight = 16.ssp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                Row(modifier = Modifier
                    .constrainAs(bottomButtons) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.sdp, vertical = 8.sdp)) {
                    KutuphanemMainMaterialButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = confirmButtonText
                    ) {
                        onOkClick()
                    }
                }
            }
        }
    }
}