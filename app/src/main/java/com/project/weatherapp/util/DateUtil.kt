package com.project.weatherapp.util

import com.project.weatherapp.entity.DayEnum
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

    fun getTimeFromEpochTime(epochTime: Long, timezone: String): String {
        val date = Date(epochTime * 1000L)
        val dateFormat = SimpleDateFormat("HH")
        dateFormat.timeZone = TimeZone.getTimeZone(timezone)
        return getHour(dateFormat.format(date))
    }

    fun getDayPhase(currentEpochTime: Long, sunriseEpochTime: Long, sunsetEpochTime: Long): DayEnum {
        val currTime: Long = (currentEpochTime * 1000)
        val sunriseTime: Long = (sunriseEpochTime * 1000)
        val sunsetTime: Long = (sunsetEpochTime * 1000)
        val dayLength: Long = this.getDayLength(sunriseTime, sunsetTime)

        val morningLimit = (.45 * dayLength).toLong() + sunriseTime
        val noonLimit = (.65 * dayLength).toLong() + sunriseTime
        val afterNoonLimit = (.85 * dayLength).toLong() + sunriseTime
        val eveningLimit = (.99 * dayLength).toLong() + sunriseTime

        val isMorning = (currTime > sunriseTime + 1) && (currTime <= morningLimit)
        val isNoon = (currTime > morningLimit + 1) && (currTime <= noonLimit)
        val isAfterNoon = (currTime > noonLimit + 1) && (currTime <= afterNoonLimit)
        val isEvening = (currTime > afterNoonLimit + 1) && (currTime <= eveningLimit)

        return if (isMorning) {
            DayEnum.MORNING
        } else if (isNoon) {
            DayEnum.NOON
        } else if (isAfterNoon) {
            DayEnum.AFTERNOON
        } else if (isEvening) {
            DayEnum.EVENING
        } else {
            DayEnum.NIGHT
        }
    }

    private fun getDayLength(sunrise: Long, sunset: Long): Long {
        return (sunset - sunrise)
    }

    private fun getHour(time: String): String {
        var phase: String
        var hour: Int = time.toInt()
        if(hour > 12) {
            phase = "PM"
            hour -= 12
        } else {
            phase = "AM"
            if(hour == 0) {
                hour +=12
            }
        }
        return "$hour $phase"
    }
}
