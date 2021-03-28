package com.project.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.project.weatherapp.entity.DayEnum
import com.project.weatherapp.entity.Weather
import com.project.weatherapp.entity.WeatherEnum
import com.project.weatherapp.entity.WeatherForecast
import com.project.weatherapp.exception.WeatherNotFoundException
import com.project.weatherapp.service.WeatherService
import com.project.weatherapp.util.DateUtil
import com.project.weatherapp.util.StringUtil
import com.project.weatherapp.util.WeatherUtil
import kotlinx.android.synthetic.main.input_view.*
import java.lang.Exception
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest


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
        val tvCity = findViewById<TextView>(R.id.tvCity)
        val tvLocation = findViewById<TextView>(R.id.tvCoordinates)
        val vHome = findViewById<View>(R.id.vHome)
        val ivLocation: ImageView = findViewById<ImageView>(R.id.ivLocation)
        val ivCurrent: ImageView = findViewById<ImageView>(R.id.ivCurrent)
        val tvCurrentTemp: TextView = findViewById<TextView>(R.id.tvCurrentTemp)
        val tvCurrentFeelsLike: TextView = findViewById<TextView>(R.id.tvCurrentFeelsLike)
        val tvCurrentDescription: TextView = findViewById<TextView>(R.id.tvCurrentDescription)

        val tvDaily1Day: TextView = findViewById<TextView>(R.id.tvDaily1Day)
        val tvDaily2Day: TextView = findViewById<TextView>(R.id.tvDaily2Day)
        val tvDaily3Day: TextView = findViewById<TextView>(R.id.tvDaily3Day)

        val tvDaily1Temp: TextView = findViewById<TextView>(R.id.tvDaily1Temp)
        val tvDaily2Temp: TextView = findViewById<TextView>(R.id.tvDaily2Temp)
        val tvDaily3Temp: TextView = findViewById<TextView>(R.id.tvDaily3Temp)

        val ivDaily1: ImageView = findViewById<ImageView>(R.id.ivDaily1)
        val ivDaily2: ImageView = findViewById<ImageView>(R.id.ivDaily2)
        val ivDaily3: ImageView = findViewById<ImageView>(R.id.ivDaily3)

        vHome.setBackgroundResource(R.drawable.gradient_background_afternoon)


        etLocation.setBackgroundResource(R.color.white)
        btnSearchWeather.setTextColor(Color.WHITE)
        ivLocation.visibility = View.GONE


        btnSearchWeather.setBackgroundColor(Color.parseColor("#FF7900"))
        btnSearchWeather.setOnClickListener {
            var weather: Weather? = null
            val locationInput: String = etLocation.text.toString()

            try {
                weather = WeatherService().getWeather(locationInput)
            } catch (e: WeatherNotFoundException) {
                vHome.setBackgroundResource(R.drawable.gradient_background_afternoon)
                ivLocation.visibility = View.GONE
                val myToast = Toast.makeText(applicationContext,"oops something went wrong",Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.LEFT,200,200)
                myToast.show()

                return@setOnClickListener
            }

            val backgroundResource =
                if(weather.dayPhase == DayEnum.MORNING) {
                    R.drawable.gradient_background_morning
                } else if(weather.dayPhase == DayEnum.NOON) {
                    R.drawable.gradient_background_noon
                } else if(weather.dayPhase == DayEnum.AFTERNOON) {
                    R.drawable.gradient_background_afternoon
                } else if(weather.dayPhase == DayEnum.EVENING) {
                    R.drawable.gradient_background_evening
                } else {
                    R.drawable.gradient_background_night
                }
            vHome.setBackgroundResource(backgroundResource)

            val feelsLike = weather.current.feelsLike
            tvCity.text = weather.location.city + ", " + weather.location.country
            tvCity.setTextColor(Color.WHITE)
            tvCity.setTypeface(null, Typeface.ITALIC)

            val lon: Double = weather.location.coordinate.lon
            val lat: Double = weather.location.coordinate.lat
            ivLocation.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            tvCoordinates.text = "$lon, $lat"
            tvCoordinates.setTextColor(Color.WHITE)

            ivCurrent.setImageResource(WeatherUtil().getWeatherImageIdFromType(weather.current.description, weather.dayPhase))
            ivCurrent.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)

            val tempInFahrenheit: Int = WeatherUtil().getFarenheitFromKelvin(weather.current.temp)
            val tempFormat: String = "$tempInFahrenheit "+ "\u2109";
            tvCurrentTemp.text = tempFormat
            tvCurrentTemp.setTextColor(Color.WHITE)

            val feelsLikeTemp: Int = WeatherUtil().getFarenheitFromKelvin(weather.current.feelsLike)
            val feelsLikeFormat: String = "Feels like $feelsLikeTemp "+ "\u2109";
            tvCurrentFeelsLike.text = feelsLikeFormat
            tvCurrentFeelsLike.setTextColor(Color.WHITE)

            tvCurrentDescription.text = "${weather.dayPhase} " + StringUtil().toUpperCase(weather.current.subDescription)
            tvCurrentDescription.setTextColor(Color.WHITE)
            ivLocation.visibility = View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE

            tvDaily1Day.text = DateUtil().getWeekDayFromEpochTime(weather.daily.get(1).dateTime, "").take(3)
            tvDaily2Day.text = DateUtil().getWeekDayFromEpochTime(weather.daily.get(2).dateTime, "").take(3)
            tvDaily3Day.text = DateUtil().getWeekDayFromEpochTime(weather.daily.get(3).dateTime, "").take(3)

            tvDaily1Day.setTextColor(Color.WHITE)
            tvDaily1Day.setTypeface(null, Typeface.BOLD)
            tvDaily2Day.setTextColor(Color.WHITE)
            tvDaily2Day.setTypeface(null, Typeface.BOLD)
            tvDaily3Day.setTextColor(Color.WHITE)
            tvDaily3Day.setTypeface(null, Typeface.BOLD)

            val daily1Forecast: WeatherForecast = weather.daily[1]
            val daily2Forecast: WeatherForecast = weather.daily[2]
            val daily3Forecast: WeatherForecast = weather.daily[3]

            val daily1Temp: Int = WeatherUtil().getFarenheitFromKelvin(daily1Forecast.temp)
            val daily2Temp: Int = WeatherUtil().getFarenheitFromKelvin(daily2Forecast.temp)
            val daily3Temp: Int = WeatherUtil().getFarenheitFromKelvin(daily3Forecast.temp)

            tvDaily1Day.text = DateUtil().getWeekDayFromEpochTime(daily1Forecast.dateTime, "").take(3)
            tvDaily2Day.text = DateUtil().getWeekDayFromEpochTime(daily2Forecast.dateTime, "").take(3)
            tvDaily3Day.text = DateUtil().getWeekDayFromEpochTime(daily3Forecast.dateTime, "").take(3)

            tvDaily1Day.setTextColor(Color.WHITE)
            tvDaily1Day.setTypeface(null, Typeface.BOLD)
            tvDaily2Day.setTextColor(Color.WHITE)
            tvDaily2Day.setTypeface(null, Typeface.BOLD)
            tvDaily3Day.setTextColor(Color.WHITE)
            tvDaily3Day.setTypeface(null, Typeface.BOLD)

            tvDaily1Temp.setTextColor(Color.WHITE)
            tvDaily1Temp.setTypeface(null, Typeface.BOLD)
            tvDaily2Temp.setTextColor(Color.WHITE)
            tvDaily2Temp.setTypeface(null, Typeface.BOLD)
            tvDaily3Temp.setTextColor(Color.WHITE)
            tvDaily3Temp.setTypeface(null, Typeface.BOLD)

            tvDaily1Temp.text = "$daily1Temp "+ "\u2109";
            tvDaily2Temp.text = "$daily2Temp "+ "\u2109";
            tvDaily3Temp.text = "$daily3Temp "+ "\u2109";

            ivDaily1.setImageResource(WeatherUtil().getWeatherImageIdFromType(daily1Forecast.description, DayEnum.NOON))
            ivDaily2.setImageResource(WeatherUtil().getWeatherImageIdFromType(daily2Forecast.description, DayEnum.NOON))
            ivDaily3.setImageResource(WeatherUtil().getWeatherImageIdFromType(daily3Forecast.description, DayEnum.NOON))

            ivDaily1.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            ivDaily2.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            ivDaily3.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)

            try {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                // TODO: handle exception
            }
        }

    }
}
