package com.al3jodroid.recipes.common.di.module

import android.content.Context
import com.al3jodroid.recipes.BuildConfig
import com.al3jodroid.recipes.client.api.RecipeApi
import com.al3jodroid.recipes.client.api.RecipeRetrofitApi
import com.al3jodroid.recipes.client.api.RecipeService.Companion.API_KEY_VAR
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.al3jodroid.recipes.common.app.RecipeApplication
import com.al3jodroid.recipes.interactor.RecipesInteractor
import com.al3jodroid.recipes.interactor.RecipesUseCase
import com.al3jodroid.recipes.repository.RecipeRepository
import com.al3jodroid.recipes.util.ConnectivityObserver
import com.al3jodroid.recipes.util.NetworkConnectivityObserver
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The module class that contain the different clients for inject in the classes that requires it
 */
@InstallIn(SingletonComponent::class)
@Module
class ClientModule @Inject constructor() {

    /**
     * Retrieves an instance of [NetworkConnectivityObserver] that allows to listen
     * the connectivity state of the app and post the changes of it
     * @return a concrete implementation of a [ConnectivityObserver] to use it
     */
    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext appContext: Context): ConnectivityObserver =
        NetworkConnectivityObserver(appContext)


    /**
     * Retrieves the retrofit implementation based on a client fot http requests
     * @param client the http client [OkHttpClient] for execute the server calls
     * @return an [Retrofit] implementation based on a URL defined in gradle
     */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()


    /**
     * Retrieves an interceptor that all to the request the defined [BuildConfig.API_KEY_VALUE]
     * that grant the access to the API
     * @return an [Interceptor] with the new parameters to add to all the requests
     */
    @Provides
    @Singleton
    fun providesGetRequestInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
                .url(
                    it.request().url.newBuilder()
                        .addQueryParameter(API_KEY_VAR, BuildConfig.API_KEY_VALUE)
                        .build()
                ).build()
            it.proceed(request)
        }
    }

    /**
     * Retrieves the http client for be used by the retrofit class in order to connect an call
     * service api
     * @param interceptor that contains additional info for run the endpoint call
     * @return an concrete implementation of [OkHttpClient]
     */
    @Provides
    @Singleton
    fun provideHttpClient(interceptor: Interceptor) =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    /**
     * Retrieves the implementation of the calls defined to the API
     * @return a concrete implementation of [RecipeRetrofitApi]
     */
    @Provides
    @Singleton
    fun provideRecipeApi(retrofitApi: Retrofit): RecipeApi = RecipeRetrofitApi(retrofitApi)


    /**
     * Retrieves an implementation of the repository as a SSOT that returns the information local
     * or remote to show in the UI
     * @return a [RecipeRepository] implementation
     */
    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: RecipeApi) = RecipeRepository(weatherApi)
}