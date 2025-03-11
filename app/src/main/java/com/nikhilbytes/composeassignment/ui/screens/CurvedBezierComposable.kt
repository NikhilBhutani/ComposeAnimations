package com.nikhilbytes.composeassignment.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
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
        targetValue = if (isRounded.not()) 1f else 0f,
        animationSpec = tween(durationMillis = 800)
    )

    // For 16dp corners
    val cornerDp = 16.dp

    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val cornerRadius = cornerDp.toPx()

        // We'll define 4 corners as cubic segments:
        // 1) Top-left corner
        // 2) Top-right corner
        // 3) Bottom-right corner (which will become the wave in wave mode)
        // 4) Bottom-left corner

        // Each corner requires:
        //   - a "start point" (where we do the cubic from),
        //   - 2 control points (C1, C2),
        //   - an "end point."

        //---------------------------
        //  RECTANGLE SHAPE POINTS
        //---------------------------
        // For the rectangle corners, we'll do small arcs:

        // Top-left corner (cubic) from (0, cornerR) to (cornerR, 0)
        val rectTLStart = Offset(0f, cornerRadius)
        val rectTLC1    = Offset(0f, 0f)                // control near top-left
        val rectTLC2    = Offset(0f, 0f)                // can be refined for better curvature
        val rectTLEnd   = Offset(cornerRadius, 0f)

        // Then we line across the top to (w - cornerRadius, 0) for top-right corner start
        val rectTRStart = Offset(w - cornerRadius, 0f)
        // top-right corner from (w - cornerR, 0) -> (w, cornerR)
        val rectTRC1    = Offset(w, 0f)
        val rectTRC2    = Offset(w, 0f)
        val rectTREnd   = Offset(w, cornerRadius)

        // Then line down the right edge to (w, h - cornerRadius) for bottom-right corner start
        val rectBRStart = Offset(w, h - cornerRadius)
        val rectBRC1    = Offset(w, h)
        val rectBRC2    = Offset(w, h)
        val rectBREnd   = Offset(w - cornerRadius, h)

        // Then line across bottom to (cornerRadius, h) for bottom-left corner start
        val rectBLStart = Offset(cornerRadius, h)
        val rectBLC1    = Offset(0f, h)
        val rectBLC2    = Offset(0f, h)
        val rectBLEnd   = Offset(0f, h - cornerRadius)

        //---------------------------
        //  WAVE SHAPE POINTS
        //---------------------------
        // We'll keep the top corners the same 16dp arcs,
        // but the BOTTOM-RIGHT corner will become a wave.

        // Wave height:
        val waveHeight = h * 0.3f

        // Top-left corner (same as rect for wave):
        val waveTLStart = Offset(0f, cornerRadius)
        val waveTLC1    = Offset(0f, 0f)
        val waveTLC2    = Offset(0f, 0f)
        val waveTLEnd   = Offset(cornerRadius, 0f)

        // Top-right corner (same as rect for wave):
        val waveTRStart = Offset(w - cornerRadius, 0f)
        val waveTRC1    = Offset(w, 0f)
        val waveTRC2    = Offset(w, 0f)
        val waveTREnd   = Offset(w, cornerRadius)

        // Now, BOTTOM-RIGHT corner for wave:
        // Instead of a small arc, we do a big cubic wave from (w, h - cornerR) to (w - cornerR, h)
        // with control points that create a wave shape.
        // Tweak as needed to get the wave shape you want.
        val waveBRStart = Offset(w, h - cornerRadius)
        val waveBRC1    = Offset(w * 0.75f, h + waveHeight)    // control near bottom beyond the edge
        val waveBRC2    = Offset(w * 0.25f, h - waveHeight)    // second control
        val waveBREnd   = Offset(w - cornerRadius, h)

        // Bottom-left corner remains the same 16dp:
        val waveBLStart = Offset(cornerRadius, h)
        val waveBLC1    = Offset(0f, h)
        val waveBLC2    = Offset(0f, h)
        val waveBLEnd   = Offset(0f, h - cornerRadius)

        //---------------------------
        //  LERP function
        //---------------------------
        fun lerpOffset(a: Offset, b: Offset): Offset {
            return Offset(
                x = lerp(a.x, b.x, fraction),
                y = lerp(a.y, b.y, fraction)
            )
        }

        //---------------------------
        //  Interpolate corners
        //---------------------------
        // For each corner, we have a start, c1, c2, end.
        // We'll do top-left corner:
        val tlStart = lerpOffset(rectTLStart, waveTLStart)
        val tlC1    = lerpOffset(rectTLC1,    waveTLC1)
        val tlC2    = lerpOffset(rectTLC2,    waveTLC2)
        val tlEnd   = lerpOffset(rectTLEnd,   waveTLEnd)

        // top-right corner
        val trStart = lerpOffset(rectTRStart, waveTRStart)
        val trC1    = lerpOffset(rectTRC1,    waveTRC1)
        val trC2    = lerpOffset(rectTRC2,    waveTRC2)
        val trEnd   = lerpOffset(rectTREnd,   waveTREnd)

        // bottom-right corner (the wave)
        val brStart = lerpOffset(rectBRStart, waveBRStart)
        val brC1    = lerpOffset(rectBRC1,    waveBRC1)
        val brC2    = lerpOffset(rectBRC2,    waveBRC2)
        val brEnd   = lerpOffset(rectBREnd,   waveBREnd)

        // bottom-left corner
        val blStart = lerpOffset(rectBLStart, waveBLStart)
        val blC1    = lerpOffset(rectBLC1,    waveBLC1)
        val blC2    = lerpOffset(rectBLC2,    waveBLC2)
        val blEnd   = lerpOffset(rectBLEnd,   waveBLEnd)

        //---------------------------
        //  Build the final path
        //---------------------------
        val path = Path().apply {
            // Move to the top-left corner start
            moveTo(tlStart.x, tlStart.y)
            // top-left corner cubic
            cubicTo(tlC1.x, tlC1.y, tlC2.x, tlC2.y, tlEnd.x, tlEnd.y)

            // line across top edge to top-right corner start
            lineTo(trStart.x, trStart.y)
            // top-right corner cubic
            cubicTo(trC1.x, trC1.y, trC2.x, trC2.y, trEnd.x, trEnd.y)

            // line down right edge to bottom-right corner start
            lineTo(brStart.x, brStart.y)
            // bottom-right corner (wave in wave mode)
            cubicTo(brC1.x, brC1.y, brC2.x, brC2.y, brEnd.x, brEnd.y)

            // line across bottom edge to bottom-left corner start
            lineTo(blStart.x, blStart.y)
            // bottom-left corner
            cubicTo(blC1.x, blC1.y, blC2.x, blC2.y, blEnd.x, blEnd.y)

            close()
        }

        // Finally, draw the shape
        drawPath(path = path, color = color, style = Fill)
    }
    

//    val fraction by animateFloatAsState(
//        targetValue = if (isRounded) 0f else 1f,
//        animationSpec = tween(800)
//    )
//
//    Canvas(modifier = modifier.fillMaxWidth().height(300.dp)) {
//        val w = size.width
//        val h = size.height
//        val curveHeight = h * 0.3f  // or any fraction
//
//        // Rectangle “points”
//        val rectP0 = Offset(0f, 0f)
//        val rectP1 = Offset(w, 0f)
//        val rectP2 = Offset(w, h)
//        val rectC1 = rectP2  // no actual curve
//        val rectC2 = rectP2
//        val rectP3 = Offset(0f, h)
//        val rectP4 = Offset(0f, 0f)
//
//        // Wave “points”
//        val waveP0 = Offset(0f, 0f)
//        val waveP1 = Offset(w, 0f)
//        val waveP2 = Offset(w, h - curveHeight)
//        val waveC1 = Offset(w * 0.75f, h)
//        val waveC2 = Offset(w * 0.3f, h)
//        val waveP3 = Offset(0f, h / 5)
//        val waveP4 = Offset(0f, 0f)
//
//        // Lerp them
//        fun lerpO(a: Offset, b: Offset) = Offset(
//            x = lerp(a.x, b.x, fraction),
//            y = lerp(a.y, b.y, fraction)
//        )
//
//        val p0 = lerpO(rectP0, waveP0)
//        val p1 = lerpO(rectP1, waveP1)
//        val p2 = lerpO(rectP2, waveP2)
//        val c1 = lerpO(rectC1, waveC1)
//        val c2 = lerpO(rectC2, waveC2)
//        val p3 = lerpO(rectP3, waveP3)
//        val p4 = lerpO(rectP4, waveP4)
//
//        val path = Path().apply {
//            moveTo(p0.x, p0.y)
//            lineTo(p1.x, p1.y)
//            lineTo(p2.x, p2.y)
//            // A single cubic
//            cubicTo(c1.x, c1.y, c2.x, c2.y, p3.x, p3.y)
//            lineTo(p4.x, p4.y)
//            close()
//        }
//
//        drawPath(path = path, color = color)
//    }

//    // 1) Fraction: 0 = normal rounded corners, 1 = big circle arc
//    val fraction by animateFloatAsState(
//        targetValue = if (isRounded) 0f else 1f,
//        animationSpec = tween(durationMillis = 800)
//    )
//
//    Canvas(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(300.dp)
//    ) {
//        val w = size.width
//        val h = size.height
//
//        // For the "small" shape: corners = 16.dp
//        val smallCorner = 16.dp.toPx()
//
//        // For the "big" shape: a circle so large that only 1/4 is visible
//        // We'll center it at the top-left corner so that the circle extends downward & rightward.
//        // This radius could be bigger or smaller, tweak to your liking:
//        val bigRadius = hypot(w, h) * 2f
//
//        // We'll animate a "corner radius" from smallCorner -> bigRadius, as a simple approximation
//        val animatedCorner = lerp(start = smallCorner, stop = bigRadius, fraction)
//
//        // Now define one path that:
//        // - At fraction=0, draws normal corners
//        // - At fraction=1, draws a large arc
//        val path = Path().apply {
//            if (fraction < 1f) {
//                // -------------------------
//                // Part 1: Rounded rectangle portion
//                // We'll do basically the same code you had for the corners,
//                // but we let "animatedCorner" push those arcs outward as fraction grows
//                // -------------------------
//                moveTo(0f, animatedCorner)
//                quadraticTo(0f, 0f, animatedCorner, 0f)
//
//                lineTo(w - animatedCorner, 0f)
//                quadraticTo(w, 0f, w, animatedCorner)
//
//                lineTo(w, h - animatedCorner)
//                quadraticTo(w, h, w - animatedCorner, h)
//
//                lineTo(animatedCorner, h)
//                quadraticTo(0f, h, 0f, h - animatedCorner)
//
//                close()
//            }
//
//            if (fraction > 0f) {
//                // -------------------------
//                // Part 2: Large circle arc portion
//                // We'll addArc from a bounding box that starts well above/left of the canvas
//                // so that only the bottom-right quarter is visible in this composable.
//                // Center is at (0,0), radius=animatedCorner if fraction=1 => bigRadius
//                // You can adjust startAngle/sweepAngle to pick which quarter is shown.
//                // -------------------------
//
//                // The bounding box for this circle:
//                val left = animatedCorner
//                val top = animatedCorner
//                val right = animatedCorner
//                val bottom = animatedCorner
//
//                // We can do an arc of 90 degrees starting at bottom-right corner of the circle
//                // or whichever quadrant you prefer. For instance:
//                // startAngle = 0f, sweepAngle=90f draws the top-right quarter of the circle, etc.
//
//                // Let's do "bottom-right quarter" as an example: startAngle=0, sweepAngle=90
//                // This might or might not look exactly how you want, so tweak if needed!
//                addArc(
//                    oval = Rect(
//                        left = left,
//                        top = top,
//                        right = right,
//                        bottom = bottom,
//                    ),
//
//                    startAngleDegrees = 0f,
//                    sweepAngleDegrees = 90f
//                )
//                close()
//            }
//        }
//
//        drawPath(path = path, color = color)
//    }


//    // Animate fraction from 0f → 1f
//    val fraction by animateFloatAsState(
//        targetValue = if (isRounded) 1f else 0f,
//        animationSpec = tween(durationMillis = 800)
//    )
//
//    Canvas(modifier = modifier
//        .fillMaxWidth()
//        .height(300.dp)) {
//        val w = size.width
//        val h = size.height
//
//        // The “small” corner radius at fraction=0
//        val minCorner = 16.dp.toPx()
//        // The “very large” corner radius at fraction=1
//        // For a big circle, you might do half the diagonal, e.g. hypot(w,h)/2f
//        val maxCorner = 16.dp.toPx()//min(w, h) / 2f
//
//        // Interpolate corner radius between these two extremes
//        val cornerR = lerp(start = minCorner, stop = maxCorner, fraction = fraction)
//
//        val path = Path().apply {
//            // Top-left corner
//            moveTo(0f, cornerR)
//            quadraticTo(0f, 0f, cornerR, 0f)
//
//            // Top edge → top-right corner
//            lineTo(w - cornerR, 0f)
//            quadraticTo(w, 0f, w, cornerR)
//
//            // Right edge → bottom-right corner
//            lineTo(w, h - cornerR)
//            quadraticTo(w, h, w - cornerR, h)
//
//            // Bottom edge → bottom-left corner
//            lineTo(cornerR, h)
//            quadraticTo(0f, h, 0f, h - cornerR)
//
//            close()
//        }
//
//        drawPath(path = path, color = color, style = Fill)
//    }
}

@Preview
@Composable
fun MyPreview() {
    CurvedBezierComposable(
        modifier = Modifier,
        isRounded = true,
        color = Color(0xFF1F2732)
    )
}