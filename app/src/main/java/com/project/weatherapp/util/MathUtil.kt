package com.project.weatherapp.util

class MathUtil {

    fun getDouble(value: Any): Double {
        return if (value is Int) {
            value.toDouble()
        } else {
            value as Double
        }
    }
}