package com.mesutemre.kutuphanem_ui.chart

import android.graphics.Paint
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp
import com.mesutemre.kutuphanem_ui.theme.black
import com.mesutemre.kutuphanem_ui.theme.white

@Composable
fun KutuphanemPieChart(
    modifier: Modifier = Modifier,
    radius: Float = 400f,
    input: List<KutuphanemPieChartInput>
) {
    var inputList by remember {
        mutableStateOf(input)
    }
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
                                (circleCenter.y + (radius - (radius - 250f) / 1.2f) * factor) * percentagePoriton.value,
                                Paint().apply {
                                    textSize = 14.sp.toPx()
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
                                textSize = 14.sp.toPx()
                                textAlign = Paint.Align.CENTER
                                color = black.toArgb()
                                isFakeBoldText = true
                            }
                        )
                    }
                }
            }
        }
    }
}