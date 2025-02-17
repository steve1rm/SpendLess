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
                    dashboardState.copy(newTransaction = true)
                }
            }

            DashboardAction.OpenSettings -> {
                println("OpenSettings Screen")
            }

            DashboardAction.OnCreateClicked -> TODO()
            is DashboardAction.OnTransactionAmountEntered -> TODO()
            is DashboardAction.OnTransactionNameEntered -> TODO()
            is DashboardAction.OnTransactionTypeClicked -> TODO()
        }
    }
}