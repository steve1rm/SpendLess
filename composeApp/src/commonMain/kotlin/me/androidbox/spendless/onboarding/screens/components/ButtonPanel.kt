package me.androidbox.spendless.onboarding.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.OnSurface

@Composable
fun ButtonPanel(textItems: List<String>, onItemClicked: (textItem: String) -> Unit) {

    var selectedIndex by remember {
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = Color(0xff8138FF).copy(0.08f), RoundedCornerShape(12.dp))
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            textItems.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .background(color = if(selectedIndex == index) Color.White else Color.Transparent, RoundedCornerShape(12.dp))
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource(),
                            onClick = {
                                selectedIndex = index
                                onItemClicked(textItems[selectedIndex])
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                        text = item,
                        color = if (selectedIndex == index) OnSurface else OnPrimaryFixed.copy(alpha = 0.7f),
                    )
                }
            }
        }
    }
}