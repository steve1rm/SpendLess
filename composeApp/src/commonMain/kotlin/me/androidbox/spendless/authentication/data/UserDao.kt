package me.androidbox.spendless.authentication.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username=:username")
    suspend fun getUser(username: String): User?

    @Upsert
    suspend fun insertUser(user: User)

//    @Delete
//    fun delete(transaction: Transaction)
}