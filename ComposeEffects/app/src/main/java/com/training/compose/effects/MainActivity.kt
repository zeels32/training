package com.training.compose.effects

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.training.compose.effects.data.IRepository
import com.training.compose.effects.ui.compose.ComposeSplashScreen
import com.training.compose.effects.ui.compose.HomeScreen
import com.training.compose.effects.ui.compose.demos.AirplaneModeScreen
import com.training.compose.effects.ui.compose.demos.Logger
import com.training.compose.effects.ui.compose.demos.NameList
import com.training.compose.effects.ui.compose.demos.SaveButton
import com.training.compose.effects.ui.compose.demos.SearchBar
import com.training.compose.effects.ui.compose.demos.UserScreen
import com.training.compose.effects.ui.theme.ComposeEffectsTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ComposeEffectsTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    var showSplash by remember { mutableStateOf(true) }

                    if (showSplash) {
                        ComposeSplashScreen(
                            onTimeout = { showSplash = false }
                        )
                    } else {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Demo(repository: IRepository) {
    val count = remember { mutableStateOf(2) }
    UserScreen(
        repository = repository,
    )
    Spacer(
        modifier = Modifier
            .background(color = androidx.compose.ui.graphics.Color.LightGray)
            .clickable {
                count.value++
            }
            .fillMaxWidth()
            .padding(top = 10.dp)
    )
    AirplaneModeScreen()
    SaveButton()
    Logger(count = count.value)
    NameList(
        listOf("Android", "Compose", "Kotlin", "Java", "Swift", "ObjectiveC")
    )
    SearchBar {
        Log.e("flow", "Search for $it")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeEffectsTheme {
        Greeting("Android")
    }
}