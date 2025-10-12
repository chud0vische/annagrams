package io.github.chud0vische.annagrams.ui.components.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.chud0vische.annagrams.ui.theme.modifiers.shuffleButtonIconStyle
import io.github.chud0vische.annagrams.ui.theme.modifiers.shuffleButtonStyle

@Composable
fun ShuffleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shuffleButtonStyle()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Shuffle letters",
            tint = Color.White.copy(alpha = 0.3f),
            modifier = Modifier.shuffleButtonIconStyle()
        )
    }
}