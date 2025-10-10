package io.github.chud0vische.annagrams.ui.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.data.model.WordDirection
import io.github.chud0vische.annagrams.ui.components.atoms.LetterView

@Composable
fun WordView(
    word: String,
    isFound: Boolean,
    modifier: Modifier = Modifier,
    direction: WordDirection = WordDirection.HORIZONTAL
) {
    when (direction) {
        WordDirection.HORIZONTAL -> {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                word.forEach { char ->

                    LetterView(
                        letter = if (isFound) char else null,
                    )
                }
            }
        }
        else -> {

        }
    }
}