package com.al3jodroid.recipes.ui.connectivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.al3jodroid.recipes.R

/**
 * A definition of a connectivity indicator that changes based
 * on a observer related to the network state of the device [ConnectivityViewModel]
 */
@Composable
fun ConnectivityIndicator(connectivityViewModel: ConnectivityViewModel) {
    val connectivityState = connectivityViewModel.uiState.collectAsState()
    when (connectivityState.value) {
        is ConnectivityUiState.ConnectedNetwork -> {} //everybody happy, no message :)
        is ConnectivityUiState.DisconnectedNetwork -> renderDisconnectedState()
        is ConnectivityUiState.UnknownNetwork -> renderUnknownState()
    }
}

/**
 * When an state of no network connectivity happens this UI message its show
 */
@Composable
fun renderDisconnectedState() {
    Text(
        text = stringResource(R.string.no_internet),
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Right,
    )
}


/**
 * Sometimes the state of a network connections its unknown (and is not connected for sure)
 * this message its shown
 */
@Composable
fun renderUnknownState() {
    Text(
        text = stringResource(R.string.unknown_network_state),
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Right,
    )
}