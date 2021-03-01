package com.project.weatherapp.util

class MathUtil {
    private val KELVIN_CONSTANT: Double = 273.15

    fun getCelciusFromCelvin(kelvin: Double): Int {
        val tempInCelsius = (kelvin - KELVIN_CONSTANT)
        return tempInCelsius.toInt()
    }
}