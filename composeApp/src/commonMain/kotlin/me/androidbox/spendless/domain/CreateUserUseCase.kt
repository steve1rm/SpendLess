package me.androidbox.spendless.domain

import me.androidbox.spendless.data.User

interface CreateUserUseCase {
    suspend fun execute(user: User): Result<Unit>
}
