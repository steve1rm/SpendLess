package me.androidbox.spendless.onboarding.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
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

            AnimatedChipSelector1()
        }
    }
}

@Composable
fun AnimatedChipSelector1() {

    val localDensity = LocalDensity.current
    val tabsList = listOf("5 min", "15 min", "30 min", "1 hour")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabWidths = remember { mutableStateListOf(-1, -1, -1, -1) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        TabRow(
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            selectedTabIndex = selectedTabIndex,
            contentColor = Color(0xff8138FF).copy(0.08f),
            indicator = { tabPositions ->
                if (tabWidths.isNotEmpty()) {  // only show Indicator after measurements are finished
                    Column(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .fillMaxHeight()
                            .requiredWidth( with(localDensity) { tabWidths[selectedTabIndex].toDp() } )
                            .padding(vertical = 8.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {}
                }
            },
            divider = {}
        ) {
            tabsList.forEachIndexed { tabIndex, tabName ->
                FilterChip(
                    modifier = Modifier
                        .wrapContentSize()
                        .zIndex(2f)
                        .onGloballyPositioned { layoutCoordinates ->
                            tabWidths[tabIndex] = layoutCoordinates.size.width
                        },
                    selected = false,
                    shape = RoundedCornerShape(12.dp),
                    border = null,
                    onClick = { selectedTabIndex = tabIndex },
                    label = {
                        Text(
                            text = tabName,
                            textAlign = TextAlign.Center,
                            color = if (selectedTabIndex == tabIndex) Color.Black else Color.DarkGray,
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun AnimatedChipSelector() {
    val localDensity = LocalDensity.current
    val tabList = listOf("5 min", "15 min", "30 min", "1 hour")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabWidths = remember { mutableStateListOf<Int>(-1, -1, -1, -1) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        TabRow(
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            selectedTabIndex = selectedTabIndex,
            contentColor = Color(0xff8138ff).copy(alpha = 0.08f),
            indicator = { tabPosition ->
                if(tabWidths.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPosition[selectedTabIndex])
                            .fillMaxHeight()
                            .requiredWidth(with(localDensity) {
                                tabWidths[selectedTabIndex].toDp()
                            })
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {}
                }
            },
            divider = {}
        ) {
            tabList.forEachIndexed { tabIndex, tabName ->
                tabList.forEachIndexed { tabIndex, tabName ->
                    FilterChip(
                        modifier = Modifier
                            .wrapContentSize()
                            .zIndex(2f)
                            .onGloballyPositioned { layoutCoordinates ->
                                tabWidths[tabIndex] = layoutCoordinates.size.width
                            },
                        selected = false,
                        shape = RoundedCornerShape(12.dp),
                        border = null,
                        onClick = {
                            selectedTabIndex = tabIndex
                        },
                        label = {
                            Text(
                                text = tabName,
                                textAlign = TextAlign.Center,
                                color = if (selectedTabIndex == tabIndex) Color.Black else Color.DarkGray
                            )
                        }
                    )
                }
            }
        }
    }
}
