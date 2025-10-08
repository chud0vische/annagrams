package io.github.chud0vische.annagrams

data class Level(
    val id: Int,
    val letters: List<Char>,
    val validWords: Set<String>,
)

object LevelRepository {
    private val levels = listOf(
        Level(
            id = 0,
            letters = "ЛОМКА".toList(),
            validWords = setOf("ЛОМКА", "ЛАК", "КОЛ", "КОМА", "МОЛКА")
        ),
        Level(
            id = 1,
            letters = "УКРОП".toList(),
            validWords = setOf("РОК", "УКРОП", "ПУК", "УКОР", "УПОР")
        ),
        Level(
            id = 2,
            letters = "КРОХА".toList(),
            validWords = setOf("КОРА", "КРОХА", "КРАХ", "ОХРА", "РОК")
        ),
    )

    fun getLevel(id: Int): Level? {
        return levels.getOrNull(id)
    }

    fun getTotalLevels(): Int = levels.size
}