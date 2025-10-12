package io.github.chud0vische.annagrams.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import io.github.chud0vische.annagrams.ui.theme.Dimensions

@Composable
fun InputButton(
    letter: Char,
    modifier: Modifier = Modifier,
    buttonSize: Dp = Dimensions.letterButtonSize,
    fontSize: TextUnit = Dimensions.largeFont
) {
    Box(
        modifier = modifier
            .size(buttonSize)
            .clip(CircleShape)
            .background(Color.DarkGray.copy(alpha = 0.5F)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString().uppercase(),
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}