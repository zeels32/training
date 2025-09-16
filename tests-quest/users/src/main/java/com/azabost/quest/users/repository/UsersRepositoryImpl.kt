package com.azabost.quest.users.repository

import com.azabost.quest.users.data.dao.UserDao
import com.azabost.quest.users.data.model.UserJdo
import com.azabost.quest.users.data.model.toUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UsersRepository {

    override suspend fun createUser(firstName: String, lastName: String) {
        require(firstName.isNotBlank())
        userDao.insertUser(UserJdo(firstName = firstName, lastName = lastName))
    }

    override fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map {
            it.map { userJdo ->
                userJdo.toUser()
            }
        }
    }

}