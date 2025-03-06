package me.androidbox.spendless.authentication.domain.imp

import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.ValidateUserUseCase
import me.androidbox.spendless.core.data.SpendLessDataSource

class ValidateUserUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : ValidateUserUseCase {
    override suspend fun execute(username: String, pin: String): User? {
        return spendLessDataSource.validateUser(username, pin)
    }
}