package io.github.chud0vische.annagrams.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.data.LayoutStrategy
import io.github.chud0vische.annagrams.data.PlacedWord

@Composable
fun WordGrid(
    words: Set<String>,
    foundWords: Set<String>,
    strategy: LayoutStrategy,
    modifier: Modifier = Modifier
) {
    when (strategy) {
        is LayoutStrategy.ListLayout -> {
            WordsList(
                words = words,
                foundWords = foundWords,
                modifier = modifier
            )
        }

        is LayoutStrategy.CrosswordLayout -> {
            Crossword(
                placedWords = strategy.placedWords,
                foundWords = foundWords,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun WordsList(
    words: Set<String>,
    foundWords: Set<String>,
    modifier: Modifier = Modifier
) {
    val sortedWords = words.toList().sortedWith(compareBy({ it.length }, { it }))

    LazyColumn(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sortedWords) { word ->
            WordView(
                word = word,
                isFound = word in foundWords
            )
        }
    }
}

@Composable
private fun Crossword(
    placedWords: List<PlacedWord>,
    foundWords: Set<String>,
    modifier: Modifier = Modifier
) {
    // TODO: Реализовать логику отрисовки кроссворда здесь.

    Text(
        text = "Режим кроссворда в разработке.\n" +
                "Слов для размещения: ${placedWords.size}",
        modifier = modifier.padding(16.dp)
    )
}