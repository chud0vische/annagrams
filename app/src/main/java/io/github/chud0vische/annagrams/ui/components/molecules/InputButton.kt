package io.github.chud0vische.annagrams.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun InputButton(
    letter: Char,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
            .background(
                color = (if (isSelected) Color.DarkGray else Color.Transparent),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        val fontSize = minOf(maxWidth, maxHeight).value / 2.5f

        Text(
            text = letter.uppercase(),
            color = Color.White,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    }
}