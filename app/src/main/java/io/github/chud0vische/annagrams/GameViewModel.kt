package io.github.chud0vische.annagrams

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var levelLetters by mutableStateOf(listOf('M', 'О','Н','К','Е'))
        private set

    private val validWords = setOf("КОМ", "КОН", "МОЕ", "НЕМО", "ОКЕЙ", "КЕЙН", "МЕНК", "КИНО")
    var typedWord by mutableStateOf("")

    var foundWords by mutableStateOf<Set<String>>(emptySet())
        private set

    fun onWordCollected(word: String) {
        typedWord = word
    }

    fun shuffleLetters() {
        levelLetters = levelLetters.shuffled()
    }
}