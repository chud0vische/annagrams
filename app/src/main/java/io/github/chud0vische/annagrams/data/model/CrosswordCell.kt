package io.github.chud0vische.annagrams.data.model

import android.opengl.Visibility

enum class CrosswordCellType() {
    REVEALED,
    HINTED,
    EMPTY,
    HIDDEN
}

data class CrosswordCell(
    val char: Char,
    val type: CrosswordCellType = CrosswordCellType.EMPTY
)