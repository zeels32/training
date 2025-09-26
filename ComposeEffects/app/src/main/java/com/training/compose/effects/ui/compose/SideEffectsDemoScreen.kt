package com.training.compose.effects.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SideEffectsDemoScreen(userId: String, onTimeout: () -> Unit) {
    val listState = rememberLazyListState()
    var counter by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    // 1. LaunchedEffect -> load initial data and collect a flow
    LaunchedEffect(userId) {
        println("flow LaunchedEffect: Loading user data for $userId...")
        // Fake initial load
        delay(1000)
        println("flow User data loaded")

        // Collect flow of counter
        snapshotFlow { counter }
            .collect { println("flow Counter changed to $it") }
    }

    // 2. produceState -> load user profile
    val userState by produceState<String?>(initialValue = null, userId) {
        repeat(10) {
            delay(500) // pretend network call
            value = "User #$it $userId"
        }
    }

    // 3. derivedStateOf -> expensive computation (just demo)
    val isEven by remember {
        derivedStateOf { counter % 2 == 0 }
    }

    // 4. DisposableEffect -> register/unregister callback
    DisposableEffect(Unit) {
        println("flow Network callback registered")
        onDispose { println("flow Network callback unregistered") }
    }

    // 5. SideEffect -> run after every recomposition
    SideEffect {
        println("flow Recomposition complete. Current counter: $counter")
    }

    // 6. rememberUpdatedState -> keep latest onTimeout callback
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(Unit) {
        delay(5000)
        onTimeout()
//        currentOnTimeout() // Always latest callback
    }

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("👤 User: ${userState ?: "Loading..."}")
        Spacer(Modifier.height(8.dp))

        Text("Counter: $counter (Even? $isEven)")
        Row {
            Button(onClick = { counter++ }) {
                Text("Increment")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                scope.launch {
                    delay(1000)
                    println("flow Saved counter=$counter to DB")
                }
            }) {
                Text("Save")
            }
        }

        Spacer(Modifier.height(16.dp))

        // 7. snapshotFlow -> watch scroll position
        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex }
                .collect { index ->
                    println("flow First visible item index: $index")
                }
        }

        LazyColumn(state = listState, modifier = Modifier.weight(1f)) {
            items(50) { i ->
                Text("Item #$i", Modifier.padding(8.dp))
            }
        }
    }
}
