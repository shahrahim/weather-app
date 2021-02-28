package com.project.weatherapp.entity

data class Weather(
    var location: WeatherLocation,
    var current: WeatherForecast,
    var daily: MutableList<WeatherForecast>

)
