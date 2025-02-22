package me.androidbox.spendless.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "pinDigest") val pinDigest: String,
    @ColumnInfo(name = "createdAt") val createdAt: Long
)
