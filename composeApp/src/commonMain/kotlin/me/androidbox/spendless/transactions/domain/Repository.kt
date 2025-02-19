package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun fetchFromRepository(): Flow<List<TransactionModel>>
}