package io.github.chud0vische.annagrams.data.repository

import io.github.chud0vische.annagrams.data.model.Level

class LevelRepository(private val crosswordRepository: CrosswordRepository) {
    suspend fun generateLevel(wordLength: Int = 5): Level? {
        val result = crosswordRepository.generateCrossword(wordLength) ?: return null

        return Level(
            result.crossword,
            result.bonusWords,
            result.mainWord.toList().shuffled()
        )
    }
}