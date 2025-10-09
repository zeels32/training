package com.training.kotlincoroutines.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CounterRepository {
    private var counter = 0

    fun counterFlow(): Flow<Int> = flow {
        while (true) {
            delay(1000)
            emit(counter++)
            if (counter == 20) {
                break
            }
        }
    }
}
