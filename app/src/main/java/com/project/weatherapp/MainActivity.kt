package com.project.weatherapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.project.weatherapp.adapter.WeatherAdapter
import com.project.weatherapp.entity.Weather
import com.project.weatherapp.exception.WeatherNotFoundException
import com.project.weatherapp.service.WeatherService
import kotlinx.android.synthetic.main.input_view.*
import java.util.*


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val btnSearchWeather = findViewById<Button>(R.id.btnSearch)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val tvLocation = findViewById<TextView>(R.id.tvCoordinates)

        etLocation.setBackgroundResource(R.color.white)
        btnSearchWeather.setTextColor(Color.WHITE)
        ivLocation.visibility = View.GONE

        val adapter: WeatherAdapter = WeatherAdapter(this)

        btnSearchWeather.setBackgroundColor(Color.parseColor("#FF7900"))
        btnSearchWeather.setOnClickListener {
            val locationInput: String = etLocation.text.toString()
            try {
                val weather: Weather = WeatherService().getWeather(locationInput)
                adapter.setWeather(weather)
                adapter.updateView()
                adapter.setDailyViews()
                adapter.clearKeyboard()
            } catch (e: WeatherNotFoundException) {
                adapter.clearViews()
                adapter.handleErrorState()
                return@setOnClickListener
            }
        }
    }


}
