package com.training.compose.effects.ui.compose

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SaveButton  () {
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    Button(
        onClick = {
            scope.launch {

                delay(1000)
                Toast.makeText(context, "Button is Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Text("Save")
    }
}
