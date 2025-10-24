package io.github.chud0vische.annagrams.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.chud0vische.annagrams.data.model.CrosswordCell
import io.github.chud0vische.annagrams.data.model.CrosswordCellType
import io.github.chud0vische.annagrams.ui.theme.AppDimensions
import io.github.chud0vische.annagrams.ui.theme.starGradient
import androidx.compose.ui.graphics.shadow.Shadow
import io.github.chud0vische.annagrams.ui.theme.AppFontFamily
import io.github.chud0vische.annagrams.ui.theme.AppTypography

@Composable
fun CrosswordCellView(
    cell: CrosswordCell,
    size: Dp,
    modifier: Modifier = Modifier,
    colors: CrosswordCellColors = CrosswordCellDefaults.colors()
) {
    val backgroundColor = colors.backgroundColor(cell.type).value
    val borderColor = colors.borderColor(cell.type).value
    val textColor = colors.contentColor(cell.type).value
    val innerShadowColor = colors.innerShadowColor(cell.type).value
    val shadowColor = colors.shadowColor(cell.type).value

    val fontSize = (size.value / 1.8F).sp
    val cornerRadius = (size.value / 6F).dp
    val cellShape = RoundedCornerShape(cornerRadius)

    val cellTextStyle = TextStyle(
        brush = if (cell.type == CrosswordCellType.REVEALED) starGradient(size, size) else null,
        fontWeight = FontWeight.Bold
    )


    Box(
        modifier = modifier
            .requiredSize(size)
            .dropShadow(
                cellShape,
                Shadow(
                    radius = cornerRadius,
                    color = shadowColor
                )
            )
            .background(
                backgroundColor,
                cellShape
            )
            .innerShadow(
                cellShape,
                Shadow(
                    radius = cornerRadius,
                    color = innerShadowColor
                )
            )
            .border(
                AppDimensions.cellBorderSize,
                borderColor,
                cellShape
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (cell.type == CrosswordCellType.HINTED || cell.type == CrosswordCellType.REVEALED) {
            Text(
                text = cell.char.toString().uppercase(),
                color = textColor,
                fontFamily = AppFontFamily,
                style = cellTextStyle,
                fontSize = fontSize,
            )
        }
    }
}