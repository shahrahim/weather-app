package com.project.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        "https://api.openweathermap.org/data/2.5/weather?zip=08844&exclude=minutely,hourly,daily,alerts&appid=ddc6ceaed94a3585de774b295163af4e".httpPost()
//            .responseJson {_, res, result ->
//                if (res.responseMessage == "OK" && res.statusCode == 200) {
//                    println(result.get().obj())
//                }
//            }
//        println("Hello")

//        Fuel.get("https://httpbin.org/get").response { request, response, result ->
//            println(request)
//            println(response)
//            val (bytes, error) = result
//            if (bytes != null) {
//                println("[response bytes] ${String(bytes)}")
//            }
//        }

        Fuel.post("https://api.openweathermap.org/data/2.5/weather?zip=08844&exclude=minutely,hourly,daily,alerts&appid=ddc6ceaed94a3585de774b295163af4e")
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    var map: Map<String, Any> = HashMap()
                    map = Gson().fromJson(String(bytes), map.javaClass)
                    println(map.get("coord"))
                }
            }


//        Fuel.post("https://api.openweathermap.org/data/2.5/weather?zip=08844&exclude=minutely,hourly,daily,alerts&appid=ddc6ceaed94a3585de774b295163af4e").responseObject<Post>(Post.Deserializer()){
//                    _,_, result ->
//                val postsArray = result.component1()
//            }
    }
}