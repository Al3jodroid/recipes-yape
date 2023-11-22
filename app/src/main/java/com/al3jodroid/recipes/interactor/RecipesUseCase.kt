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


    /**
     * Based on a [String] id search the recipes related to it (cached local, or server)
     * @param id related to the recipe to search
     * @return [RecipeResult] to show in UIs
     */
    suspend fun getRecipeById(id: String): RecipeResult?


    /**
     * Cache a [RecipeResult] object for future use (most cases immediately)
     * @param recipe object that going to be temporary stored
     */
    fun cacheRecipe(recipe: RecipeResult)
}