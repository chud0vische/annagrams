package io.github.chud0vische.annagrams.data

data class Level(
    val crosswordWords: List<PlacedWord>,
    val bonusWordsPool: Set<String>,
    val letters: List<Char>,
)

class LevelRepository(private val wordDao: WordDao) {
    suspend fun generateLevel(wordLength: Int = 5): Level? {
        val mainWord = wordDao.getRandomWordByLength(wordLength)?.word ?: return null

        val allWordsFromDb = wordDao.getAllWords()

        val allPossibleSubWords = findSubWords(mainWord, allWordsFromDb)

        if (allPossibleSubWords.size < 5) {
            return generateLevel(wordLength)
        }

        val crosswordWordsSet = allPossibleSubWords.sortedByDescending { it.length }.take(5).toSet()

        val bonusWords = allPossibleSubWords - crosswordWordsSet

        val placements = crosswordWordsSet.sortedBy { it.length }.mapIndexed { index, word ->
            PlacedWord(
                text = word,
                startX = 0,
                startY = index,
                direction = WordDirection.HORIZONTAL
            )
        }

        return Level(
            letters = mainWord.toCharArray().toList().shuffled(), // Перемешиваем буквы основного слова
            crosswordWords = placements,
            bonusWordsPool = bonusWords
        )
    }

    private fun findSubWords(mainWord: String, dictionary: List<String>): Set<String> {
        val mainWordCharCount = mainWord.groupingBy { it }.eachCount()

        return dictionary.filter { word ->
            if (word.length > mainWord.length || word.length < 3) {
                return@filter false
            }

            val wordCharCount = word.groupingBy { it }.eachCount()

            wordCharCount.all { (char, count) ->
                count <= mainWordCharCount.getOrDefault(char, 0)
            }

        }.toSet()
    }
}