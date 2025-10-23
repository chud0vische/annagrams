package io.github.chud0vische.annagrams.ui.components.organisms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

@Composable
fun rememberInputPanelState(
    onWordCollect: (String) -> Unit,
): InputPanelState {
    return remember {
        InputPanelState(onWordCollect)
    }
}

@Stable
class InputPanelState(
    private val onWordCollect: (String) -> Unit,
) {
    var letters by mutableStateOf<List<Char>>(emptyList())
        private set

    var typedWord by mutableStateOf("")
        private set

    val letterPositions = mutableStateOf<Map<Int, Rect>>(emptyMap())
    val selectedButtonIndices = mutableStateListOf<Int>()
    var currentDragPosition by mutableStateOf<Offset?>(null)

    fun onDragStart(startPosition: Offset) {
        typedWord = ""
        onWordCollect("")
        selectedButtonIndices.clear()
        currentDragPosition = startPosition
    }

    fun onDrag(dragPosition: Offset) {
        currentDragPosition = dragPosition

        val indexUnderFinger = letterPositions.value.entries
            .find { (_, rect) -> rect.contains(dragPosition) }
            ?.key

        if (indexUnderFinger != null && indexUnderFinger !in selectedButtonIndices) {
            selectedButtonIndices.add(indexUnderFinger)
            typedWord += letters[indexUnderFinger]
        }
    }

    fun onDragEnd() {
        if (selectedButtonIndices.isNotEmpty()) {
            onWordCollect(typedWord)
        }

        typedWord = ""
        selectedButtonIndices.clear()
        currentDragPosition = null
    }

    fun onDragCancel() {
        typedWord = ""
        onWordCollect("")
        selectedButtonIndices.clear()
        currentDragPosition = null
    }

    fun updateLetters(newLetters: List<Char>) {
        letters = newLetters.shuffled()
    }

    fun shuffleLetters() {
        letters = letters.shuffled()
    }
}