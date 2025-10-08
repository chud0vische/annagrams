package io.github.chud0vische.annagrams.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.chud0vische.annagrams.ui.theme.Dimen
import io.github.chud0vische.annagrams.ui.theme.FoundWordColor
import io.github.chud0vische.annagrams.ui.theme.PlaceholderColor

@Composable
fun FoundWordsGrid(
    allWords: Set<String>,
    foundWords: Set<String>,
    modifier: Modifier = Modifier
) {
    val sortedWords = allWords.toList().sortedWith(compareBy( { it.length }, { it }))
    val placeholder = "⬜"

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sortedWords) { word ->
            val isFound = word in foundWords

            val textToShow = if (isFound) {
                word
            } else {
                placeholder.repeat(word.length)
            }

            Text(
                text = textToShow,
                fontSize = Dimen.mediumFont,
                fontWeight = FontWeight.Bold,
                color = if (isFound) FoundWordColor else PlaceholderColor,
                letterSpacing = if (isFound) 1.sp else 4.sp
            )
        }
    }
}