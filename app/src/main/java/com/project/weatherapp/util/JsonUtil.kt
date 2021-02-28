package com.project.weatherapp.util

import org.json.JSONArray
import org.json.JSONObject

class JsonUtil {

    fun getNewJsonObject(str: String): JSONObject {
        return JSONObject(str)
    }

    fun getJsonObjectByKey(jsonObject: JSONObject, key: String): JSONObject {
        return jsonObject.getJSONObject(key)
    }

    fun getJsonArrayByKey(jsonObject: JSONObject, key: String): JSONArray {
        return jsonObject.getJSONArray(key)
    }

    fun getValueByKey(jsonObject: JSONObject, key: String): Any {
        return jsonObject.get(key)
    }

}