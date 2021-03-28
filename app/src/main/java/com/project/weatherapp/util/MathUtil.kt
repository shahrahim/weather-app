package com.project.weatherapp.util

class MathUtil {

    fun getDouble(value: Any): Double {
        val double = if(value is Int) {
            value.toDouble()
        } else {
            value as Double
        }
        println(double)
        return double
    }
}