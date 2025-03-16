package me.androidbox.spendless.dashboard

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import me.androidbox.spendless.SpendLessPreference
import me.androidbox.spendless.authentication.domain.GetUserUseCase
import me.androidbox.spendless.core.presentation.hasActiveSession
import me.androidbox.spendless.transactions.data.Transaction
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.FetchLargestTransactionUseCase
import me.androidbox.spendless.transactions.domain.FetchMostPopularCategoryUseCase
import me.androidbox.spendless.transactions.domain.FetchTotalSpentPreviousWeekUseCase
import me.androidbox.spendless.transactions.domain.InsertTransactionUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class DashBoardViewModel(
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val fetchAllTransactionsUseCase: FetchAllTransactionsUseCase,
    private val fetchLargestTransactionUseCase: FetchLargestTransactionUseCase,
    private val fetchTotalSpentPreviousWeekUseCase: FetchTotalSpentPreviousWeekUseCase,
    private val fetchMostPopularCategoryUseCase: FetchMostPopularCategoryUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val spendLessPreference: SpendLessPreference
) : ViewModel() {

    private var hasFetched = false

    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState = _dashboardState.asStateFlow()
        .onStart {
            if(!hasFetched) {
                fetchActiveUser()
                fetchTransactions()
                fetchLargestTransaction()
                fetchTotalSpentPreviousWeek()
                fetchMostPopularCategory()
                hasFetched = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DashboardState()
        )

    private val _dashboardEvent = Channel<DashboardEvents>()
    val dashboardEvents = _dashboardEvent.receiveAsFlow()

    private fun fetchActiveUser() {
        viewModelScope.launch {
            _dashboardState.update { state ->
                state.copy(
                    username = spendLessPreference.getUsername() ?: ""
                )
            }
        }
    }

    private fun fetchMostPopularCategory() {
        viewModelScope.launch {
            fetchMostPopularCategoryUseCase.execute()
                .collectLatest { result ->
                    println("RESULT VM")
                    result.onSuccess { transaction ->
                        _dashboardState.update { dashboardState ->
                            dashboardState.copy(
                                popularTransaction = transaction
                            )
                        }
                    }.onFailure {
                        println("RESULT.FAILURE VM")
                        it.printStackTrace()
                        _dashboardState.update { dashboardState ->
                            dashboardState.copy(
                                popularTransaction = Transaction()
                            )
                        }
                    }
                }
        }
    }

    private fun fetchTotalSpentPreviousWeek() {
        viewModelScope.launch {
            val totalSpent = fetchTotalSpentPreviousWeekUseCase.execute()
            _dashboardState.update { dashboardState ->
                dashboardState.copy(
                    totalPreviousSpent = totalSpent
                )
            }
        }
    }

    private fun fetchLargestTransaction() {
        viewModelScope.launch {
             fetchLargestTransactionUseCase.execute()
                 .catch {
                     println("CATCH ME LARGEST ${it.printStackTrace()}")
                 }
                .collectLatest { transaction ->
                    println("Largest transaction $transaction")
                    _dashboardState.update { dashboardState ->
                        dashboardState.copy(largestTransaction = transaction)
                    }
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

    /**
     * Check if session is still active
     * if not active
     * get username from sharedpreferences
     * get get PIN from user table
     * show the PIN prompt screen
     * Correct Open transaction sheet
     *
     * */
    private suspend fun canShowTransactionScreen(): Boolean {
        return hasActiveSession(spendLessPreference.getTimeStamp())
    }

    fun onAction(action: DashboardAction) {
        when(action) {
            is DashboardAction.OpenNewTransaction -> {
                /**
                 * Check if session is still active
                 * if not active
                 * get username from sharedpreferences
                 * get get PIN from user table
                 * show the PIN prompt screen
                 * Correct Open transaction sheet
                 *
                 * */
                viewModelScope.launch {
                    if(canShowTransactionScreen()) {
                        _dashboardState.update { dashboardState ->
                            dashboardState.copy(showTransactionBottomSheet = action.shouldOpen)
                        }
                    }
                    else {
                        // Open PinPrompt screen - send event
                        _dashboardEvent.send(DashboardEvents.OpenPinPromptScreen(pin = "12346"))
                    }
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

                        /* Clear the transaction so its not retained when opening the transaction bottom sheet */
                        _dashboardState.update { dashboardState ->
                            dashboardState.copy(
                                transaction = Transaction(),
                                showTransactionBottomSheet = false
                            )
                        }
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

            is DashboardAction.OpenPinPromptScreen -> {
                /** No-op - handled in the composable nav graph */
            }
        }
    }
}