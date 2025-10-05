package io.github.chud0vische.annagrams

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    // TODO: Перенести всё на StringBuilder, организовать лучше и оптимизировать
    private var currentLevelId by mutableIntStateOf(0)
    private var currentLevel: Level? = null

    var levelLetters by mutableStateOf<List<Char>>(emptyList())
        private set

    var validWords by mutableStateOf<Set<String>>(emptySet())
        private set

    var foundWords by mutableStateOf<Set<String>>(emptySet())
        private set

    var typedWord by mutableStateOf("")
        private set

    val isLevelCompleted: Boolean
        get() = if (validWords.isEmpty()) false else foundWords.size == validWords.size

    init {
        loadLevel(currentLevelId)
    }

    private fun loadLevel(id: Int) {
        val level = LevelRepository.getLevel(id)

        if (level != null) {
            currentLevel = level
            currentLevelId = id

            levelLetters = level.letters
            validWords = level.validWords
            foundWords = emptySet()
            typedWord = ""

        }
    }

    fun restartLevel() {
        loadLevel(currentLevelId)
    }

    fun nextLevel() {
        val nextLevelId = currentLevelId + 1

        if (nextLevelId > LevelRepository.getTotalLevels()) {
            loadLevel(0)
        } else {
            loadLevel(nextLevelId)
        }
    }

    fun onLetterSelected(letter: Char) {
        typedWord += letter
    }

    fun onWordCollected(word: String) {
        if (word.isEmpty()){
            typedWord = ""
            return
        }

        if (word in validWords && word !in foundWords) {
            foundWords += word
        }

        // Clear typed word after check
        typedWord = ""
    }

    fun shuffleLetters() {
        levelLetters = levelLetters.shuffled()
    }
}