package com.azabost.quest.users.repository

import com.azabost.quest.users.data.model.UserJdo
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun createUser(firstName: String, lastName: String)
    fun getUsers(): Flow<List<User>>
}