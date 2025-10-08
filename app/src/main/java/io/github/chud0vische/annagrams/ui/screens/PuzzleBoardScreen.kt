package io.github.chud0vische.annagrams.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import io.github.chud0vische.annagrams.GameViewModel
import io.github.chud0vische.annagrams.ui.composables.FoundWordsGrid
import io.github.chud0vische.annagrams.ui.composables.LevelControlActions
import io.github.chud0vische.annagrams.ui.composables.WordComposer
import io.github.chud0vische.annagrams.ui.theme.Dimen
import io.github.chud0vische.annagrams.ui.theme.FoundWordColor

@Composable
fun PuzzleBoardScreen(viewModel: GameViewModel) {
    val inputWord = viewModel.typedWord
    val foundWords = viewModel.foundWords
    val allWords = viewModel.validWords
    val isLevelCompleted = viewModel.isLevelCompleted

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.screenPadding),
        contentAlignment = Alignment.Center
    ) {
        FoundWordsGrid(
            allWords = allWords,
            foundWords = foundWords,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = Dimen.foundWordsListTopPadding)
        )

        Text(
            text = inputWord,
            fontSize = Dimen.mediumFont,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = Dimen.inputWordTopPadding)
        )

        if (isLevelCompleted) {
            Text(
                text = "Level Completed!",
                fontSize = Dimen.largeFont,
                fontWeight = FontWeight.Bold,
                color = FoundWordColor,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = Dimen.inputWordTopPadding)
            )
        }

        LevelControlActions(
            isLevelCompleted = isLevelCompleted,
            onRestartClick = { viewModel.restartLevel() },
            onNextLevelClick = { viewModel.nextLevel() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )

        WordComposer(
            letters = viewModel.levelLetters,
            onWordCollect = { word -> viewModel.onWordCollected(word) },
            onLetterSelected = { letter -> viewModel.onLetterSelected(letter)},
            onShuffleClick = { viewModel.shuffleLetters() },

            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimen.keyboardBottomPadding)
        )
    }
}