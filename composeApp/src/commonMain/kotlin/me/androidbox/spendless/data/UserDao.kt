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
<<<<<<< HEAD
<<<<<<< HEAD
    suspend fun insertUser(vararg user: User)
=======
    suspend fun insertAll(vararg user: User)
>>>>>>> f15e979 (add user table)
=======
    suspend fun insertUser(vararg user: User)
>>>>>>> 57bf3bd (set up CreateUserUseCase)

//    @Delete
//    fun delete(transaction: Transaction)
}