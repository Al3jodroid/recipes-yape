package com.al3jodroid.recipes.ui.connectivity

/**
 *  UI states related with connectivity status, not data/business model
 */
sealed class ConnectivityUiState {
    data object UnknownNetwork : ConnectivityUiState()
    data object ConnectedNetwork : ConnectivityUiState()
    data object DisconnectedNetwork : ConnectivityUiState()
}