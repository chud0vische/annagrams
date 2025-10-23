package io.github.chud0vische.annagrams.ui.components.molecules

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.chud0vische.annagrams.ui.theme.BasicBlueColor
import io.github.chud0vische.annagrams.ui.theme.BasicPinkColor
import io.github.chud0vische.annagrams.ui.theme.InputButtonBackgroundColor
import io.github.chud0vische.annagrams.ui.theme.starGradient

@Composable
fun InputButton(
    letter: Char,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isSelected) InputButtonBackgroundColor else InputButtonBackgroundColor
            .copy(alpha = 0f),
        label = "BackgroundColorAnimation"
    )

    val animatedShadowColor by animateColorAsState(
        targetValue = if (isSelected) BasicBlueColor else Color.White.copy(alpha = 0.05f),
        label = "ShadowColorAnimation"
    )

    BoxWithConstraints(
        modifier = modifier
            .background(
                color = animatedBackgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        val fontSize = minOf(maxWidth, maxHeight).value / 1.9f

        Text(
            text = letter.uppercase(),
            fontSize = fontSize.sp,
            color = Color.White.copy(alpha = 0.85f),
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = animatedShadowColor,
                    offset = Offset.Zero,
                    blurRadius = 20f
                )
            )
        )
    }
}