package com.al3jodroid.recipes.common.di.module

import com.al3jodroid.recipes.client.geocoder.GeocoderService
import com.al3jodroid.recipes.interactor.RecipesInteractor
import com.al3jodroid.recipes.interactor.RecipesUseCase
import com.al3jodroid.recipes.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This module allows to inject the different interactor (use cases) where its needed
 */
@InstallIn(SingletonComponent::class)
@Module
class InteractorModule @Inject constructor() {
    /**
     * Retrieves an instance of [RecipesUseCase]
     * @param recipeRepository the repository that helps to retrieve the information of recipes
     * @param geocoderService the service that helps to return a Lat,Long value from a location name
     * @return a concrete implementation [RecipesInteractor] as the use-case
     */
    @Provides
    @Singleton
    fun provideRecipesUseCase(
        recipeRepository: RecipeRepository,
        geocoderService: GeocoderService
    ): RecipesUseCase =
        RecipesInteractor(recipeRepository, geocoderService)

}