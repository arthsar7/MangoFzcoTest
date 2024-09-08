package com.test.mangofzcotest.data.network.interceptors

import com.test.mangofzcotest.utils.log
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject

class LoggingInterceptorLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val jsonStr = when {
            message.startsWith("{") -> {
                JSONObject(message).toString(4)
            }
            message.startsWith("[") -> {
                JSONArray(message).toString(4)
            }
            else -> {
                ""
            }
        }

        if (jsonStr.isEmpty()) {
            log("OkHttp", message)
        } else {
            StringBuilder()
                .appendLine("=================== JSON BODY START ===================")
                .appendLine(jsonStr)
                .appendLine("===================  JSON BODY END  ===================")
                .toString().also {
                    log("OkHttp", it)
                }
        }
    }
}