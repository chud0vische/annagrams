package io.github.chud0vische.annagrams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.theme.WordsTheme
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FiveButtonsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FiveButtonsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { Log.d("MainActivity", "Button 1 Clicked") }) {
            Text("Кнопка 1")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { Log.d("MainActivity", "Button 2 Clicked") }) {
            Text("Кнопка 2")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { Log.d("MainActivity", "Button 3 Clicked") }) {
            Text("Кнопка 3")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { Log.d("MainActivity", "Button 4 Clicked") }) {
            Text("Кнопка 4")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { Log.d("MainActivity", "Button 5 Clicked") }) {
            Text("Кнопка 5")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FiveButtonsScreenPreview() {
    WordsTheme {
        FiveButtonsScreen()
    }
}