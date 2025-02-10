package me.androidbox.spendless.onboarding.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.OnSurfaceVariant
import me.androidbox.spendless.core.presentation.SurfaceContainer

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            /** Summary box */
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = SurfaceContainer, RoundedCornerShape(16.dp)),
                elevation = 2.dp
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "$10,382.45",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.W600,
                            color = OnSurface,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "spend this month",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            color = OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            /** Expense format */
            Text(
                text = "Expenses format",
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = OnSurfaceVariant
            )

            var selectedIndex by remember {
                mutableStateOf(0)
            }

            val items = listOf("5 min", "15 min", "30 min", "1 hour")

            val animatedOffset by animateDpAsState(
                targetValue = 0.dp, // This should be the selected text position
                animationSpec = tween(durationMillis = 500)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = Color(0xff8138FF).copy(0.08f), RoundedCornerShape(12.dp))
                    .padding(4.dp),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(16.dp)
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    items.forEachIndexed { index, item ->
                        Text(
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            text = item,
                            color = if (selectedIndex == index) Color.Black else Color.DarkGray,
                            modifier = Modifier
                                .clickable { selectedIndex = index }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .width(100.dp) // Should be the same width as the text items
                        .height(48.dp)
                        .background(color = Color.White, RoundedCornerShape(12.dp))
                        .offset(x = animatedOffset, y = 0.dp),
                )
            }

            Text(
                text = "Currency",
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = OnSurfaceVariant
            )

            Text(
                text = "Decimal separator",
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = OnSurfaceVariant
            )

            Text(
                text = "Thousands separator",
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = OnSurfaceVariant
            )
        }
    }
}