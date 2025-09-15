package com.azabost.quest.users.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.azabost.quest.coroutines.MainDispatcherRule
import com.azabost.quest.resources.AndroidStringResources
import com.azabost.quest.resources.StringResources
import com.azabost.quest.users.R
import com.azabost.quest.users.repository.UsersRepository
import com.azabost.quest.users.repository.UsersRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.checkerframework.checker.units.qual.s
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [34])
@RunWith(AndroidJUnit4::class)
class UsersViewModelRoboTest {


    private val context: Context = ApplicationProvider.getApplicationContext();
    private val userRepository: UsersRepository = UsersRepositoryImpl()
    private val stringResources = AndroidStringResources(context)
    private val viewModel: UsersViewModel = UsersViewModel(
        usersRepository = userRepository,
        stringResources = stringResources
    )


    @Test
    fun `testing the success case of first name and last name for the createUser function`() = runTest {
        viewModel.toasts.test {
            viewModel.createUser("John", "Doe")
            Assertions.assertEquals("User John Doe created", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `testing the error case of first name and last name for the createUser function`() = runTest {

        viewModel.toasts.test {
            viewModel.createUser("", "Doe")
            val result = awaitItem()
            Assertions.assertEquals("Failed to create user  Doe", result)
            cancelAndIgnoreRemainingEvents()
        }
    }

}