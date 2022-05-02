package com.mesutemre.kutuphanem.model

import androidx.compose.material.SnackbarDuration

data class SnackbarMessageEvent(
    val message:String,
    val duration:SnackbarDuration = SnackbarDuration.Short,
    val type:Int
)
