package io.github.chud0vische.annagrams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.chud0vische.annagrams.ui.theme.AnnagramsTheme
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import io.github.chud0vische.annagrams.ui.game.GameViewModel
import io.github.chud0vische.annagrams.ui.screens.PuzzleBoardScreen
import io.github.chud0vische.annagrams.ui.theme.*
import io.github.chud0vische.annagrams.ui.game.GameViewModelFactory

class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnnagramsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppBackgroundColor
                ) {
                    PuzzleBoardScreen(viewModel = gameViewModel)
                }
            }
        }
    }
}



