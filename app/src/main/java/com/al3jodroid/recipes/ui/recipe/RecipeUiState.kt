package com.al3jodroid.recipes.ui.recipe

import com.al3jodroid.recipes.model.data.RecipeResult

sealed class RecipeUiState {
    //states related with data/business
    data object Initial : RecipeUiState()
    data object Loading : RecipeUiState()
    data object Unavailable : RecipeUiState()
    data class Success(val info: RecipeResult) : RecipeUiState()
}