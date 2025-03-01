package me.androidbox.spendless.domain

import me.androidbox.spendless.data.User

interface InsertUserUseCase {
    suspend fun execute(user: User)
}
