package com.al3jodroid.recipes.util

import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.model.dto.RecipeResultDto


/**
 * Extension function for map the [RecipeResultDto] and convert it in domain models [RecipeResult]
 */
fun RecipeResultDto.toRecipeResult(): RecipeResult {
    return RecipeResult(name = strMeal, country = strArea)
}

