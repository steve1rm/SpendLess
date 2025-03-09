@file:OptIn(ExperimentalFoundationApi::class)

package me.androidbox.spendless.dashboard.presentation.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.dashboard.AllTransactions
import me.androidbox.spendless.onboarding.screens.components.TransactionItem

@Composable
fun TransactionsListItems(
    modifier: Modifier = Modifier,
    listOfTransactions: List<AllTransactions>,
) {
    val currentDate = remember {
        Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = rememberLazyListState()
    ) {
        listOfTransactions.forEach { transactionHeader ->
            stickyHeader(
                content = {
                    val createdAt = Instant.fromEpochMilliseconds(transactionHeader.createdAt)
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date

                    val displayDate = when (createdAt) {
                        currentDate -> {
                            "Today"
                        }
                        currentDate.minus(DatePeriod(days = 1)) -> {
                            "Yesterday"
                        }
                        else -> {
                            createdAt.format(
                                LocalDate.Format {
                                    monthName(MonthNames.ENGLISH_FULL)
                                    chars(" ")
                                    dayOfMonth(Padding.NONE)
                                }
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth().background(color = Background),
                        text = displayDate
                    )
                }
            )

            items(
                items = transactionHeader.transactions,
                key = { transaction ->
                    transaction.id
                },
                itemContent = { transaction ->
                    TransactionItem(
                        transaction = transaction
                    )
                }
            )
        }
    }
}
