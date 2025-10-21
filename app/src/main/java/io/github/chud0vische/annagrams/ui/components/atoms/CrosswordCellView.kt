package io.github.chud0vische.annagrams.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.chud0vische.annagrams.data.model.CrosswordCell
import io.github.chud0vische.annagrams.data.model.CrosswordCellType
import io.github.chud0vische.annagrams.ui.theme.AppDimensions
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.shadow.Shadow

@Composable
fun CrosswordCellView(
    cell: CrosswordCell,
    size: Dp,
    modifier: Modifier = Modifier,
    colors: CrosswordCellColors = CrosswordCellDefaults.colors()
) {
    val isCharVisible = cell.type == CrosswordCellType.HINTED || cell.type == CrosswordCellType.REVEALED

    val backgroundColor = colors.backgroundColor(cell.type).value
    val borderColor = colors.borderColor(cell.type).value
    val textColor = colors.contentColor(cell.type).value
    val innerShadowColor = colors.innerShadowColor(cell.type).value
    val shadowColor = colors.shadowColor(cell.type).value


    val fontSize = (size.value / 2.2f).sp
    val cornerRadius = (size.value / 5f).dp

    Box(
        modifier = modifier
            .size(size)
            .background(
                backgroundColor,
                RoundedCornerShape(cornerRadius)
            )
            .border(
                AppDimensions.cellBorderSize,
                borderColor,
                RoundedCornerShape(cornerRadius)
            )
            .innerShadow(
                RoundedCornerShape(cornerRadius),
                Shadow(
                    radius = cornerRadius,
                    color = innerShadowColor
                )
            )
            .dropShadow(
                RoundedCornerShape(cornerRadius),
                Shadow(
                    radius = cornerRadius,
                    color = shadowColor
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isCharVisible) {
            Text(
                text = cell.char.toString().uppercase(),
                color = textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}