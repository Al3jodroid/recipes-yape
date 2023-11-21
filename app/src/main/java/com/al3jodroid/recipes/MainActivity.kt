package com.al3jodroid.recipes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.al3jodroid.recipes.ui.connectivity.ConnectivityIndicator
import com.al3jodroid.recipes.ui.connectivity.ConnectivityViewModel
import com.al3jodroid.recipes.ui.home.HomeScreen
import com.al3jodroid.recipes.ui.theme.RecipesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : RecipesActivity() {
    //A classic way to call the viewModel without hilt
    private val connectivityViewModel: ConnectivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityViewModel.startListenConnectivity()

        setContent {
            RecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        ConnectivityIndicator(connectivityViewModel) //Indicator of connectivity
                        HomeScreen()
                    }

                }
            }
        }
    }
}

