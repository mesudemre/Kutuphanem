package com.mesutemre.kutuphanem.model

import androidx.annotation.StringRes
import androidx.compose.material.SnackbarDuration

data class SnackbarMessageEvent(
    @StringRes val messageId: Int? = null,
    val message: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val type: Int
)
