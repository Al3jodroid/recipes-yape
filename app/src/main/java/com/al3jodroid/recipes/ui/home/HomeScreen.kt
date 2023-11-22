package com.al3jodroid.recipes.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.al3jodroid.recipes.model.data.RecipeResult

@Composable
fun HomeScreen(onRecipeClick: (RecipeResult) -> Unit = {}) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val stateValueCollect = homeViewModel.uiState.collectAsState().value
    //render the composable components
    Column {
        RenderHomeState(stateValueCollect, onRecipeClick)
        RenderButton(stateValueCollect) { homeViewModel.searchRecipes("chicken") }
    }
}

@Composable
fun RenderButton(homeUiState: HomeUiState, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Search chicken")
    }
}

@Composable
fun RenderHomeState(homeUiState: HomeUiState, onRecipeClick: (RecipeResult) -> Unit) {
    when (homeUiState) {
        is HomeUiState.Empty -> RenderEmpty()
        is HomeUiState.Initial -> RenderInitial()
        is HomeUiState.Loading -> RenderLoading()
        is HomeUiState.Success -> RenderSuccess(homeUiState.info, onRecipeClick)
        is HomeUiState.Unavailable -> RenderUnavailable()
        is HomeUiState.NavigateToRecipe -> GoToRecipeDetails()
    }
}

@Composable
fun GoToRecipeDetails() {

}

@Composable
fun RenderEmpty(modifier: Modifier = Modifier) {
    Text(
        text = "Empty Stuff.",
        modifier = modifier
    )
}

@Composable
fun RenderInitial(modifier: Modifier = Modifier) {
    Text(
        text = "Initial State.",
        modifier = modifier
    )
}

@Composable
fun RenderLoading(modifier: Modifier = Modifier) {
    Text(
        text = "Loading...",
        modifier = modifier
    )
}

@Composable
fun RenderSuccess(
    info: List<RecipeResult>,
    onRecipeClick: (RecipeResult) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
        items(info) {
            Recipe(recipe = it, onclick = onRecipeClick)
        }
    }
}

@Composable
fun RenderUnavailable(modifier: Modifier = Modifier) {
    Text(
        text = "Unavailable.",
        modifier = modifier
    )
}

@Composable
private fun Recipe(recipe: RecipeResult, onclick: (RecipeResult) -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onclick(recipe) }
    ) {
        Text(
            text = recipe.name,
        )
    }
}







