package com.training.kotlincoroutines.presentation.ui.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.training.kotlincoroutines.presentation.ui.theme.KotlincoroutinesTheme
import com.training.kotlincoroutines.presentation.viewmodel.CounterViewModel
import com.training.kotlincoroutines.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewmodel by viewModels<MainViewModel>()
    private val counterViewModel by viewModels<CounterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*lifecycleScope.launch {
            viewmodel.counterStateFlow.collect {
                Log.e("flow", "onCreate: state $it")
            }
        }

        lifecycleScope.launch {
            viewmodel.counterSharedFlow.collect {
                Log.e("flow", "onCreate shared: $it")
            }
        }*/

        lifecycleScope.launch {
            viewmodel.showToast.collect {
                if (it) {
                    Toast.makeText(this@MainActivity, "toast from the stateflow $it", Toast.LENGTH_SHORT).show()
                }
                Log.e("flow", "toast from the stateflow $it" )
            }
        }

        lifecycleScope.launch {
            viewmodel.showToastShared.collect {
                if (it) {
                    Toast.makeText(this@MainActivity, "toast from the sharedFlow $it", Toast.LENGTH_SHORT).show()
                }
                Log.e("flow", "toast from the sharedFlow $it" )
            }
        }





        enableEdgeToEdge()
        setContent {
            KotlincoroutinesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Greeting(
                            name = "Launch Toast From StateFlow",
                            modifier = Modifier
                                .padding(innerPadding),
                            onClick = {
                                viewmodel.launchToastFromState()
                            }
                        )

                        Greeting(
                            name = "Launch Toast From SharedFlow",
                            modifier = Modifier
                                .padding(innerPadding),
                            onClick = {
                                viewmodel.launchToastFromShared()
                            }
                        )
//                        CounterScreen(counterViewModel.counter)
                    }

                }
            }
        }
    }
}

@Composable
fun CounterScreen(counter: StateFlow<Int>) {
    val count by counter.collectAsState()
    Log.e("flow", "CounterScreen: $count")
    Text("Count: $count")
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {

    Text(
        text = name,
        modifier = modifier.clickable {
            onClick()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlincoroutinesTheme {
        Greeting("Android")
    }
}