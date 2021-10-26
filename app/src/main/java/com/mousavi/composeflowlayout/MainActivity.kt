package com.mousavi.composeflowlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mousavi.composeflowlayout.ui.theme.ComposeFlowLayoutTheme
import kotlin.random.Random

val topics = listOf(
    "Arts & Crafts ",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFlowLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        FlowLayout {
                            topics.forEach {
                                Chip(text = it) {

                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun FlowLayout(
    content: @Composable () -> Unit,
) {
    Layout(content = content) { measureables, constrants ->

        val xArr = IntArray(measureables.size) { 0 }
        val yArr = IntArray(measureables.size) { 0 }
        var yPos = 0
        var xPos = 0
        var rowWidth = 0
        var maxHeightOfRow = 0
        var height = 0

        val placeables = measureables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constrants)
            if (xPos + placeable.width > constrants.maxWidth) {
                xPos = 0
                yPos += maxHeightOfRow
                rowWidth = 0
                maxHeightOfRow = 0
            }
            rowWidth += placeable.width
            if (placeable.height > maxHeightOfRow) {
                maxHeightOfRow = placeable.height
                height += maxHeightOfRow
            }
            if (rowWidth > constrants.maxWidth) {
                yPos += maxHeightOfRow
                rowWidth = 0
                xPos = 0
                maxHeightOfRow = 0
            }

            xArr[index] = xPos
            yArr[index] = yPos

            xPos += placeable.width
            placeable
        }

        layout(constrants.maxWidth, height) {
            placeables.forEachIndexed { index, placeable ->

                placeable.placeRelative(
                    x = xArr[index],
                    y = yArr[index]
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeFlowLayoutTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            FlowLayout {
                repeat(40) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color(
                                red = Random.nextInt(0, 200),
                                green = Random.nextInt(0, 200),
                                blue = Random.nextInt(0, 200)
                            ), shape = CircleShape)
                            .padding(10.dp),
                        text = "Text #${
                            Random.nextInt(1,
                                10000)
                        } ${if (Random.nextBoolean()) "\nNewline" else ""}",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Chip(
    text: String,
    onSelected: (String) -> Unit,
) {
    var selected by remember {
        mutableStateOf(false)
    }

    val animColor = animateColorAsState(
        targetValue = if (!selected) Color.LightGray else Color(0xFFD11F90),
        animationSpec = tween(
            durationMillis = 500
        )
    )

    Text(
        modifier = Modifier
            .padding(10.dp)
            .background(color = animColor.value, shape = CircleShape)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                selected = !selected
                onSelected(text)
            }
            .padding(10.dp)
        ,
        text = text,
        color = Color.White
    )


}