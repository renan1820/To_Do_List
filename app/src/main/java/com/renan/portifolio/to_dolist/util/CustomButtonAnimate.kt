package com.renan.portifolio.to_dolist.util

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.renan.portifolio.to_dolist.ui.theme.nunitoFontFamily


@Composable
fun CustomButtonAnimate(
    colorStroke: Color = Color.Red,
    text: String = "Animated",
    colorText: Color = Color.Black,
    height: Dp = 100.dp,
    weight: Dp = 100.dp,
    onClick: () -> Unit,
    animatedStroke: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val strokeWidth by infiniteTransition.animateFloat(
        initialValue = 5f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .height(height)
            .width(weight)
            .drawBehind {
                drawSquareProgress(height, weight, if(animatedStroke) strokeWidth else 5f, colorStroke)
            }
            .clickable(onClick = onClick)
            .padding(2.dp),

        ){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = text, color = colorText, fontFamily = nunitoFontFamily, fontWeight = FontWeight.Bold)
        }
    }
}

private fun DrawScope.drawSquareProgress(height: Dp, weight: Dp, strokeWidth: Float, colorStroke: Color) {
    drawRect(
        color = colorStroke,
        size = Size(weight.toPx(), height.toPx()),
        style = Stroke(strokeWidth)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSquareProgressIndicator() {
    CustomButtonAnimate(height = 20.dp, weight = 200.dp, onClick = {})
}