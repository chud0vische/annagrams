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
            val mainWord = wordsRepository.getRandomWordByLength(5) ?: continue
            Log.d("CrosswordGen", "Попытка: $attempt, Главное слово: '$mainWord'")

            val allWordsFromDB = wordsRepository.getAllWords()

            val allPossibleSubWords = findSubwords(mainWord, allWordsFromDB) + mainWord

            if (allPossibleSubWords.size < minWordsCount) {
                Log.d("CrosswordGen", "Найдено мало подслов (${allPossibleSubWords.size}), пропускаем...")
                continue
            }

            Log.d("CrosswordGen", "Подслова: $allPossibleSubWords, size: ${allPossibleSubWords.size}")

            val wordsForCrossword = allPossibleSubWords.take(maxWordsCount).toSet()
            Log.d("CrosswordGen", "Слова для кроссворда: $wordsForCrossword")


            // Crossword Generation
            val generationResult = crosswordGenerator.generate(wordsForCrossword)

            if (generationResult != null) {
                val (crossword, unplacedWords) = generationResult

                val bonusWords = (unplacedWords + allPossibleSubWords.drop(maxWordsCount))
                    .map { it.toList() }
                    .toSet()

                if (crossword.words.size >= minWordsCount) {
                    Log.d("CrosswordGen", "Генерация кроссворда прошла успешно")
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

        return dictionary.filter { word ->
            if (word.length > mainWord.length || word.length < 3 || word == mainWord) {
                return@filter false
            }

            val wordCharCount = word.groupingBy { it }.eachCount()

            wordCharCount.all { (char, count) ->
                count <= mainWordCharCount.getOrDefault(char, 0)
            }
        }.shuffled().toSet()
    }
}