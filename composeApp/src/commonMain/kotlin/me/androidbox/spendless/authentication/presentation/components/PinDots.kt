package me.androidbox.spendless.authentication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.spendless.core.presentation.OnBackground
import me.androidbox.spendless.core.presentation.Primary

@Composable
fun PinDots(
    modifier: Modifier = Modifier,
    isFirstDotEnabled: Boolean,
    isSecondDotEnabled: Boolean,
    isThirdDotEnabled: Boolean,
    isFourthDotEnabled: Boolean,
    isFifthDotEnabled: Boolean) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp, Alignment.CenterHorizontally)
    ) {
        Box(
            modifier
                .size(18.dp)
                .background(color = if(isFirstDotEnabled) Primary else OnBackground.copy(alpha = 0.12f), CircleShape)
        )

        Box(
            modifier
                .size(18.dp)
                .background(color = if(isSecondDotEnabled) Primary else OnBackground.copy(alpha = 0.12f), CircleShape)
        )

        Box(
            modifier
                .size(18.dp)
                .background(color = if(isThirdDotEnabled) Primary else OnBackground.copy(alpha = 0.12f), CircleShape)
        )

        Box(
            modifier
                .size(18.dp)
                .background(color = if(isFourthDotEnabled) Primary else OnBackground.copy(alpha = 0.12f), CircleShape)
        )

        Box(
            modifier
                .size(18.dp)
                .background(color = if(isFifthDotEnabled) Primary else OnBackground.copy(alpha = 0.12f), CircleShape)
        )
    }
}