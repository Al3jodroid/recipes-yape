package com.al3jodroid.recipes.client.api

import com.al3jodroid.recipes.model.dto.RecipeListResultDto
import com.al3jodroid.recipes.model.dto.RecipeResultDto

/**
 * Define the different api calls allowed to execute in the server
 */
interface RecipeApi {
    /**
     * Retrieve the list of recipes based on string query
     * @param query received from the user to search recipes
     * @return a list of [RecipeResultDto] that matches with the query
     */
    suspend fun searchRecipes(query: String): Result<RecipeListResultDto?>

}