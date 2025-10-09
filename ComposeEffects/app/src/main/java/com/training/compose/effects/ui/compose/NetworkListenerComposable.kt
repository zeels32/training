package com.training.compose.effects.ui.compose

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun NetworkListenerComposable(onStatusChange: (Boolean) -> Unit) {
    val context = LocalContext.current

    // Seconds since this listener became active
    val elapsedSeconds by produceState(initialValue = 0) {
        while (true) {
            delay(1000)
            value += 1 // increment each second
        }
    }

    // DisposableEffect to register/unregister network callback
    DisposableEffect(Unit) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onStatusChange(true)
            }

            override fun onLost(network: Network) {
                onStatusChange(false)
            }
        }

        // Register
        connectivityManager.registerDefaultNetworkCallback(callback)

        // Unregister on dispose
        onDispose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    // 👇 This runs after the composable is placed in the UI tree
    SideEffect {
        Log.e("compose", "NetworkListenerComposable:  Listening to network changes" )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Loading details for $elapsedSeconds s")
    }
}
