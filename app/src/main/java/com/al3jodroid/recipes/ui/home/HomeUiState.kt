package com.al3jodroid.recipes.ui.home

sealed class HomeUiState {
    //states related with data/business
    data object Initial : HomeUiState()
    data object Loading : HomeUiState()
    data object Empty : HomeUiState()
    data object Unavailable : HomeUiState()
    data class Success(val info: String) : HomeUiState()
}