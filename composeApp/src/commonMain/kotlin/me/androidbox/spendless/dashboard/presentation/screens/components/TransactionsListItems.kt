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


// Helper function to calculate display date string
private fun calculateDisplayDate(createdAtMillis: Long, today: LocalDate): String {
    val transactionDate = Instant.fromEpochMilliseconds(createdAtMillis)
        .toLocalDateTime(TimeZone.currentSystemDefault()).date
    val yesterday = today.minus(DatePeriod(days = 1))

    return when (transactionDate) {
        today -> "Today"
        yesterday -> "Yesterday"
        else -> {
            // Using kotlinx-datetime formatting
            transactionDate.format(LocalDate.Format {
                // Choose your desired format components
                monthName(MonthNames.ENGLISH_ABBREVIATED) // Or ENGLISH_FULL
                char(' ')
                dayOfMonth()
//               Append year if needed for older dates:
//               if (transactionDate.year != today.year) {
//                   char(',')
//                   char(' ')
//                   year()
//               }
            })

            // --- Alternative using java.time formatter (if needed for specific locale/patterns) ---
            // Note: Requires converting kotlinx.datetime.LocalDate to java.time.LocalDate
            // java.time.LocalDate.of(transactionDate.year, transactionDate.month, transactionDate.dayOfMonth)
            //    .format(java.time.format.DateTimeFormatter.ofPattern("MMM d")) // Example pattern
        }
    }
}

@OptIn(ExperimentalFoundationApi::class) // Required for stickyHeader
@Composable
fun TransactionsListItems(
    modifier: Modifier = Modifier,
    preferenceState: PreferenceState,
    listOfTransactions: List<AllTransactions>, // List of headers, each with its own list of transactions
) {
    val currentDate = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    // 1. Group transactions by calculated display date string ("Today", "Yesterday", "MMM d")
    val groupedTransactions = remember(listOfTransactions, currentDate) {
        listOfTransactions
            .flatMap { header -> // Flatten into individual transactions tagged with original createdAt
                header.transactions.map { transaction -> Pair(header.createdAt, transaction) }
            }
            .groupBy { (createdAt, _) -> // Group by the calculated date string
                calculateDisplayDate(createdAt, currentDate)
            }
        // Result: Map<String, List<Pair<Long, TransactionTable>>>
        // e.g., "Today" -> [ (ts1, txA), (ts2, txB), ... ]
        //       "Yesterday" -> [ (ts3, txC), ... ]
    }

    // Optional: Define desired order for headers if groupBy doesn't preserve it
    val sortedGroupKeys = remember(groupedTransactions) {
        groupedTransactions.keys.sortedWith(compareBy { dateString ->
            when (dateString) {
                "Today" -> 0 // Today first
                "Yesterday" -> 1 // Yesterday second
                else -> 2 // Other dates later (could parse dateString for precise sorting if needed)
            }
        })
    }


    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        state = rememberLazyListState()
    ) {
        // 2. Iterate through the *grouped* data
        sortedGroupKeys.forEach { displayDate -> // Use sorted keys for desired order
            val transactionsInGroup = groupedTransactions[displayDate] ?: emptyList()

            // Filter out empty groups just in case
            if (transactionsInGroup.isNotEmpty()) {
                // 3. Add ONE sticky header per group (using the displayDate as key)
                stickyHeader(key = displayDate) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Background /* Replace with your actual background color */)
                            .padding(vertical = 8.dp), // Add some padding to headers
                        text = displayDate
                        // Add other styling as needed (fontWeight, etc.)
                    )
                }

                // 4. Add items for the current group
                items(
                    // We only need the TransactionTable part for the item content
                    items = transactionsInGroup.map { it.second },
                    key = { transaction ->
                        transaction.id // Use transaction ID as key
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
