package com.al3jodroid.recipes.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface RecipeDestination {
    val route: String
}

object Home : RecipeDestination {
    override val route = "home"
}

object Recipe : RecipeDestination {
    override val route = "detail"

    const val recipeIdArg = "recipe_id"
    val routeWithArgs = "$route/{$recipeIdArg}"
    val args = listOf(navArgument(recipeIdArg) {
        type = NavType.StringType
    })
    val deepLinks = listOf(
        navDeepLink { uriPattern = "recipes://$route/{$recipeIdArg}" }
    )
}
