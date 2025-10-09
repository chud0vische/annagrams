package io.github.chud0vische.annagrams.data

enum class WordDirection {
    HORIZONTAL,
    VERTICAL
}

data class PlacedWord(
    val text: String,
    val startX: Int,
    val startY: Int,
    val direction: WordDirection
) {
    fun contains(gridChar: GridChar): Boolean {
        for (i in text.indices) {
            val currentX = if (direction == WordDirection.HORIZONTAL) startX + i else startX
            val currentY = if (direction == WordDirection.VERTICAL) startY + i else startY

            if (gridChar.x == currentX && gridChar.y == currentY && gridChar.char.equals(text[i], ignoreCase = true)) {
                return true
            }
        }

        return false
    }
}