package com.project.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.requests.CancellableRequest
import com.project.weatherapp.util.HttpUtil
import com.project.weatherapp.util.JsonUtil
import org.json.JSONObject
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherLocationUrl: String = "https://api.openweathermap.org/data/2.5/weather"
        val zipcode = "08844"
        val locationSpec: String = "zip=$zipcode"
        val appId = "ddc6ceaed94a3585de774b295163af4e"
        val weatherLocationParams: String = "$locationSpec&exclude=minutely,hourly,daily,alerts&appid=$appId"

        val responseStr = HttpUtil().getResponseStrFromHttpRequest("$weatherLocationUrl?$weatherLocationParams")
        println(responseStr)

    }
}
