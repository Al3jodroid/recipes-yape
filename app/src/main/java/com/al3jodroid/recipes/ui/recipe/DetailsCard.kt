package com.al3jodroid.recipes.ui.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.al3jodroid.recipes.model.data.RecipeResult

@Composable
fun DetailsCard(recipe: RecipeResult) {
    //render the composable components
    Column {
        Text(text = "Detail recipe Screen")
        Text(text = recipe.toString())
    }
}

