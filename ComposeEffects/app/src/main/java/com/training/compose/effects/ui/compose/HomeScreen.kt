package com.training.compose.effects.ui.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var items by remember { mutableStateOf(listOf<String>()) }
    var counter by remember { mutableIntStateOf(1) }

    // Network connectivity state (default false)
    var isConnected by remember { mutableStateOf(false) }

    // Scroll state for LazyColumn
    val listState = rememberLazyListState()

    // Automatically populate a few dozens of items on first composition
    LaunchedEffect(Unit) {
        val initialItems = (1..5).map { "Item $it" }
        items = initialItems
        counter = initialItems.size + 1
    }

    // Derived state to determine FAB visibility
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(if (isConnected) "Connected" else "No network")
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (showScrollToTop) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                ) {

                    Image(
                        imageVector = androidx.compose.material.icons.Icons.Default.KeyboardArrowUp,
                        contentDescription = "Scroll to top"
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            var text by remember { mutableStateOf("") }
            InputTextField {
                text = it
            }

            Button(
                onClick = {
                    if (text.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please enter a name" )
                        }
                    }
                    items = items + text
                    counter++
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Add to list")
            }

            // Toggle for showing network listener
            var showNetworkListener by remember { mutableStateOf(false) }

            NetworkActivityToggle(showNetworkListener) {
                showNetworkListener = !showNetworkListener
            }

            if (showNetworkListener) {
                NetworkListenerComposable(
                    onStatusChange = { status ->
                        isConnected = status
                    }
                )
            }

            ItemList(listState, items, scope, snackbarHostState)


            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo }
                    .collectLatest { layoutInfo ->
                        val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                        if (lastVisibleIndex == items.size - 1) {
                            Log.e("compose", "ScrollToBottomListener: Scrolled to bottom")
                        }
                    }
            }
        }
    }
}

@Composable
fun InputTextField(function: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            function(it)
        },
        placeholder = { Text("Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

}


