package me.androidbox.spendless.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.transactions.domain.InsertTransactionUseCase

class DashBoardViewModel(
    private val insertTransactionUseCase: InsertTransactionUseCase
) : ViewModel() {

    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState = _dashboardState.asStateFlow()

    fun onAction(action: DashboardAction) {
        when(action) {
            is DashboardAction.OpenNewTransaction -> {
                _dashboardState.update { dashboardState ->
                    dashboardState.copy(newTransaction = action.shouldOpen)
                }
            }

            DashboardAction.OpenSettings -> {
                println("OpenSettings Screen")
            }

            DashboardAction.OnCreateClicked -> {
                if(_dashboardState.value.name.count() in 4..14) {
                    println("Create transaction save to the database")
                    viewModelScope.launch {
                        insertTransactionUseCase.execute(
                            transaction = TransactionTable(
                                title = dashboardState.value.name,
                                counterparty = dashboardState.value.counterParty,
                                note = dashboardState.value.note,
                                amount = dashboardState.value.amount.toFloat(),
                                categoryId = dashboardState.value.category,
                                createdAt = Clock.System.now().toEpochMilliseconds()
                            )
                        )
                    }
                }
            }
            is DashboardAction.OnTransactionAmountEntered -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        amount = action.amount
                    )
                }
            }
            is DashboardAction.OnTransactionNameEntered -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        name = action.name
                    )
                }
            }
            is DashboardAction.OnTransactionTypeClicked -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        type = action.transactionType
                    )
                }
            }

            DashboardAction.OnShowAllClicked -> {
                /** Go to all transactions */
            }
        }
    }
}