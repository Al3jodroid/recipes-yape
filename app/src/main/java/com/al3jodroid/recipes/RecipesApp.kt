package com.al3jodroid.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.al3jodroid.recipes.ui.connectivity.ConnectivityIndicator
import com.al3jodroid.recipes.ui.navigation.BuildRecipesNavHost
import com.al3jodroid.recipes.ui.theme.RecipesTheme

// set a reference of the NavHostController allowing to access to it in any place of the UI tree
val LocalNavController = staticCompositionLocalOf<NavHostController> { error("Not found!") }

@Composable
fun RecipesApp() {
    RecipesTheme {
        val navController = rememberNavController()
        //this its added for access to the navigation controller anywhere
        //without pass it via arguments through the classes
        CompositionLocalProvider(LocalNavController provides navController) {

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column {
                    //thanks to the argument type definition the viewModel type can be inferred too
                    ConnectivityIndicator(hiltViewModel())
                    BuildRecipesNavHost(
                        navController = navController,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}