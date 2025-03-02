package me.androidbox.spendless.authentication.domain

import me.androidbox.spendless.authentication.data.User

interface InsertUserUseCase {
    suspend fun execute(user: User)
}
