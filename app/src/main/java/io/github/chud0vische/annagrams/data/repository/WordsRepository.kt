package io.github.chud0vische.annagrams.data.repository

import io.github.chud0vische.annagrams.data.db.WordDao

class WordsRepository(private val wordDao: WordDao) {
    suspend fun getRandomWordByLength(length: Int): String? {
        return wordDao.getRandomWordByLength(length)?.word
    }

    suspend fun getAllWords(): List<String> {
        return wordDao.getAllWords()
    }
}