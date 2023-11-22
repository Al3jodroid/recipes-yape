package com.al3jodroid.recipes.ui.recipe

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.al3jodroid.recipes.model.data.RecipeResult

@Composable
fun RecipeScreen(recipeId: String) {
    val recipeViewModel = hiltViewModel<RecipeViewModel>()
    val stateValueCollect = recipeViewModel.uiState.collectAsState().value
    recipeViewModel.getRecipeById(recipeId)
    //render the composable components
    RenderRecipeState(recipeUiState = stateValueCollect)
}

@Composable
fun RenderRecipeState(recipeUiState: RecipeUiState) {
    when (recipeUiState) {
        RecipeUiState.Initial -> RenderInitialRecipe()
        RecipeUiState.Loading -> RenderLoadingRecipe()
        is RecipeUiState.Success -> RenderSuccessRecipe(recipeUiState.info)
        RecipeUiState.Unavailable -> RenderUnavailableRecipe()
    }

}

@Composable
fun RenderUnavailableRecipe() {
    Text(
        text = "Unavailable State.",
    )
}

@Composable
fun RenderSuccessRecipe(info: RecipeResult) {
    Text(
        text = info.toString()
    )
}

@Composable
fun RenderLoadingRecipe() {
    Text(
        text = "Loading State.",
    )
}

@Composable
fun RenderInitialRecipe() {
    Text(
        text = "Initial State.",
    )
}
