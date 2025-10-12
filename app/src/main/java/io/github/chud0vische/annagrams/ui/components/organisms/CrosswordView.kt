package io.github.chud0vische.annagrams.ui.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.chud0vische.annagrams.data.model.Crossword
import io.github.chud0vische.annagrams.ui.components.atoms.CrosswordCellView

@Composable
fun CrosswordView(
    crossword: Crossword,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        crossword.grid.forEach { row -> // row — это список CrosswordCell

            Row {
                row.forEach { cell -> // cell — это CrosswordCell

                    CrosswordCellView(
                        cell = cell,
                        // Добавляем отступ ко всем сторонам ячейки
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }
    }
}