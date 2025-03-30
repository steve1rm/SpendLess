package me.androidbox.spendless.dashboard.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.androidbox.spendless.dashboard.presentation.screens.components.TransactionsListItems
import me.androidbox.spendless.onboarding.screens.PreferenceState
import me.androidbox.spendless.settings.presentation.components.SpendLessTheme
import me.androidbox.spendless.transactions.data.AllTransactions

@Composable
fun AllTransactionScreen(
    modifier: Modifier = Modifier,
    transactions: List<AllTransactions>,
    preferenceState: PreferenceState,
    onNavigationClicked: () -> Unit
) {
    SpendLessTheme(
        modifier = modifier,
        toolBarTitle = "All Transactions",
        onNavigationClicked = {
            onNavigationClicked()
        },
        content = { paddingValues ->
            TransactionsListItems(
                modifier = modifier.padding(paddingValues),
                listOfTransactions = transactions,
                preferenceState = preferenceState
            )
        }
    )
}