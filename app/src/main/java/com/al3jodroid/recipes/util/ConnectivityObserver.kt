package com.al3jodroid.recipes.util

import kotlinx.coroutines.flow.Flow

/**
 * An interface definition that helps to understand how and when observe the
 * network changes of the app for notifications or validate the api calls.
 */
interface ConnectivityObserver {
    /**
     * The method required to be implement for listen the changes of the network connectivity
     */
    fun observe(): Flow<Status>

    /**
     * A simple enumerations that contains the current states modeled and emitted
     * for the network changes
     */
    enum class Status {
        Available, Unavailable
    }
}