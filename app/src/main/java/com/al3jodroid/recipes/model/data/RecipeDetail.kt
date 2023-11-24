package com.al3jodroid.recipes.model.data

class RecipeDetail(
    var instructions: String = "",
    val ingredientsAndMeasure: List<Pair<String, String>> = ArrayList()
)
