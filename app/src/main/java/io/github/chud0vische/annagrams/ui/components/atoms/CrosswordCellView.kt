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
import io.github.chud0vische.annagrams.data.model.CrosswordCell
import io.github.chud0vische.annagrams.data.model.CrosswordCellType
import io.github.chud0vische.annagrams.ui.theme.Dimensions

@Composable
fun CrosswordCellView(cell: CrosswordCell, modifier: Modifier = Modifier) {

    // Определяем, видима ли буква (для отображения текста)
    val isCharVisible = cell.type == CrosswordCellType.HINTED || cell.type == CrosswordCellType.REVEALED
    // Определяем, является ли ячейка частью слова (для стилей)
    val isWordCell = cell.type != CrosswordCellType.EMPTY

    // --- Определение стилей ---

    // 1. Цвет фона
    val backgroundColor = when (cell.type) {
        CrosswordCellType.EMPTY -> Color.Transparent // Пустой фон
        CrosswordCellType.HIDDEN -> Color(0xFFE0E0E0) // Светло-серый, ждет ввода (как обычная клетка)
        CrosswordCellType.HINTED -> MaterialTheme.colorScheme.tertiaryContainer // Например, оранжевый для подсказки
        CrosswordCellType.REVEALED -> MaterialTheme.colorScheme.primaryContainer // Голубой/Зеленый для отгаданного слова
    }

    // 2. Цвет границы
    val borderColor = if (isWordCell) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

    // 3. Цвет текста
    val textColor = when (cell.type) {
        CrosswordCellType.HINTED -> MaterialTheme.colorScheme.onTertiaryContainer
        CrosswordCellType.REVEALED -> MaterialTheme.colorScheme.onPrimaryContainer
        else -> Color.Transparent // Скрытый или пустой
    }

    Box(
        modifier = modifier
            .size(Dimensions.letterBoxSize)
            .background(backgroundColor, RoundedCornerShape(Dimensions.letterBoxCornerRadius))
            .border(
                Dimensions.letterBoxStrokeWidth,
                borderColor,
                RoundedCornerShape(Dimensions.letterBoxCornerRadius)
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isCharVisible) {
            Text(
                // Показываем букву в верхнем регистре
                text = cell.char.toString().uppercase(),
                color = textColor,
                fontSize = Dimensions.letterBoxFontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}