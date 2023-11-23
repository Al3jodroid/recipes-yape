package com.al3jodroid.recipes.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink


/**
 *Information needed on every Recipes navigation destination
 */
interface RecipeDestination {
    val icon: ImageVector
    val route: String

}
object Home : RecipeDestination {
    override val icon = Icons.Filled.Home
    override val route = "home"
}


object Recipe : RecipeDestination {
    override val icon = Icons.Filled.List
    override val route = "detail"

    const val recipeIdArg = "recipe_id"
    val routeWithArgs = "$route/{$recipeIdArg}"
    val args= listOf(navArgument(recipeIdArg) {
        type = NavType.StringType
    })
    val deepLinks = listOf(
        navDeepLink { uriPattern = "recipes://$route/{$recipeIdArg}"}
    )
}

object LocationRecipe : RecipeDestination {
    override val icon = Icons.Filled.LocationOn
    override val route = "location"

    const val locationRecipeIdArg = "recipe_id"
    val routeWithArgs = "${Recipe.route}/{$locationRecipeIdArg}"
    val args= listOf(navArgument(locationRecipeIdArg) {
        type = NavType.StringType
    })
    val deepLinks = listOf(
        navDeepLink { uriPattern = "recipes://${Recipe.route}/{$locationRecipeIdArg}"}
    )
}


// Screens to be displayed in the top RallyTabRow
val recipeRowScreens = listOf(Recipe, LocationRecipe)
