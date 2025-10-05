package io.github.chud0vische.annagrams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import io.github.chud0vische.annagrams.ui.theme.AnnagramsTheme
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.boundsInParent

// TODO: полностью переработать...
class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnnagramsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // TODO: Вынести все значения цветов и размеров из MainActivity
                    color = Color(0xFF121212)
                ) {
                    GameScreen(viewModel = gameViewModel)
                }
            }
        }
    }
}

@Composable
fun GameScreen(viewModel: GameViewModel) {
    // TODO: Вынести все значения из GameScreen
    val currentWord = viewModel.typedWord
    val foundWords = viewModel.foundWords
    val allWords = viewModel.validWords
    val isLevelCompleted = viewModel.isLevelCompleted

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        FoundWordsList(
            allWords = allWords,
            foundWords = foundWords,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )

        Text(
            text = currentWord,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 100.dp)
        )

        if (isLevelCompleted) {
            Text(
                text = "Level Completed!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 100.dp)
            )
        }

        GameActions(
            isLevelCompleted = isLevelCompleted,
            onRestartClick = { viewModel.restartLevel() },
            onNextLevelClick = { viewModel.nextLevel() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )

        LetterKeyboard(
            letters = viewModel.levelLetters,
            onWordCollect = { word -> viewModel.onWordCollected(word) },
            onLetterSelected = { letter -> viewModel.onLetterSelected(letter)},
            onShuffleClick = { viewModel.shuffleLetters() },

            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
        )
    }
}

@Composable
fun LetterKeyboard(
    letters: List<Char>,
    onWordCollect: (String) -> Unit,
    onLetterSelected: (Char) -> Unit,
    onShuffleClick: () -> Unit,
    modifier: Modifier = Modifier,
    // TODO: Вынести все значения из LetterKeyboard
    distanceBetweenLetters: Int = 100,
    keyBoardSize: Int = 250
) {
    val letterPositions = remember(letters) { mutableStateMapOf<Int, Rect>() }
    val selectedButtonIndices  = remember { mutableStateListOf<Int>() }

    var currentDragPosition by remember { mutableStateOf<Offset?>(Offset.Zero) }


    Box(
        modifier = modifier
            .size(keyBoardSize.dp)
            // TODO: вынести в отдельную функцию
            .pointerInput(letters) {
                detectDragGestures(
                    onDragStart = {
                        onWordCollect("")
                        selectedButtonIndices.clear()
                        currentDragPosition = it
                    },
                    onDragEnd = {
                        if (selectedButtonIndices.isNotEmpty()) {
                            val word = selectedButtonIndices.map { index -> letters[index] }.joinToString("")
                            onWordCollect(word)
                        }

                        selectedButtonIndices.clear()
                        currentDragPosition = null
                    },
                    onDragCancel = {
                        onWordCollect("")
                        selectedButtonIndices.clear()
                        currentDragPosition = null
                    },
                    onDrag = { change, _ ->
                        val position = change.position
                        currentDragPosition = position

                        val indexUnderFinger = letterPositions.entries
                            .find { (_, rect) -> rect.contains(position) }
                            ?.key

                        if (indexUnderFinger != null && indexUnderFinger !in selectedButtonIndices) {
                            selectedButtonIndices.add(indexUnderFinger)
                            onLetterSelected(letters[indexUnderFinger])
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val points = selectedButtonIndices
                .mapNotNull { index -> letterPositions[index]?.center }
                .toMutableList()

            currentDragPosition?.let {
                if (points.isNotEmpty()) {
                    points.add(it)
                }
            }

            if (points.size > 1) {
                for (i in 0 until points.size -1) {
                    drawLine(
                        color = Color.White,
                        start = points[i],
                        end = points[i+1],
                        strokeWidth = 10f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        ShuffleButton(
            onShuffleClick,
        )

        letters.forEachIndexed { index, letter ->
            // GPT Black Magic
            val angle = index * 2 * Math.PI / letters.size
            val rotationAngle = -Math.PI / 2

            val offsetX = (cos(angle + rotationAngle) * distanceBetweenLetters).dp
            val offsetY = (sin(angle + rotationAngle) * distanceBetweenLetters).dp

            LetterButton(
                letter = letter,
                modifier = Modifier
                    .offset(x = offsetX, y = offsetY)
                    .onGloballyPositioned { layoutCoordinates ->
                        letterPositions[index] = layoutCoordinates.boundsInParent()
                    },

            )
        }
    }
}

@Composable
fun FoundWordsList(
    allWords: Set<String>,
    foundWords: Set<String>,
    modifier: Modifier = Modifier
) {
    val sortedWords = allWords.toList().sortedWith(compareBy( { it.length }, { it }))
    val placeholder = "⬜"

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sortedWords) { word ->
            val isFound = word in foundWords

            val textToShow = if (isFound) {
                word
            } else {
                placeholder.repeat(word.length)
            }

            Text(
                text = textToShow,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = if (isFound) Color.Green else Color.Gray,
                letterSpacing = if (isFound) 1.sp else 4.sp
            )
        }
    }
}

@Composable
fun LetterButton(
    letter: Char,
    modifier: Modifier = Modifier,
    buttonSize: Int = 60,
    fontSize: Int = 28
) {
    Box(
        modifier = modifier
            .size(buttonSize.dp)
            .clip(CircleShape)
            .background(Color.DarkGray.copy(alpha = 0.5F)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString(),
            color = Color.White,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ShuffleButton(
    onShuffleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.DarkGray.copy(alpha = 0.5F))
            .clickable { onShuffleClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "\uD83D\uDD04",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GameActions(
    isLevelCompleted: Boolean,
    onRestartClick: () -> Unit,
    onNextLevelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .size(width = 150.dp, height = 50.dp)
            .clip(CircleShape)
            .background(if (isLevelCompleted) Color.Green else Color.Red)
            .clickable {
                if (isLevelCompleted) onNextLevelClick() else onRestartClick() },

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isLevelCompleted) "Next" else "Restart",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}