package com.al3jodroid.recipes.model.data

data class RecipeResult(
    val id: String,
    val name: String,
    val origin: String,
    val thumbnailUrl: String,
    var latLong: Pair<Double, Double>? = null,
    var recipeDetail: RecipeDetail? = null
)
