package com.nikhilbytes.composeassignment.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurvedBezierComposable(
    modifier: Modifier,
    color: Color,
    isRounded: Boolean = false,
) {
    val curveHeight = 80
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val width = size.width
            val canvasHeight = size.height

            val path = Path().apply {
                if (isRounded) {
                    val cornerRadius = 16.dp.toPx()
                    moveTo(0f, cornerRadius)
                    quadraticTo(0f, 0f, cornerRadius, 0f)

                    lineTo(width - cornerRadius, 0f)

                    quadraticTo(width, 0f, width, cornerRadius)

                    lineTo(width, canvasHeight - cornerRadius)

                    quadraticTo(width, canvasHeight, width - cornerRadius, canvasHeight)

                    lineTo(cornerRadius, canvasHeight)
                    quadraticTo(0f, canvasHeight, 0f, canvasHeight - cornerRadius)
                    close()
                } else {
                    moveTo(0f, 0f)
                    lineTo(width, 0f)
                    lineTo(width, canvasHeight - curveHeight)
                    cubicTo(
                        x1 = width * 0.75f,
                        y1 = canvasHeight,
                        x2 = width * 0.3f,
                        y2 = canvasHeight,
                        x3 = 0f,
                        y3 = canvasHeight / 5
                    )

                    lineTo(0f, 0f)
                    close()
                }
            }

            drawPath(
                path = path,
                color = color,
                style = Fill
            )
        }
    }
}

@Preview
@Composable
fun MyPreview() {
//    CurvedBezierComposable {
//
//    }
}