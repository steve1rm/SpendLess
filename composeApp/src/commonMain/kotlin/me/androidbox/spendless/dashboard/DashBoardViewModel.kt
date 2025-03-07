package me.androidbox.spendless.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.FetchLargestTransactionUseCase
import me.androidbox.spendless.transactions.domain.InsertTransactionUseCase

class DashBoardViewModel(
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val fetchAllTransactionsUseCase: FetchAllTransactionsUseCase,
    private val fetchLargestTransactionUseCase: FetchLargestTransactionUseCase
) : ViewModel() {

    private var hasFetched = false

    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState = _dashboardState.asStateFlow()
        .onStart {
            if(!hasFetched) {
                fetchTransactions()
                fetchLargestTransaction()
                hasFetched = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DashboardState()
        )

    private fun fetchLargestTransaction() {
        viewModelScope.launch {
             fetchLargestTransactionUseCase.execute()
                .collectLatest {
                    println("Largest transaction $it")
                }
        }
    }

    private fun fetchTransactions() {
        viewModelScope.launch {
            val transactions = fetchAllTransactionsUseCase.execute()
            transactions.collectLatest { listOfTransactions ->
                _dashboardState.update { dashboardState ->
                    dashboardState.copy(
                        listOfTransactions = listOfTransactions
                    )
                }
            }
        }
    }

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
                if(_dashboardState.value.transaction.name.count() in 4..14) {
                    println("Create transaction save to the database")
                    viewModelScope.launch {
                        insertTransactionUseCase.execute(
                            transaction = TransactionTable(
                                name = dashboardState.value.transaction.name,
                                counterParty = dashboardState.value.transaction.counterParty,
                                note = dashboardState.value.transaction.note,
                                amount = dashboardState.value.transaction.amount,
                                category = dashboardState.value.transaction.category.ordinal,
                                createAt = Clock.System.now().toEpochMilliseconds(),
                                type = dashboardState.value.transaction.type.ordinal
                            )
                        )
                    }
                }
            }
            is DashboardAction.OnTransactionAmountEntered -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        transaction = transactionState.transaction.copy(amount = action.amount)
                    )
                }
            }
            is DashboardAction.OnTransactionNameEntered -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        transaction = transactionState.transaction.copy(name = action.name)
                    )
                }
            }
            is DashboardAction.OnTransactionTypeClicked -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        transaction = transactionState.transaction.copy(type = action.transactionType)
                    )
                }
            }

            DashboardAction.OnShowAllClicked -> {
                /** Go to all transactions */
            }

            is DashboardAction.OnTransactionCategoryChanged -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        transaction = transactionState.transaction.copy(category = action.category)
                    )
                }
            }
        }
    }
}