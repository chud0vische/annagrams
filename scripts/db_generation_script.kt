import kotlin.io.forEachLine
import kotlin.text.isNotEmpty
import kotlin.text.lowercase
import kotlin.text.trim
import kotlin.text.trimIndent
import kotlin.use

fun main() {
    val textFilePath = "words.txt"
    val databasePath = "annagrams_words.db"
    val tableName = "words_ru"

    java.sql.DriverManager.getConnection("jdbc:sqlite:$databasePath").use { connection ->
        kotlin.io.println("База данных '$databasePath' открыта")

        val statement = connection.createStatement()
        val createTableSql = """
            CREATE TABLE IF NOT EXISTS $tableName (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                word TEXT NOT NULL UNIQUE,
                length INTEGER NOT NULL
            )
        """.trimIndent()
        statement.executeUpdate(createTableSql)
        statement.close()
        kotlin.io.println("Таблица '$tableName' создана")

        val insertSql = "INSERT INTO $tableName (word, length) VALUES (?, ?)"
        val preparedStatement: java.sql.PreparedStatement = connection.prepareStatement(insertSql)

        connection.autoCommit = false

        var wordsAdded = 0

        java.io.File(textFilePath).forEachLine { line ->
            val word = line.trim().lowercase()
            if (word.isNotEmpty()) {
                try {
                    preparedStatement.setString(1, word)
                    preparedStatement.setInt(2, word.length)
                    preparedStatement.executeUpdate()
                    wordsAdded++
                } catch (e: java.sql.SQLException) {
                    // Игнорируем ошибку, если слово уже существует
                }
            }
        }

        connection.commit()

        kotlin.io.println("\n Готово!")
        kotlin.io.println("Добавлено $wordsAdded уникальных слов в базу данных")
    }
}