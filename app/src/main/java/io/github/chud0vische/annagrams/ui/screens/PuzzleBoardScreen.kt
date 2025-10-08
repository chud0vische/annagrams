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
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.GameViewModel
import io.github.chud0vische.annagrams.data.LayoutStrategy
import io.github.chud0vische.annagrams.ui.composables.LevelControlButton
import io.github.chud0vische.annagrams.ui.composables.WordInputPad
import io.github.chud0vische.annagrams.ui.composables.WordGrid
import io.github.chud0vische.annagrams.ui.theme.Dimen
import io.github.chud0vische.annagrams.ui.theme.FoundWordColor

@Composable
fun PuzzleBoardScreen(viewModel: GameViewModel) {
    val inputWord = viewModel.typedWord
    val foundWords = viewModel.foundWords
    val words = viewModel.validWords
    val isLevelCompleted = viewModel.isLevelCompleted

    // TODO: Переключатель стратегий
    val currentStrategy = LayoutStrategy.ListLayout

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.screenPadding),
        contentAlignment = Alignment.Center
    ) {
        WordGrid(
            words = words, // Имя параметра `allWords` изменилось на `words`
            foundWords = foundWords,
            strategy = currentStrategy, // Передаем нашу новую стратегию
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
                .padding(top = Dimen.inputWordTopPadding, bottom = 120.dp)
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

        LevelControlButton(
            isLevelCompleted = isLevelCompleted,
            onRestartClick = { viewModel.restartLevel() },
            onNextLevelClick = { viewModel.nextLevel() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )

        WordInputPad(
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