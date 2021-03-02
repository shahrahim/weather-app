package com.project.weatherapp.service;

import com.project.weatherapp.config.WeatherConfig
import com.project.weatherapp.entity.*
import com.project.weatherapp.util.DateUtil
import com.project.weatherapp.util.HttpUtil;
import com.project.weatherapp.util.JsonUtil
import com.project.weatherapp.util.WeatherUtil
import org.json.JSONArray
import org.json.JSONObject

class WeatherService {

    private val httpUtil = HttpUtil()

    private val jsonUtil = JsonUtil()

    private val weatherConfig = WeatherConfig()

    private val zipCodePattern = Regex("^\\d{5}(?:[-\\s]\\d{4})?\$")


    fun getWeather(location: String): Weather {
        val weatherLocation: WeatherLocation = this.getWeatherLocation(location)
        val weatherUrl: String = this.getWeatherUrl(weatherLocation)
        val weather: Weather

        val jsonResponse: JSONObject = httpUtil.getJsonObjectFromHttpRequest(weatherUrl)
        val currentWeather: JSONObject = jsonUtil.getJsonObjectByKey(jsonResponse,"current")
        val dailyWeather: JSONArray = jsonUtil.getJsonArrayByKey(jsonResponse,"daily")
        val timeZone: String = jsonUtil.getValueByKey(jsonResponse, "timezone") as String

        val currentForecast: WeatherForecast = this.getCurrentForecast(currentWeather)
        val dailyForecastList: MutableList<WeatherForecast> = this.getDailyForecast(dailyWeather)
        val isDaytime: Boolean = DateUtil().isDaytime(currentForecast.dateTime, currentForecast.sunrise,
            currentForecast.sunset)
        return Weather(weatherLocation, currentForecast, dailyForecastList, timeZone, isDaytime)
    }


    private fun getWeatherLocation(location: String): WeatherLocation {
        val locationUrl: String = this.getLocationUrl(location)

        val jsonResponse: JSONObject = httpUtil.getJsonObjectFromHttpRequest(locationUrl)
        val weatherCoordinateJson: JSONObject = jsonUtil.getJsonObjectByKey(jsonResponse, "coord")

        val lon: Double = jsonUtil.getValueByKey(weatherCoordinateJson,"lon") as Double
        val lat: Double = jsonUtil.getValueByKey(weatherCoordinateJson,"lat") as Double
        val city: String = jsonUtil.getValueByKey(jsonResponse,"name") as String

        val weatherCoordinate: WeatherCoordinate = WeatherCoordinate(lon, lat)
        return WeatherLocation(weatherCoordinate, city)
    }

    private fun getCurrentForecast(weatherObj: JSONObject): WeatherForecast {
        val temp: Double = jsonUtil.getValueByKey(weatherObj,"temp") as Double
        val feelsLike: Double = jsonUtil.getValueByKey(weatherObj,"feels_like") as Double
        val forecast: WeatherForecast = this.getForecastInfo(weatherObj)
        forecast.temp = temp
        forecast.feelsLike = feelsLike
        return forecast
    }

    private fun getDailyForecast(weatherObjArray: JSONArray): MutableList<WeatherForecast> {
        val forecastList: MutableList<WeatherForecast> = mutableListOf<WeatherForecast>()

        for(i in 0 until weatherObjArray.length()) {
            val weatherObj: JSONObject = weatherObjArray.getJSONObject(i)
            val temperatureObj: JSONObject = jsonUtil.getJsonObjectByKey(weatherObj,"temp")
            val feelsLikeObj: JSONObject = jsonUtil.getJsonObjectByKey(weatherObj,"feels_like")

            val forecast: WeatherForecast = this.getForecastInfo(weatherObj)
            val temperature: Double = jsonUtil.getValueByKey(temperatureObj,"day") as Double
            val feelsLike: Double = jsonUtil.getValueByKey(feelsLikeObj,"day") as Double
            forecast.temp = temperature
            forecast.feelsLike = feelsLike
            forecastList.add(i,forecast)
        }
        return forecastList
    }

    private fun getForecastInfo(weatherObj: JSONObject): WeatherForecast {
        val weatherInfo: JSONObject = jsonUtil.getJsonArrayByKey(weatherObj, "weather")
            .getJSONObject(0)
        val mainDescription: String = jsonUtil.getValueByKey(weatherInfo, "main") as String
        val subDescription: String = jsonUtil.getValueByKey(weatherInfo, "description") as String
        val currentTime: Int = jsonUtil.getValueByKey(weatherObj, "dt") as Int
        val sunriseTime: Int = jsonUtil.getValueByKey(weatherObj, "sunrise") as Int
        val sunsetTime: Int = jsonUtil.getValueByKey(weatherObj, "sunset") as Int
        val clouds: Int = jsonUtil.getValueByKey(weatherObj, "clouds") as Int
        val weatherType: WeatherEnum = WeatherUtil().getWeatherTypeFromString(mainDescription, clouds)

        return WeatherForecast(
            0.0, 0.0, mainDescription,
            subDescription, currentTime.toLong(), sunriseTime.toLong(), sunsetTime.toLong(), clouds, weatherType
        )
    }

    private fun getLocationUrl(location: String): String {
        val locationParam: String = this.getLocationParam(location)
        val url: String = weatherConfig.openWeatherApiUrl
        val uri: String = weatherConfig.locationUri
        val excludeParams: String = weatherConfig.locationExcludeParams
        val appId: String = weatherConfig.appId
        return "$url/$uri?$locationParam=$location&exclude=$excludeParams&appid=$appId"
    }

    private fun getLocationParam(location: String): String {
        return if(this.isZipcode(location)) {
            "zip"
        } else {
            "q"
        }
    }

    private fun isZipcode(location: String): Boolean {
        return zipCodePattern.matches(location)
    }

    private fun getWeatherUrl(weatherLocation: WeatherLocation): String {
        val url: String = weatherConfig.openWeatherApiUrl
        val uri: String = weatherConfig.weatherLookupUri
        val excludeParams: String = weatherConfig.weatherLookupExcludeParams
        val appId: String = weatherConfig.appId
        val lon: Double = weatherLocation.coordinate.lon
        val lat: Double = weatherLocation.coordinate.lat
        return "$url/$uri?lon=$lon&lat=$lat&exclude=$excludeParams&appid=$appId"
    }
}
