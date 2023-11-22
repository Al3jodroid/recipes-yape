package com.al3jodroid.recipes.interactor

import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.repository.RecipeRepository

class RecipesInteractor(private val recipesRepository: RecipeRepository) : RecipesUseCase {

    private val recipeCache = HashMap<String, RecipeResult>()

    override suspend fun searchRecipes(query: String): List<RecipeResult>? =
        recipesRepository.getRecipes(query)

    override suspend fun getRecipeById(id: String): RecipeResult? {
        return if(recipeCache.containsKey(id)) recipeCache[id]
        else recipesRepository.getRecipeById(id)
    }

    override fun cacheRecipe(recipe: RecipeResult) {
        recipeCache[recipe.id] = recipe
    }
}