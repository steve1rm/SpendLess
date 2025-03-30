@file:OptIn(ExperimentalFoundationApi::class)

package me.androidbox.spendless.dashboard.presentation.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.onboarding.screens.PreferenceState
import me.androidbox.spendless.onboarding.screens.components.TransactionItem
import me.androidbox.spendless.transactions.data.AllTransactions

private fun calculateDisplayDate(createdAtMillis: Long, today: LocalDate): String {
    val transactionDate = Instant.fromEpochMilliseconds(createdAtMillis)
        .toLocalDateTime(TimeZone.currentSystemDefault()).date
    val yesterday = today.minus(DatePeriod(days = 1))

    return when (transactionDate) {
        today -> "Today"
        yesterday -> "Yesterday"
        else -> {
            transactionDate.format(LocalDate.Format {
                monthName(MonthNames.ENGLISH_ABBREVIATED)
                char(' ')
                dayOfMonth()
            })
        }
    }
}
@Composable
fun TransactionsListItems(
    modifier: Modifier = Modifier,
    preferenceState: PreferenceState,
    listOfTransactions: List<AllTransactions>
) {
    val currentDate = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    val groupedTransactions = remember(listOfTransactions, currentDate) {
        listOfTransactions
            .flatMap { header ->
                header.transactions.map { transaction -> Pair(header.createdAt, transaction) }
            }
            .groupBy { (createdAt, _) ->
                calculateDisplayDate(createdAt, currentDate)
            }
    }

    val sortedGroupKeys = remember(groupedTransactions) {
        groupedTransactions.keys.sortedWith(compareBy { dateString ->
            when (dateString) {
                "Today" -> 0 // Today first
                "Yesterday" -> 1 // Yesterday second
                else -> 2 // Other dates
            }
        })
    }


    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        state = rememberLazyListState()
    ) {
        sortedGroupKeys.forEach { displayDate ->
            val transactionsInGroup = groupedTransactions[displayDate] ?: emptyList()

            if (transactionsInGroup.isNotEmpty()) {
                stickyHeader(key = displayDate) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Background)
                            .padding(vertical = 8.dp),
                        text = displayDate
                    )
                }

                items(
                    items = transactionsInGroup.map { it.second },
                    key = { transaction ->
                        transaction.id
                    },
                    itemContent = { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            preferenceState = preferenceState
                        )
                    }
                )
            }
        }
    }
}
