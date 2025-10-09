package io.github.chud0vische.annagrams.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.composables.WordGrid

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (uiState.isLoading) {
            // Показываем индикатор загрузки, пока генерируется уровень
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Уровень ${uiState.levelNumber}", style = MaterialTheme.typography.headlineMedium)

                WordGrid(
                    placedWords = uiState.crosswordWords,
                    foundWords = uiState.foundWords,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // TODO: Отобразить бонусные слова
                Text(text = "Найдено бонусных слов: ${uiState.foundBonusWords.size}", modifier = Modifier.padding(
                    16.dp
                ))
            }

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TODO: Заменить на кастомное поле ввода из букв
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Введите слово") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    viewModel.submitWord(text)
                    text = ""
                }) {
                    Text("Проверить")
                }

                if (uiState.isLevelCompleted) {
                    Button(onClick = { viewModel.loadLevel() }, modifier = Modifier.padding(
                        top = 8.dp
                    )) {
                        Text("Следующий уровень")
                    }
                }
            }
        }
    }
}