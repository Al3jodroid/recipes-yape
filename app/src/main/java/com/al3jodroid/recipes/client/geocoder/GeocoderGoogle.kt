package com.al3jodroid.recipes.client.geocoder

import android.content.Context
import android.location.Geocoder
import android.util.Log

class GeocoderGoogle(context: Context) : GeocoderService {
    //just a helper string for the log messages
    private val mTAG = GeocoderGoogle::class.java.simpleName

    private val geocoder = Geocoder(context)
    override fun convertLocationToLatLong(locationName: String): Pair<Double, Double>? {
        val locationToSearch = "$locationName country"

        return try {
            val resultSearch = geocoder.getFromLocationName(locationToSearch, 1)
            val address = resultSearch?.get(0)
            Pair(address!!.latitude, address!!.longitude)

        } catch (e: Exception) {
            Log.e(mTAG, "No geocode result for: $locationToSearch ${e.message}")
            null
        }
    }
}