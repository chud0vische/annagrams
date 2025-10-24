package io.github.chud0vische.annagrams.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import io.github.chud0vische.annagrams.ui.components.atoms.SettingsButton
import io.github.chud0vische.annagrams.ui.components.molecules.StarsCount
import io.github.chud0vische.annagrams.ui.components.organisms.CrosswordView
import io.github.chud0vische.annagrams.ui.components.organisms.InputPanel
import io.github.chud0vische.annagrams.ui.theme.AppDimensions
import io.github.chud0vische.annagrams.ui.theme.CircularIndicatorColor
import io.github.chud0vische.annagrams.ui.theme.Dimensions
import io.github.chud0vische.annagrams.ui.viewmodel.GameViewModel
import io.github.chud0vische.annagrams.R
import io.github.chud0vische.annagrams.ui.components.organisms.StarryBackground


@Composable
fun GameScreen(viewModel: GameViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val hazeState = rememberHazeState(blurEnabled = true)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.game_bg_blured),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .blur(radius = 100.dp),
            contentScale = ContentScale.Fit
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.game_bg_blured),
            contentDescription = null,
            modifier = Modifier
                .scale(1f, -1f)
                .fillMaxWidth()
                .blur(radius = 100.dp),
            contentScale = ContentScale.Fit
        )
    }

    if (!uiState.isLoading) {
        Box(modifier = Modifier
            .hazeSource(state = hazeState)
        ) {
            StarryBackground(stars = uiState.stars)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {

        if (uiState.isLoading) {
            CircularProgressIndicator(
                color = CircularIndicatorColor
            )
        }

        if (!uiState.isLoading && uiState.crossword.words.isEmpty()) {
            Text(
                "Не удалось сгенерировать уровень.\nПроверьте базу данных.",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = Dimensions.largeFont
            )
        }
        else if (!uiState.isLoading && !uiState.crossword.words.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        StarsCount(count = 12)
                        SettingsButton(
                            onClick = { viewModel.loadLevel() }
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CrosswordView(
                        uiState.crossword,
                        hazeState,
                        modifier = Modifier.padding(horizontal = AppDimensions.gameScreenPadding)
                    )
                }

                InputPanel(
                    inputLetters = uiState.inputLetters,
                    onWordCollect = { word ->
                        viewModel.submitWord(word.toList())
                    },
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

//            NavigationButton(
//                isLevelCompleted = uiState.isLevelCompleted,
//                onRestartClick = { viewModel.loadLevel() },
//                onNextLevelClick = { viewModel.loadLevel() },
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(Dimensions.screenPadding)
//            )
        }
    }
}