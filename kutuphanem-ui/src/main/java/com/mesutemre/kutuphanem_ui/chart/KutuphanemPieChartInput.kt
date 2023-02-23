package com.mesutemre.kutuphanem_ui.chart

import androidx.compose.ui.graphics.Color


data class KutuphanemPieChartInput(
    val color: Color,
    val value: Int,
    val description: String,
    val isTapped: Boolean = false
)
