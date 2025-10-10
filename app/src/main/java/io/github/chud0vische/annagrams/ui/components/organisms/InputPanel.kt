package io.github.chud0vische.annagrams.ui.components.organisms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.components.atoms.InputButton
import io.github.chud0vische.annagrams.ui.components.atoms.ShuffleButton
import io.github.chud0vische.annagrams.ui.theme.Dimensions
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun InputPanel(
    letters: List<Char>,
    onWordCollect: (String) -> Unit,
    onLetterSelected: (Char) -> Unit,
    onShuffleClick: () -> Unit,
    modifier: Modifier = Modifier,
    distanceBetweenLetters: Int = Dimensions.distanceBetweenLetters,
    keyBoardSize: Dp = Dimensions.keyboardSize
) {
    val letterPositions = remember(letters) { mutableStateMapOf<Int, Rect>() }
    val selectedButtonIndices  = remember { mutableStateListOf<Int>() }

    var currentDragPosition by remember { mutableStateOf<Offset?>(Offset.Zero) }


    Box(
        modifier = modifier
            .size(keyBoardSize)
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
                        strokeWidth = Dimensions.lineStrokeWidth,
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

            InputButton(
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