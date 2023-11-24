package com.al3jodroid.recipes.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.al3jodroid.recipes.LocalNavController
import com.al3jodroid.recipes.R
import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.ui.navigation.navigateToRecipe

/**
 * The initial Compose component that contain the search and list result to view
 * the recipes found based on the input of the user
 */
@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val stateValueCollect = homeViewModel.uiState.collectAsState().value
    //render the composable components
    Column {
        RenderTitle()
        RenderFormSearch(homeViewModel)
        RenderHomeState(stateValueCollect)
    }
}

/**
 * Separate the rendering of the title from the logic of render the info from server
 */
@Composable
fun RenderTitle(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.End, modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.yape_recipe_title),
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(text = stringResource(R.string.search_app_parenthesis))
    }
}

/**
 * Render a simple search input, with the properties of read, validate and call the remote search
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RenderFormSearch(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardActions = KeyboardActions(onSearch = {
        //this code its repeated, and can be extracted
        //but its a conflict between @composable scope, and ()->Unit reference
        if (text.isNotEmpty()) {
            homeViewModel.searchRecipes(text)
            keyboardController?.hide()
        }
    })

    OutlinedTextField(trailingIcon = {
        IconButton(onClick = {
            //this code its repeated, and can be extracted
            //but its a conflict between @composable scope, and ()->Unit reference
            if (text.isNotEmpty()) {
                homeViewModel.searchRecipes(text)
                keyboardController?.hide()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.home_icon)
            )
        }
    },
        modifier = modifier.fillMaxWidth(),
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        value = text,
        onValueChange = { text = it },
        singleLine = true,
        label = { Text(stringResource(R.string.search_recipes_hint)) })
}

/**
 * The only place where the states are read, and show specific UI states based on it
 * for the initial search screen
 */
@Composable
fun RenderHomeState(stateValueCollect: HomeUiState) {
    when (stateValueCollect) {
        is HomeUiState.Empty -> RenderEmpty()
        is HomeUiState.Initial -> RenderInitial()
        is HomeUiState.Loading -> RenderLoading()
        is HomeUiState.Success -> RenderSuccess(stateValueCollect.info)
        is HomeUiState.Unavailable -> RenderUnavailable()
    }
}

/**
 * When the request from server don't retrieve nothing from a search
 */
@Composable
fun RenderEmpty(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.no_result_ingredients_search),
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

/**
 * A explicative and welcome message that encourage to start a search
 */
@Composable
fun RenderInitial(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.start_typing),
            modifier = modifier.padding(8.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}

/**
 * When a coroutine of search its running this its the screen that show the UI state to the user
 */
@Composable
fun RenderLoading(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

/**
 * When the coroutine from the Viewmodel retrieve at least one result this Composable function
 * render al list with the results related to it.
 */
@Composable
fun RenderSuccess(info: List<RecipeResult>, modifier: Modifier = Modifier) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val navController = LocalNavController.current

    LazyVerticalGrid(
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(info.size) { index ->
            RecipeItem(recipe = info[index], onRecipeClick = {
                homeViewModel.onClickRecipeFromList(info[index])
                navController.navigateToRecipe(info[index].id)
            })
        }
    }
}

/**
 * When a comm. problem or parsing happens this screen shows to communicate to the user the problem
 */
@Composable
fun RenderUnavailable(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.no_results),
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

/**
 * An specific separation of a RecipeItem, composable component shown in the list that
 * represent a recipe, and show an image and a name of it
 */
@Composable
private fun RecipeItem(
    recipe: RecipeResult, onRecipeClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(all = 8.dp)
            .clickable(onClick = onRecipeClick)
            .height(256.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

    ) {
        Text(modifier = modifier.padding(8.dp), text = recipe.name)
        renderImageRecipe(recipe = recipe)
    }
}

/**
 * An special compose component that helps ot load an image from the web; be aware that its public
 * due to the advantage of using it in other places of the app, wit different [Modifier] properties
 */
@Composable
fun renderImageRecipe(recipe: RecipeResult, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        model = recipe.thumbnailUrl,
        contentDescription = stringResource(R.string.image_thumbnail_description, recipe.name),
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}