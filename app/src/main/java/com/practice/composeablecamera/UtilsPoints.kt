//package com.practice.composeablecamera


// mutiple points

//calling method.

//        var touchPositions by remember { mutableStateOf(emptyList<Offset>()) }
/*          // Draw circles on touch positions
        Draw().DrawCirclesOnTouch(touchPositions) { newPosition ->
            touchPositions = touchPositions + newPosition
            circlePosition = newPosition
        }*/

// Method
/*
    //    multiple touches multiple circles
    @Composable
    fun DrawCirclesOnTouch(touchPositions: List<Offset>, onNewTouchPosition: (Offset) -> Unit) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Check if the new touch position is unique
                    if (touchPositions.none { it == offset }) {
                        onNewTouchPosition(offset)
                    }
                }
            }
        ) {
            touchPositions.forEachIndexed { index, position ->
                drawCircle(
                    color = Color.Red,
                    radius = 20f,
                    center = position
                )
            }
        }

    }
*/