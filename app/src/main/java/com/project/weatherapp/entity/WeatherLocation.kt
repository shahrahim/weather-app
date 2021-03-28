package com.project.weatherapp.entity

data class WeatherLocation (
    var coordinate: WeatherCoordinate,
    var city: String,
    var country: String
)