package com.training.compose.effects.data

import kotlinx.coroutines.delay

interface IRepository {
    suspend fun loadUser(userId: String): User
}

class RepositoryImpl : IRepository {

    override suspend fun loadUser(userId: String): User {

        // Simulate network delay
        delay(2000)
        return User(userId, "User $userId")

    }


}