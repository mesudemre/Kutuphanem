package com.mesutemre.kutuphanem.util.customcomponents.chart

import android.graphics.Paint
import android.graphics.Path
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import com.mesutemre.kutuphanem.ui.theme.sdp
import com.mesutemre.kutuphanem.util.getColorListForPieChart
import java.lang.Integer.min
import kotlin.math.atan2
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun KutuphanemPieChart(
    modifier: Modifier,
    pointList: List<Float>
) {
    val total = pointList.sum()
    val proportions = pointList.map {
        it * 100 / total
    }
    val sweepAnglePercentage = proportions.map {
        360 * it / 100
    }
    val colorList = getColorListForPieChart()
        PieChart(
            modifier = modifier,
            progress = sweepAnglePercentage,
            colors = colorList
        )
}

@Composable
fun PieChart(
    modifier: Modifier,
    progress: List<Float>,
    colors: List<Color>,
    isDonut: Boolean = false,
    percentColor: Color = Color.White
) {

    if (progress.isEmpty() || progress.size != colors.size) return

    val total = progress.sum()
    val proportions = progress.map {
        it * 100 / total
    }
    val angleProgress = proportions.map {
        360 * it / 100
    }

    val progressSize = mutableListOf<Float>()
    progressSize.add(angleProgress.first())

    for (x in 1 until angleProgress.size)
        progressSize.add(angleProgress[x] + progressSize[x - 1])

    var activePie by remember {
        mutableStateOf(-1)
    }

    var startAngle = 270f

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {

        val sideSize = min(constraints.maxWidth, constraints.maxHeight)
        val padding = (sideSize * if (isDonut) 30 else 20) / 100f


        val pathPortion = remember {
            androidx.compose.animation.core.Animatable(initialValue = 0f)
        }
        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }

        val size = Size(sideSize.toFloat() - padding, sideSize.toFloat() - padding)

        Canvas(
            modifier = Modifier
                .width(200.sdp)
                .height(200.sdp)
                .pointerInput(true) {

                    if (!isDonut)
                        return@pointerInput

                    detectTapGestures {
                        val clickedAngle = convertTouchEventPointToAngle(
                            sideSize.toFloat(),
                            sideSize.toFloat(),
                            it.x,
                            it.y
                        )
                        progressSize.forEachIndexed { index, item ->
                            if (clickedAngle <= item) {
                                if (activePie != index)
                                    activePie = index

                                return@detectTapGestures
                            }
                        }
                    }
                }
        ) {

            angleProgress.forEachIndexed { index, arcProgress ->
                drawPie(
                    colors[index],
                    startAngle,
                    arcProgress * pathPortion.value,
                    size,
                    padding = padding,
                    isDonut = isDonut,
                    isActive = activePie == index
                )
                startAngle += arcProgress
            }

            if (activePie != -1)
                drawContext.canvas.nativeCanvas.apply {
                    val fontSize = 60.sdp.toPx()
                    drawText(
                        "${proportions[activePie].roundToInt()}%",
                        (sideSize / 2) + fontSize / 4, (sideSize / 2) + fontSize / 3,
                        Paint().apply {
                            color = percentColor.toArgb()
                            textSize = fontSize
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
        }
    }

}

private fun DrawScope.drawPie(
    color: Color,
    startAngle: Float,
    arcProgress: Float,
    size: Size,
    padding: Float,
    isDonut: Boolean = false,
    isActive: Boolean = false
): Path {

    return Path().apply {
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = arcProgress,
            useCenter = !isDonut,
            size = size,
            style = if (isDonut) Stroke(
                width = if (isActive) 120f else 100f,
            ) else Fill,
            topLeft = Offset(0f, 0f)
        )
    }
}

private fun convertTouchEventPointToAngle(
    width: Float,
    height: Float,
    xPos: Float,
    yPos: Float
): Double {
    var x = xPos - (width * 0.5f)
    val y = yPos - (height * 0.5f)

    var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
    angle = if (angle < 0) angle + 360 else angle
    return angle
}