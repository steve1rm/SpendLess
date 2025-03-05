package me.androidbox.spendless.authentication.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface SessionDao {
//    @Query("SELECT * FROM session WHERE userId=:userId")
//    suspend fun getUser(username: String): User?
//
//    @Query("SELECT * FROM user WHERE username=:username AND pin=:pin")
//    suspend fun validateUser(username: String, pin: String): User?

    @Upsert
    suspend fun insertSession(session: Session)

//    @Delete
//    fun delete(transaction: Transaction)
}