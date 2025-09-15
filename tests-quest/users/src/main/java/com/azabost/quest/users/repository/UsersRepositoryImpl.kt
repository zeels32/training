package com.azabost.quest.users.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.plus

@Singleton
class UsersRepositoryImpl @Inject constructor(): UsersRepository {

    private val _users = MutableStateFlow(
        listOf(
            "Sylvester" to "Stallone",
            "John" to "Rambo",
            "A" to "P",
            "B" to "Q",
            "C" to "R",
            "D" to "S",
            "E" to "T",
            "F" to "U",
            "G" to "V",
            "H" to "W",
            "I" to "X",
            "J" to "Y",
            "K" to "Z",
        ).mapIndexed { index, (firstName, lastName) ->
            User(id = index, firstName = firstName, lastName = lastName)
        }
    )

    override fun getUsers(): StateFlow<List<User>> {
        return _users.asStateFlow()
    }

    override suspend fun createUser(firstName: String, lastName: String) {
        require(firstName.isNotEmpty() || firstName.isNotBlank())
        _users.update { it + User(id = it.lastIndex + 1, firstName = firstName, lastName = lastName) }
    }
}