package io.github.chud0vische.annagrams.data.repository

import io.github.chud0vische.annagrams.data.db.WordDao
import io.github.chud0vische.annagrams.data.model.Crossword
import io.github.chud0vische.annagrams.data.model.CrosswordWord
import io.github.chud0vische.annagrams.data.model.Level
import io.github.chud0vische.annagrams.data.model.Point
import io.github.chud0vische.annagrams.data.model.WordDirection

class LevelRepository(private val wordDao: WordDao) {
    suspend fun generateLevel(wordLength: Int = 5): Level? {
        val mainWord = wordDao.getRandomWordByLength(wordLength)?.word ?: return null
        val allWordsFromDb = wordDao.getAllWords()

        val allPossibleSubWords = findSubWords(mainWord, allWordsFromDb)

        // TODO: обработать, случай когда невозможно сгенирировать
        if (allPossibleSubWords.size < 5) {

            return generateLevel(wordLength)
        }

        val crosswordWords = allPossibleSubWords
            .take(5)
            .mapIndexed { i, word ->
                CrosswordWord(
                    word.toList(),
                    Point(0, i),
                    WordDirection.HORIZONTAL
                )
            }.toSet()

        val bonusWords = allPossibleSubWords
            .drop(5)
            .map { it.toList() }
            .toSet()

        val crossword = Crossword.createCrosswordFromWords(
            // TODO: изменение размера сетки
            5,
            5,
            crosswordWords,
        )

        val inputLetters = mainWord.toList()

        return Level(
            crossword,
            bonusWords,
            inputLetters
        )
    }

    private fun findSubWords(mainWord: String, dictionary: List<String>): Set<String> {
        val mainWordCharCount = mainWord.groupingBy { it }.eachCount()

        return dictionary.filter { word ->
            if (word.length > mainWord.length || word.length < 3) {
                return@filter false
            }

            val wordCharCount = word.groupingBy { it }.eachCount()

            wordCharCount.all { (char, count) ->
                count <= mainWordCharCount.getOrDefault(char, 0)
            }
        }.shuffled().toSet()
    }
}