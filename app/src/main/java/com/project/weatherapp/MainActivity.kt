package com.project.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import com.project.weatherapp.entity.Weather
import com.project.weatherapp.service.WeatherService
import com.project.weatherapp.util.HttpUtil
import com.project.weatherapp.util.JsonUtil
import org.json.JSONObject
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weather: Weather = WeatherService().getWeather("08844")
        println(weather.current.feelsLike)


    }
}
