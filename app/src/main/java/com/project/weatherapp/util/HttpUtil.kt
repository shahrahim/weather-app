package com.project.weatherapp.util;

import com.github.kittinunf.fuel.Fuel
import com.project.weatherapp.exception.WeatherNotFoundException
import org.json.JSONObject
import java.util.logging.Level
import java.util.logging.Logger

class HttpUtil {

    private val jsonUtil: JsonUtil = JsonUtil()

    private val logger = Logger.getAnonymousLogger()

    fun getJsonObjectFromHttpRequest(url: String): JSONObject {
        return try {
            val jsonResponseStr = Fuel.post(url)
                .header("Content-Type", "application/json")
                .response { result -> }.get()
                .body().asString("application/json")
            jsonUtil.getNewJsonObject(jsonResponseStr)
        } catch (e: Exception) {
            logger.log(Level.SEVERE, "Could not get json response for $url")
            throw WeatherNotFoundException("Something went wrong")
        }
    }
}
