package com.project.weatherapp.entity

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class WeatherObject (
    val lat: Double,
    val lon: Double,
    val timezone: String,

    @Json(name = "timezone_offset")
    val timezoneOffset: Long,

    val current: Current,
    val daily: List<Daily>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<WeatherObject>(json)
    }
}

data class Current (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,

    @Json(name = "feels_like")
    val feelsLike: Double,

    val pressure: Long,
    val humidity: Long,

    @Json(name = "dew_point")
    val dewPoint: Long,

    val uvi: Long,
    val clouds: Long,
    val visibility: Long,

    @Json(name = "wind_speed")
    val windSpeed: Double,

    @Json(name = "wind_deg")
    val windDeg: Long,

    val weather: List<WeatherDescription>
)

data class Daily (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,

    @Json(name = "feels_like")
    val feelsLike: FeelsLike,

    val pressure: Long,
    val humidity: Long,

    @Json(name = "dew_point")
    val dewPoint: Double,

    @Json(name = "wind_speed")
    val windSpeed: Double,

    @Json(name = "wind_deg")
    val windDeg: Long,

    val weather: List<WeatherDescription>,
    val clouds: Long,
    val pop: Double,
    val rain: Double? = null,
    val uvi: Double,
    val snow: Double? = null
)

data class FeelsLike (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp (
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)
