package com.loyer.socket_example.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Util {
    companion object {
        inline fun <reified T> convertFromJson(json: String, jsonParser: Gson): T {
            return jsonParser.fromJson(json, object : TypeToken<T>() {}.type)
        }
    }
}