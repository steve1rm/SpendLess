package me.androidbox.spendless.settings.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.settings.data.PreferenceTable

fun interface FetchPreferenceUseCase {
    fun execute(): Flow<PreferenceTable>
}