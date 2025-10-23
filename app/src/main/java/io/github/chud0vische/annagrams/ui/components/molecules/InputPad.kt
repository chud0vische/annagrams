package io.github.chud0vische.annagrams.ui.components.molecules

import androidx.compose.animation.core.copy
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.theme.ButtonBackgroundColor
import io.github.chud0vische.annagrams.ui.theme.ButtonBorderColor
import io.github.chud0vische.annagrams.ui.theme.InputButtonBackgroundColor
import io.github.chud0vische.annagrams.ui.theme.InputPadBackgroundColor
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun InputPad(
    letters: List<Char>,
    letterPositions: MutableState<Map<Int, Rect>>,
    selectedIndices: List<Int>,
    dragPosition: Offset?,
    modifier: Modifier = Modifier,
    shuffleButton: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(InputPadBackgroundColor, CircleShape)
                    .border(
                        1.dp,
                        brush = ButtonBorderColor,
                        shape = CircleShape
                    )
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                val lineColor = InputButtonBackgroundColor
                val lineStrokeWidth = 10f

                val positions = letterPositions.value
                val indices = selectedIndices

                if (indices.size > 1) {
                    for (i in 0 until indices.size - 1) {
                        val fromCenter = positions[indices[i]]?.center
                        val toCenter = positions[indices[i + 1]]?.center

                        if (fromCenter != null && toCenter != null) {
                            drawLine(
                                color = lineColor,
                                start = fromCenter,
                                end = toCenter,
                                strokeWidth = lineStrokeWidth,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }

                val lastButtonCenter = indices.lastOrNull()?.let { positions[it]?.center }
                if (lastButtonCenter != null && dragPosition != null) {
                    drawLine(
                        color = lineColor,
                        start = lastButtonCenter,
                        end = dragPosition,
                        strokeWidth = lineStrokeWidth,
                        cap = StrokeCap.Round
                    )
                }
            }

            shuffleButton()

            Layout(
                content = {
                    letters.forEachIndexed { index, letter ->
                        InputButton(letter = letter, isSelected = index in selectedIndices)
                    }
                }
            ) { measurables, constraints ->

                val safeAreaSize = min(constraints.maxWidth, constraints.maxHeight) * 1f
                val buttonSize = (safeAreaSize / 4).toInt()
                val buttonConstraints = Constraints.fixed(buttonSize, buttonSize)

                val letterPlaceables = measurables.map {
                    it.measure(buttonConstraints)
                }

                val padding = buttonSize / 6
                val radius = (safeAreaSize / 2 - buttonSize / 2 - padding).toInt()
                val expansionPx = buttonSize * 0.1f

                layout(constraints.maxWidth, constraints.maxHeight) {
                    val angleStep = 2 * Math.PI / letterPlaceables.size
                    val newPositions = mutableMapOf<Int, Rect>()

                    letterPlaceables.forEachIndexed { index, placeable ->
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
}