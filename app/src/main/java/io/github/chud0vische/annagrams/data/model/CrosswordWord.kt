package io.github.chud0vische.annagrams.data.model

data class CrosswordWord(
    val word: List<Char>,
    val startPoint: Point,
    val direction: WordDirection
)