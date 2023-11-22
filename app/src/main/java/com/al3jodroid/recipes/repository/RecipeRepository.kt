package com.al3jodroid.recipes.repository

import com.al3jodroid.recipes.client.api.RecipeApi
import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.util.toRecipeResult

/**
 * This class act as a SSOT for the application retrieving all the information needed based on the
 * user input and selection, for a correct accomplishment of the business requirements
 */
class RecipeRepository(private val recipeApi: RecipeApi) {

    /**
     * the SSOT that going to retrieve to upper layers the recipes related with the query send
     * @param query that contain the string to use to find the similar recipes
     * @return a list of [RecipeResult] that contains the information of the recipes found
     */
    suspend fun getRecipes(query: String): List<RecipeResult>? {
        return recipeApi.searchRecipes(query).getOrNull()?.meals?.map {
            it.toRecipeResult()
        }?.toList()
    }
}