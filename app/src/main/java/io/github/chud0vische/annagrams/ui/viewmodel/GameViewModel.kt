package io.github.chud0vische.annagrams.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.chud0vische.annagrams.data.model.Crossword
import io.github.chud0vische.annagrams.data.repository.LevelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameUiState(
    val crossword: Crossword,
    val bonusWordsPool: Set<List<Char>> = emptySet(),
    val inputLetters: List<Char> = emptyList(),
    val foundWords: Set<List<Char>> = emptySet(),
    val foundBonusWords: Set<List<Char>> = emptySet(),
    val isLevelCompleted: Boolean = false,
    val isLoading: Boolean = true
)
class GameViewModel(private val repository: LevelRepository): ViewModel() {
    private val _uiState = MutableStateFlow(
        GameUiState(Crossword.createEmpty())
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadLevel()
    }

    // TODO: изменение размера
    fun loadLevel(wordLength: Int = 5) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val newLevel = repository.generateLevel(wordLength)

                newLevel ?: run {
                    // TODO: Обработать ошибку генерации уровня
                    return@launch
                }

                val (crossword, bonusWordsPool, inputLetters) = newLevel

                _uiState.update { currentState ->
                    currentState.copy(
                        crossword,
                        bonusWordsPool,
                        inputLetters
                    )
                }
            } catch (e: Exception) {
                // TODO: обработка исключений
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun submitWord(submittedWord: List<Char>) {
        _uiState.update { currentState ->
            val isAlreadyFound = submittedWord in currentState.foundWords
                    && submittedWord in currentState.foundBonusWords

            val isNewBonusWord = submittedWord in currentState.bonusWordsPool
                    && submittedWord !in currentState.foundBonusWords

            val isNewCrosswordWord = submittedWord !in currentState.foundWords
                    && submittedWord in currentState.crossword.words.map { it.chars }

            when {
                //TODO: Показать, что слово уже найдено
                isAlreadyFound -> {
                    currentState
                }

                // TODO: Показать анимацию "Бонусное слово"
                isNewBonusWord -> {
                    val newFoundBonusWords = currentState.foundBonusWords + setOf(submittedWord)
                    currentState.copy(foundBonusWords = newFoundBonusWords)
                }

                // TODO: Анимация нахождения слова кроссворда
                isNewCrosswordWord -> {
                    val newFoundCrosswordWords = currentState.foundWords + setOf(submittedWord)

                    currentState.copy(
                        crossword = currentState.crossword.revealWord(submittedWord),
                        foundWords = newFoundCrosswordWords
                    )
                }

                else -> currentState
            }
        }

        checkLevelCompletion()
    }

    private fun checkLevelCompletion() {
        val currentState = _uiState.value

        // TODO: Показать "Уровень пройден!"
        if (currentState.foundWords.size == currentState.crossword.words.size)
            _uiState.update { it.copy(isLevelCompleted = true) }
    }
}