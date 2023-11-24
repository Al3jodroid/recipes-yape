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
        const val SEARCH_BY_NAME = "search.php"
        const val SEARCH_BY_ID = "lookup.php"
        const val API_KEY_VAR = "key"
    }

    /**
     * define the call for search recipes based on query string
     * @param query that contains the string to use to search recipes
     * @return A [Response] with a list of [RecipeResultDto] that match with the query
     */
    @GET(SEARCH_BY_NAME)
    suspend fun searchRecipes(@Query("s") query: String): Response<RecipeListResultDto>


    /**
     * define the call for search a recipe based on an id
     * @param query that contains the sid to search in backend
     * @return A [Response] with a list of [RecipeResultDto] that match with the query
     */
    @GET(SEARCH_BY_ID)
    suspend fun searchRecipeById(@Query("i") query: String): Response<RecipeListResultDto>

}