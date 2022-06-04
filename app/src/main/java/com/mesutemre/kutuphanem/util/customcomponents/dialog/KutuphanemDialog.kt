package com.mesutemre.kutuphanem.util.customcomponents.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.model.*
import com.mesutemre.kutuphanem.ui.theme.*
import com.mesutemre.kutuphanem.util.customcomponents.button.KutuphanemMainMaterialButton
import com.mesutemre.kutuphanem.util.customcomponents.button.KutuphanemTerritaryButton

@Composable
fun CustomKutuphanemDialog(
    modifier: Modifier,
    @KutuphanemDialogType type: Int,
    title: String,
    description: String,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit
) {
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
                            .height(40.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        when (type) {
                            QA_DLG -> {
                                Icon(
                                    Icons.Filled.QuestionAnswer,
                                    contentDescription = title,
                                    modifier = Modifier.height(40.sdp),
                                    tint = MaterialTheme.colorPalette.lacivert
                                )
                            }
                            WARNING_DLG -> {
                                Icon(
                                    Icons.Filled.Warning,
                                    contentDescription = title,
                                    modifier = Modifier.height(40.sdp),
                                    tint = MaterialTheme.colorPalette.turuncu
                                )
                            }
                            INFO_DLG -> {
                                Icon(
                                    Icons.Filled.Info,
                                    contentDescription = title,
                                    modifier = Modifier.height(40.sdp),
                                    tint = MaterialTheme.colorPalette.primaryTextColor
                                )
                            }
                            SUCCESS_DLG -> {
                                Icon(
                                    Icons.Filled.CheckCircleOutline,
                                    contentDescription = title,
                                    modifier = Modifier.height(40.sdp),
                                    tint = MaterialTheme.colorPalette.fistikYesil
                                )
                            }
                            ERROR_DLG -> {
                                Icon(
                                    Icons.Filled.ErrorOutline,
                                    contentDescription = title,
                                    modifier = Modifier.height(40.sdp),
                                    tint = MaterialTheme.colorPalette.kirmizi
                                )
                            }
                            else -> {
                                Icon(
                                    Icons.Filled.Info,
                                    contentDescription = title,
                                    modifier = Modifier.height(40.sdp),
                                    tint = MaterialTheme.colorPalette.primaryTextColor
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
                        style = MaterialTheme.typography.normalUbuntuBlack,
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
                            buttonText = stringResource(id = R.string.vazgecLabel),
                            modifier = Modifier.weight(0.5f)
                        ) {
                            onDismissDialog.invoke()
                        }
                        KutuphanemMainMaterialButton(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(start = 8.sdp),
                            text = stringResource(id = R.string.ayarlaLabel)
                        ) {
                            onConfirmDialog.invoke()
                        }
                    }
                }
            }
        }
    }
}