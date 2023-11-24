package com.al3jodroid.recipes.ui.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.al3jodroid.recipes.R
import com.al3jodroid.recipes.model.data.RecipeResult
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * A Card that going to show only the googleMaps UI object with a marker previously calculated
 */
@Composable
fun MapCard(recipe: RecipeResult, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(all = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        if (recipe.latLong != null) renderMapWithPinValidated(recipe)
        else renderLocationFail()
    }
}

/**
 * This method should only be called when the lat-long its validated for correct render in the
 * GoogleMaps UI component
 */
@Composable
fun renderMapWithPinValidated(recipe: RecipeResult) {
    val locationToPin = LatLng(recipe.latLong!!.first, recipe.latLong!!.second)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locationToPin, 5f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            scrollGesturesEnabled = false,
            rotationGesturesEnabled = false,
            tiltGesturesEnabled = false
        )
    ) {
        Marker(
            state = MarkerState(position = locationToPin),
            title = recipe.name,
            snippet = recipe.origin
        )
    }
}

/**
 * When a location cant be referenced in coordinates, this its the message
 * that helps to the user understand the problem.
 */
@Composable
fun renderLocationFail(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.the_origin_location_cant_be_found),
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}
