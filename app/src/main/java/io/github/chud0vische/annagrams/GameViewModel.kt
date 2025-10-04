package io.github.chud0vische.annagrams

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val levelLetters = listOf('M', 'О','Н','К','Е')
    val typedWord = mutableStateOf("")

    fun onLetterClick(letter: Char) {
        typedWord.value += letter
    }

    fun shuffleLetters() {

    }
}