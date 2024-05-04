package com.practice.composeablecamera

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class Utils {
    companion object {
        var p1 = Offset(100f, 100f)
        var p2 = Offset(1000f, 100f)
        var p3 = Offset(1000f, 2000f)
        var p4 = Offset(100f, 2000f)

        var q1 = Offset(300f, 800f)
        var q2 = Offset(800f, 800f)
        var q3 = Offset(800f, 1400f)
        var q4 = Offset(300f, 1400f)

        var a1 = Offset(100f, 100f)
        var a2 = Offset(1000f, 100f)
        var a3 = Offset(1000f, 2000f)
        var a4 = Offset(100f, 2000f)

        // Arrays for points p and q
        val pointsP = arrayOf(p1, p2, p3, p4)
        val pointsQ = arrayOf(q1, q2, q3, q4)
        val pointsA = arrayOf(a1, a2, a3, a4)


    }

    data class LargRecPoints(
        val p1: Offset,
        val p2: Offset,
        val p3: Offset,
        val p4: Offset
    ) {
        companion object {
            fun getDefaultPoints(): LargRecPoints {
                return LargRecPoints(
                    p1 = Offset(100f, 100f),
                    p2 = Offset(1000f, 100f),
                    p3 = Offset(1000f, 2000f),
                    p4 = Offset(100f, 2000f)
                )
            }
        }
    }

    data class SmallRecPoints(
        val p1: Offset,
        val p2: Offset,
        val p3: Offset,
        val p4: Offset
    ) {
        companion object {
            fun getDefaultPoints(): SmallRecPoints {
                return SmallRecPoints(
                    p1 = Offset(300f, 800f),
                    p2 = Offset(800f, 800f),
                    p3 = Offset(800f, 1400f),
                    p4 = Offset(300f, 1400f)
                )
            }
        }
    }



}