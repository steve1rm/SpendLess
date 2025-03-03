package me.androidbox.spendless.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.InsertTransactionUseCase

class TransactionViewModel(
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val fetchAllTransactionsUseCase: FetchAllTransactionsUseCase
) : ViewModel() {

    private var hasFetched: Boolean = false

    private val _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState.asStateFlow()
        .onStart {
            if(!hasFetched) {
                fetchAllTransactions()
                hasFetched = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = TransactionState())

    fun fetchAllTransactions() {
        try {
            viewModelScope.launch {
                _transactionState.update { transactionState ->
                    transactionState.copy(
                        isLoading = true
                    )
                }

                fetchAllTransactionsUseCase.execute()
                    .collect{ transactionModel ->
                        println(transactionModel)

                        _transactionState.update { transactionState ->
                            transactionState.copy(
                                listOfTransactions = transactionModel,
                                isLoading = false
                            )
                        }
                    }
            }
        }
        catch (exception: Exception) {
            _transactionState.update { transactionState ->
                transactionState.copy(
                    isLoading = false
                )
            }
        }
    }

    fun onAction(action: TransactionAction) {
        when(action) {
            TransactionAction.OnCreateClicked -> {
                println("create clicked")
                viewModelScope.launch {
                    insertTransactionUseCase.execute(transaction = TransactionTable(
                        title = "test",
                        counterparty = listOf(),
                        amount = 4556f,
                        note = "this is a note",
                        createdAt = Clock.System.now().toEpochMilliseconds(),
                        categoryId = 101
                    ))
                }
            }
            is TransactionAction.OnTransactionAmountEntered -> {
                _transactionState.update { transactionState ->
                    transactionState.copy(
                        amount = action.amount
                    )
                }
            }
            is TransactionAction.OnTransactionNameEntered -> {
                _transactionState.update { transactionState ->
                    transactionState.copy(
                        name = action.name
                    )
                }
            }
            is TransactionAction.OnTransactionTypeClicked -> {
                _transactionState.update { transactionState ->
                    transactionState.copy(
                        type = action.transactionType
                    )
                }
            }
        }
    }
}