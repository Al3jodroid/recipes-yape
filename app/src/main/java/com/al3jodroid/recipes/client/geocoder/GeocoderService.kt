package com.al3jodroid.recipes.client.geocoder

/**
 * The interface defined for the service that going to geocode a location based on a description
 */
interface GeocoderService {
    /**
     * define the method that should "calculate" the lat-long coordinates based on a description
     * @param locationName that contains the string to use to search recipes
     * @return A [Pair] with the longitude and latitude values of a place
     */
    fun convertLocationToLatLong(locationName: String): Pair<Double, Double>?
}