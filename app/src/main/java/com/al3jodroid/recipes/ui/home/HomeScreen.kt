package com.al3jodroid.recipes.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.al3jodroid.recipes.LocalNavController
import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.ui.navigation.navigateToRecipe

@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    //render the composable components
    Column {
        RenderFormSearch(homeViewModel)
        RenderHomeState(homeViewModel)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RenderFormSearch(homeViewModel: HomeViewModel) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardActions = KeyboardActions(
        onSearch = {
            if(text.isNotEmpty()){
                homeViewModel.searchRecipes(text)
                keyboardController?.hide()
            }
        }
    )

    Row {
        OutlinedTextField(
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            label = { Text("Lets search some recipes!") }
        )
        RenderButton(onSearchClick = {
            if(text.isNotEmpty()){
                homeViewModel.searchRecipes(text)
                keyboardController?.hide()
            }
        })
    }
}

@Composable
fun RenderButton(onSearchClick: () -> Unit) {

    IconButton(onClick = onSearchClick) {
        Icon(imageVector = Icons.Filled.Search, contentDescription = "Button Search")
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
            RecipeItem(recipe = it, onRecipeClick = {
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
private fun RecipeItem(recipe: RecipeResult, onRecipeClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onRecipeClick)
    ) {
        Text(recipe.name)
        AsyncImage(
            model = recipe.thumbnailUrl,
            contentDescription = null,
        )
    }
}






