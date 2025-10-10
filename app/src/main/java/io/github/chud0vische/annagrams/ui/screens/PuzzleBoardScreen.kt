package io.github.chud0vische.annagrams.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.viewmodel.GameViewModel
import io.github.chud0vische.annagrams.ui.components.molecules.NavigationButton
import io.github.chud0vische.annagrams.ui.components.organisms.InputPanel
import io.github.chud0vische.annagrams.ui.components.organisms.WordGrid
import io.github.chud0vische.annagrams.ui.theme.Dimensions
import io.github.chud0vische.annagrams.ui.theme.FoundWordColor


@Composable
fun PuzzleBoardScreen(viewModel: GameViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var typedWord by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            if (uiState.crosswordWords.isEmpty()) {
                CircularProgressIndicator()
            }
        }

        if (!uiState.isLoading && uiState.crosswordWords.isEmpty()) {
            Text(
                "Не удалось сгенерировать уровень.\nПроверьте базу данных.",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
        else if (uiState.crosswordWords.isNotEmpty()) {
            // ИСПОЛЬЗУЕМ COLUMN ДЛЯ ВЕРТИКАЛЬНОГО РАСПРЕДЕЛЕНИЯ
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimensions.screenPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween // Распределяет пространство
            ) {

                WordGrid(
                    placedWords = uiState.crosswordWords,
                    foundWords = uiState.foundWords,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = Dimensions.foundWordsListTopPadding)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(
                        text = typedWord.ifEmpty { " " },
                        fontSize = Dimensions.mediumFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    if (uiState.isLevelCompleted) {
                        Text(
                            text = "Level Completed!",
                            fontSize = Dimensions.largeFont,
                            fontWeight = FontWeight.Bold,
                            color = FoundWordColor
                        )
                    }
                }

                InputPanel(
                    letters = uiState.letters,
                    onWordCollect = { word ->
                        viewModel.submitWord(word)
                        typedWord = ""
                    },
                    onLetterSelected = { letter -> typedWord += letter },
                    onShuffleClick = { /* TODO */ },
                    modifier = Modifier.padding(bottom = Dimensions.keyboardBottomPadding)
                )
            }

            NavigationButton(
                isLevelCompleted = uiState.isLevelCompleted,
                onRestartClick = { /* TODO */ },
                onNextLevelClick = { viewModel.loadLevel() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Dimensions.screenPadding) // Добавим отступы
            )
        }
    }
}