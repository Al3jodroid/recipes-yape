package com.al3jodroid.recipes.ui.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.al3jodroid.recipes.model.data.RecipeDetail
import com.al3jodroid.recipes.model.data.RecipeResult


/**
 * Based on a [recipe] use the info to render cards and texts that contains all the additional
 * information related with a card
 */
@Composable
fun DetailsCard(recipe: RecipeResult, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        RenderIngredients(recipe.recipeDetail?.ingredientsAndMeasure, modifier)
        RenderInstructions(recipe.recipeDetail, modifier)
    }
}

/**
 * Render only the instructions with a separated title in UI
 */
@Composable
fun RenderInstructions(recipeDetail: RecipeDetail?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(all = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Text(
            text = "Preparation",
            modifier = modifier.padding(8.dp),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium)
        )
        Text(
            text = recipeDetail?.instructions ?: "",
            modifier = Modifier
                .padding(PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp))
        )
    }
}

/**
 * Render the ingredients based on a lecture of [Pair] list that contains the corresponded
 * ingredient-quantity relation previously build in the Util.kt file
 */
@Composable
fun RenderIngredients(ingredients: List<Pair<String, String>>?, modifier: Modifier = Modifier) {
    var ingredientsString = ""
    ingredients?.forEach {
        ingredientsString += "$it\n"
    }
    Card(
        modifier = modifier.padding(all = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Text(
            text = "Ingredients",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium)
        )

        Text(
            text = ingredientsString,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}

