package com.al3jodroid.recipes.ui.home

import android.util.Log
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
import com.al3jodroid.recipes.LocalNavController
import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.ui.navigation.navigateToRecipe

@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    //render the composable components
    Column {
        RenderHomeState(homeViewModel)
        RenderButton(onSearchClick = { homeViewModel.searchRecipes("chicken") })
    }
}

@Composable
fun RenderButton(onSearchClick: () -> Unit) {
    Button(onClick = onSearchClick) {
        Text("Search chicken")
    }
}

@Composable
fun RenderHomeState(homeViewModel: HomeViewModel) {
    val stateValueCollect = homeViewModel.uiState.collectAsState().value
    Log.d("RenderHomeState", stateValueCollect.toString())

    when (stateValueCollect) {
        is HomeUiState.Empty -> RenderEmpty()
        is HomeUiState.Initial -> RenderInitial()
        is HomeUiState.Loading -> RenderLoading()
        is HomeUiState.Success -> RenderSuccess(stateValueCollect.info)

        is HomeUiState.Unavailable -> RenderUnavailable()

    }
}

@Composable
fun RenderEmpty(modifier: Modifier = Modifier) {
    Text(
        text = "Empty Stuff.", modifier = modifier
    )
}

@Composable
fun RenderInitial(modifier: Modifier = Modifier) {
    Text(
        text = "Initial State.", modifier = modifier
    )
}

@Composable
fun RenderLoading(modifier: Modifier = Modifier) {
    Text(
        text = "Loading...", modifier = modifier
    )
}

@Composable
fun RenderSuccess(info: List<RecipeResult>, modifier: Modifier = Modifier) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val navController = LocalNavController.current

    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        items(info) {
            Recipe(recipe = it, onRecipeClick = {
                homeViewModel.onClickRecipeFromList(it)
                navController.navigateToRecipe(it.id)
            })
        }
    }
}

@Composable
fun RenderUnavailable(modifier: Modifier = Modifier) {
    Text(
        text = "Unavailable.", modifier = modifier
    )
}

@Composable
private fun Recipe(recipe: RecipeResult, onRecipeClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onRecipeClick)
    ) {
        Text(recipe.name)
    }
}






