@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.spendless.dashboard.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.toLocalDateTime
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.OnSecondaryContainer
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.Primary
import me.androidbox.spendless.core.presentation.PrimaryFixed
import me.androidbox.spendless.core.presentation.SecondaryContainer
import me.androidbox.spendless.core.presentation.SecondaryFixed
import me.androidbox.spendless.dashboard.DashboardAction
import me.androidbox.spendless.dashboard.DashboardState
import me.androidbox.spendless.dashboard.presentation.screens.components.TransactionsListItems
import me.androidbox.spendless.formatMoney
import me.androidbox.spendless.onboarding.screens.PreferenceState
import me.androidbox.spendless.onboarding.screens.components.PopularItem
import me.androidbox.spendless.transactions.data.AllTransactions
import me.androidbox.spendless.transactions.data.Transaction
import me.androidbox.spendless.transactions.screens.CreateTransactionContent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.cash
import spendless.composeapp.generated.resources.settings

@Composable
fun DashboardScreen(
    shouldNavigateOnWidget: Boolean,
    modifier: Modifier = Modifier,
    dashboardState: DashboardState,
    dashboardAction: (action: DashboardAction) -> Unit,
) {

    /** TODO use disposable effect */
    LaunchedEffect(shouldNavigateOnWidget) {
        if(shouldNavigateOnWidget) {
            dashboardAction(DashboardAction.OpenNewTransaction(shouldOpen = true))
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    Brush.radialGradient(
                        colors = listOf(Primary, OnPrimaryFixed),
                        center = Offset(x = 400.dp.value, y = 400.dp.value),
                        radius = 1000f
                    )
                )
            },
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                title = {
                    Text(
                        text = dashboardState.username,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = OnPrimary
                    )
                },
                actions = {
                    Box(
                        modifier = modifier
                            .size(48.dp)
                            .background(color = Color.White.copy(0.2f), RoundedCornerShape(16.dp))
                            .clickable(
                                onClick = {
                                    dashboardAction(DashboardAction.OpenSettings)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = vectorResource(resource = Res.drawable.settings),
                            contentDescription = "navigate to settings",
                            tint = OnPrimary.copy(0.8f)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DashboardHeader(
                    modifier = Modifier.weight(1f),
                    largestTransaction = dashboardState.largestTransaction,
                    totalPreviousSpent = dashboardState.totalPreviousSpent,
                    popularTransaction = dashboardState.popularTransaction,
                    preferenceState = dashboardState.preferenceState,
                    hasPopularCategory = dashboardState.listOfTransactions.isNotEmpty(),
                    totalAmount = dashboardState.totalTransactionAmount)

                DashboardTransactions(
                    modifier = Modifier.weight(2f),
                    listOfTransactions = dashboardState.listOfTransactions,
                    preferenceState = dashboardState.preferenceState ,
                    onShowAllClicked = {
                        dashboardAction(DashboardAction.OnShowAllClicked)
                    })
            }

            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            val coroutineScope = rememberCoroutineScope()

            if(dashboardState.showTransactionBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier
                        .fillMaxHeight()
                        .windowInsetsPadding(WindowInsets.statusBars),
                    sheetState = sheetState,
                    onDismissRequest = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    },
                    properties = ModalBottomSheetProperties(
                        shouldDismissOnBackPress = false
                    ),
                    dragHandle = null,
                    content = {
                        CreateTransactionContent(
                            state = dashboardState,
                            action = { action ->
                                dashboardAction(action)
                            },
                            openTransaction = { shouldOpen ->
                                dashboardAction(DashboardAction.OpenNewTransaction(shouldOpen))
                            }
                        )
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dashboardAction(DashboardAction.OpenNewTransaction(shouldOpen = true))
                },
                containerColor = SecondaryContainer,
                content = {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = "Add new transaction",
                        tint = OnSecondaryContainer
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
fun DashboardHeader(
    modifier: Modifier = Modifier,
    largestTransaction: Transaction,
    popularTransaction: Transaction,
    totalPreviousSpent: Double,
    preferenceState: PreferenceState,
    hasPopularCategory: Boolean,
    totalAmount: Double
) {

    Column(modifier = modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = totalAmount.formatMoney(
                currency = preferenceState.currency,
                expensesFormat = preferenceState.expensesFormat,
                thousandsSeparator = preferenceState.thousandsSeparator,
                decimalSeparator = preferenceState.decimalSeparator
            ),
            fontSize = 46.sp,
            fontWeight = FontWeight.W600,
            color = Color.White
        )

        Text(
            text = "Account Balance",
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            color = OnPrimary
        )
    }

    if(hasPopularCategory) {
        PopularItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            popularCategory = popularTransaction
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.BottomCenter)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LargestTransaction(
            modifier = Modifier.weight(1.6f),
            largestTransaction = largestTransaction,
            preferenceState = preferenceState,
            hasTransactions = largestTransaction.name.isNotEmpty()
        )

        PreviousTransaction(
            modifier = Modifier.weight(1f),
            totalPreviousSpent = totalPreviousSpent,
            preferenceState = preferenceState
        )
    }
}

@Composable
fun DashboardTransactions(
    modifier: Modifier = Modifier,
    listOfTransactions: List<AllTransactions>,
    preferenceState: PreferenceState,
    onShowAllClicked: () -> Unit
) {
    if(listOfTransactions.isNotEmpty()) {
        /** Show transactions here */
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Background, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Latest Transactions",
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp,
                    color = OnSurface
                )

                Text(
                    modifier = Modifier.clickable(
                        onClick = onShowAllClicked
                    ),
                    text = "Show all",
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    color = Primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TransactionsListItems(
                listOfTransactions = listOfTransactions,
                preferenceState = preferenceState)
        }
    }
    else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Background, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(96.dp),
                painter = painterResource(resource = Res.drawable.cash),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                text = "No transactions to show",
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun LargestTransaction(
    modifier: Modifier = Modifier,
    largestTransaction: Transaction,
    preferenceState: PreferenceState,
    hasTransactions: Boolean = false
) {
    if(hasTransactions) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(color = PrimaryFixed, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = largestTransaction.name,
                    maxLines = 2,
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp,
                    color = OnSurface
                )

                Text(
                    textAlign = TextAlign.Center,
                    text = largestTransaction.amount.toDouble().formatMoney(
                        currency = preferenceState.currency,
                        expensesFormat = preferenceState.expensesFormat,
                        thousandsSeparator = preferenceState.thousandsSeparator,
                        decimalSeparator = preferenceState.decimalSeparator
                    ),
                    maxLines = 2,
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp,
                    color = OnSurface
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Largest transaction",
                    maxLines = 2,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = OnSurface
                )

                Text(
                    textAlign = TextAlign.Center,
                    text = Instant.fromEpochMilliseconds(
                        largestTransaction.createAt
                    ).toLocalDateTime(TimeZone.currentSystemDefault()).date
                        .format(
                            LocalDate.Format {
                                dayOfMonth(Padding.NONE)
                                chars(" ")
                                monthName(MonthNames.ENGLISH_ABBREVIATED)
                                chars(", ")
                                year(Padding.NONE)
                            }
                        ),
                    maxLines = 2,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = OnSurface
                )
            }
        }
    }
    else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(color = PrimaryFixed, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Your largest transaction will appear here",
                maxLines = 2,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                color = OnSurface
            )
        }
    }
}

@Composable
fun PreviousTransaction(
    modifier: Modifier = Modifier,
    totalPreviousSpent: Double,
    preferenceState: PreferenceState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(color = SecondaryFixed, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = totalPreviousSpent.formatMoney(
                currency = preferenceState.currency,
                expensesFormat = preferenceState.expensesFormat,
                thousandsSeparator = preferenceState.thousandsSeparator,
                decimalSeparator = preferenceState.decimalSeparator
            ),
            maxLines = 2,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            color = OnSurface
        )

        Text(
            textAlign = TextAlign.Center,
            text = "Previous week",
            maxLines = 2,
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            color = OnSurface
        )
    }
}