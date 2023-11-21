package com.al3jodroid.recipes.ui.connectivity

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ConnectivityIndicator(connectivityViewModel: ConnectivityViewModel) {
    val connectivityState = connectivityViewModel.uiState.collectAsState()
    when (connectivityState.value) {
        is ConnectivityUiState.ConnectedNetwork -> renderConnectedState()
        is ConnectivityUiState.DisconnectedNetwork -> renderDisconnectedState()
        is ConnectivityUiState.UnknownNetwork -> renderUnknownState()
    }
}

@Composable
fun renderConnectedState() {
    Text(
        text = "All systems go!",
        modifier = Modifier.background(color = Color.Green)
    )
}

@Composable
fun renderDisconnectedState() {
    Text(
        text = "No network.",
        modifier = Modifier.background(color = Color.Yellow)
    )
}

@Composable
fun renderUnknownState() {
    Text(
        text = "Unknown network state...",
        modifier = Modifier.background(color = Color.LightGray)
    )
}

