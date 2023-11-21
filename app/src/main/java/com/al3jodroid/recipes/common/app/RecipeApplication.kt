package com.al3jodroid.recipes.common.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The Application extension for take advantage of common functions needed
 * and the entry point for hilt components definitions
 */
@HiltAndroidApp
class RecipeApplication : Application()