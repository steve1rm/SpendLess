package me.androidbox.spendless.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashBoardViewModel : ViewModel() {

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
        }
    }
}