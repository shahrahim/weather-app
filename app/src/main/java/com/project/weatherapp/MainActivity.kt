package com.project.weatherapp

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import com.project.weatherapp.entity.Weather
import com.project.weatherapp.service.WeatherService
import com.project.weatherapp.util.HttpUtil
import com.project.weatherapp.util.JsonUtil
import com.project.weatherapp.util.MathUtil
import kotlinx.android.synthetic.main.input_view.*
import kotlinx.android.synthetic.main.input_view.view.*
import org.json.JSONObject
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val btnSearchWeather = findViewById<Button>(R.id.btnSearch)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val tvCity = findViewById<TextView>(R.id.tvCity)
        val tvLocation = findViewById<TextView>(R.id.tvCoordinates)
        val vHome = findViewById<View>(R.id.vHome)
        val ivLocation: ImageView = findViewById<ImageView>(R.id.ivLocation)
        val ivCurrent: ImageView = findViewById<ImageView>(R.id.ivCurrent)
        val tvCurrentTemp: TextView = findViewById<TextView>(R.id.tvCurrentTemp)

        vHome.setBackgroundResource(R.drawable.gradient_background_day)
        etLocation.setBackgroundResource(R.color.white)
        btnSearchWeather.setTextColor(Color.WHITE)
        ivLocation.visibility = View.GONE

        btnSearchWeather.setBackgroundColor(Color.parseColor("#FF7900"))
        btnSearchWeather.setOnClickListener {
            val locationInput: String = etLocation.text.toString()
            val weather: Weather = WeatherService().getWeather(locationInput)
            println(weather.current.feelsLike)
            val feelsLike = weather.current.feelsLike
            tvCity.text = weather.location.city
            tvCity.setTextColor(Color.WHITE)
            tvCity.setTypeface(null, Typeface.ITALIC)
            val lon: Double = weather.location.coordinate.lon
            val lat: Double = weather.location.coordinate.lat
            ivCurrent.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            tvCoordinates.text = "$lon, $lat"
            tvCoordinates.setTextColor(Color.WHITE)

            val tempInCelcius: Int = MathUtil().getCelciusFromCelvin(weather.current.temp)

            val tempFormat: String = "$tempInCelcius "+ "\u2103";
            tvCurrentTemp.text = tempFormat
            tvCurrentTemp.setTextColor(Color.WHITE)


            ivLocation.visibility = View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE

        }

    }
}
