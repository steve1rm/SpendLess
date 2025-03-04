package me.androidbox.spendless.authentication.domain.imp

import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.GetUserUseCase
import me.androidbox.spendless.core.data.SpendLessDataSource

class GetUserUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : GetUserUseCase {
    override suspend fun execute(username: String): User? {
        return spendLessDataSource.getUser(username)
    }
}