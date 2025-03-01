package me.androidbox.spendless.domain.imp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.data.User
import me.androidbox.spendless.domain.CreateUserUseCase


class CreateUserUseCaseImp(
    private val dataSource: SpendLessDataSource
) : CreateUserUseCase {
    override suspend fun execute(user: User): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                dataSource.insertUser(user)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}