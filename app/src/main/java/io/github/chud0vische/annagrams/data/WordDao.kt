package io.github.chud0vische.annagrams.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM words_ru WHERE length = :length ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWordByLength(length: Int): Word?

    @Query("SELECT word FROM words_ru")
    suspend fun getAllWords(): List<String>
}

