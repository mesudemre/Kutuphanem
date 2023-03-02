package com.mesutemre.kutuphanem_ui.chart

import android.graphics.Paint
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.content.res.ResourcesCompat
import com.mesutemre.kutuphanem_ui.R
import com.mesutemre.kutuphanem_ui.theme.black
import com.mesutemre.kutuphanem_ui.theme.googleDarkGray
import com.mesutemre.kutuphanem_ui.theme.sdp
import com.mesutemre.kutuphanem_ui.theme.white

@Composable
fun KutuphanemPieChart(
    modifier: Modifier = Modifier,
    radius: Float = 250.sdp.value,
    input: List<KutuphanemPieChartInput>
) {
    var inputList by remember {
        mutableStateOf(input)
    }
    val customTypeFace = ResourcesCompat.getFont(LocalContext.current, R.font.ubuntu)
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val pathPortion = remember {
            androidx.compose.animation.core.Animatable(initialValue = 0f)
        }
        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }
        val percentagePoriton = remember {
            androidx.compose.animation.core.Animatable(initialValue = 0f)
        }
        LaunchedEffect(key1 = true) {
            percentagePoriton.animateTo(
                1f, animationSpec = tween(
                    durationMillis = 1500,
                    easing = FastOutLinearInEasing
                )
            )
        }
        val fontSize = with(LocalDensity.current) {
            12.sdp.toSp()
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height

            val totalValue = input.sumOf {
                it.value
            }
            val anglePerValue = 360f / totalValue
            var currentStartAngle = 0f
            inputList.forEach { pieChartInput ->
                val angleToDraw = pieChartInput.value * anglePerValue * pathPortion.value
                val circleCenter = Offset(x = width / 2f, y = height / 2f)
                scale(1f) {
                    drawArc(
                        color = pieChartInput.color,
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw,
                        useCenter = true,
                        size = Size(
                            width = radius * 1.5f,
                            height = radius * 1.5f
                        ),
                        topLeft = Offset(
                            (width - radius * 1.5f) / 2f,
                            (height - radius * 1.5f) / 2f
                        )
                    )
                    currentStartAngle += angleToDraw
                }
                var rotateAngle = currentStartAngle - angleToDraw / 2f - 90f
                var factor = 1f
                if (rotateAngle > 90f) {
                    rotateAngle = (rotateAngle + 180).mod(360f)
                    factor = -0.92f
                }
                val percentage = (pieChartInput.value / totalValue.toFloat() * 100).toInt()

                drawContext.canvas.nativeCanvas.apply {
                    if (percentage > 3) {
                        rotate(rotateAngle) {
                            drawText(
                                "% $percentage",
                                circleCenter.x,
                                (circleCenter.y + (radius - (radius - 200f) / 0.9f) * factor) * percentagePoriton.value,
                                Paint().apply {
                                    textSize = fontSize.toPx()
                                    //textSize = 14.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = white.toArgb()
                                    isFakeBoldText = true
                                }
                            )
                        }
                    }
                }


                rotate(rotateAngle) {
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "${pieChartInput.description}",
                            circleCenter.x,
                            circleCenter.y + radius * 0.85f * factor,
                            Paint().apply {
                                textSize = fontSize.toPx()
                                typeface = customTypeFace
                                textAlign = Paint.Align.CENTER
                                color = black.toArgb()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Kutuphanem3dBarChart(
    modifier: Modifier = Modifier,
    inputList: List<KutuphanemBarChartInput>
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val listSum by remember {
            mutableStateOf(
                inputList.sumOf {
                    it.value
                }
            )
        }
        val pathPortion = remember {
            androidx.compose.animation.core.Animatable(initialValue = 0f)
        }
        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }
        inputList.forEach { item ->
            val percentage = item.value / listSum.toFloat()
            KutuphanemBarChartItem(
                modifier = Modifier
                    .height(40.sdp * pathPortion.value * percentage * inputList.size)
                    .width(40.sdp),
                primaryColor = item.color, value = item.value, description = item.description
            )
        }
    }
}

@Composable
private fun KutuphanemBarChartItem(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    value: Int,
    description: String
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val barWidth = width / 5 * 3
            val barHeight = height / 8 * 7
            val barHeight3dPart = height - barHeight
            val barWidth3dPart = (width - barWidth) * (height * 0.002f)

            var path = Path().apply {
                moveTo(0f, height)
                lineTo(barWidth, height)
                lineTo(barWidth, height - barHeight)
                lineTo(0f, height - barHeight)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(googleDarkGray, primaryColor)
                )
            )
            path = Path().apply {
                moveTo(barWidth, height - barHeight)
                lineTo(barWidth3dPart + barWidth, 0f)
                lineTo(barWidth3dPart + barWidth, barHeight)
                lineTo(barWidth, height)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(primaryColor, googleDarkGray)
                )
            )
            path = Path().apply {
                moveTo(0f, barHeight3dPart)
                lineTo(barWidth, barHeight3dPart)
                lineTo(barWidth + barWidth3dPart, 0f)
                lineTo(barWidth3dPart, 0f)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(googleDarkGray, primaryColor)
                )
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${value}",
                    barWidth / 5f,
                    height + 55f,
                    android.graphics.Paint().apply {
                        color = black.toArgb()
                        textSize = 10.sdp.toPx()
                    }
                )
            }

            drawContext.canvas.nativeCanvas.apply {
                rotate(-50f, pivot = Offset(barWidth3dPart - 5, 0f)) {
                    drawText(
                        description,
                        0f,
                        0f,
                        android.graphics.Paint().apply {
                            color = black.toArgb()
                            textSize = 8.sdp.toPx()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }
}