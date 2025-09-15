package com.azabost.quest.users.repository

import kotlinx.coroutines.flow.StateFlow

interface UsersRepository {
    fun getUsers(): StateFlow<List<User>>
    suspend fun createUser(firstName: String, lastName: String)
}