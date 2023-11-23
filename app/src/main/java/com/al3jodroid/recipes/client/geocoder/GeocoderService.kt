package com.al3jodroid.recipes.client.geocoder
interface GeocoderService {
    fun convertLocationToLatLong(locationName: String): Pair<Double, Double>?
}