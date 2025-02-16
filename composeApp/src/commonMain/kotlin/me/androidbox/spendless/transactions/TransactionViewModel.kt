package me.androidbox.spendless.transactions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TransactionViewModel : ViewModel() {

    private val _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState.asStateFlow()

    fun onAction(action: TransactionAction) {
        when(action) {
            TransactionAction.OnCreateClicked -> {
                println("create clicked")
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