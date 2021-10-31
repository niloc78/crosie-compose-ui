package com.crosie.crosie_compose_ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.crosie.crosie_compose_ui.ui.theme.CrosiecomposeuiTheme
import com.crosie.flexible_drawer.FlexibleDrawer
import com.crosie.flexible_drawer.rememberDrawerState2
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrosiecomposeuiTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}
@Composable
@Preview(showSystemUi = true, showBackground = true)
fun navDrawer() {

    CrosiecomposeuiTheme() {
        val drawerState = rememberDrawerState2(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        FlexibleDrawer(drawerState = drawerState,
            drawerContent = {
                //whatever is inside the drawer
                Column {
                    Text(text = "Helllo!!", color = Color.Black)
                    Text(text = "Helllo!!", color = Color.Black)
                    Text(text = "Helllo!!", color = Color.Black)
                    Text(text = "Helllo!!", color = Color.Black)
                }
            },
            pushAside = true,
            drawerPortion = 0.25f
        ) {
            //stuff outside drawer
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                Text(text = "Helllo!!", color = Color.Black)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun helloTest2() {
    CrosiecomposeuiTheme {
        val isPressed = remember { mutableStateOf(false) }
        Button(onClick = { isPressed.value = false }) {
            Text(text = "Click Me!")
        }
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center) {
            helloTest(modifier = Modifier,
                isTimedLongPressed = isPressed,
                strokeStartColor = Color.Blue,
                strokeEndColor = Color.Cyan,
                pressedColor = Color.Green,
                circleColor = Color.Cyan,
                strokeWidth = 35f,
                radius = 60f,
                innerPadding = 30f,
                unPressOnTap = true,
                timer = 2000L) {
                Log.d("was timelongpressed", "timelongpressed!")
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun helloTest(modifier : Modifier = Modifier, isTimedLongPressed : MutableState<Boolean> = remember{ mutableStateOf(false) },
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
                                onTimedLongPress()
                                isTimedLongPressed.value = true
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CrosiecomposeuiTheme {
        Greeting("Android")
    }
}