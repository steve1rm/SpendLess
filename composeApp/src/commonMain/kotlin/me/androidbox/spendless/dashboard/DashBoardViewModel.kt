package me.androidbox.spendless.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import me.androidbox.spendless.SpendLessPreference
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator
import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.core.presentation.formatMoney
import me.androidbox.spendless.core.presentation.hasActiveSession
import me.androidbox.spendless.dashboard.presentation.screens.components.TransactionsListItems
import me.androidbox.spendless.onboarding.screens.PreferenceState
import me.androidbox.spendless.onboarding.screens.components.TransactionItem
import me.androidbox.spendless.settings.domain.FetchPreferenceUseCase
import me.androidbox.spendless.transactions.data.Transaction
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.FetchLargestTransactionUseCase
import me.androidbox.spendless.transactions.domain.FetchMostPopularCategoryUseCase
import me.androidbox.spendless.transactions.domain.FetchTotalSpentPreviousWeekUseCase
import me.androidbox.spendless.transactions.domain.FetchTotalTransactionAmountUseCase
import me.androidbox.spendless.transactions.domain.InsertTransactionUseCase
import me.androidbox.spendless.transactions.screens.toAmount

@OptIn(ExperimentalCoroutinesApi::class)
class DashBoardViewModel(
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val fetchAllTransactionsUseCase: FetchAllTransactionsUseCase,
    private val fetchLargestTransactionUseCase: FetchLargestTransactionUseCase,
    private val fetchTotalSpentPreviousWeekUseCase: FetchTotalSpentPreviousWeekUseCase,
    private val fetchMostPopularCategoryUseCase: FetchMostPopularCategoryUseCase,
    private val fetchTotalTransactionAmountUseCase: FetchTotalTransactionAmountUseCase,
    private val fetchPreferenceUseCase: FetchPreferenceUseCase,
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
                fetchPreferences()
                fetchTotalTransactionAmount()
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

    private fun fetchTotalTransactionAmount() {
        viewModelScope.launch {
            fetchTotalTransactionAmountUseCase.execute().collect { totalAmount ->
                _dashboardState.update { dashboardState ->
                    dashboardState.copy(
                        totalTransactionAmount = totalAmount
                    )
                }
            }
        }
    }

    private fun fetchPreferences() {
        viewModelScope.launch {
            fetchPreferenceUseCase.execute().collectLatest { preferences ->
                _dashboardState.update { dashboardState ->
                    dashboardState.copy(
                        preferenceState = PreferenceState(
                            decimalSeparator = DecimalSeparator.entries[preferences.decimalSeparator],
                            thousandsSeparator = ThousandsSeparator.entries[preferences.thousandsSeparator],
                            currency = Currency.entries[preferences.currency],
                            expensesFormat = ExpensesFormat.entries[preferences.expensesFormat]
                        )
                    )
                }
            }
        }
    }

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

    private suspend fun showPinPromptAuthentication(): Boolean {
        return !hasActiveSession(spendLessPreference.getTimeStamp())
    }

    private suspend fun checkForActiveSession(activeSessionAction: suspend () -> Unit) {
        if(hasActiveSession(spendLessPreference.getTimeStamp())) {
            activeSessionAction()
        }
        else {
            // Open PinPrompt screen - send event
            _dashboardEvent.send(DashboardEvents.OpenPinPromptScreen(pin = "12346"))
        }
    }

    fun onAction(action: DashboardAction) {
        when(action) {
            is DashboardAction.OpenNewTransaction -> {
                viewModelScope.launch {
                    checkForActiveSession(
                        activeSessionAction = {
                            _dashboardState.update { dashboardState ->
                                dashboardState.copy(showTransactionBottomSheet = action.shouldOpen)
                            }
                        }
                    )
                }
            }

            DashboardAction.OpenSettings -> {
                viewModelScope.launch {
                    checkForActiveSession(
                        activeSessionAction = {
                            _dashboardEvent.send(DashboardEvents.OpenSettingsScreen)
                        }
                    )
                }
            }

            DashboardAction.OnCreateClicked -> {
                if(_dashboardState.value.transaction.name.count() in 4..14) {
                    println("Create transaction save to the database")
                    viewModelScope.launch {
                        val amount = if(dashboardState.value.transaction.type == TransactionType.RECEIVER) {
                            - (dashboardState.value.transaction.amount.toAmount())
                        } else {
                            dashboardState.value.transaction.amount.toAmount()
                        }

                        insertTransactionUseCase.execute(
                            transaction = TransactionTable(
                                name = dashboardState.value.transaction.name,
                                counterParty = dashboardState.value.transaction.counterParty,
                                note = dashboardState.value.transaction.note,
                                amount = amount,
                                category = if(dashboardState.value.transaction.type == TransactionType.RECEIVER) dashboardState.value.transaction.category.ordinal else TransactionItems.INCOME.ordinal,
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
            //    println("action.amount: ${action.amount}")
            }
            is DashboardAction.OnTransactionNameEntered -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        transaction = transactionState.transaction.copy(name = action.name)
                    )
                }
            }

            is DashboardAction.OnTransactionNoteEntered -> {
                _dashboardState.update { transactionState ->
                    transactionState.copy(
                        transaction = transactionState.transaction.copy(note = action.note)
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
                viewModelScope.launch {
                    checkForActiveSession(
                        activeSessionAction = {
                            _dashboardEvent.send(DashboardEvents.OpenAllTransactionsScreen)
                        }
                    )
                }
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

            is DashboardAction.ShouldShowTransactionNote -> {
                _dashboardState.update { dashboardState ->
                    dashboardState.copy(
                        showInputNote = true
                    )
                }
            }
        }
    }
}