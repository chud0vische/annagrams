package io.github.chud0vische.annagrams.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_ru",
    indices = [Index(value = ["length"], name = "idx_word_length")]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val length: Int
)