package com.al3jodroid.recipes.interactor

import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.repository.RecipeRepository

class RecipesInteractor(private val recipesRepository: RecipeRepository) : RecipesUseCase {
    override suspend fun searchRecipes(query: String): List<RecipeResult>? =
        recipesRepository.getRecipes(query)
}