package io.github.chud0vische.annagrams.data.model

data class Level(
    val crosswordWords: List<CrosswordWord>,
    val bonusWordsPool: Set<List<Char>>,
    val letters: List<Char>,
)