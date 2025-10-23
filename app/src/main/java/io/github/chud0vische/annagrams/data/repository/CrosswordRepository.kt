package io.github.chud0vische.annagrams.data.repository

import android.util.Log
import io.github.chud0vische.annagrams.data.model.Crossword
import io.github.chud0vische.annagrams.data.model.CrosswordGenerator

data class CrosswordGenerationResult(
    val crossword: Crossword,
    val bonusWords: Set<List<Char>>,
    val mainWord: String
)

class CrosswordRepository(
    private val wordsRepository: WordsRepository,
    private val crosswordGenerator: CrosswordGenerator) {
    suspend fun generateCrossword(
        wordLength: Int = 5,
        minWordsCount: Int = 6,
        maxWordsCount: Int = 40
    ): CrosswordGenerationResult? {
        val maxAttempt = 100

        for (attempt in 1..maxAttempt) {
            val mainWord = wordsRepository.getRandomWordByLength(wordLength) ?: continue
            Log.d("CrosswordGen", "Попытка: $attempt, Главное слово: '$mainWord'")

            val allWordsFromDB = wordsRepository.getAllWords()

            val allPossibleSubWords = findSubwords(mainWord, allWordsFromDB)
            val sortedSubWords = allPossibleSubWords.sortedByDescending { it.length }

            val wordsForGeneration = sortedSubWords + listOf(mainWord)

            if (wordsForGeneration.size < minWordsCount) {
                Log.d("CrosswordGen", "Найдено мало подслов (${allPossibleSubWords}), пропускаем...")
                continue
            }

            Log.d("CrosswordGen", "Подслова: $allPossibleSubWords, size: ${allPossibleSubWords.size}")

            // Crossword Generation
            val generationResult = crosswordGenerator.generate(wordsForGeneration.toSet())

            if (generationResult != null) {
                val (crossword, unplacedWords) = generationResult

                val bonusWords = unplacedWords.map { it.toList() }.toSet()

                if (crossword.words.size >= minWordsCount) {
                    Log.d("CrosswordGen", "Генерация кроссворда прошла успешно")
                    Log.d("CrosswordGen", "Бонусные слова: $bonusWords")
                    return CrosswordGenerationResult(crossword, bonusWords, mainWord)
                } else {
                    Log.d("CrosswordGen", "Генерация кроссворда не удалась")
                    continue
                }
            } else {
                Log.d("CrosswordGen", "Генерация кроссворда не удалась")
            }

            Log.d("CrosswordGen", "Генерация кроссворда завершена")
        }

        Log.d("CrosswordGen", "Не удалось сгенерировать кроссворд")
        return null
    }

    private fun findSubwords(mainWord: String, dictionary: List<String>): Set<String> {
        val mainWordCharCount = mainWord.groupingBy { it }.eachCount()

        return dictionary.asSequence().filter { word ->
            if (word.length > mainWord.length || word.length < 3 || word == mainWord) {
                false
            } else {
                val wordCharCount = word.groupingBy { it }.eachCount()

                wordCharCount.all { (char, count) ->
                    count <= mainWordCharCount.getOrDefault(char, 0)
                }
            }
        }.toSet()
    }
}