package me.androidbox.spendless.domain.imp

import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.data.User
import me.androidbox.spendless.domain.InsertUserUseCase

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