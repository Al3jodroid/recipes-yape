package com.al3jodroid.recipes.client.api

import com.al3jodroid.recipes.model.dto.RecipeListResultDto
import com.al3jodroid.recipes.model.dto.RecipeResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The interface used by retrofit for run the api calls defined in [RecipeApi]
 */
interface RecipeService {
    companion object {
        const val SEARCH = "search.php"
        const val API_KEY_VAR = "key"
    }

    /**
     * define the call for search recipes based on query string
     * @param query that contains the string to use to search recipes
     * @return A [Response] with a list of [RecipeResultDto] that match with the query
     */
    @GET(SEARCH)
    suspend fun searchRecipes(@Query("s") query: String): Response<RecipeListResultDto>

}