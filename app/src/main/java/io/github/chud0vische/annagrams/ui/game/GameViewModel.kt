package io.github.chud0vische.annagrams.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.chud0vische.annagrams.data.Level
import io.github.chud0vische.annagrams.data.LevelRepository
import io.github.chud0vische.annagrams.data.PlacedWord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameUiState(
    val levelNumber: Int = 1,
    val letters: List<Char> = emptyList(),
    val crosswordWords: List<PlacedWord> = emptyList(),
    val bonusWordsPool: Set<String> = emptySet(),
    val foundWords: Set<String> = emptySet(),
    val foundBonusWords: Set<String> = emptySet(),
    val isLevelCompleted: Boolean = false,
    val isLoading: Boolean = true
)

class GameViewModel(private val repository: LevelRepository): ViewModel() {
    // TODO: Перенести всё на StringBuilder, организовать лучше и оптимизировать

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Загружаем самый первый уровень при создании ViewModel
        loadLevel()
    }

    fun loadLevel(wordLength: Int = 5) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val newLevel: Level? = repository.generateLevel(wordLength)

            if (newLevel != null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        levelNumber = if (currentState.levelNumber == 1 && currentState.letters.isEmpty()) 1 else currentState.levelNumber + 1,
                        letters = newLevel.letters,
                        crosswordWords = newLevel.crosswordWords,
                        bonusWordsPool = newLevel.bonusWordsPool,

                        foundWords = emptySet(),
                        foundBonusWords = emptySet(),
                        isLevelCompleted = false
                    )
                }
            } else {
                // TODO: Обработать ошибку генерации уровня
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun submitWord(word: String) {
        val currentState = _uiState.value

        val isMainWord = currentState.crosswordWords.any { it.text == word }

        if (isMainWord && word !in currentState.foundWords) {
            val newFoundWords = currentState.foundWords + word
            _uiState.update {
                it.copy(foundWords = newFoundWords)
            }

            checkLevelCompletion()
            return
        }

        if (word in currentState.bonusWordsPool && word !in currentState.foundBonusWords) {
            val newFoundBonusWords = currentState.foundBonusWords + word
            _uiState.update {
                it.copy(foundBonusWords = newFoundBonusWords)
            }
            // TODO: Показать анимацию "Бонусное слово!" и начислить очки
            return
        }

        // TODO: Обработать случай, когда слово уже найдено или неверно
    }

    private fun checkLevelCompletion() {
        val currentState = _uiState.value

        if (currentState.foundWords.size == currentState.crosswordWords.size) {
            _uiState.update { it.copy(isLevelCompleted = true) }
            // TODO: Показать диалог "Уровень пройден!"
        }
    }

}