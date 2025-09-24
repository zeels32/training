package com.training.kotlincoroutines.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val counterStateFlow = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)


    val counterSharedFlow = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .shareIn(viewModelScope, SharingStarted.Lazily, 0)


    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    private val _showToastShared = MutableSharedFlow<Boolean>(1)
    val showToastShared: SharedFlow<Boolean> = _showToastShared
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )


    fun launchToastFromState() {
        _showToast.value = true
    }

    fun launchToastFromShared() = viewModelScope.launch {
        _showToastShared.emit(true)
    }
}
