package com.al3jodroid.recipes.ui.home


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val stateValueCollect = homeViewModel.uiState.collectAsState().value
    //render the composable components
    Column {
        RenderHomeState(stateValueCollect)
        RenderButton(stateValueCollect,homeViewModel::callExecution)
    }
}

@Composable
fun RenderButton(homeUiState: HomeUiState, onClick: () -> Unit) {
    Button(onClick = onClick) {
        if (homeUiState is HomeUiState.Initial) {
            Text("Button to Success State")
        } else {
            Text("Button to Initial State")
        }
    }
}

@Composable
fun RenderHomeState(homeUiState: HomeUiState) {
    when (homeUiState) {
        is HomeUiState.Empty -> RenderEmpty()
        is HomeUiState.Initial -> RenderInitial()
        is HomeUiState.Loading -> RenderLoading()
        is HomeUiState.Success -> RenderSuccess(homeUiState.info)
        is HomeUiState.Unavailable -> RenderUnavailable()
    }
}

@Composable
fun RenderEmpty() {
    TODO("Not yet implemented")
}

@Composable
fun RenderInitial(modifier: Modifier = Modifier) {
    Text(
        text = "Initial State.",
        modifier = modifier
    )
}

@Composable
fun RenderLoading() {
    TODO("Not yet implemented")
}

@Composable
fun RenderSuccess(info: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello!: $info.",
        modifier = modifier
    )
}

@Composable
fun RenderUnavailable() {
    TODO("Not yet implemented")
}







