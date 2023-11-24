package com.al3jodroid.recipes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.al3jodroid.recipes.ui.home.HomeScreen
import com.al3jodroid.recipes.ui.recipe.RecipeScreen

@Composable
fun BuildRecipesNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController, startDestination = Home.route, modifier = modifier
    ) {
        composable(route = Home.route) { HomeScreen() }
        composable(
            route = Recipe.routeWithArgs,
            arguments = Recipe.args,
            deepLinks = Recipe.deepLinks
        ) { navBackStackEntry ->
            val recipeId = navBackStackEntry.arguments?.getString(Recipe.recipeIdArg)
            RecipeScreen(recipeId ?: "")
        }
    }
}

fun NavHostController.navigateToRecipe(recipeId: String) {
    this.navigateSingleTopTo("${Recipe.route}/$recipeId")
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    popUpTo(
        this@navigateSingleTopTo.graph.findStartDestination().id
    ) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

