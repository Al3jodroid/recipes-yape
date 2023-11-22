package com.al3jodroid.recipes.ui.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.al3jodroid.recipes.ui.home.HomeViewModel

@Composable
fun DetailsCard(recipeId: String?) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val stateValueCollect = homeViewModel.uiState.collectAsState().value
    //render the composable components
    Column {
        Text(text = "Details recipe Screen")
    }
}