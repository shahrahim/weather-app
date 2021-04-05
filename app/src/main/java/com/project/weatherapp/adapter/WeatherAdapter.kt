package com.project.weatherapp.adapter

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.project.weatherapp.R
import com.project.weatherapp.entity.DayEnum
import com.project.weatherapp.entity.Weather
import com.project.weatherapp.entity.WeatherForecast
import com.project.weatherapp.entity.WeatherView
import com.project.weatherapp.util.DateUtil
import com.project.weatherapp.util.StringUtil
import com.project.weatherapp.util.WeatherUtil
import java.util.*


class WeatherAdapter(_activity: Activity) {

    private var activity: Activity = _activity

    private lateinit var weather: Weather

    private var weatherView = WeatherView(_activity)

    fun setWeather(weather: Weather) {
        this.weather = weather
    }

    fun updateView() {
        val backgroundResource: Int = this.weatherView.getWeatherBackgroundResource(weather)
        weatherView.vHome.setBackgroundResource(backgroundResource)

        val feelsLike = weather.current.feelsLike
        weatherView.tvCity.text = weather.location.city + ", " + weather.location.country
        weatherView.tvCity.setTextColor(Color.WHITE)
        weatherView.tvCity.setTypeface(null, Typeface.ITALIC)

        val lon: Double = weather.location.coordinate.lon
        val lat: Double = weather.location.coordinate.lat
        weatherView.ivLocation.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        weatherView.tvLocation.text = "$lon, $lat"
        weatherView.tvLocation.setTextColor(Color.WHITE)

        weatherView.ivCurrent.setImageResource(
            WeatherUtil().getWeatherImageIdFromType(
                weather.current.description,
                weather.dayPhase
            )
        )
        weatherView.ivCurrent.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)

        val tempInFahrenheit: Int = WeatherUtil().getFarenheitFromKelvin(weather.current.temp)
        val tempFormat: String = "$tempInFahrenheit " + "\u2109";
        weatherView.tvCurrentTemp.text = tempFormat
        weatherView.tvCurrentTemp.setTextColor(Color.WHITE)

        val feelsLikeTemp: Int = WeatherUtil().getFarenheitFromKelvin(weather.current.feelsLike)
        val feelsLikeFormat: String = "Feels like $feelsLikeTemp " + "\u2109";
        weatherView.tvCurrentFeelsLike.text = feelsLikeFormat
        weatherView.tvCurrentFeelsLike.setTextColor(Color.WHITE)

        weatherView.tvCurrentDescription.text =
            "${weather.dayPhase} " + StringUtil().toUpperCase(weather.current.subDescription)
        weatherView.tvCurrentDescription.setTextColor(Color.WHITE)
        weatherView.ivLocation.visibility = View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE

    }

    fun setDailyViews() {
        for (index in 0..2) {
            println(weatherView.dailyDayTvs.size)
            val dailyDayTv = weatherView.dailyDayTvs[index]
            val dailyTempTv = weatherView.dailyTempTvs[index]
            val dailyIv: ImageView = weatherView.dailyIvs[index]

            val dailyForecast: WeatherForecast = weather.daily[index+1]
            val dailyTemp: Int = WeatherUtil().getFarenheitFromKelvin(dailyForecast.temp)

            dailyDayTv.setTextColor(Color.WHITE)
            dailyDayTv.setTypeface(null, Typeface.BOLD)
            dailyTempTv.setTextColor(Color.WHITE)
            dailyTempTv.setTypeface(null, Typeface.BOLD)
            dailyDayTv.text = DateUtil().getWeekDayFromEpochTime(dailyForecast.dateTime, "").take(3)
            dailyTempTv.text = "$dailyTemp " + "\u2109";
            dailyIv.setImageResource(WeatherUtil().getWeatherImageIdFromType(dailyForecast.description, DayEnum.NOON))
            dailyIv.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        }
    }

    fun handleErrorState() {
        val myToast = Toast.makeText(activity, "oops something went wrong", Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.LEFT, 200, 200)
        myToast.show()
    }

    fun clearViews() {
        weatherView.vHome.setBackgroundColor(Color.parseColor("#6B99FF"))
        weatherView.etLocation.setText("")
        weatherView.tvCity.text = ""
        weatherView.tvLocation.text = ""
        weatherView.ivLocation.visibility = View.GONE
        weatherView.ivCurrent.visibility = View.GONE
        weatherView.tvCurrentTemp.text = ""
        weatherView.tvCurrentFeelsLike.text = ""
        weatherView.tvCurrentDescription.text = ""

        for (view in weatherView.dailyDayTvs) {
            view.text = ""
        }
        for (view in weatherView.dailyTempTvs) {
            view.text = ""
        }
        for (view in weatherView.dailyIvs) {
            view.visibility = View.GONE
        }
        this.clearKeyboard()
    }

    fun clearKeyboard() {
        val imm: InputMethodManager? = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}