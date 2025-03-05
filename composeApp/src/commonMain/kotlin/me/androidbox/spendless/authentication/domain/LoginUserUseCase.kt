package me.androidbox.spendless.authentication.domain

import me.androidbox.spendless.authentication.data.User

interface LoginUserUseCase {
    suspend fun execute(username: String, pin: String)
}
