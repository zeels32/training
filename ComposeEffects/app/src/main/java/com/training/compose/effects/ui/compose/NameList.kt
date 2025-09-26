package com.training.compose.effects.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun NameList(names: List<String>) {
    val longNames by remember {
        derivedStateOf { names.filter { it.length > 5 } }
    }
    Text("Long names: ${longNames.size}")


//    val data = remember ( key) { someCalculation(key) } // this will re-execute when key changes
//    val data = remember { derivedStateOf { someCalculation(key) } // this will re-execute but only recompose if the value differences

}