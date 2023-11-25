package com.al3jodroid.recipes

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.al3jodroid.recipes.client.geocoder.GeocoderGoogle
import com.al3jodroid.recipes.client.geocoder.GeocoderService
import com.al3jodroid.recipes.interactor.RecipesInteractor
import com.al3jodroid.recipes.interactor.RecipesUseCase
import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.repository.RecipeRepository
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipesInteractorTest : MockApi() {
    private lateinit var interactor: RecipesUseCase
    private lateinit var repository: RecipeRepository
    private lateinit var geocoderService: GeocoderService

    @Before
    @Throws(Exception::class)
    fun init() {
        initMockApi()
        repository = RecipeRepository(recipeRetrofitApi)
        val context = ApplicationProvider.getApplicationContext<Context>()
        geocoderService = GeocoderGoogle(context)
        interactor = RecipesInteractor(repository, geocoderService)
    }

    @Test
    @Throws(Exception::class)
    fun testRecipesSearch() = runTest {
        mockServer.enqueue(MockResponse().setBody(buildResponse("mock_recipes.json")))
        val recipeList: List<RecipeResult>? = interactor.searchRecipes("bread")
        val recipeListResultDto = recipeRetrofitApi.searchRecipes("bread")

        var position = 0
        //this values should correspond to the file response "mock_recipes.json"
        recipeListResultDto.getOrNull()?.meals?.forEach {
            Assert.assertTrue(it.idMeal == recipeList?.get(position)?.id)
            Assert.assertTrue(it.strMeal == recipeList?.get(position)?.name)
            Assert.assertTrue(it.strArea == recipeList?.get(position)?.origin)
            Assert.assertTrue(it.strMealThumb == recipeList?.get(position)?.thumbnailUrl)
            position = position.inc()
        }
    }

    @Test
    @Throws(Exception::class)
    fun testGetRecipeById() = runTest {
        mockServer.enqueue(MockResponse().setBody(buildResponse("mock_recipe_by_id.json")))
        val recipeResult: RecipeResult? = interactor.getRecipeById("52772")
        val recipeListResultDto = recipeRetrofitApi.getRecipeById("52772")

        //this values should correspond to the file response "mock_recipes_by_id.json"
        recipeListResultDto.getOrNull()?.meals?.get(0).let {
            Assert.assertTrue(it?.idMeal == recipeResult?.id)
            Assert.assertTrue(it?.strMeal == recipeResult?.name)
            Assert.assertTrue(it?.strArea == recipeResult?.origin)
            Assert.assertTrue(it?.strMealThumb == recipeResult?.thumbnailUrl)
            Assert.assertTrue(it?.strMealThumb == recipeResult?.thumbnailUrl)

        }
    }
}