package io.github.chud0vische.annagrams.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.chud0vische.annagrams.R
import io.github.chud0vische.annagrams.ui.icons.AppIcons
import io.github.chud0vische.annagrams.ui.theme.AppDimensions
import io.github.chud0vische.annagrams.ui.theme.ButtonBackgroundColor
import io.github.chud0vische.annagrams.ui.theme.ButtonBorderColor

@Composable
fun StarsCount(modifier: Modifier = Modifier, count: Int = 0) {
    Row(
        modifier = modifier
            .border(
                AppDimensions.buttonBorderSize,
                ButtonBorderColor,
                RoundedCornerShape(AppDimensions.buttonBorderRadius)
            )
            .background(
                ButtonBackgroundColor,
                RoundedCornerShape(AppDimensions.buttonBorderRadius)
            )
            .height(48.dp)
            .padding(horizontal = AppDimensions.paddingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.size(32.dp)
        ){
            AppIcons.Star(
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = count.toString(),
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium,
        )
    }
}