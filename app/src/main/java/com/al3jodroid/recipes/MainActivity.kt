package com.al3jodroid.recipes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.al3jodroid.recipes.ui.connectivity.ConnectivityIndicator
import com.al3jodroid.recipes.ui.connectivity.ConnectivityViewModel
import com.al3jodroid.recipes.ui.home.HomeScreen
import com.al3jodroid.recipes.ui.home.HomeViewModel
import com.al3jodroid.recipes.ui.recipe.RecipeScreen
import com.al3jodroid.recipes.ui.theme.RecipesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : RecipesActivity() {
    //A classic way to call the viewModel without hilt
    private val connectivityViewModel: ConnectivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityViewModel.startListenConnectivity()

        setContent {
            val navController = rememberNavController()
            RecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        ConnectivityIndicator(connectivityViewModel) //Indicator of connectivity
                        //HomeScreen()
                        RecipesNavHost(
                            navController = navController,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecipesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(
                onRecipeClick = { recipe ->
                    homeViewModel.onClickRecipeFromList(recipe)
                    //TODO MUST fix this navigation based on states
                    navController.navigateToRecipe(recipe.id)
                })
        }

        composable(
            route = Recipe.routeWithArgs,
            arguments = Recipe.arguments,
            deepLinks = Recipe.deepLinks
        ) { navBackStackEntry ->
            val recipeId =
                navBackStackEntry.arguments?.getString(Recipe.recipeIdArg)
            RecipeScreen(recipeId ?: "")
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateToRecipe(recipeId: String) {
    this.navigateSingleTopTo("${Recipe.route}/$recipeId")
}

