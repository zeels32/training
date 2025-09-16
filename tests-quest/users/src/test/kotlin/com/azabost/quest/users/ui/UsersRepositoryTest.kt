package com.azabost.quest.users.ui

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.azabost.quest.users.data.dao.UserDao
import com.azabost.quest.users.data.database.AppDatabase
import com.azabost.quest.users.data.model.UserJdo
import com.azabost.quest.users.repository.UsersRepository
import com.azabost.quest.users.repository.UsersRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        .build()
    private val userDao: UserDao = database.userDao()
    private val usersRepository: UsersRepository = UsersRepositoryImpl(userDao)


    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `test UsersRepo for inserting user in db and retrieving the same inserted user`() = runTest {

        usersRepository.createUser("John", "Doe")

        val users = usersRepository.getUsers().first()

        assertEquals(1, users.size)
        assertEquals("John", users.first().firstName)
        assertEquals("Doe", users.first().lastName)
    }

    @Test
    fun `test UserDAO for inserting user in db and retrieving the same inserted user`() = runTest {

        userDao.insertUser(UserJdo(firstName = "John", lastName = "Doe"))

        val users = userDao.getAllUsers().first()

        assertEquals(1, users.size)
        assertEquals("John", users.first().firstName)
        assertEquals("Doe", users.first().lastName)
    }
}