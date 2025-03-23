package me.androidbox.spendless.settings.domain.imp

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.settings.domain.FetchPreferenceUseCase

class FetchPreferenceUseCaseImp(private val spendLessDataSource: SpendLessDataSource) : FetchPreferenceUseCase {
    override fun execute(): Flow<PreferenceTable> {
        return spendLessDataSource.getPreference()
    }
}