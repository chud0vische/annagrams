package io.github.chud0vische.annagrams.ui.components.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.chud0vische.annagrams.data.model.CrosswordCell
import io.github.chud0vische.annagrams.data.model.CrosswordCellType
import io.github.chud0vische.annagrams.ui.theme.Dimensions
import androidx.compose.ui.unit.sp

@Composable
fun CrosswordCellView(cell: CrosswordCell, size: Dp, modifier: Modifier = Modifier) {

    val isCharVisible = cell.type == CrosswordCellType.HINTED || cell.type == CrosswordCellType.REVEALED
    val isWordCell = cell.type != CrosswordCellType.EMPTY

    val backgroundColor = when (cell.type) {
        CrosswordCellType.EMPTY -> Color.Transparent
        CrosswordCellType.HIDDEN -> Color(0xFFE0E0E0)
        CrosswordCellType.HINTED -> MaterialTheme.colorScheme.tertiaryContainer
        CrosswordCellType.REVEALED -> MaterialTheme.colorScheme.primaryContainer
    }

    val borderColor = if (isWordCell) MaterialTheme.colorScheme.primary else Color.Transparent

    val textColor = when (cell.type) {
        CrosswordCellType.HINTED -> MaterialTheme.colorScheme.onTertiaryContainer
        CrosswordCellType.REVEALED -> MaterialTheme.colorScheme.onPrimaryContainer

        else -> Color.Transparent
    }

    val fontSize = (size.value / 2.2f).sp

    Box(
        modifier = modifier
            .size(size)
            .background(backgroundColor, RoundedCornerShape(4.dp))
            .border(
                Dimensions.letterBoxStrokeWidth,
                borderColor,
                RoundedCornerShape(4.dp)
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