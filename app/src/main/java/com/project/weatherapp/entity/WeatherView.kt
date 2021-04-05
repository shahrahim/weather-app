package com.project.weatherapp.entity

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.project.weatherapp.R

class WeatherView(_activity: Activity) {

    var activity: Activity = _activity

    val btnSearchWeather: Button = activity.findViewById<Button>(R.id.btnSearch)

    val etLocation: EditText = activity.findViewById<EditText>(R.id.etLocation)

    val tvCity: TextView = activity.findViewById<TextView>(R.id.tvCity)
    val tvLocation: TextView = activity.findViewById<TextView>(R.id.tvCoordinates)

    val vHome: View = activity.findViewById<View>(R.id.vHome)

    val ivLocation: ImageView = activity.findViewById<ImageView>(R.id.ivLocation)

    val ivCurrent: ImageView = activity.findViewById<ImageView>(R.id.ivCurrent)

    val tvCurrentTemp: TextView = activity.findViewById<TextView>(R.id.tvCurrentTemp)
    val tvCurrentFeelsLike: TextView = activity.findViewById<TextView>(R.id.tvCurrentFeelsLike)
    val tvCurrentDescription: TextView = activity.findViewById<TextView>(R.id.tvCurrentDescription)

    val dailyDayTvs = listOf(
        activity.findViewById<TextView>(R.id.tvDaily1Day),
        activity.findViewById<TextView>(R.id.tvDaily2Day), activity.findViewById<TextView>(R.id.tvDaily3Day)
    )

    val dailyTempTvs = listOf(
        activity.findViewById<TextView>(R.id.tvDaily1Temp),
        activity.findViewById<TextView>(R.id.tvDaily2Temp), activity.findViewById<TextView>(R.id.tvDaily3Temp)
    )

    val dailyIvs = listOf(
        activity.findViewById<ImageView>(R.id.ivDaily1),
        activity.findViewById<ImageView>(R.id.ivDaily2), activity.findViewById<ImageView>(R.id.ivDaily3)
    )

    fun getWeatherBackgroundResource(weather: Weather): Int {
        return if (weather.dayPhase == DayEnum.MORNING) {
            R.drawable.gradient_background_morning
        } else if (weather.dayPhase == DayEnum.NOON) {
            R.drawable.gradient_background_noon
        } else if (weather.dayPhase == DayEnum.AFTERNOON) {
            R.drawable.gradient_background_afternoon
        } else if (weather.dayPhase == DayEnum.EVENING) {
            R.drawable.gradient_background_evening
        } else {
            R.drawable.gradient_background_night
        }
    }


}