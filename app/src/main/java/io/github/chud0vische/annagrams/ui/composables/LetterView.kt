package io.github.chud0vische.annagrams.ui.composables

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.chud0vische.annagrams.ui.theme.Dimen

@Composable
fun LetterView(letter: Char?, modifier: Modifier = Modifier) {
    val boxSize = Dimen.letterBoxSize
    val cornerRadius = Dimen.letterBoxCornerRadius

    val backgroundColor = if (letter != null) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
    val borderColor = if (letter != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val textColor = if (letter != null) MaterialTheme.colorScheme.onPrimaryContainer else Color.Transparent

    Box(
        modifier = modifier
            .size(boxSize)
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .border(Dimen.letterBoxStrokeWidth, borderColor, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        if (letter != null) {
            Text(
                text = letter.toString().uppercase(),
                color = textColor,
                fontSize = Dimen.letterBoxFontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}