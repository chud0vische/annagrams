package io.github.chud0vische.annagrams.data.repository

import io.github.chud0vische.annagrams.data.db.WordDao
import io.github.chud0vische.annagrams.data.model.CrosswordWord
import io.github.chud0vische.annagrams.data.model.Level
import io.github.chud0vische.annagrams.data.model.Point
import io.github.chud0vische.annagrams.data.model.WordDirection

class LevelRepository(private val wordDao: WordDao) {
    suspend fun generateLevel(wordLength: Int = 5): Level? {
        val mainWord = wordDao.getRandomWordByLength(wordLength)?.word?.toCharArray()?.toList() ?:
            return null

        val allWordsFromDb = wordDao.getAllWords()

        val allPossibleSubWords = findSubWords(mainWord.joinToString(), allWordsFromDb)

        if (allPossibleSubWords.size < 5) {
            return generateLevel(wordLength)
        }

        allPossibleSubWords
            .toMutableList()
            .shuffle()

        val crosswordWordsSet = allPossibleSubWords
            .take(5)
            .toSet()

        val bonusWords = (allPossibleSubWords - crosswordWordsSet).map { it.toList() }.toSet()

        val crosswordWords = crosswordWordsSet
            .toList()
            .map { word ->
                word.toList()
            }
            .toSet()

        val placements = crosswordWords.mapIndexed { index, word ->
            CrosswordWord(
                word = word,
                startPoint = Point(x = 0, y = index),
                direction = WordDirection.HORIZONTAL
            )
        }

        return Level(
            letters = mainWord.shuffled(),
            crosswordWords = placements,
            bonusWordsPool = bonusWords
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

        }.toSet()
    }
}