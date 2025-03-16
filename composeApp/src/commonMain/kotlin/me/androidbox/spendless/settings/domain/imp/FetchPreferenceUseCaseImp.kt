package me.androidbox.spendless.settings.domain.imp

import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.settings.domain.FetchPreferenceUseCase

class FetchPreferenceUseCaseImp(private val spendLessDataSource: SpendLessDataSource) : FetchPreferenceUseCase {
    override suspend fun execute(): PreferenceTable {
        return spendLessDataSource.getPreference()
    }
}