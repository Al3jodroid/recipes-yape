package com.al3jodroid.recipes.interactor

import com.al3jodroid.recipes.model.data.RecipeResult

/**
 * The use case that set the methods that should be implemented for business and recipe model
 */
interface RecipesUseCase {
    /**
     * Based on a [String] search the recipes related to it (decided by the server)
     * @param query the string that contains the name or name alike to search
     * @return a list of [RecipeResult] to show in UIs
     */
    suspend fun searchRecipes(query: String): List<RecipeResult>?
}