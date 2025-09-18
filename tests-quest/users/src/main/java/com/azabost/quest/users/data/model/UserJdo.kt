package com.azabost.quest.users.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azabost.quest.users.repository.User

@Entity(tableName = "users")
data class UserJdo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean = false
)

fun UserJdo.toUser(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName
    )
}