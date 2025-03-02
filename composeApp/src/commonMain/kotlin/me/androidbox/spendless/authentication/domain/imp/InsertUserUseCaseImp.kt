package me.androidbox.spendless.authentication.domain.imp

import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.InsertUserUseCase
import me.androidbox.spendless.generatePinDigest

class InsertUserUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : InsertUserUseCase {
    override suspend fun execute(user: User){
        val pinHash: String = generatePinDigest(user.username, user.pin)
        println("INSERTUSERUSECASE pinDigest $pinHash")

        val securedUser: User = User(
            username = user.username,
            pin = pinHash
        )

        spendLessDataSource.insertUser(securedUser)
    }
}