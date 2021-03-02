package com.project.weatherapp.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    fun getWeekDayFromEpochTime(epochTime: Long, timezone: String): String {
        val date = Date(epochTime * 1000)
        val c: Calendar = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone(timezone)
        val sdf = SimpleDateFormat("EEEE")
        return sdf.format(date)
    }

    fun isDaytime(currentEpochTime: Long, sunriseEpochTime: Long, sunsetEpochTime: Long): Boolean {
        val currTime: Long = (currentEpochTime * 1000)
        val sunriseTime: Long = (sunriseEpochTime * 1000)
        val sunsetTime: Long = (sunsetEpochTime * 1000)
        return currTime in (sunriseTime + 1) until sunsetTime
    }

}