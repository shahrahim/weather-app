package com.project.weatherapp.entity

data class WeatherForecast(
    var temp: Double,
    var feelsLike: Double,
    var mainDescription: String,
    var subDescription: String,
    var dateTime: Int
)
