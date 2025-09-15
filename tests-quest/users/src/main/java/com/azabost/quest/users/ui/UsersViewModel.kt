package com.azabost.quest.users.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azabost.quest.resources.StringResources
import com.azabost.quest.users.R
import com.azabost.quest.users.repository.User
import com.azabost.quest.users.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val stringResources: StringResources,
) : ViewModel() {

    val users: StateFlow<List<User>> = usersRepository.getUsers()

    private val _toasts = MutableSharedFlow<String>()
    val toasts: SharedFlow<String> = _toasts.asSharedFlow()

    fun createUser(firstName: String, lastName: String) {
        viewModelScope.launch {
            try {
                usersRepository.createUser(firstName = firstName, lastName = lastName)
                _toasts.emit(stringResources.getString(R.string.user_created, firstName, lastName))
            } catch (e: Exception) {
                _toasts.emit(stringResources.getString(R.string.user_not_created, firstName, lastName))
            }
        }
    }

    companion object {
        private const val TAG = "UsersViewModel"
    }
}