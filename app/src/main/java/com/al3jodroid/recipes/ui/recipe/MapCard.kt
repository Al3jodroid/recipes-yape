package com.al3jodroid.recipes.ui.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.al3jodroid.recipes.model.data.RecipeResult
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapCard(recipe: RecipeResult) {
    Column {
        Text(text = "Map recipe Screen")
        if (recipe.latLong != null) renderMapWithPinValidated(recipe)
        else renderLocationFail()
    }
}

@Composable
fun renderMapWithPinValidated(recipe: RecipeResult) {
    val locationToPin = LatLng(recipe.latLong!!.first, recipe.latLong!!.second)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locationToPin, 5f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = locationToPin),
            title = recipe.name,
            snippet = recipe.origin
        )
    }
}

@Composable
fun renderLocationFail() {
    Text(text = "Well, cant draw it :(")
}
