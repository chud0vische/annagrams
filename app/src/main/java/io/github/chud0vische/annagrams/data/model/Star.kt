package io.github.chud0vische.annagrams.data.model

import androidx.compose.ui.geometry.Offset

enum class StarState {
    DEFAULT,
    REVEALED,
    BONUS_REVEALED
}

data class Star(
    val id: Int,
    val state: StarState
)