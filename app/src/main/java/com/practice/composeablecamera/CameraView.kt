package com.practice.composeablecamera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.practice.composeablecamera.ui.theme.ComposeableCameraTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CameraView : ComponentActivity() {
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var previewView: PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        previewView = PreviewView(this)

        setContent {
            ComposeableCameraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CameraPreview()
//                    views_drawing()
                    MovingCircleAnimation()

                }
            }
        }
    }

    @Composable
    fun CameraPreview() {
        val context = LocalContext.current
        AndroidView({ previewView }) { view ->
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider, view)
            }, ContextCompat.getMainExecutor(context))
        }
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider, previewView: PreviewView) {
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        val preview = androidx.camera.core.Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        cameraProvider.unbindAll()
        val camera = cameraProvider.bindToLifecycle(
            this,
            cameraSelector,
            preview
        )
    }


    @Preview(showBackground = true)
    @Composable
    fun CameraPreviewPreview() {
        CameraPreview()
    }

    /* @Composable
    fun views_drawing() {
        var circlePosition by remember { mutableStateOf(Offset.Zero) }
        Box(modifier = Modifier.fillMaxSize()) {
            var refreshScreen by remember { mutableStateOf(true) }
            var rectangleState by remember { mutableStateOf(true) }
            var index1 by remember { mutableStateOf(0) }
            var index2 by remember { mutableStateOf(0) }
            var index3 by remember { mutableStateOf(0) }
            var index4 by remember { mutableStateOf(0) }
            // Define the initial and target points for animation
            val point1X by animateFloatAsState(
                if (rectangleState && (index1 == 0 || index1 == 4)) Utils.pointsQ[0].x else Utils.pointsP[0].x,
                animationSpec = tween(durationMillis = 500)
            )
            val point1Y by animateFloatAsState(
                if (rectangleState && (index1 == 0 || index1 == 4)) Utils.pointsQ[0].y else Utils.pointsP[0].y,
                animationSpec = tween(durationMillis = 500)
            )
            val point2X by animateFloatAsState(
                if (rectangleState && (index2 == 1 || index2 == 5)) Utils.pointsQ[1].x else Utils.pointsP[1].x,
                animationSpec = tween(durationMillis = 500)
            )
            val point2Y by animateFloatAsState(
                if (rectangleState && (index2 == 1 || index2 == 5)) Utils.pointsQ[1].y else Utils.pointsP[1].y,
                animationSpec = tween(durationMillis = 500)
            )
            val point3X by animateFloatAsState(
                if (rectangleState && (index3 == 2 || index3 == 6)) Utils.pointsQ[2].x else Utils.pointsP[2].x,
                animationSpec = tween(durationMillis = 500)
            )
            val point3Y by animateFloatAsState(
                if (rectangleState && (index3 == 2 || index3 == 6)) Utils.pointsQ[2].y else Utils.pointsP[2].y,
                animationSpec = tween(durationMillis = 500)
            )
            val point4X by animateFloatAsState(
                if (rectangleState && (index4 == 3 || index4 == 7)) Utils.pointsQ[3].x else Utils.pointsP[3].x,
                animationSpec = tween(durationMillis = 500)
            )
            val point4Y by animateFloatAsState(
                if (rectangleState && (index4 == 3 || index4 == 7)) Utils.pointsQ[3].y else Utils.pointsP[3].y,
                animationSpec = tween(durationMillis = 500)
            )
            Log.d("Points", "point1X: $point1X, point1Y: $point1Y")
            Log.d("Points", "point2X: $point2X, point2Y: $point2Y")
            Log.d("Points", "point3X: $point3X, point3Y: $point3Y")
            Log.d("Points", "point4X: $point4X, point4Y: $point4Y")
            LaunchedEffect(Unit) {
                withContext(Dispatchers.Default) {
                    while (true) {
                        // Delay for 1 second
                        delay(1000L)
                        // Toggle between drawing rectangles based on rectangleState
                        withContext(Dispatchers.Main) {
                            rectangleState = !rectangleState
                            index1 = (index1 + 1) % 7
                            index2 = (index2 + 1) % 7
                            index3 = (index3 + 1) % 7
                            index4 = (index4 + 1) % 7
                        }
                    }
                }
            }
            if (refreshScreen || !refreshScreen) {
                Draw().RectangleOverlayLarg(
                    Offset(point1X, point1Y),
                    Offset(point2X, point2Y),
                    Offset(point3X, point3Y),
                    Offset(point4X, point4Y)
                )
            }
            // Draw circle at touch position
            var touchPosition by remember { mutableStateOf(Offset.Zero) }
            Draw().DrawCircleAtTouchPosition(touchPosition) { newPosition ->
                touchPosition = newPosition
                circlePosition = newPosition
            }
            Draw().DisplayCirclePosition(circlePosition)
        }
    }*/
    @Composable
    fun views_drawing() {

        var circlePosition by remember { mutableStateOf(Offset.Zero) }

        Box(modifier = Modifier.fillMaxSize()) {
            var refreshScreen by remember { mutableStateOf(true) }
            var rectangleState by remember { mutableStateOf(true) }
            var index by remember { mutableStateOf(0) }

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) { // withContext(Dispatchers.Default) { =====it can be like this as

                    while (true) {
                        // Delay for 1 second
                        delay(1000L)

                        // Toggle between drawing rectangles based on rectangleState
                        if (rectangleState) {
                            Utils.pointsA[index] = Utils.pointsP[index]
                            refreshScreen = !refreshScreen
                        } else {
                            Utils.pointsA[index] = Utils.pointsQ[index]
                            refreshScreen = !refreshScreen
                        }

                        // Increment index and toggle rectangleState if necessary
                        if (index == 3) {
                            index = 0
                            rectangleState = !rectangleState
                        } else {
                            index++
                        }
                    }
                }
            }

            if (refreshScreen || !refreshScreen) {
                Draw().RectangleOverlayLarg(
                    Utils.pointsA[0],
                    Utils.pointsA[1],
                    Utils.pointsA[2],
                    Utils.pointsA[3]
                )
            }
        }

    }

    /*
    @Composable
    fun views_drawing() {

        var circlePosition by remember { mutableStateOf(Offset.Zero) }

        Box(modifier = Modifier.fillMaxSize()) {
            var refreshScreen by remember { mutableStateOf(true) }
            var rectangleState by remember { mutableStateOf(true) }
            var index by remember { mutableStateOf(0) }
            var pointsA by remember { mutableStateOf(Utils.pointsA[index]) }
            val animationSpec = tween<Float>(durationMillis = 2000)

            LaunchedEffect(Unit) {
//                withContext(Dispatchers.Default) { // withContext(Dispatchers.Default) { =====it can be like this as
                    while (true) {
                        delay(3000L)

                        // Delay for 1 second
                        // Toggle between drawing rectangles based on rectangleState
                        if (rectangleState) {
//                            Utils.pointsA[index] = Utils.pointsP[index]
                            Toast.makeText(
                                this@CameraView,
                                "Offset $index = ${pointsA.x},${pointsA.y}",
                                Toast.LENGTH_SHORT
                            ).show()
                            launch {
                                animate(
                                    initialValue = pointsA.x,
                                    targetValue = pointsQ[index].x,
                                    animationSpec = animationSpec
                                ) { value, _ ->
                                    pointsA = Offset(value, pointsA.y)
                                }
                                Utils.pointsA[index] = Offset(pointsA.x, pointsA.y)
                            }
                            launch {
                                animate(
                                    initialValue = pointsA.y,
                                    targetValue = pointsQ[index].y,
                                    animationSpec = animationSpec
                                ) { value, _ ->
                                    pointsA = Offset(pointsA.x, value)
                                }
                                Utils.pointsA[index] = Offset(pointsA.x, pointsA.y)
                            }

//                            Utils.pointsA[index] = Offset(pointsA.x, pointsA.y)
                            refreshScreen = !refreshScreen
                        } else {
//                            Utils.pointsA[index] = Utils.pointsQ[index]
                            launch {
                                animate(
                                    initialValue = pointsA.x,
                                    targetValue = pointsP[index].x,
                                    animationSpec = animationSpec
                                ) { value, _ ->
                                    pointsA = Offset(value, pointsA.y)
                                }
                                Utils.pointsA[index] = Offset(pointsA.x, pointsA.y)
                            }
                            launch {
                                animate(
                                    initialValue = pointsA.y,
                                    targetValue = pointsP[index].y,
                                    animationSpec = animationSpec
                                ) { value, _ ->
                                    pointsA = Offset(pointsA.x, value)
                                }
                                Utils.pointsA[index] = Offset(pointsA.x, pointsA.y)
                            }
//                            Utils.pointsA[index] = Offset(pointsA.x, pointsA.y)
                            refreshScreen = !refreshScreen
                        }
                        // Increment index and toggle rectangleState if necessary
                        if (index == 3) {
                            index = 0
                            rectangleState = !rectangleState
                        } else {
                            index++
                        }
                    }
//                }
            }

            if (refreshScreen || !refreshScreen) {
                Draw().RectangleOverlayLarg(
                    Utils.pointsA[0],
                    Utils.pointsA[1],
                    Utils.pointsA[2],
                    Utils.pointsA[3]
                )
            }
        }

        // Draw circle at touch position
        var touchPosition by remember { mutableStateOf(Offset.Zero) }
        Draw().DrawCircleAtTouchPosition(touchPosition) { newPosition ->
            touchPosition = newPosition
            circlePosition = newPosition
        }

        Draw().DisplayCirclePosition(circlePosition)
    }
*/


    //  single gadano movinf back n forth
    /*
    @Composable
    fun MovingCircleAnimation() {
        var p1 by remember { mutableStateOf(Utils.pointsP[0]) }
        val q1 = Utils.pointsQ[0]
        val a1 = Utils.pointsA[0]

        LaunchedEffect(Unit) {
            val animationSpec = tween<Float>(durationMillis = 2000)
            // Animate p1 to q1
            launch {
                animate(
                    initialValue = p1.x,
                    targetValue = q1.x,
                    animationSpec = animationSpec
                ) { value, _ ->
                    p1 = Offset(value, p1.y)
                }
            }

            launch {
                animate(
                    initialValue = p1.y,
                    targetValue = q1.y,
                    animationSpec = animationSpec
                ) { value, _ ->
                    p1 = Offset(p1.x, value)
                }
            }

            // Wait for 1 second before starting the next set of animations
            delay(1000)

            // Animate p1 to a1
            launch {
                animate(
                    initialValue = p1.x,
                    targetValue = a1.x,
                    animationSpec = animationSpec
                ) { value, _ ->
                    p1 = Offset(value, p1.y)
                }
            }

            launch {
                animate(
                    initialValue = p1.y,
                    targetValue = a1.y,
                    animationSpec = animationSpec
                ) { value, _ ->
                    p1 = Offset(p1.x, value)
                }
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw the first circle
            drawCircle(
                color = Color.Red,
                center = p1,
                radius = 50f
            )
        }
    }
*/


    @Composable
    fun MovingCircleAnimation() {
        val animationSpec = tween<Float>(durationMillis = 2000)
        val pointsP = Utils.pointsP
        val pointsQ = Utils.pointsQ
        val pointsA = Utils.pointsA

        val circleAnimations = remember {
            pointsP.mapIndexed { index, p ->
                val q = pointsQ[index]
                val a = pointsA[index]

                Triple(
                    mutableStateOf(p),
                    mutableStateOf(q),
                    mutableStateOf(a)
                )
            }
        }

        var isAnimating by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            while (true) {
                if (!isAnimating) {
                    circleAnimations.forEach { (p, q, a) ->
                        // Animate p to q
                        launch {
                            isAnimating = true
                            animate(
                                initialValue = p.value.x,
                                targetValue = q.value.x,
                                animationSpec = animationSpec
                            ) { value, _ ->
                                p.value = Offset(value, p.value.y)
                            }
                        }

                        launch {
                            animate(
                                initialValue = p.value.y,
                                targetValue = q.value.y,
                                animationSpec = animationSpec
                            ) { value, _ ->
                                p.value = Offset(p.value.x, value)
                            }
                        }

                        // Wait for the first animation to complete
                        delay(animationSpec.durationMillis.toLong())
                    }

                    // Animate from Q back to P
                    circleAnimations.forEach { (p, q, a) ->
                        launch {
                            animate(
                                initialValue = p.value.x,
                                targetValue = a.value.x,
                                animationSpec = animationSpec
                            ) { value, _ ->
                                p.value = Offset(value, p.value.y)
                            }
                        }

                        launch {
                            animate(
                                initialValue = p.value.y,
                                targetValue = a.value.y,
                                animationSpec = animationSpec
                            ) { value, _ ->
                                p.value = Offset(p.value.x, value)
                            }
                        }

                        // Wait for the second animation to complete
                        delay(animationSpec.durationMillis.toLong())
                    }

                    isAnimating = false
                } else {
                    delay(100) // Adjust this delay as needed to reduce CPU usage
                }
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            circleAnimations.forEachIndexed { index, (p, _, _) ->
                val nextIndex = (index + 1) % circleAnimations.size
                val nextCircle = circleAnimations[nextIndex].first.value

                // Draw line between current circle and next circle
                drawLine(
                    color = Color.Blue,
                    start = p.value,
                    end = nextCircle,
                    strokeWidth = 15f
                )

                // Draw the circle
                drawCircle(
                    color = Color.Red,
                    center = p.value,
                    radius = 50f
                )
            }
        }
    }








}