package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.dashboard.Transaction

interface Repository {
    fun fetchFromRepository(): Flow<List<Transaction>>
}