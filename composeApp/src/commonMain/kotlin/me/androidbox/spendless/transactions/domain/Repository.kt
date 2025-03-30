package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.transactions.data.Transaction

interface Repository {
    fun fetchFromRepository(): Flow<List<Transaction>>
}