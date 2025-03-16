package me.androidbox.spendless.settings.domain

import me.androidbox.spendless.settings.data.PreferenceTable

fun interface FetchPreferenceUseCase {
    suspend fun execute(): PreferenceTable
}