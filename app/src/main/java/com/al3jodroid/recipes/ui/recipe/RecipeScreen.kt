package com.al3jodroid.recipes.ui.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.al3jodroid.recipes.R
import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.ui.home.renderImageRecipe


/**
 * Define the "root" of the details and map screen related with a single recipe to show
 */
@Composable
fun RecipeScreen(recipeId: String, modifier: Modifier = Modifier) {
    val recipeViewModel = hiltViewModel<RecipeViewModel>()
    val stateValueCollect = recipeViewModel.uiState.collectAsState().value
    recipeViewModel.getRecipeById(recipeId)
    RenderRecipeState(recipeUiState = stateValueCollect, modifier)
}

/**
 * Evaluate the emission of states in this place only for change the UI depending of the [RecipeUiState]
 */
@Composable
fun RenderRecipeState(recipeUiState: RecipeUiState, modifier: Modifier = Modifier) {
    when (recipeUiState) {
        is RecipeUiState.Loading -> RenderLoadingDetails(modifier)
        is RecipeUiState.Success -> RenderSuccessDetails(recipeUiState.info, modifier)
        is RecipeUiState.Unavailable -> RenderUnavailableDetails(modifier)
    }
}

/**
 * When a coroutine its running this loading state its shown
 */
@Composable
fun RenderLoadingDetails(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

/**
 * When an state of success load, here start to render a couple of tabs that going to show
 * the details of a recipe and a map with the "origin location"
 */
@Composable
fun RenderSuccessDetails(recipe: RecipeResult, modifier: Modifier = Modifier) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Details", "Location")

    Column(modifier = Modifier.fillMaxWidth()) {
        renderImageRecipe(recipe, modifier.height(170.dp))
        RenderTitle(recipe, modifier)
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index })
            }
        }
        when (tabIndex) {
            0 -> DetailsCard(recipe)
            1 -> MapCard(recipe)
        }
    }
}

/**
 * Show an specific title based on the [recipe] object that contain the info to show
 */
@Composable
fun RenderTitle(recipe: RecipeResult, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.End, modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = recipe.name,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Medium)
        )
        Text(
            color = MaterialTheme.colorScheme.primary,
            text = stringResource(R.string.origin, recipe.origin)
        )
    }
}

/**
 * In the case that the reading information from the data related with a recipe its wrong or corrupted
 * this state cover the case of wrong behaviour (also a missing internet connection can be a cause)
 */
@Composable
fun RenderUnavailableDetails(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.details_recipe_cant_load),
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}