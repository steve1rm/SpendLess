package me.androidbox.spendless.authentication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "session")
data class Session(
    @PrimaryKey val userId: Int,
    val createdAt: String
)
