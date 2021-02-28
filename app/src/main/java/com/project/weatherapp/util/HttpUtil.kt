package com.project.weatherapp.util;

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Body
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpPost
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.net.URL

class HttpUtil {
    fun getResponseStrFromHttpRequest(url: String): String {
        return Fuel.post(url)
            .header("Content-Type", "application/json")
            .response { result -> }.get()
            .body().asString("application/json")
    }
}
