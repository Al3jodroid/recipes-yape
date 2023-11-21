package com.al3jodroid.recipes

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import com.al3jodroid.recipes.common.app.RecipeApplication

/**
 * An abstract implementation (also could be an interface with the new java SDK)
 * for allow to access to a "generic functions" and retrieve an custom
 * implementation of the appContext without casting it everywhere
 */
abstract class RecipesActivity: ComponentActivity() {
    override fun getApplicationContext(): RecipeApplication {
        return super.getApplicationContext() as RecipeApplication
    }

    /**
     * Helps to know with a simple @return boolean the orientation of the device
     */
    fun isPortrait() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}