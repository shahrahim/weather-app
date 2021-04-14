package com.project.weatherapp.service

import com.project.weatherapp.config.LocationConfig
import com.project.weatherapp.entity.Coordinate
import com.project.weatherapp.util.HttpUtil
import com.project.weatherapp.util.JsonUtil
import org.json.JSONArray
import org.json.JSONObject

class LocationService {

    fun getDefaultLocation(): String {
        val geoLocationJson: JSONObject = HttpUtil().getJsonObjectFromHttpRequest(getGeoLocationUrl())
        val locationObject: JSONObject = JsonUtil().getJsonObjectByKey(geoLocationJson,"location")

        val lon: Double = JsonUtil().getValueByKey(locationObject,"lng") as Double
        val lat: Double = JsonUtil().getValueByKey(locationObject,"lat") as Double
        val coordinate: Coordinate = Coordinate(lon,lat)
        return getZipcodeOrCityFromCoordinates(coordinate)
    }

    private fun getZipcodeOrCityFromCoordinates(coordinate: Coordinate): String {
        val geoCodeUrl = this.getGeoCodeUrl(coordinate)
        val geoCodeObject: JSONObject = HttpUtil().getJsonObjectFromHttpRequest(geoCodeUrl)
        val geoCodeResults: JSONObject = JsonUtil().getJsonArrayByKey(geoCodeObject, "results").getJSONObject(0)
        val addressComponents: JSONArray = JsonUtil().getJsonArrayByKey(geoCodeResults, "address_components")

        var zipcode: String? = null
        var city: String? = null

        for(i in 0 until addressComponents.length()) {
            val addressComponent: JSONObject = addressComponents.getJSONObject(i)
            val types: JSONArray = addressComponent.getJSONArray("types")

            for (k in 0 until types.length()) {
                if(types.getString(k).equals("postal_code")) {
                    zipcode = addressComponent.getString("long_name")
                } else if(types.getString(k).equals("administrative_area_level_3")) {
                    city = addressComponent.getString("long_name").split(" ").first()
                }
            }
        }
        if(zipcode != null) {
            return zipcode
        } else if(city != null) {
            return city
        }
        return ""
    }

    private fun getGeoLocationUrl(): String {
        val geoLocationUrl = LocationConfig().geoLocationUrl
        val key = LocationConfig().key
        return "$geoLocationUrl?key=$key"
    }

    private fun getGeoCodeUrl(coordinate: Coordinate): String {
        val geoCodeUrl = LocationConfig().geoCodeUrl
        val key = LocationConfig().key
        val lon: Double = coordinate.lon
        val lat: Double = coordinate.lat
        val latLonParam = "latlng=$lat,$lon&sensor=true"
        return "$geoCodeUrl?$latLonParam&key=$key"
    }
}