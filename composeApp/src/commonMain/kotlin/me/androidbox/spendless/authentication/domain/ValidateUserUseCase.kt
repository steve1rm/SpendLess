package me.androidbox.spendless.authentication.domain

import me.androidbox.spendless.authentication.data.User

interface ValidateUserUseCase {
    suspend fun execute(username: String, pin: String): User?
}