package me.androidbox.spendless.domain.imp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.InsertUserUseCase
import me.androidbox.spendless.data.SpendLessDataSource


class InsertUserUseCaseImp(
    private val dataSource: SpendLessDataSource
) : InsertUserUseCase {
    override suspend fun execute(user: User) {
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