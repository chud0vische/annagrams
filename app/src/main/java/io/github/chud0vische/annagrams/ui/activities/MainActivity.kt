package io.github.chud0vische.annagrams.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import io.github.chud0vische.annagrams.data.db.AppDatabase
import io.github.chud0vische.annagrams.data.db.WordDao
import io.github.chud0vische.annagrams.data.model.CrosswordGenerator
import io.github.chud0vische.annagrams.data.repository.CrosswordRepository
import io.github.chud0vische.annagrams.data.repository.WordsRepository
import io.github.chud0vische.annagrams.ui.screens.GameScreen
import io.github.chud0vische.annagrams.ui.theme.AnnagramsTheme
import io.github.chud0vische.annagrams.ui.theme.AppBackgroundColor
import io.github.chud0vische.annagrams.ui.viewmodel.GameViewModel
import io.github.chud0vische.annagrams.ui.viewmodel.GameViewModelFactory

class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels {
        val wordDao = AppDatabase.getDatabase(applicationContext).wordDao()
        val wordsRepository = WordsRepository(wordDao)
        val crosswordGenerator = CrosswordGenerator(9, ' ')

        val crosswordRepository = CrosswordRepository(wordsRepository, crosswordGenerator)
        GameViewModelFactory(applicationContext, crosswordRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

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