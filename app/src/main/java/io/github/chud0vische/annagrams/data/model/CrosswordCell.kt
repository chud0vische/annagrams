package io.github.chud0vische.annagrams.data.model

data class CrosswordCell(
    val char: Char,
    val type: CrosswordCellType = CrosswordCellType.EMPTY
)