package com.azabost.quest.users.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.azabost.quest.users.data.model.UserJdo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userJdo: UserJdo)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserJdo>>
}
