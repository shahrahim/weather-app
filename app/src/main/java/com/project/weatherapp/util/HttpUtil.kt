package com.project.weatherapp.util;

import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject

class HttpUtil {

    val jsonUtil: JsonUtil = JsonUtil()

    fun getJsonResponseFromHttpRequest(url: String): JSONObject {
        val jsonResponseStr = Fuel.post(url)
            .header("Content-Type", "application/json")
            .response { result -> }.get()
            .body().asString("application/json")
        return jsonUtil.getNewJsonObject(jsonResponseStr)
    }
}
