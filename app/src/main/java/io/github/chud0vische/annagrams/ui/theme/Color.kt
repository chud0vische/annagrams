package io.github.chud0vische.annagrams.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val AppBackgroundColor = Color(0xFF1C1C1C)

val CellHiddenColor = Color(0xFF5D5D5D).copy(alpha = 0.8F)
val CellRevealedColor = Color(0xFFFFFFFF).copy(alpha = 0.8F)
val CellHintedColor = Color(0xFF5D5D5D).copy(alpha = 0.3F)
val CellBorderColor = Color(0xFF7E8695).copy(alpha = 0.3F)
val CellRevealedTextColor = Color(0xFF2C2C2C)
val CellHintedTextColor = Color(0xFFE7E7E7)
val CellRevealedInnerShadowColor = Color(0xFF6A74D8).copy(alpha = 0.2F)
val CellRevealedShadowColor = Color(0xFFEFF0F4).copy(alpha = 0.1F)

val ButtonBackgroundColor = Color(0xFF272727)

val ButtonBorderColor = Brush.linearGradient(
    listOf(Color.White.copy(0.15F), Color(0xFF999999).copy(0.08F)),
    Offset( Float.POSITIVE_INFINITY, 0F),
    Offset(0F, Float.POSITIVE_INFINITY, )
)