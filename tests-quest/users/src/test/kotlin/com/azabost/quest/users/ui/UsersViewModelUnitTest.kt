package com.azabost.quest.users.ui

import app.cash.turbine.test
import com.azabost.quest.resources.StringResources
import com.azabost.quest.users.R
import com.azabost.quest.users.repository.UsersRepository
import com.azabost.quest.users.repository.UsersRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UsersViewModelUnitTest {

    private lateinit var viewModel: UsersViewModel
    private lateinit var userRepository: UsersRepository
    private lateinit var stringResources: StringResources

    @BeforeEach
    fun setUp() {
        stringResources = mockk()

        // Stub getString for success
        every { stringResources.getString(R.string.user_created, *anyVararg()) } answers {
            val args = arg<Array<Any>>(1)
            val first = args[0] as String
            val last = args[1] as String
            "User $first $last created"
        }

        // Stub getString for failure
        every { stringResources.getString(R.string.user_not_created, *anyVararg()) } answers {
            val args = arg<Array<Any>>(1)
            val first = args[0] as String
            val last = args[1] as String
            "Failed to create user $first $last"
        }

        userRepository = UsersRepositoryImpl()
        viewModel = UsersViewModel(
            usersRepository = userRepository,
            stringResources = stringResources
        )
    }


    @Test
    fun `testing the success case of first name and last name for the createUser function`() = runTest {
        val firstName = "John"
        val lastName = "Doe"

        viewModel.toasts.test {
            viewModel.createUser(firstName, lastName)
            val result = awaitItem()
            Assertions.assertEquals("User $firstName $lastName created", result)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `testing the error case of first name and last name for the createUser function`() = runTest {
        val firstName = ""
        val lastName = "Doe"
        viewModel.toasts.test {
            viewModel.createUser(firstName, lastName)
            val result = awaitItem()
            Assertions.assertEquals("Failed to create user $firstName $lastName", result)
            cancelAndIgnoreRemainingEvents()
        }
    }

}