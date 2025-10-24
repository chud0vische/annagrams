package io.github.chud0vische.annagrams.ui.components.organisms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.ui.components.atoms.ShuffleButton
import io.github.chud0vische.annagrams.ui.components.molecules.InputPad
import io.github.chud0vische.annagrams.ui.theme.Dimensions

@Composable
fun InputPanel(
    modifier: Modifier = Modifier,
    inputLetters: List<Char>,
    onWordCollect: (String) -> Unit,
    keyBoardSize: Dp = 290.dp
) {
    val state = rememberInputPanelState(onWordCollect)

    LaunchedEffect(inputLetters) {
        state.updateLetters(inputLetters)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.typedWord.uppercase(),
            fontSize = Dimensions.mediumFont,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(top = 24.dp)
                .height(Dimensions.mediumFont.value.dp * 2)
        )

        Box(
            modifier = modifier
                .size(keyBoardSize)
                .pointerInput(state.letters) {
                    detectDragGestures(
                        onDragStart = { offset -> state.onDragStart(offset) },
                        onDragEnd = { state.onDragEnd() },
                        onDragCancel = { state.onDragCancel() },
                        onDrag = { change, _ -> state.onDrag(change.position) }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            InputPad(
                state.letters,
                state.letterPositions,
                state.selectedButtonIndices,
                state.currentDragPosition,
                shuffleButton = {
                    ShuffleButton(
                        onClick = { state.shuffleLetters() }
                    )
                }
            )

        }
    }

}