package com.training.kotlincoroutines.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.kotlincoroutines.repository.CounterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CounterViewModel(
    private val repo: CounterRepository = CounterRepository()
) : ViewModel() {

    val counter: StateFlow<Int> = repo.counterFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0
        )

}
