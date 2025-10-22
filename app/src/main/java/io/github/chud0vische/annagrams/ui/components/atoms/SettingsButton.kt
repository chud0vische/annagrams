package io.github.chud0vische.annagrams.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.theme.AppDimensions
import io.github.chud0vische.annagrams.ui.theme.ButtonBackgroundColor
import io.github.chud0vische.annagrams.ui.theme.ButtonBorderColor

@Composable
fun SettingsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(48.dp)
            .border(
                AppDimensions.buttonBorderSize,
                ButtonBorderColor,
                RoundedCornerShape(AppDimensions.buttonBorderRadius)
            )
            .background(
                ButtonBackgroundColor,
                RoundedCornerShape(AppDimensions.buttonBorderRadius)
            )
    ) {
        Icon(
            imageVector = Icons.Default.Dehaze,
            tint = Color.White,
            contentDescription = "Settings",
            modifier = modifier
        )

    }
}