package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.androidbox.spendless.core.presentation.TransactionItems
import kotlin.random.Random
import kotlin.time.Duration.Companion.days

fun interface FetchAllTransactionsUseCase {
    fun execute(): Flow<List<TransactionModel>>
}

fun populate(): Flow<List<TransactionModel>> {

    println("POPULATE")

    val listOfJournals = listOf(
        TransactionModel(
            title = "Holiday",
            note = "Planning a trip to Hong Kong Planning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong Kong Planning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong Kong",
            date = Clock.System.now().toEpochMilliseconds(),
            description = TransactionItems.CLOTHING,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Going to work",
            note = "Planning a trip to Hong Kong",
            date = Clock.System.now().toEpochMilliseconds(),
            description = TransactionItems.ENTERTAINMENT,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Getting Coffee",
            note = "Planning a trip to Hong Kong Planning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong Kong",
            date = Clock.System.now().toEpochMilliseconds(),
            description = TransactionItems.FOOD,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Writing code",
            note = "Planning a trip to Hong Kong",
            date = subtractDay(Clock.System.now().toEpochMilliseconds(), 1),
            description = TransactionItems.FOOD,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Unit testing",
            note= "Planning a trip to Hong Kong",
            date = subtractDay(Clock.System.now().toEpochMilliseconds(), 1),
            description = TransactionItems.ENTERTAINMENT,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Traveling to the south",
            note = "Planning a trip to Hong Kong",
            date = subtractDay(Clock.System.now().toEpochMilliseconds(), 2),
            description = TransactionItems.EDUCATION,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Buy new Dell",
            note = "Planning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong KongPlanning a trip to Hong Kong",
            date = subtractDay(Clock.System.now().toEpochMilliseconds(), 3),
            description = TransactionItems.HEALTH,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Working on moving location",
            note = "Lets get this done today, and move all our stuff to another property. Lets Go...",
            date = subtractDay(Clock.System.now().toEpochMilliseconds(), 4),
            description = TransactionItems.CLOTHING,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Coffee with Donuts @ Dunkin Donuts",
            note = "Lets get this party started. Start planning and less day dreaming",
            date = subtractDay(Clock.System.now().toEpochMilliseconds(), 4),
            description = TransactionItems.HEALTH,
            amount = Random.nextFloat()
        ),
        TransactionModel(
            title = "Relocation is in progress",
            note = "Getting ready with flight and package of suitcases and other things",
            date = subtractDay(Clock.System.now().toEpochMilliseconds(), 4),
            description = TransactionItems.EDUCATION,
            amount = Random.nextFloat()
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