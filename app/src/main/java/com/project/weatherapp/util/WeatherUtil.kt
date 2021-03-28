package com.project.weatherapp.util

import com.project.weatherapp.R
import com.project.weatherapp.entity.DayEnum
import com.project.weatherapp.entity.Weather
import com.project.weatherapp.entity.WeatherEnum
import com.project.weatherapp.entity.WeatherForecast

class WeatherUtil {

    private val KELVIN_CONSTANT: Double = 273.15

    fun getFarenheitFromKelvin(kelvin: Double): Int {
        val tempInCelsius: Int = this.getCelciusFromKelvin(kelvin)
        println("Temp in celcius is: $tempInCelsius")
        val tempInFarenheit: Double = (tempInCelsius * (9/5)) + 32.0
        println("Temp in farenheit: ${tempInFarenheit}")
        return tempInFarenheit.toInt()
    }

    fun getCelciusFromKelvin(kelvin: Double): Int {
        val tempInCelsius = (kelvin - KELVIN_CONSTANT)
        return tempInCelsius.toInt()
    }

    fun getWeatherTypeFromString(weatherDescription: String, cloudiness: Int): WeatherEnum {
        var weatherType: WeatherEnum = WeatherEnum.NA

        val weatherCloudType: WeatherEnum = if (isHeavyCloudy(cloudiness)) {
            WeatherEnum.HEAVYCLOUD
        } else {
            WeatherEnum.LIGHTCOUD
        }
        when (weatherDescription) {
            "Thunderstorm" -> weatherType = WeatherEnum.THUNDERSTORM
            "Drizzle" -> weatherType = WeatherEnum.DRIZZLE
            "Rain" -> weatherType = WeatherEnum.RAIN
            "Snow" -> weatherType = WeatherEnum.SNOW
            "Clear" -> weatherType = WeatherEnum.CLEAR
            "Clouds" -> weatherType = weatherCloudType
            "Mist" -> weatherType = WeatherEnum.FOG
            "fog" -> weatherType = WeatherEnum.FOG
            "Smoke" -> weatherType = WeatherEnum.SMOKE
            "Haze" -> weatherType = WeatherEnum.HAZE
            "Dust" -> weatherType = WeatherEnum.DUST
            "Sand" -> weatherType = WeatherEnum.SAND
            "Tornado" -> weatherType = WeatherEnum.TORNADO
            "Ash" -> weatherType = WeatherEnum.NA
            "Squail" -> weatherType = WeatherEnum.NA
        }
        return weatherType
    }

    fun getWeatherImageIdFromType(weatherType: WeatherEnum, dayPhase: DayEnum): Int {
        val imageId: Int

        var clearImageId: Int = 0
        var lightCloudImageId: Int = 0

        if(weatherType == WeatherEnum.CLEAR) {
            clearImageId = if(dayPhase != DayEnum.NIGHT) {
                R.drawable.clear
            } else {
                R.drawable.clear_night
            }
        } else if (weatherType == WeatherEnum.LIGHTCOUD) {
            lightCloudImageId = if(dayPhase != DayEnum.NIGHT) {
                R.drawable.light_cloud
            } else {
                R.drawable.light_cloud_night
            }
        }

        when (weatherType) {
            WeatherEnum.THUNDERSTORM -> imageId = R.drawable.thunderstorm
            WeatherEnum.DRIZZLE -> imageId = R.drawable.drizzle
            WeatherEnum.RAIN -> imageId = R.drawable.rain
            WeatherEnum.SNOW -> imageId = R.drawable.snow
            WeatherEnum.CLEAR -> imageId = clearImageId
            WeatherEnum.HEAVYCLOUD -> imageId = R.drawable.cloudy
            WeatherEnum.LIGHTCOUD -> imageId = lightCloudImageId
            WeatherEnum.FOG -> imageId = R.drawable.fog
            WeatherEnum.SMOKE -> imageId = R.drawable.smoke
            WeatherEnum.HAZE -> imageId = R.drawable.haze
            WeatherEnum.DUST -> imageId = R.drawable.dust
            WeatherEnum.SAND -> imageId = R.drawable.sand
            WeatherEnum.TORNADO -> imageId = R.drawable.tornado
            WeatherEnum.NA -> imageId = R.drawable.alien
        }
        return imageId
    }

    private fun isHeavyCloudy(clouds: Int): Boolean {
        return clouds >= 85
    }
}