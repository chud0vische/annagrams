package io.github.chud0vische.annagrams.data.model

data class Crossword(
    val grid: List<List<CrosswordCell>>,
    val width: Int,
    val height: Int,
    val wordPlacements: List<CrosswordWord>
) {
    companion object {

        fun createCrosswordFromWordPlacements(
            width: Int,
            height: Int,
            placements: List<CrosswordWord>,
            emptyChar: Char = ' '
        ): Crossword {
            val initialGrid: MutableList<MutableList<Char>> =
                MutableList(height) { MutableList(width) { emptyChar } }

            for (placement in placements) {
                var x = placement.startPoint.x
                var y = placement.startPoint.y

                for (char in placement.word) {
                    initialGrid[y][x] = char
                    if (placement.direction == WordDirection.HORIZONTAL) x++ else y++
                }
            }

            val cellGrid = initialGrid.mapIndexed { y, row ->
                row.mapIndexed { x, char ->
                    CrosswordCell(char = char, isVisible = false)
                }
            }

            return Crossword(
                grid = cellGrid,
                width = width,
                height = height,
                wordPlacements = placements
            )
        }
    }
}