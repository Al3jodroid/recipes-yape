package com.al3jodroid.recipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.al3jodroid.recipes.common.app.RecipeApplication
import com.al3jodroid.recipes.ui.connectivity.ConnectivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //A classic way to call the viewModel without hilt
    private val connectivityViewModel: ConnectivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //can be done inside the RecipesApp, call here for reference/pedagogic purposes
        connectivityViewModel.startListenConnectivity()
        setContent {
            //the only compose call that start all the declarative UI
            RecipesApp()
        }
    }

    override fun getApplicationContext(): RecipeApplication {
        return super.getApplicationContext() as RecipeApplication
    }
}






