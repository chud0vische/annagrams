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
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.layout.boundsInParent

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = currentWord,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )

        LetterKeyboard(
            letters = viewModel.levelLetters,
            onWordCollect = { word ->
                viewModel.onWordCollected(word)
            }
        )
    }
}

@Composable
fun LetterKeyboard(
    letters: List<Char>,
    onWordCollect: (String) -> Unit,
    // TODO: Вынести все значения из LetterKeyboard
    distanceBetweenLetters: Int = 100,
    keyBoardSize: Int = 250
) {
    val letterPositions = remember { mutableStateMapOf<Char, Rect>() }
    val selectedLetters = remember { mutableStateListOf<Char>() }
    var currentDragPosition by remember { mutableStateOf<Offset?>(Offset.Zero) }


    Box(
        modifier = Modifier
            .size(keyBoardSize.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        selectedLetters.clear()
                        currentDragPosition = it
                    },
                    onDragEnd = {
                        if (selectedLetters.isNotEmpty()) {
                            onWordCollect(selectedLetters.joinToString(""))
                        }

                        selectedLetters.clear()
                        currentDragPosition = null
                    },
                    onDragCancel = {
                        selectedLetters.clear()
                        currentDragPosition = null
                    },
                    onDrag = { change, _ ->
                        val position = change.position
                        currentDragPosition = position
                        val letterUnderFinger = letterPositions.entries
                            .find { (_, rect) -> rect.contains(position) }
                            ?.key

                        if (letterUnderFinger != null && !selectedLetters.contains(letterUnderFinger)) {
                            selectedLetters.add(letterUnderFinger)
                        }
                    }
                )
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val points = selectedLetters
                .mapNotNull { letterPositions[it]?.center }
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
                        letterPositions[letter] = layoutCoordinates.boundsInParent()
                    },

            )
        }
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
            text = "Перемешать",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LetterButton(
    letter: Char,
    modifier: Modifier = Modifier,
    buttonSize: Int = 30,
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