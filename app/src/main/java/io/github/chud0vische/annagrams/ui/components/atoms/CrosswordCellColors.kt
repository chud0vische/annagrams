package io.github.chud0vische.annagrams.ui.components.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import io.github.chud0vische.annagrams.data.model.CrosswordCellType
import io.github.chud0vische.annagrams.ui.theme.CellHiddenBorderColor
import io.github.chud0vische.annagrams.ui.theme.CellRevealedBorderColor
import io.github.chud0vische.annagrams.ui.theme.CellHiddenColor
import io.github.chud0vische.annagrams.ui.theme.CellHintedColor
import io.github.chud0vische.annagrams.ui.theme.CellHintedTextColor
import io.github.chud0vische.annagrams.ui.theme.CellRevealedColor
import io.github.chud0vische.annagrams.ui.theme.CellRevealedInnerShadowColor
import io.github.chud0vische.annagrams.ui.theme.CellRevealedShadowColor
import io.github.chud0vische.annagrams.ui.theme.CellRevealedTextColor

@Immutable
class CrosswordCellColors internal constructor(
    private val emptyBackgroundColor: Color,
    private val hiddenBackgroundColor: Color,
    private val hintedBackgroundColor: Color,
    private val revealedBackgroundColor: Color,
    private val revealedBorderColor: Color,
    private val hiddenBorderColor: Color,
    private val emptyBorderColor: Color,
    private val hintedContentColor: Color,
    private val revealedContentColor: Color,
    private val emptyContentColor: Color,
) {
    @Composable
    internal fun backgroundColor(type: CrosswordCellType): State<Color> {
        val color = when (type) {
            CrosswordCellType.EMPTY -> emptyBackgroundColor
            CrosswordCellType.HIDDEN -> hiddenBackgroundColor
            CrosswordCellType.HINTED -> hintedBackgroundColor
            CrosswordCellType.REVEALED -> revealedBackgroundColor
        }
        return rememberUpdatedState(color)
    }

    @Composable
    internal fun borderColor(type: CrosswordCellType): State<Color> {
        val color = when (type) {
            CrosswordCellType.REVEALED -> revealedBorderColor
            CrosswordCellType.HIDDEN -> hiddenBorderColor
            else -> emptyBorderColor
        }
        return rememberUpdatedState(color)
    }

    @Composable
    internal fun contentColor(type: CrosswordCellType): State<Color> {
        val color = when (type) {
            CrosswordCellType.HINTED -> hintedContentColor
            CrosswordCellType.REVEALED -> revealedContentColor
            else -> emptyContentColor
        }
        return rememberUpdatedState(color)
    }

    @Composable
    internal fun innerShadowColor(type: CrosswordCellType): State<Color> {
        val color = when (type) {
            CrosswordCellType.REVEALED -> CellRevealedInnerShadowColor
            else -> Color.Transparent
        }
        return rememberUpdatedState(color)
    }

    @Composable
    internal fun shadowColor(type: CrosswordCellType): State<Color> {
        val color = when (type) {
            CrosswordCellType.REVEALED -> CellRevealedShadowColor
            else -> Color.Transparent
        }
        return rememberUpdatedState(color)
    }
}

@Immutable
object CrosswordCellDefaults {

    @Composable
    fun colors(
        emptyBackgroundColor: Color = Color.Transparent,
        hiddenBackgroundColor: Color = CellHiddenColor,
        hintedBackgroundColor: Color = CellHintedColor,
        revealedBackgroundColor: Color = CellRevealedColor,
        revealedBorderColor: Color = CellRevealedBorderColor,
        emptyBorderColor: Color = Color.Transparent,
        hiddenBorderColor: Color = CellHiddenBorderColor,
        hintedContentColor: Color = CellHintedTextColor,
        revealedContentColor: Color = CellRevealedTextColor,
        emptyContentColor: Color = Color.Transparent,
    ): CrosswordCellColors = CrosswordCellColors(
        emptyBackgroundColor = emptyBackgroundColor,
        hiddenBackgroundColor = hiddenBackgroundColor,
        hintedBackgroundColor = hintedBackgroundColor,
        revealedBackgroundColor = revealedBackgroundColor,
        revealedBorderColor = revealedBorderColor,
        hiddenBorderColor = hiddenBorderColor,
        emptyBorderColor = emptyBorderColor,
        hintedContentColor = hintedContentColor,
        revealedContentColor = revealedContentColor,
        emptyContentColor = emptyContentColor,
    )
}