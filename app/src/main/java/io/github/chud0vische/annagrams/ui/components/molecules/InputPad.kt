package io.github.chud0vische.annagrams.ui.components.molecules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun InputPad(
    letters: List<Char>,
    letterPositions: MutableState<Map<Int, Rect>>,
    selectedIndices: List<Int>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Layout(
            modifier = Modifier.fillMaxSize(),
            content = {
                letters.forEachIndexed { index, letter ->
                    InputButton(letter = letter, isSelected = index in selectedIndices)
                }
            }
        ) { measurables, constraints ->

            val safeAreaSize = min(constraints.maxWidth, constraints.maxHeight) * 0.8f
            val buttonSize = (safeAreaSize / 3).toInt()
            val buttonConstraints = Constraints.fixed(buttonSize, buttonSize)

            val placeables = measurables.map { it.measure(buttonConstraints) }

            val radius = (safeAreaSize / 2 - buttonSize / 2).toInt()

            layout(constraints.maxWidth, constraints.maxHeight) {
                val angleStep = 2 * Math.PI / placeables.size
                val newPositions = mutableMapOf<Int, Rect>()

                val expansionPx = 24.dp.toPx()

                placeables.forEachIndexed { index, placeable ->
                    val angle = index * angleStep - Math.PI / 2
                    val x = (radius * cos(angle)).toInt()
                    val y = (radius * sin(angle)).toInt()

                    val buttonRect = Rect(
                        left = (constraints.maxWidth / 2 + x - placeable.width / 2).toFloat(),
                        top = (constraints.maxHeight / 2 + y - placeable.height / 2).toFloat(),
                        right = (constraints.maxWidth / 2 + x + placeable.width / 2).toFloat(),
                        bottom = (constraints.maxHeight / 2 + y + placeable.height / 2).toFloat()
                    )

                    val expandedRect = buttonRect.copy(
                        left = buttonRect.left - expansionPx,
                        top = buttonRect.top - expansionPx,
                        right = buttonRect.right + expansionPx,
                        bottom = buttonRect.bottom + expansionPx
                    )

                    newPositions[index] = expandedRect

                    placeable.placeRelative(
                        x = buttonRect.left.toInt(),
                        y = buttonRect.top.toInt()
                    )
                }

                letterPositions.value = newPositions
            }
        }
    }
}