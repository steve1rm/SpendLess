package me.androidbox.spendless.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username='%'")
    suspend fun getUser(): User

    @Insert
    suspend fun insertUser(vararg user: User)

//    @Delete
//    fun delete(transaction: Transaction)
}