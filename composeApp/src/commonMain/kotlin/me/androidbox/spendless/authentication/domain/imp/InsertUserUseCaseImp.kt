package me.androidbox.spendless.authentication.domain.imp

import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.InsertUserUseCase

class InsertUserUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : InsertUserUseCase {
    override suspend fun execute(user: User){
//                generatePinDigest(username, pin)  TODO
        spendLessDataSource.insertUser(user)
    }

    private fun generatePinDigest(username: String, pin: Int) {
    }
}