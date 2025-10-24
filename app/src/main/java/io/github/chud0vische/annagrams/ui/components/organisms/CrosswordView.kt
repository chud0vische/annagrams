package io.github.chud0vische.annagrams.ui.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import io.github.chud0vische.annagrams.data.model.Crossword
import io.github.chud0vische.annagrams.ui.components.atoms.CrosswordCellView
import io.github.chud0vische.annagrams.ui.theme.AppDimensions
import kotlin.math.floor
import kotlin.math.min

@Composable
fun CrosswordView(
    crossword: Crossword,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints {
            if (crossword.width > 0 && crossword.height > 0) {
                val density = LocalDensity.current

                val marginPx = with(density) { AppDimensions.cellMargin.toPx() }
                val maxWidthPx = with(density) { maxWidth.toPx() }
                val maxHeightPx = with(density) { maxHeight.toPx() }

                val totalHorizontalMarginPx = marginPx * 2 * crossword.width
                val totalVerticalMarginPx = marginPx * 2 * crossword.height

                val availableWidthPx = maxWidthPx - totalHorizontalMarginPx - 5
                val availableHeightPx = maxHeightPx - totalVerticalMarginPx - 5

                val finalCellSizePx = floor(
                    min(
                        availableWidthPx / crossword.width,
                        availableHeightPx / crossword.height
                    )
                )

                val finalCellSizeDp = with(density) { finalCellSizePx.toDp() }

                if (finalCellSizeDp > 0.dp) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        crossword.grid.forEach { row ->
                            Row {
                                row.forEach { cell ->
                                    CrosswordCellView(
                                        cell = cell,
                                        size = finalCellSizeDp,
                                        hazeState = hazeState,
                                        modifier = Modifier
                                            .padding(AppDimensions.cellMargin)
                                            .requiredSize(finalCellSizeDp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}