package io.github.chud0vische.annagrams.data.model

data class Level(
    val crossword: Crossword,
    val bonusWordsPool: Set<List<Char>>,
    val inputLetters: List<Char>,
)