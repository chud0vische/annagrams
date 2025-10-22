package io.github.chud0vische.annagrams.ui.icons

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import io.github.chud0vische.annagrams.R

object AppIcons {
    @Composable
    fun Star(modifier: Modifier = Modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_star_small),
            contentDescription = "Star Icon",
            modifier = modifier,
        )
    }
}