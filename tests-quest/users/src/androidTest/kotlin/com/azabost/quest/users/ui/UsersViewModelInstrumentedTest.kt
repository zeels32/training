package com.azabost.quest.users.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.azabost.quest.resources.AndroidStringResources
import com.azabost.quest.users.data.dao.UserDao
import com.azabost.quest.users.data.model.UserJdo
import com.azabost.quest.users.repository.UsersRepository
import com.azabost.quest.users.repository.UsersRepositoryImpl
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersViewModelInstrumentedTest {


    private val userDao: UserDao = mockk{
        coJustRun {
            insertUser(any<UserJdo>())
        }
        coEvery {
            getAllUsers()
        } returns flowOf(emptyList())
    }
    private val context: Context = ApplicationProvider.getApplicationContext();
    private val userRepository: UsersRepository = UsersRepositoryImpl(userDao)
    private val stringResources = AndroidStringResources(context)
    private val viewModel: UsersViewModel = UsersViewModel(
        usersRepository = userRepository,
        stringResources = stringResources
    )


    @Test
    fun testing_the_success_case_of_first_name_and_last_name_for_the_createUser_function() = runTest {
        viewModel.toasts.test {
            viewModel.createUser("John", "Doe")
            awaitItem() shouldBe "User John Doe created"
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun testing_the_error_case_of_first_name_and_last_name_for_the_createUser_function() = runTest {

        viewModel.toasts.test {
            viewModel.createUser("", "Doe")
            awaitItem() shouldBe "Failed to create user  Doe"
            cancelAndIgnoreRemainingEvents()
        }
    }

}
