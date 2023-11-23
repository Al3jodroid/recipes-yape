package com.al3jodroid.recipes.ui.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.al3jodroid.recipes.model.data.RecipeResult

@Composable
fun RecipeScreen(recipeId: String) {
    val recipeViewModel = hiltViewModel<RecipeViewModel>()
    val stateValueCollect = recipeViewModel.uiState.collectAsState().value
    recipeViewModel.getRecipeById(recipeId)
    RenderRecipeState(recipeUiState = stateValueCollect)
}

@Composable
fun RenderRecipeState(recipeUiState: RecipeUiState) {
    when (recipeUiState) {
        RecipeUiState.Initial -> RenderInitialDetails()
        RecipeUiState.Loading -> RenderLoadingDetails()
        is RecipeUiState.Success -> RenderSuccessDetails(recipeUiState.info)
        RecipeUiState.Unavailable -> RenderUnavailableDetails()
    }
}

@Composable
fun RenderInitialDetails() {
    Text(
        text = "Initial State.",
    )
}

@Composable
fun RenderLoadingDetails() {
    Text(
        text = "Loading State.",
    )
}
@Composable
fun RenderSuccessDetails(recipe: RecipeResult) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Recipe", "Map")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> DetailsCard(recipe)
            1 -> MapCard(recipe)
        }
    }
}

@Composable
fun RenderUnavailableDetails() {
    Text(
        text = "Unavailable State.",
    )
}









