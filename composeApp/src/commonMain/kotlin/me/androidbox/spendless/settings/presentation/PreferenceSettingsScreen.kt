package me.androidbox.spendless.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.Primary
import me.androidbox.spendless.settings.presentation.components.SpendLessTheme

@Composable
fun PreferenceSettingsScreen(
    modifier: Modifier = Modifier,
    preferenceContent: @Composable () -> Unit,
    onBackClicked: () -> Unit
) {
    SpendLessTheme(
        modifier = modifier,
        toolBarTitle = "Preferences",
        onNavigationClicked = onBackClicked,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                preferenceContent()

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    onClick = {}
                ) {
                    Text(
                        text = "Start Tracking",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        color = OnPrimary
                    )
                }
            }
        }
    )
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
