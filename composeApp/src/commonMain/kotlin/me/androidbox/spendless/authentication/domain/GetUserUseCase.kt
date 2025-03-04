package me.androidbox.spendless.authentication.domain

import me.androidbox.spendless.authentication.data.User

interface GetUserUseCase {
    suspend fun execute(username: String): User?
}