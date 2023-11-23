package com.al3jodroid.recipes.ui.recipe

import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.al3jodroid.recipes.model.data.RecipeResult
import com.al3jodroid.recipes.ui.home.HomeViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapCard(recipe: RecipeResult) {
    val geocoder = Geocoder(LocalContext.current)
    val resultSearch = geocoder.getFromLocationName(recipe.country+ " country", 1)
    val address = resultSearch?.get(0)

    Column {
        Text(text = "Map recipe Screen")
        val singapore = LatLng(address?.latitude ?: 0.0, address?.longitude ?: 0.0)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 4f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}