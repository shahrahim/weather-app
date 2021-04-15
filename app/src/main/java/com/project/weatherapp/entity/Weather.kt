package com.project.weatherapp.entity

data class Weather(
    var location: Location,
    var current: WeatherForecast,
    var hourly: MutableList<WeatherForecast>,
    var daily: MutableList<WeatherForecast>,
    val timezone: String,
    val dayPhase: DayEnum
)
