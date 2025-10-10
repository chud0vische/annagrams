package io.github.chud0vische.annagrams.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import io.github.chud0vische.annagrams.ui.theme.Dimensions

@Composable
fun LetterView(letter: Char?, modifier: Modifier = Modifier) {
    val boxSize = Dimensions.letterBoxSize
    val cornerRadius = Dimensions.letterBoxCornerRadius

    val backgroundColor = if (letter != null) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
    val borderColor = if (letter != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val textColor = if (letter != null) MaterialTheme.colorScheme.onPrimaryContainer else Color.Transparent

    Box(
        modifier = modifier
            .size(boxSize)
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .border(Dimensions.letterBoxStrokeWidth, borderColor, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center,

    ) {
        if (letter != null) {
            Text(
                text = letter.toString().uppercase(),
                color = textColor,
                fontSize = Dimensions.letterBoxFontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}