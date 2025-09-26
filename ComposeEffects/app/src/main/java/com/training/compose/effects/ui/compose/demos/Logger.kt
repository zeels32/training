package com.training.compose.effects.ui.compose.demos

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

@Composable
fun Logger(count: Int) {
    SideEffect {
        Log.e("Logger", "Current count is $count")
    }
    Text("Count: $count")
}