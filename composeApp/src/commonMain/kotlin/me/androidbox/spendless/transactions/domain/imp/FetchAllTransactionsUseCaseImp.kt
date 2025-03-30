package me.androidbox.spendless.transactions.domain.imp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.transactions.data.AllTransactions
import me.androidbox.spendless.transactions.data.Transaction
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import kotlin.random.Random
import kotlin.time.Duration.Companion.days

class FetchAllTransactionsUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : FetchAllTransactionsUseCase {
    override fun execute(): Flow<List<AllTransactions>> {
        return spendLessDataSource.getAllTransaction().map { transactionList ->
            transactionList
                .sortedByDescending { it.createAt }
                .groupBy { it.createAt }
        }.map {
            it.entries
                .map { mapOfTransactions ->
                    AllTransactions(
                        createdAt = mapOfTransactions.key,
                        transactions = mapOfTransactions.value.map { transactionTable ->
                            Transaction(
                                id = transactionTable.id,
                                name = transactionTable.name,
                                type = TransactionType.entries[transactionTable.type],
                                counterParty = transactionTable.counterParty,
                                category = TransactionItems.entries[transactionTable.category],
                                note = transactionTable.note,
                                createAt = transactionTable.createAt,
                                amount = transactionTable.amount
                            )
                        }
                    )
                }
        }
    }
}

fun populate(): Flow<List<Transaction>> {
    println("POPULATE")
    val today = Clock.System.now().toEpochMilliseconds()

    val listOfJournals = listOf(
        Transaction(
            id = 0,
            name = "Holiday",
            note = "Planning a trip to Hong Kong Planning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong Kong Planning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong Kong",
            createAt = today,
            category = TransactionItems.CLOTHING,
            amount = Random.nextDouble()
        ),
        Transaction(
            id = 1,
            name = "Going to work",
            note = "Planning a trip to Hong Kong",
            createAt = today,
            category = TransactionItems.ENTERTAINMENT,
            amount = Random.nextDouble()
         ),
        Transaction(
            id = 3,
            name = "Writing code",
            note = "Planning a trip to Hong Kong",
            createAt = subtractDay(today, 1),
            category = TransactionItems.FOOD,
            amount = Random.nextDouble()
         ),
        Transaction(
            id = 4,
            name = "Unit testing",
            note= "Planning a trip to Hong Kong",
            createAt = subtractDay(today, 1),
            category = TransactionItems.ENTERTAINMENT,
            amount = Random.nextDouble()
        ),
        Transaction(
            id = 5,
            name = "Traveling to the south",
            note = "Planning a trip to Hong Kong",
            createAt = subtractDay(today, 2),
            category = TransactionItems.EDUCATION,
            amount = Random.nextDouble()
        ),
        Transaction(
            id = 2,
            name = "Getting Coffee",
            note = "Planning a trip to Hong Kong Planning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong Kong",
            createAt = today,
            category = TransactionItems.FOOD,
            amount = Random.nextDouble()
        ),
        Transaction(
            id = 6,
            name = "Buy new Dell",
            note = "Planning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong Kong",
            createAt = subtractDay(today, 3),
            category = TransactionItems.HEALTH,
            amount = Random.nextDouble()
        ),
        Transaction(
            id = 7,
            name = "Working on moving location",
            note = "Lets get this done today, and move all our stuff to another property. Lets Go...",
            createAt = subtractDay(today, 4),
            category = TransactionItems.CLOTHING,
            amount = Random.nextDouble()
        ),
        Transaction(
            id = 8,
            name = "Coffee with Donuts @ Dunkin Donuts",
            note = "Lets get this party started. Start planning and less day dreaming",
            createAt = subtractDay(today, 4),
            category = TransactionItems.HEALTH,
            amount = Random.nextDouble()
        ),
        Transaction(
            id = 9,
            name = "Relocation is in progress",
            note = "Getting ready with flight and package of suitcases and other things",
            createAt = subtractDay(today, 4),
            category = TransactionItems.EDUCATION,
            amount = Random.nextDouble()
        ))

    return flow {
        emit(listOfJournals)
    }
}

fun subtractDay(timestamp: Long, day: Int): Long {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val oneDayAgoInstant = instant.minus(day.days)
    return oneDayAgoInstant.toEpochMilliseconds()
}