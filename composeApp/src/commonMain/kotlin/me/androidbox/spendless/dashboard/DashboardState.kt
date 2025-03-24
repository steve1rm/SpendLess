package me.androidbox.spendless.dashboard

import me.androidbox.spendless.onboarding.screens.PreferenceState
import me.androidbox.spendless.transactions.data.AllTransactions
import me.androidbox.spendless.transactions.data.Transaction

data class DashboardState(
    val username: String = "",
    val showTransactionBottomSheet: Boolean = false,
    val transaction: Transaction = Transaction(),
    val largestTransaction: Transaction = Transaction(),
    val popularTransaction: Transaction = Transaction(),
    val listOfTransactions: List<AllTransactions> = emptyList(),
    val totalPreviousSpent: Long = 0L,
    val showPinPromptScreen: Boolean = false,
    val totalTransactionAmount: Long = 0L,

    /** Preferences for currency, decimal formatting, etc.
     *  TODO Should this be here, check in the discord channel */
    val preferenceState: PreferenceState = PreferenceState()
)




