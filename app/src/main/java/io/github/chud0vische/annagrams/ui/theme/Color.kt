package io.github.chud0vische.annagrams.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Dp.toFloat(): Float {
    return this.value
}

fun Dp.toInt(): Int {
    return this.value.toInt()
}

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val AppBackgroundColor = Color(0xFF1C1C1C)

val CellHiddenColor = Color(0xFF5D5D5D).copy(alpha = 0.6F)
val CellRevealedColor = Color(0xFFFFFFFF).copy(alpha = 0.01F)
val CellHintedColor = Color(0xFF5D5D5D).copy(alpha = 0.3F)
val CellBorderColor = Color(0xFF7E8695).copy(alpha = 0.3F)
val CellRevealedTextColor = Color(0xFF2C2C2C)
val CellHintedTextColor = Color(0xFFE7E7E7)
val CellRevealedInnerShadowColor = Color(0xFF6781DA).copy(alpha = 0.3F)
val CellRevealedShadowColor = Color(0xFF717FC1).copy(alpha = 0.1F)

val ButtonBackgroundColor = Color(0xFF272727)

val CircularIndicatorColor = Color(0xFF7E8CDD)


fun starGradient(height: Dp, width: Dp) = Brush.radialGradient(
    colors = listOf(Color(0xFFDABBE8), Color(0xFF3769D4)),
    center = Offset(height.toFloat() / 2F, width.toFloat() / 2F),
    radius = height.toFloat(),
)

val ButtonBorderColor = Brush.linearGradient(
    listOf(Color.White.copy(0.15F), Color(0xFF999999).copy(0.08F)),
    Offset( Float.POSITIVE_INFINITY, 0F),
    Offset(0F, Float.POSITIVE_INFINITY, )
)