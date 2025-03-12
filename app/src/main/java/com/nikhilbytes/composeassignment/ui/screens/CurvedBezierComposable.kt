package com.nikhilbytes.composeassignment.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.hypot
import kotlin.math.min

@Composable
fun CurvedBezierComposable(
    modifier: Modifier,
    color: Color,
    isRounded: Boolean = false,
) {

    val fraction by animateFloatAsState(
        targetValue = if (isRounded) 0f else 1f,
        animationSpec = tween(durationMillis = 700)
    )

    Canvas(modifier) {

        val width = size.width
        val height = size.height
        if (width <= 0f || height <= 0f) return@Canvas


        val finalCenterX = width * .50f
        val finalCenterY = -50f

        val circleRadius = width * 0.7f//min(width, height) / 2f

        val cornerRadius = 16.dp.toPx()

        val currentCornerRadius = lerp(cornerRadius, circleRadius, fraction)

        val currentWidth = lerp(width, circleRadius * 2, fraction)
        val currentHeight = lerp(height, circleRadius * 2, fraction)


        val startCenterX = width / 2f
        val startCenterY = height / 2f

        val centerX = lerp(startCenterX, finalCenterX, fraction)
        val centerY = lerp(startCenterY, finalCenterY, fraction)

        val left = centerX - (currentWidth / 2f)
        val top = centerY - (currentHeight / 2f)
        val right = left + currentWidth
        val bottom = top + currentHeight

        val maxCornerRadius = min(currentWidth, currentHeight) / 2f
        val effectiveCornerRadius = min(currentCornerRadius, maxCornerRadius)

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = left,
                    top = top,
                    right = right,
                    bottom = bottom,
                    cornerRadius = CornerRadius(effectiveCornerRadius)
                )
            )
        }

        drawPath(path = path, color = color, style = Fill)
    }
}

@Preview
@Composable
fun MyPreview() {
    CurvedBezierComposable(
        modifier = Modifier

            .width(564.dp)
            .height(564.dp)
            .absoluteOffset(x = 50.dp, y = (-50).dp),
        isRounded = false,
        color = Color(0xFFFFFFFF)
    )
}