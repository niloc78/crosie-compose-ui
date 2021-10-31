package com.crosie.circular_progress_button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun CircularProgressButton(modifier : Modifier = Modifier, isTimedLongPressed : MutableState<Boolean> = remember{ mutableStateOf(false) },
                           innerPadding : Float = 30f, roundedStrokeCap : Boolean = true,
                           strokeWidth : Float = 35f, radius : Float = 60f, timer : Long = 2000L,
                           circleColor : Color = Color.Cyan, strokeStartColor : Color = Color.Blue,
                           strokeEndColor : Color = Color.Cyan, pressedColor : Color = Color.Green,
                           showPressedStroke : Boolean = true, strokeDecreaseTimer : Int = 300,
                           overlapCircleOverStroke : Boolean = false, unPressOnTap : Boolean = false,
                           onTimedLongPress : () -> Unit = {}) {
    val scope = rememberCoroutineScope()
    var progress by remember { mutableStateOf(0f) }
    var isRunning by remember {
        mutableStateOf(false)
    }

    progress = animateFloatAsState(
        targetValue = if(isRunning) {
            360f
        } else {
            if(isTimedLongPressed.value && showPressedStroke) 360f else 0f
        }, // set else to 0
        animationSpec = tween(durationMillis = if(isRunning) timer.toInt() else strokeDecreaseTimer)
    ).value
    Box(contentAlignment = Alignment.Center, modifier = modifier
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    if (!isTimedLongPressed.value) {
                        isRunning = true
                        val job = scope.launch {
                            delay(timer)
                        }
                        job.invokeOnCompletion {
                            if (it == null) { // completed normally, invoke ontimedlongpress, reset ui
                                isTimedLongPressed.value = true
                                onTimedLongPress()
                            }
                            isRunning = false
                        }
                        val released = tryAwaitRelease()
                        if (!job.isCompleted && released) {
                            job.cancel()
                        }
                    } else if (isTimedLongPressed.value && unPressOnTap) {
                        isTimedLongPressed.value = false
                    }
                }
            )
        }
        .clip(CircleShape)
        .size(radius.dp)
    ) {
        Canvas(modifier = Modifier
            .wrapContentSize()) {
            inset(size.width/2 - radius - (strokeWidth/2) - innerPadding, size.height/2 - radius - (strokeWidth/2) - innerPadding) {
                val gradientBrush = Brush.horizontalGradient(listOf(strokeStartColor, strokeEndColor), startX = Float.POSITIVE_INFINITY, endX = 0f)
                if(overlapCircleOverStroke) {
                    drawArc(brush = gradientBrush,
                        startAngle = 270f,
                        sweepAngle = progress,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = if(roundedStrokeCap) StrokeCap.Round else StrokeCap.Butt),
                        blendMode = BlendMode.SrcIn)
                    drawCircle(
                        color = if(isTimedLongPressed.value) pressedColor else circleColor,
                        radius = radius,
                        center = center
                    )
                } else {
                    drawCircle(
                        color = if(isTimedLongPressed.value) pressedColor else circleColor,
                        radius = radius,
                        center = center
                    )
                    drawArc(brush = gradientBrush,
                        startAngle = 270f,
                        sweepAngle = progress,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = if(roundedStrokeCap) StrokeCap.Round else StrokeCap.Butt),
                        blendMode = BlendMode.SrcIn)
                }
            }
        }
    }
}