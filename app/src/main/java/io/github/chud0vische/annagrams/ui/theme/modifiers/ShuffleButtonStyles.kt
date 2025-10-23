package io.github.chud0vische.annagrams.ui.theme.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import io.github.chud0vische.annagrams.ui.theme.AppDimensions
import io.github.chud0vische.annagrams.ui.theme.ShuffleButtonColor

fun Modifier.shuffleButtonStyle(): Modifier = this
    .size(AppDimensions.shuffleButtonSize)
    .clip(CircleShape)
    .background(ShuffleButtonColor)

fun Modifier.shuffleButtonIconStyle(): Modifier = this
    .size(AppDimensions.shuffleButtonSize / 2)