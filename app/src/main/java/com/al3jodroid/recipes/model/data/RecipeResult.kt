package com.al3jodroid.recipes.model.data

data class RecipeResult(
    var positionOnList: Int = 0, //this helps in UI to update the values of it inside a list
    val name: String,
    val country: String,
    var recipeDetail: RecipeDetail? = null
)
