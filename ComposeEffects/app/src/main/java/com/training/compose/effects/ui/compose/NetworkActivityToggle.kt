package com.training.compose.effects.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NetworkActivityToggle(showNetworkListener: Boolean, onToggle: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "Network Listener is active $showNetworkListener",
            modifier = Modifier
        )

        Switch(
            checked = showNetworkListener,
            onCheckedChange = {
                onToggle()
            },
            modifier = Modifier
        )
    }
}