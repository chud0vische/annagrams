package io.github.chud0vische.annagrams.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import io.github.chud0vische.annagrams.ui.screens.GameScreen
import io.github.chud0vische.annagrams.ui.theme.AnnagramsTheme
import io.github.chud0vische.annagrams.ui.theme.AppBackgroundColor
import io.github.chud0vische.annagrams.ui.viewmodel.GameViewModel
import io.github.chud0vische.annagrams.ui.viewmodel.GameViewModelFactory

class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnnagramsTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = AppBackgroundColor
                ) {
                    GameScreen(viewModel = gameViewModel)
                }
            }
        }
    }
}