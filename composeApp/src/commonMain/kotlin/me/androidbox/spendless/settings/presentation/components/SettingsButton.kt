package me.androidbox.spendless.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.SurfaceContainerLow

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: String,
    onClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(all = 4.dp)
            .clickable(onClick = onClicked),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .size(40.dp)
            .background(color = SurfaceContainerLow, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center) {
            icon()
        }

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = OnSurface
        )
    }
}