package com.project.weatherapp.config

data class WeatherConfig (
    val openWeatherApiUrl: String = "https://api.openweathermap.org/data/2.5",
    var locationUri: String = "weather",
    val weatherLookupUri: String = "onecall",
    val locationExcludeParams: String = "minutely,hourly,daily,alerts",
    val weatherLookupExcludeParams: String = "minutely,hourly,alerts",
    val appId: String = "ddc6ceaed94a3585de774b295163af4e"
)
