package com.al3jodroid.recipes.client.api

import com.al3jodroid.recipes.model.dto.RecipeListResultDto
import com.al3jodroid.recipes.model.dto.RecipeResultDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

/**
 * A class that implement the calls defined by the [RecipeApi] with the help of the
 * [RecipeService] retrofit definition for server calls
 */
class RecipeRetrofitApi(retrofit: Retrofit) : RecipeApi {
    //just a helper string for the log messages
    private val mTAG = RecipeRetrofitApi::class.java.simpleName

    //the service that execute the different api methods defined
    private val mService: RecipeService = retrofit.create(RecipeService::class.java)

    override suspend fun searchRecipes(query: String): Result<RecipeListResultDto?> {
        try {
            return withContext(Dispatchers.IO) {
                val response = mService.searchRecipes(query)
                response.runCatching {
                    return@withContext if (this.isSuccessful) Result.success(this.body())
                    else Result.failure(Exception())
                }
            }
        } catch (e: Exception) {
            return Result.failure(Exception())
        }
    }

    override suspend fun getRecipeById(id: String): Result<RecipeListResultDto?> {
        try {
            return withContext(Dispatchers.IO) {
                val response = mService.searchRecipeById(id)
                response.runCatching {
                    return@withContext if (this.isSuccessful) Result.success(this.body())
                    else Result.failure(Exception())
                }
            }
        } catch (e: Exception) {
            return Result.failure(Exception())
        }
    }
}