package io.github.chud0vische.annagrams.data

sealed interface LayoutStrategy {
    data object ListLayout : LayoutStrategy
    data class CrosswordLayout(val placedWords: List<PlacedWord>) : LayoutStrategy
}