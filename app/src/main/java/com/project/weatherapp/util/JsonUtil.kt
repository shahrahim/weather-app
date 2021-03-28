package com.project.weatherapp.util

import com.project.weatherapp.exception.WeatherNotFoundException
import org.json.JSONArray
import org.json.JSONObject
import java.util.logging.Level
import java.util.logging.Logger

class JsonUtil {

    private val logger = Logger.getAnonymousLogger()

    fun getNewJsonObject(str: String): JSONObject {
        return try {
            JSONObject(str)
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Could not get json $str")
            throw WeatherNotFoundException("Somethinf went wrong")
        }
    }

    fun getJsonObjectByKey(jsonObject: JSONObject, key: String): JSONObject {
        return try {
            jsonObject.getJSONObject(key)
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Could not get json key $key")
            throw WeatherNotFoundException("Somethinf went wrong")
        }
    }

    fun getJsonArrayByKey(jsonObject: JSONObject, key: String): JSONArray {
        return try {
            jsonObject.getJSONArray(key)
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Could not get json array: $key")
            throw WeatherNotFoundException("Somethinf went wrong")
        }
    }

    fun getValueByKey(jsonObject: JSONObject, key: String): Any {
        return try {
            jsonObject.get(key)
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Could not get value by key: $key")
            throw WeatherNotFoundException("Somethinf went wrong")
        }
    }

}