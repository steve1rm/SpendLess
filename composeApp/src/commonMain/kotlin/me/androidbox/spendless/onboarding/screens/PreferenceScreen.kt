@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.spendless.onboarding.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.OnSurfaceVariant
import me.androidbox.spendless.core.presentation.Primary
import me.androidbox.spendless.core.presentation.SurfaceContainer
import me.androidbox.spendless.core.presentation.components.GenericDropDownItem
import me.androidbox.spendless.core.presentation.components.GenericDropDownMenu
import me.androidbox.spendless.onboarding.screens.components.ButtonPanel

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.background(color = Color.Green),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Set SpendLess\nto your preferences",
                    fontWeight = FontWeight.W600,
                    fontSize = 28.sp,
                    color = OnSurface,
                    lineHeight = 32.sp
                )

                Text(
                    text = "You can change it at anytime in the settings",
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp,
                    color = OnSurfaceVariant
                )

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
                            .fillMaxWidth()
                            .background(color = SurfaceContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                        ) {
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
                    fontWeight = FontWeight.W500,
                    color = OnSurface
                )

                val itemsExpenses = listOf("-$10", "($10)")

                ButtonPanel(itemsExpenses) { item ->
                    println(item)
                }

                var shouldShowDropDown by remember {
                    mutableStateOf(false)
                }

                val currencyList = Currency.entries.toList() // "US Dollar (USD)", "Euro (EUR)", "British Pounds Sterling (GBP)", "Japanese Yen (JPY)", "Swiss Franc (CHF)", "Canadian Dollar (CAD)", "Australian Dollar (AUD)", "Chinese Yuan Renminbi (CNY)", "Indian Rupee (INR)", "South African Rand (ZAR)", "Thai Baht (THB)")
                var selectedCurrency by remember {
                    mutableStateOf(currencyList.first())
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Currency",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500,
                            color = OnSurface
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(48.dp)
                                .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                .padding(start = 12.dp, end = 10.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource(),
                                    onClick = {
                                        shouldShowDropDown = !shouldShowDropDown
                                    }
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "${selectedCurrency.symbol} ${selectedCurrency.title}"
                            )

                            Icon(
                                imageVector = if (shouldShowDropDown) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "open close dropdown"
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    if(shouldShowDropDown) {
                        GenericDropDownMenu(
                            dropDownMenuItems = currencyList,
                            onDismissed = {
                                shouldShowDropDown = false
                            },
                            onMenuItemClicked = { item, index ->
                                selectedCurrency = item
                            },
                            itemContent = { currency ->
                                GenericDropDownItem(
                                    text = currency.title,
                                    isSelected = false
                                )
                            }
                        )
                    }
                }

                Text(
                    text = "Decimal separator",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    color = OnSurface
                )
                val itemsDecimalSeparator = listOf("1.00", "1,00")

                ButtonPanel(itemsDecimalSeparator) { item ->
                    println(item)
                }

                Text(
                    text = "Thousands separator",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    color = OnSurface
                )

                val itemsThousandsSeparator = listOf("1.000", "1,000", "1 000")

                ButtonPanel(itemsThousandsSeparator) { item ->
                    println(item)
                }

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
