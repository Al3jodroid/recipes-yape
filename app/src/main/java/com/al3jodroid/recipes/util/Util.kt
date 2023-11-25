package com.al3jodroid.recipes.util

import android.util.Log
import com.al3jodroid.recipes.model.data.RecipeDetail
import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.model.dto.RecipeResultDto

const val INGREDIENT_PREFIX = "strIngredient"
const val MEASURE_PREFIX = "strMeasure"
/**
 * Extension function for map the [RecipeResultDto] and convert it in domain models [RecipeResult]
 */
fun RecipeResultDto.toRecipeResult(): RecipeResult {
    return RecipeResult(
        name = strMeal ?: "",
        id = idMeal ?: "",
        origin = strArea ?: "",
        thumbnailUrl = strMealThumb ?: "",
        recipeDetail = this.toRecipeResultDetails()
    )
}

/**
 * Extension function for map the [RecipeResultDto] and convert it in domain models [RecipeDetail]
 */
fun RecipeResultDto.toRecipeResultDetails(): RecipeDetail {
    return RecipeDetail(
        instructions = strInstructions ?: "",
        ingredientsAndMeasure = this.toRecipeResultIngredientsAndMeasuresWithReflection(),
    )
}

/**
 * A really tricky extension function for map the [RecipeResultDto] ingredients, that unfortunately
 * came from the server in separated variables called ingredient1,ingredient2, ingredient3, so its needed
 * to manage reflexion of classes and string tokenization and validations to try to map in a more effective way
 */
fun RecipeResultDto.toRecipeResultIngredientsAndMeasuresWithReflection(): List<Pair<String, String>> {
    val ingredients: HashMap<String, String> = HashMap()
    val listToReturn: ArrayList<Pair<String, String>> = ArrayList()

    try {
        for (property in this::class.members) {
            var nameVar = property.name

            var isIngredientValid =
                nameVar.startsWith(INGREDIENT_PREFIX) && property.call(this) != null
                        && (property.call(this) as String).trim().isNotEmpty()

            var isMeasureValid =
                nameVar.startsWith(MEASURE_PREFIX) && property.call(this) != null
                        && (property.call(this) as String).trim().isNotEmpty()

            if (isIngredientValid) {
                var suffix = nameVar.removePrefix(INGREDIENT_PREFIX)
                ingredients[suffix] = (property.call(this) as String).trim()

            } else if (isMeasureValid) {
                var suffix = nameVar.removePrefix(MEASURE_PREFIX)
                val pair = Pair(
                    ingredients[suffix] ?: "",
                    (property.call(this) as String).trim()
                )

                //finally add the pairs of ingredient-measure to the list for return
                listToReturn.add(pair)
            }
        }
    } catch (e: Exception) {
        Log.e(this::class.toString(), "cant build ingredients-measure ${e.message.toString()}")
    }
    return listToReturn
}