package io.github.chud0vische.annagrams.ui.components.molecules

import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.components.atoms.InputButton
import io.github.chud0vische.annagrams.ui.theme.Dimensions.distanceBetweenLetters
import kotlin.collections.set
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun InputPad(letters: List<Char>, letterPositions: SnapshotStateMap<Int, Rect>) {
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