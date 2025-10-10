package io.github.chud0vische.annagrams.ui.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.components.molecules.WordView

@Composable
fun WordGrid(
    placedWords: List<CrosswordWord>,
    foundWords: Set<String>,
    modifier: Modifier = Modifier
) {
    val sortedWords = placedWords.sortedWith(compareBy({ it.text.length }, { it.text }))

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sortedWords) { placedWord ->

            WordView(
                word = placedWord.text,
                isFound = placedWord.text in foundWords,
            )
        }
    }
}