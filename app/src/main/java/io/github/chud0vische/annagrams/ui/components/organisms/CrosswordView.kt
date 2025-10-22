package io.github.chud0vische.annagrams.ui.components.organisms

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.data.model.Crossword
import io.github.chud0vische.annagrams.ui.components.atoms.CrosswordCellView
import io.github.chud0vische.annagrams.ui.theme.AppDimensions

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CrosswordView(
    crossword: Crossword,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        if (crossword.width > 0) {
            val cellSize = maxWidth / crossword.width
            val finalCellSize = cellSize - (AppDimensions.cellMargin * 2)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                crossword.grid.forEach { row ->
                    Row {
                        row.forEach { cell ->
                            CrosswordCellView(
                                cell = cell,
                                size = finalCellSize,
                                modifier = Modifier
                                    .padding(AppDimensions.cellMargin)
                            )
                        }
                    }
                }
            }
        }
    }
}