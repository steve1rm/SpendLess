package me.androidbox.spendless.authentication.domain.imp

import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.InsertUserUseCase
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.core.domain.generatePinDigest

class InsertUserUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : InsertUserUseCase {
    override suspend fun execute(user: User){
        val pinHash: String = generatePinDigest(user.pin)
        println("PINHASH: $pinHash")

        val securedUser = User(
            username = user.username,
            pin = pinHash
        )

        spendLessDataSource.insertUser(securedUser)
    }
}
