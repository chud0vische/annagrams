package io.github.chud0vische.annagrams.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import io.github.chud0vische.annagrams.ui.theme.Dimensions

@Composable
fun NavigationButton(
    isLevelCompleted: Boolean,
    onRestartClick: () -> Unit,
    onNextLevelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = Dimensions.actionButtonWidth, height = Dimensions.actionButtonHeight)
            .clip(CircleShape)
            .background(if (isLevelCompleted) Color.Green else Color.Red)
            .clickable {
                if (isLevelCompleted) onNextLevelClick() else onRestartClick() },

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isLevelCompleted) "Next" else "Restart",
            color = Color.White,
            fontSize = Dimensions.smallFont,
            fontWeight = FontWeight.Bold
        )
    }
}