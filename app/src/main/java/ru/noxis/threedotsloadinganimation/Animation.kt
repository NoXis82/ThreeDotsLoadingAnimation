package ru.noxis.threedotsloadinganimation

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp


@Composable
fun ThreeDotsLoadingAnimation(
    size: Int, //Variable size
    color: Color //Variable color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val anchor1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500), //0.5 second animation
            repeatMode = RepeatMode.Reverse //Ping-pong effect
        ), label = "anchor1"
    )

    val anchor2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500), //0.5 second animation
            initialStartOffset = StartOffset(250, StartOffsetType.Delay), //Stagger delay
            repeatMode = RepeatMode.Reverse //Ping-pong effect
        ), label = "anchor2"
    )

    val anchor3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500), //0.5 second animation
            initialStartOffset = StartOffset(500, StartOffsetType.Delay), //Stagger delay
            repeatMode = RepeatMode.Reverse //Ping-pong effect
        ), label = "anchor2"
    )

    // Shared modifier for all three dots
    fun Modifier.dotBehaviour(anchor: Float) = this
        .offset {
            //Vertical bounce animation: -2 * size * progress
            IntOffset(0, (-2 * size * anchor.dp.toPx()).toInt())
        }
        .scale(lerp(1f, 1.25f, anchor)) //Size pulse effect
        .alpha(lerp(0.7f, 1f, anchor)) //Fade-in/out effect

    Row(
        horizontalArrangement = Arrangement.spacedBy(size.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .dotBehaviour(anchor1)
                .size(size.dp)
                .clip(CircleShape)
                .background(color)
        )

        Box(
            modifier = Modifier
                .dotBehaviour(anchor2)
                .size(size.dp)
                .clip(CircleShape)
                .background(color)
        )

        Box(
            modifier = Modifier
                .dotBehaviour(anchor3)
                .size(size.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}
