package com.al3jodroid.recipes.common.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.al3jodroid.recipes.common.app.RecipeApplication
import com.al3jodroid.recipes.util.ConnectivityObserver
import com.al3jodroid.recipes.util.NetworkConnectivityObserver
import dagger.hilt.android.qualifiers.ApplicationContext
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
}