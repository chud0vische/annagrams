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
    private val _uiState = MutableStateFlow(GameUiState(Crossword.createEmpty()))
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

    fun submitWord(submittedWord: String) {
        _uiState.update { currentState ->
            val submittedWordAsList = submittedWord.lowercase().toList()

            val isMainWord = currentState.crossword.words.any { it.chars == submittedWordAsList }
            val isBonusWord = submittedWordAsList in currentState.bonusWordsPool

            when {
                isMainWord && submittedWordAsList !in currentState.foundWords -> {
                    val updatedCrossword = currentState.crossword.revealWord(submittedWordAsList)
                    val newFoundWords = setOf<List<Char>>()

                    currentState.copy(
                        updatedCrossword,
                        foundWords = newFoundWords
                    )
                }

                isBonusWord && submittedWordAsList !in currentState.foundBonusWords -> {
                    val newFoundBonusWords = currentState.foundBonusWords + submittedWordAsList

                    // TODO: Показать анимацию "Бонусное слово"
                    currentState.copy(foundBonusWords = newFoundBonusWords)
                }

                submittedWordAsList in currentState.foundWords || submittedWordAsList in currentState.foundBonusWords -> {
                    // TODO: Показать короткое сообщение: "Слово уже найдено"
                    currentState
                }

                else -> {
                    currentState
                }
            }
        }
    }

    private fun checkLevelCompletion() {
        val currentState = _uiState.value

        if (currentState.foundWords.size == currentState.crosswordWords.size) {
            _uiState.update { it.copy(isLevelCompleted = true) }
            // TODO: Показать диалог "Уровень пройден!"
        }
    }

}