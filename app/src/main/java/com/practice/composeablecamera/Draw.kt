// Draw.kt
package com.practice.composeablecamera

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

class Draw {

    @Composable
    fun RectangleOverlayLarg(p1: Offset, p2: Offset, p3: Offset, p4: Offset) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.Red, radius = 20f, center = p1)
            drawCircle(Color.Red, radius = 20f, center = p2)
            drawCircle(Color.Red, radius = 20f, center = p3)
            drawCircle(Color.Red, radius = 20f, center = p4)

            drawLine(
                Color.Green,
                start = p1,
                end = p2,
                strokeWidth = 8f
            )
            drawLine(
                Color.Cyan,
                start = p2,
                end = p3,
                strokeWidth = 8f
            )
            drawLine(
                Color.Red,
                start = p3,
                end = p4,
                strokeWidth = 8f
            )
            drawLine(
                Color.Yellow,
                start = p4,
                end = p1,
                strokeWidth = 8f
            )
        }
    }

    companion object{
    }

    /*    @Composable
    fun RectangleOverlayLarg() {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.Red, radius = 20f, center = Utils.p1)
            drawCircle(Color.Red, radius = 20f, center = Utils.p2)
            drawCircle(Color.Red, radius = 20f, center = Utils.p3)
            drawCircle(Color.Red, radius = 20f, center = Utils.p4)

            drawLine(
                Color.Green,
                start = Utils.p1,
                end = Utils.p2,
                strokeWidth = 8f
            )
            drawLine(
                Color.Cyan,
                start = Utils.p2,
                end = Utils.p3,
                strokeWidth = 8f
            )
            drawLine(
                Color.Red,
                start = Utils.p3,
                end = Utils.p4,
                strokeWidth = 8f
            )
            drawLine(
                Color.Yellow,
                start = Utils.p4,
                end = Utils.p1,
                strokeWidth = 8f
            )
        }
    }*/
   @Composable
    fun RectangleOverlaySmall(rectanglePoints: Utils.SmallRecPoints) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.Red, radius = 20f, center = rectanglePoints.p1)
            drawCircle(Color.Red, radius = 20f, center = rectanglePoints.p2)
            drawCircle(Color.Red, radius = 20f, center = rectanglePoints.p3)
            drawCircle(Color.Red, radius = 20f, center = rectanglePoints.p4)

            drawLine(
                Color.Green,
                start = rectanglePoints.p1,
                end = rectanglePoints.p2,
                strokeWidth = 8f
            )
            drawLine(
                Color.Cyan,
                start = rectanglePoints.p2,
                end = rectanglePoints.p3,
                strokeWidth = 8f
            )
            drawLine(
                Color.Red,
                start = rectanglePoints.p3,
                end = rectanglePoints.p4,
                strokeWidth = 8f
            )
            drawLine(
                Color.Yellow,
                start = rectanglePoints.p4,
                end = rectanglePoints.p1,
                strokeWidth = 8f
            )
        }
    }

    //  single touch single circle
        @Composable
        fun DrawCircleAtTouchPosition(
            touchPosition: Offset,
            onPositionChanged: (Offset) -> Unit
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            onPositionChanged(offset)
                        }
                    }
            ) {
                drawCircle(
                    color = Color.Blue,
                    radius = 20f,
                    center = touchPosition
                )
            }
        }

    @Composable
    fun DisplayCirclePosition(circlePosition: Offset) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Latest Circle: X = ${circlePosition.x}, Y = ${circlePosition.y}",
            color = Color.Yellow,
            style = MaterialTheme.typography.bodySmall
        )
    }


}
