package com.project.weatherapp

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.project.weatherapp.adapter.WeatherAdapter
import com.project.weatherapp.entity.DayEnum
import com.project.weatherapp.entity.Weather
import com.project.weatherapp.entity.WeatherForecast
import com.project.weatherapp.exception.WeatherNotFoundException
import com.project.weatherapp.service.WeatherService
import com.project.weatherapp.util.DateUtil
import com.project.weatherapp.util.WeatherUtil
import kotlinx.android.synthetic.main.input_view.*


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

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
