package me.androidbox.spendless.transactions.domain.imp

import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.core.domain.encryptTransaction
import me.androidbox.spendless.transactions.data.EncryptedTransactionTable
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.transactions.domain.InsertEncryptedTransactionUseCase
import me.androidbox.spendless.transactions.domain.InsertTransactionUseCase

class InsertEncryptedTransactionUseCaseImp(
    private val dataSource: SpendLessDataSource
) : InsertEncryptedTransactionUseCase {
    override suspend fun execute(transactionToEncrypt: TransactionTable) {

        val encryptedTransaction = EncryptedTransactionTable(
            encryptedString = encryptTransaction(transactionToEncrypt)
        )
        println("txn to encode: $encryptedTransaction")

        dataSource.insertEncryptedTransaction(encryptedTransaction)
    }
}