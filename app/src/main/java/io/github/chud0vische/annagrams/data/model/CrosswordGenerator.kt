package io.github.chud0vische.annagrams.data.model

import android.util.Log

class CrosswordGenerator(
    private val maxGridSize: Int = 15,
    private val emptyChar: Char = ' ',
) {
    fun generate(words: Set<String>): Pair<Crossword, Set<String>>? {
        val sortedWords = words.sortedByDescending { it.length }

        if (sortedWords.isEmpty()) {
            return null
        }

        val placedWords = mutableListOf<CrosswordWord>()
        val grid = mutableMapOf<Point, Char>()

        val firstWord = CrosswordWord(
            sortedWords.first().toList(),
            Point(0, maxGridSize / 2),
            WordDirection.HORIZONTAL
        )

        placeWordOnGrid(
            firstWord,
            grid,
            placedWords
        )

        val unplacedWord = mutableSetOf<String>()

        for (i in 1 until sortedWords.size) {
            val wordToPlace = sortedWords[i]
            val bestFit = findBestFit(wordToPlace, placedWords, grid)

            if (bestFit != null) {
                placeWordOnGrid(bestFit, grid, placedWords)
            } else {
                unplacedWord.add(wordToPlace)
            }
        }

        if (placedWords.size < 3) {
            return null
        }

        return  normalizeGrid(placedWords) to unplacedWord
    }

    private fun placeWordOnGrid(
        word: CrosswordWord,
        grid: MutableMap<Point, Char>,
        crosswordWords: MutableList<CrosswordWord>
    ) {

        var x = word.startPoint.x
        var y = word.startPoint.y

        for (char in word.chars) {
            grid[Point(x, y)] = char

            if (word.direction == WordDirection.HORIZONTAL) x++ else y++
        }

        crosswordWords.add(word)
    }

    private fun findBestFit(
        word: String,
        placedWords: List<CrosswordWord>,
        grid: Map<Point, Char>
    ): CrosswordWord? {
        val possiblePlacements = mutableListOf<CrosswordWord>()

        for ((charIndex, charToPlace) in word.withIndex()) {
            for (placed in placedWords) {
                for ((placedCharIndex, placedChar) in placed.chars.withIndex()) {
                    if (charToPlace == placedChar) {
                        val newDirection = if (placed.direction == WordDirection.HORIZONTAL)
                            WordDirection.VERTICAL else WordDirection.HORIZONTAL

                        val newX: Int
                        val newY: Int

                        if (newDirection == WordDirection.VERTICAL) {
                            newX = placed.startPoint.x + placedCharIndex
                            newY = placed.startPoint.y - charIndex
                        } else {
                            newX = placed.startPoint.x - charIndex
                            newY = placed.startPoint.y + placedCharIndex
                        }

                        val candidate =
                            CrosswordWord(
                                word.toList(),
                                Point(newX, newY),
                                newDirection
                            )

                        if (canPlaceWordAt(candidate, grid)) {
                            possiblePlacements.add(candidate)
                        }
                    }
                }
            }
        }

        if (possiblePlacements.isEmpty()) {
            Log.d("CrosswordGen", "Не удалось найти подходящее место для слова '$word'")
        }

        return possiblePlacements.maxByOrNull { placement ->
            var score = 0
            for ((i, _) in placement.chars.withIndex()) {
                val x = if (placement.direction == WordDirection.HORIZONTAL) placement.startPoint.x + i else placement.startPoint.x
                val y = if (placement.direction == WordDirection.VERTICAL) placement.startPoint.y + i else placement.startPoint.y

                // Проверяем соседей перпендикулярно слову
                val (dx, dy) = if (placement.direction == WordDirection.HORIZONTAL) (0 to 1) else (1 to 0)

                if (grid[Point(x + dx, y + dy)] == null) score++
                if (grid[Point(x - dx, y - dy)] == null) score++
            }

            score
        }
    }

    private fun canPlaceWordAt(word: CrosswordWord, grid: Map<Point, Char>): Boolean {
        val x = word.startPoint.x
        val y = word.startPoint.y

        // Out of bounds check
        if (word.direction == WordDirection.HORIZONTAL) {
            if (x < 0 || x + word.chars.size > maxGridSize || y < 0 || y >= maxGridSize)
                return false
        } else {
            if (y < 0 || y + word.chars.size > maxGridSize  || x < 0 || x >= maxGridSize)
                return false
        }

        for ((i, char) in word.chars.withIndex()) {
            val currentPoint =
                if (word.direction == WordDirection.HORIZONTAL)
                    Point(x + i, y)
                else
                    Point(x, y + i)

            val existingChar = grid[currentPoint]

            // Check if here another char
            if (existingChar != null && existingChar != char)
                return false

            val (dx, dy) =
                if (word.direction == WordDirection.HORIZONTAL)
                    (0 to 1)
                else
                    (1 to 0)

            val adjacent1 = grid[Point(currentPoint.x + dx, currentPoint.y + dy)]
            val adjacent2 = grid[Point(currentPoint.x - dx, currentPoint.y - dy)]

            if (existingChar == null) {
                if (adjacent1 != null || adjacent2 != null)
                    return false
            }
        }

        if (word.direction == WordDirection.HORIZONTAL) {
            if (grid[Point(word.startPoint.x - 1, word.startPoint.y)] != null
                || grid[Point(word.startPoint.x + word.chars.size, word.startPoint.y)] != null)
                    return false
        } else {
            if (grid[Point(word.startPoint.x, word.startPoint.y - 1)] != null
                || grid[Point(word.startPoint.x, word.startPoint.y + word.chars.size)] != null)
                    return false
        }

        return true
    }

    private fun normalizeGrid(placedWords: List<CrosswordWord>): Crossword {
        if (placedWords.isEmpty())
            return Crossword.createEmpty()

        val minX = placedWords.minOf { it.startPoint.x }
        val minY = placedWords.minOf { it.startPoint.y }

        val maxX = placedWords.maxOf {
            if (it.direction == WordDirection.HORIZONTAL) {
                it.startPoint.x + it.chars.size - 1
            } else {
                it.startPoint.x
            }
        }

        val maxY = placedWords.maxOf {
            if (it.direction == WordDirection.VERTICAL) {
                it.startPoint.y + it.chars.size - 1
            } else {
                it.startPoint.y
            }
        }

        val width = maxX - minX + 1
        val height = maxY - minY + 1

        val finalGrid = MutableList(height) {
            MutableList(width) { CrosswordCell(emptyChar, CrosswordCellType.EMPTY) }
        }

        val finalWords = mutableSetOf<CrosswordWord>()

        for (word in placedWords) {
            val newStartX = word.startPoint.x - minX
            val newStartY = word.startPoint.y - minY

            finalWords.add(
                CrosswordWord(
                    word.chars,
                    Point(newStartX, newStartY),
                    word.direction
                )
            )

            // finalWords.add(CrosswordWord(word.chars, Point(newStartX, newStartY), word
            // .direction))

            for ((i, char) in word.chars.withIndex()) {
                val x = if (word.direction == WordDirection.HORIZONTAL)
                    newStartX + i else newStartX

                val y = if (word.direction == WordDirection.VERTICAL)
                    newStartY + i else newStartY

                finalGrid[y][x] = CrosswordCell(char, CrosswordCellType.HIDDEN)
            }
        }

        return Crossword(finalGrid, width, height, finalWords)
    }
}