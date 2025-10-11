package io.github.chud0vische.annagrams.data.model

data class CrosswordWord(
    val chars: List<Char>,
    val startPoint: Point,
    val direction: WordDirection
)