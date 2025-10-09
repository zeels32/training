package com.training.compose.effects.ui.compose.demos

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.training.compose.effects.data.IRepository
import com.training.compose.effects.data.User
import kotlinx.coroutines.delay

@Composable
fun UserScreen(repository: IRepository) {

    var userId by remember { mutableStateOf("1") }
    var user by remember { mutableStateOf<User?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Simulate user ID change after 5 seconds
        delay(5000)
        userId = "2"
        Log.e("recompose", "onCreate: $userId")
    }

    LaunchedEffect(userId) {
        Log.e("recompose", "UserScreen: $userId")
        user = repository.loadUser(userId) // suspend function
    }


    Text(user?.name ?: "Loading...")
}