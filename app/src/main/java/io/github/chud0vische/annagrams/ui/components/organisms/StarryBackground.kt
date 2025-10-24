package io.github.chud0vische.annagrams.ui.components.organisms

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.text.color
import io.github.chud0vische.annagrams.R
import io.github.chud0vische.annagrams.data.model.Star
import io.github.chud0vische.annagrams.data.model.StarState
import io.github.chud0vische.annagrams.ui.theme.BGStarBonusRevealedColor
import io.github.chud0vische.annagrams.ui.theme.BGStarRevealedColor
import kotlin.random.Random

private data class StarVisuals(
    val position: Offset,
    val radius: Float
)

@Composable
fun StarryBackground(stars: List<Star>) {
    val defaultStarColor = Color.White.copy(alpha = 0.8f)
    val revealedStarColor = BGStarRevealedColor
    val bonusStarColor = BGStarBonusRevealedColor

    val starVisuals = remember(stars.size) {
        List(stars.size) {
            StarVisuals(
                position = Offset(Random.nextFloat(), Random.nextFloat()),
                radius = (Random.nextFloat() * 0.3f + 0.75f) * 15f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "star_animations")

    val revealedStarAlpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "revealed_star_alpha_pulse"
    )

    val twinklingAlphas = stars.map { star ->
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 0.8f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500 + star.id * 100),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(
                    offsetMillis = star.id * 150,
                    offsetType = StartOffsetType.Delay
                )
            ),
            label = "twinkle_alpha_${star.id}"
        )
    }

    val bonusStarAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bonus_star_alpha_pulse"
    )

    val starVector = ImageVector.vectorResource(id = R.drawable.star_bg_small)
    val starPainter = rememberVectorPainter(image = starVector)

    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(
                vertical = 100.dp,
                horizontal = 50.dp
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.4f)
        ) {
            stars.forEachIndexed { index, star ->
                val visuals = starVisuals[index]

                val (finalColor, finalAlpha) = when (star.state) {
                    StarState.REVEALED -> revealedStarColor to revealedStarAlpha
                    StarState.BONUS_REVEALED -> bonusStarColor to bonusStarAlpha
                    StarState.DEFAULT -> defaultStarColor to twinklingAlphas[index].value
                }

                val radiusInPx = with(density) { visuals.radius.dp.toPx() }

                val position = Offset(
                    visuals.position.x * size.width,
                    visuals.position.y * size.height
                )

                drawStarVector(
                    painter = starPainter,
                    position = position,
                    size = radiusInPx,
                    alpha = finalAlpha,
                    color = finalColor
                )
            }
        }
    }
}

private fun DrawScope.drawStarVector(
    painter: Painter,
    position: Offset,
    size: Float,
    alpha: Float,
    color: Color
) {
    val glowSize = size * 1f
    val glowAlpha = alpha * 0.2f

    val paint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        maskFilter = BlurMaskFilter(glowSize * 0.4f, BlurMaskFilter.Blur.NORMAL)
    }

    withTransform({
        val scale = size / painter.intrinsicSize.width
        translate(left = position.x, top = position.y)
        scale(scale, scale)
        translate(left = -painter.intrinsicSize.width / 2, top = -painter.intrinsicSize.height / 2)
    }) {
        drawIntoCanvas { canvas ->
            val localCenter = Offset(painter.intrinsicSize.width / 2, painter.intrinsicSize.height / 2)
            val diamondRadius = (painter.intrinsicSize.width / 2) * 1.0f

            val diamondPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(localCenter.x, localCenter.y - diamondRadius)
                lineTo(localCenter.x + diamondRadius, localCenter.y)
                lineTo(localCenter.x, localCenter.y + diamondRadius)
                lineTo(localCenter.x - diamondRadius, localCenter.y)
                close()
            }

            canvas.nativeCanvas.drawPath(
                diamondPath.asAndroidPath(),
                paint.apply {
                    this.color = color.copy(alpha = glowAlpha).toArgb()
                }
            )
        }

        with(painter) {
            draw(
                size = intrinsicSize,
                alpha = alpha,
                colorFilter = ColorFilter.tint(color)
            )
        }
    }
}