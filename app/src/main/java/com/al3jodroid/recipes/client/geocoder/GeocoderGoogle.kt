package com.al3jodroid.recipes.client.geocoder

import android.content.Context
import android.location.Geocoder
import android.util.Log

class GeocoderGoogle(context: Context) : GeocoderService {
    //just a helper string for the log messages
    private val mTAG = GeocoderGoogle::class.java.simpleName

    //this additional parameter its added for better work of the geocoder, basically
    //the location info came as: 'chinese', 'mexican' etc etc, so adding this string the geocoder works better
    private val mCOUNTRY="country"

    private val geocoder = Geocoder(context)
    override fun convertLocationToLatLong(locationName: String): Pair<Double, Double>? {
        val locationToSearch = "$locationName $mCOUNTRY"

        return try {
            //The deprecated call happens because this way to call the Geocoding
            //can block the UI thread, so must be used with a callback async
            //BUT in this case this method its called in another one that its invoked
            //from a coroutine free thread blocking and already using Flows and observers
            val resultSearch = geocoder.getFromLocationName(locationToSearch, 1)
            val address = resultSearch?.get(0)
            Pair(address!!.latitude, address!!.longitude)

        } catch (e: Exception) {
            Log.e(mTAG, "No geocode result for: $locationToSearch ${e.message}")
            null
        }
    }
}