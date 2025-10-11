package io.github.chud0vische.annagrams.data.model

data class Crossword(
    val grid: List<List<CrosswordCell>>,
    val width: Int,
    val height: Int,
    val words: Set<CrosswordWord>
) {
    companion object {
        fun createEmpty(): Crossword {
            return Crossword(
                emptyList(),
                0,
                0,
                emptySet()
            )
        }

        fun createCrosswordFromWords(
            width: Int,
            height: Int,
            words: Set<CrosswordWord>,
            emptyChar: Char = ' '
        ): Crossword {
            val initialGrid: MutableList<MutableList<Char>> =
                MutableList(height) { MutableList(width) { emptyChar } }

            for (word in words) {
                var x = word.startPoint.x
                var y = word.startPoint.y

                for (char in word.chars) {
                    initialGrid[y][x] = char
                    if (word.direction == WordDirection.HORIZONTAL) x++ else y++
                }
            }

            // hiding all crossword cells
            val cellGrid = initialGrid.mapIndexed { y, row ->
                row.mapIndexed { x, char ->
                    CrosswordCell(char = char, isVisible = false)
                }
            }

            return Crossword(
                cellGrid,
                width,
                height,
                words
            )
        }
    }


    /* TODO: если пользователь открыл всё слово с подсказками...
        Либо просто отображать Char с подсказкой не как отгаданный
    */
    fun revealChar(point: Point): Crossword {
        val newGridMutable = grid.map { it.toMutableList() }.toMutableList()

        val oldCell = newGridMutable[point.y][point.x]
        val newCell = oldCell.copy(isVisible = true)

        newGridMutable[point.y][point.x] = newCell

        return this.copy(grid = newGridMutable.map { it.toList() })
    }

    fun revealWord(wordChars: List<Char>): Crossword {
        val word = words.find { it.chars == wordChars }
            ?: return this

        val newGridMutable = grid.map { it.toMutableList() }.toMutableList()

        var (x, y) = word.startPoint

        for (char in word.chars) {
            val oldCell = newGridMutable[y][x]
            newGridMutable[y][x] = oldCell.copy(isVisible = true)

            when (word.direction)  {
                WordDirection.HORIZONTAL -> x++
                WordDirection.VERTICAL -> y++
            }
        }

        return this.copy(newGridMutable.map { it.toList() })
    }
}