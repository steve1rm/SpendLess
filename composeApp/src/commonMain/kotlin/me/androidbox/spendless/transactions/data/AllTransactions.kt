package me.androidbox.spendless.transactions.data

data class AllTransactions(
    val createdAt: Long = 0L,
    val transactions: List<Transaction> = emptyList()
)