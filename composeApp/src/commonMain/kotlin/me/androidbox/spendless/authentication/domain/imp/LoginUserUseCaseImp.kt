package me.androidbox.spendless.authentication.domain.imp

import kotlinx.datetime.Clock
import me.androidbox.spendless.authentication.data.Session
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.InsertUserUseCase
import me.androidbox.spendless.authentication.domain.LoginUserUseCase

class LoginUserUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : LoginUserUseCase {
    override suspend fun execute(username: String, pin: String){
        if(checkPin(username, pin)) {
            println("pin check ok")
            val user = spendLessDataSource.getUser(username)
//            val lastSession = spendLessDataSource.getSession(user.id)
            user?.let {
                val session = Session(
                    createdAt = Clock.System.now().toString(),
                    userId = user.id
                )
                spendLessDataSource.createSession(session)
            }
        } else {
            println("pin check wrong")
            // how to handle incorrect pin?
        }
    }

    private fun checkPin(username: String, pin: String): Boolean {
        return true
    }
}