package com.kalpeet.elkdocs

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kalpeet.elkdocs.ui.theme.ElkDocsTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElkDocsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                    BottomNavigationBar()


                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(

        modifier = Modifier.padding(top = 22.dp, start = 6.dp, end = 6.dp)
    ) {
        val sdf = SimpleDateFormat("EEEE dd ")
        val currentDateAndTime = sdf.format(Date())
        Row(horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.width(150.dp))
            Text(text = currentDateAndTime)
        }
        Spacer(modifier = Modifier.height(35.dp))


        var isClockRunning by remember {
            mutableStateOf(true)
        }
        Row() {
            Spacer(modifier = Modifier.width(30.dp))
            AnalogClockComposable(
                modifier = Modifier.clickable {
                    isClockRunning = !isClockRunning
                },
                isClockRunning = isClockRunning
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
        Row(horizontalArrangement = Arrangement.Start) {
            Spacer(modifier = Modifier.width(30.dp))
            Column(horizontalAlignment = Alignment.Start) {

                Text(text = "That Cool Song")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "It's Artist")

            }
            Spacer(modifier = Modifier.width(120.dp))

            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play")
            }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Forward")
            }

        }
        Spacer(modifier = Modifier.height(25.dp))
        Divider(
            color = Color.Red, modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row() {
            Spacer(modifier = Modifier.width(180.dp))
            Text(text = "34%")
        }


        Spacer(modifier = Modifier.height(40.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Row() {

                IconButton(onClick = { /*TODO*/ }) {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.baseline_cancel_24),
                        contentDescription = "Cancel"
                    )

                }
                Spacer(modifier = Modifier.width(100.dp))
                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_mode_edit_24),
                        contentDescription = "Edit"
                    )
                    Text(text = "EDIT")

                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_done_24),
                        contentDescription = "Done"
                    )
                    Text(text = "DONE")

                }


            }
        }


    }

}

@Composable
fun AnalogClockComposable(
    modifier: Modifier = Modifier,
    minSize: Dp = 350.dp,
    time: LocalTime = LocalTime.now(),
    isClockRunning: Boolean = true
) {

    var seconds by remember { mutableStateOf(time.second) }
    var minutes by remember { mutableStateOf(time.minute) }
    var hours by remember { mutableStateOf(time.hour) }

    var hourAngle by remember {
        mutableStateOf(0.0)
    }

    LaunchedEffect(key1 = minutes) {
        //Just putting this in LaunchedEffect so that we are going only to calculate
        //the angle when the minutes change
        hourAngle = (minutes / 60.0 * 30.0) - 90.0 + (hours * 30)
    }

    LaunchedEffect(isClockRunning) {
        while (isClockRunning) {
            seconds += 1
            if (seconds > 60) {
                seconds = 1
                minutes++
            }
            if (minutes > 60) {
                minutes = 1
                hours++
            }
            delay(1000)
        }
    }
    val transition = rememberInfiniteTransition()
    val color by transition.animateColor(
        initialValue = Color.Black, targetValue = Color.Red, animationSpec = infiniteRepeatable(
            animation = tween(500), repeatMode = RepeatMode.Reverse
        )
    )

    BoxWithConstraints() {

        //This is the estate we are going to work with
        val width = if (minWidth < 1.dp) minSize else minWidth
        val height = if (minHeight < 1.dp) minSize else minHeight

        Canvas(
            modifier = modifier
                .size(width, height)
        ) {

            //lets draw the circle now
            //but before we do that lets calculate the radius,
            //which is going to be 40% of the radius so we can achieve responsiveness
            val radius = size.width * .4f
            drawCircle(
                color = color,
                style = Stroke(width = radius * .05f /* 5% of the radius */),
                radius = radius,
                center = size.center
            )

            //The degree difference between the each 'minute' line
            val angleDegreeDifference = (360f / 60f)

            //drawing all lines
            (1..60).forEach {
                val angleRadDifference =
                    (((angleDegreeDifference * it) - 90f) * (PI / 180f)).toFloat()
                val lineLength = if (it % 5 == 0) radius * .85f else radius * .93f
                val lineColour = if (it % 5 == 0) Color.White else Color.Gray
                val startOffsetLine = Offset(
                    x = lineLength * cos(angleRadDifference) + size.center.x,
                    y = lineLength * sin(angleRadDifference) + size.center.y
                )
                val endOffsetLine = Offset(
                    x = (radius - ((radius * .05f) / 2)) * cos(angleRadDifference) + size.center.x,
                    y = (radius - ((radius * .05f) / 2)) * sin(angleRadDifference) + size.center.y
                )
                drawLine(
                    color = lineColour,
                    start = startOffsetLine,
                    end = endOffsetLine
                )
                if (it % 5 == 0) {
                    //here we are using the native canvas (native canvas is the traditional one we use dto work with the views), so that we can draw text on the canvas
                    drawContext.canvas.nativeCanvas.apply {
                        val positionX = (radius * .75f) * cos(angleRadDifference) + size.center.x
                        val positionY = (radius * .75f) * sin(angleRadDifference) + size.center.y
                        val text = (it / 5).toString()
                        val paint = Paint()
                        paint.textSize = radius * .15f
                        paint.color = android.graphics.Color.GRAY
                        val textRect = Rect()
                        paint.getTextBounds(text, 0, text.length, textRect)

                        drawText(
                            text,
                            positionX - (textRect.width() / 2),
                            positionY + (textRect.width() / 2),
                            paint
                        )
                    }
                }
            }

            //now draw the center of the screen :O
            drawCircle(
                color = color,
                radius = radius * .02f, //only 2% of the main radius
                center = size.center
            )

            //hour hand
            drawLine(
                color = Color.White,
                start = size.center,
                end = Offset(
                    //don't forget, the hourAngle is calculated in the one of the LaunchedEffects
                    x = (radius * .55f) * cos((hourAngle * (PI / 180)).toFloat()) + size.center.x,
                    y = (radius * .55f) * sin((hourAngle * (PI / 180)).toFloat()) + size.center.y
                ),
                strokeWidth = radius * .02f,
                cap = StrokeCap.Square
            )

//          minutes hand - just dividing the seconds with 60 and multiplying it by 6 degrees (which is the difference between the lines)
            // subtracting 90 as the 0degrees is actually at 3 o'clock
            val minutesAngle = (seconds / 60.0 * 6.0) - 90.0 + (minutes * 6.0)
            drawLine(
                color = Color.White,
                start = size.center,
                end = Offset(
                    x = (radius * .7f) * cos((minutesAngle * (PI / 180)).toFloat()) + size.center.x,
                    y = (radius * .7f) * sin((minutesAngle * (PI / 180)).toFloat()) + size.center.y
                ),
                strokeWidth = radius * .01f,
                cap = StrokeCap.Square
            )

            //seconds hand
            drawLine(
                color = Color.Magenta,
                start = size.center,
                end = Offset(
                    x = (radius * .9f) * cos(seconds.secondsToRad()) + size.center.x,
                    y = (radius * .9f) * sin(seconds.secondsToRad()) + size.center.y
                ),
                strokeWidth = 1.dp.toPx(),
                cap = StrokeCap.Round
            )

            //paused text
            if (!isClockRunning) {
                drawContext.canvas.nativeCanvas.apply {
                    val text = "PAUSED"
                    val paint = Paint()
                    paint.textSize = radius * .15f
                    paint.color = android.graphics.Color.MAGENTA

                    val textRect = Rect()
                    paint.getTextBounds(text, 0, text.length, textRect)

                    drawText(
                        text,
                        size.center.x - (textRect.width() / 2),
                        size.center.y + (textRect.width() / 2),
                        paint
                    )
                }
            }

        }
    }
}

fun Int.secondsToRad(): Float {
    val angle = (360f / 60f * this) - 90f
    return (angle * (PI / 180f)).toFloat()
}

@Composable
fun BottomNavigationBar() {
    val bottomMenuItemsList = prepareBottomMenu()
    val context = LocalContext.current.applicationContext
    var selectedItem by remember {
        mutableStateOf("Music Controls")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        BottomAppBar(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
            bottomMenuItemsList.forEach { menuItem ->
                NavigationBarItem(
                    selected = (selectedItem == menuItem.label),
                    onClick = {
                        selectedItem = menuItem.label
                        Toast.makeText(context, menuItem.label, Toast.LENGTH_SHORT).show()
                    },
                    icon = {
                        // Text(text = menuItem.label)
                    },
                    label = {
                        Text(text = menuItem.label)
                    },
                    enabled = true
                )
            }
        }
    }

}

fun prepareBottomMenu(): List<BottomNavItem> {
    val bottomMenuItemsList = arrayListOf<BottomNavItem>()
    bottomMenuItemsList.add(BottomNavItem("Background"))
    bottomMenuItemsList.add(BottomNavItem("Music Controls"))
    bottomMenuItemsList.add(BottomNavItem("Notifications"))
    return bottomMenuItemsList
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElkDocsTheme {

    }
}