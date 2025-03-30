package me.androidbox.spendless.transactions.domain.imp

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.transactions.domain.FetchTotalSpentPreviousWeekUseCase

class FetchTotalSpentPreviousWeekUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : FetchTotalSpentPreviousWeekUseCase {
    override suspend fun execute(): Double {
        val currentDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val daysFromMonday = currentDate.dayOfWeek.ordinal
        val mostRecentMonday = currentDate.minus(DatePeriod(days = daysFromMonday))

        val startOfPreviousWeek = mostRecentMonday
            .minus(DatePeriod(days = 7))
            .atStartOfDayIn(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        val endOfPreviousWeek = mostRecentMonday
            .minus(DatePeriod(days = 1))
            .atTime(23, 59, 59)
            .toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        return spendLessDataSource.getTotalSpentPreviousWeek(
            startOfPreviousWeek = startOfPreviousWeek,
            endOfPreviousWeek = endOfPreviousWeek
        )
    }
}