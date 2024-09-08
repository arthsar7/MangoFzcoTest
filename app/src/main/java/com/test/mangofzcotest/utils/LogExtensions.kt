package com.test.mangofzcotest.utils

import android.util.Log

inline fun <reified T : Any> T.log(message: String?) {
    if (!isReleaseVersion)  {
        Log.d(this::class.java.simpleName, message.toString())
    }
}

inline fun <reified T : Any> T.log(tag: String, message: String?) {
    if (!isReleaseVersion)  {
        Log.d(tag, message.toString())
    }
}

inline fun <reified T : Any> T.log(throwable: Throwable?) {
    if (!isReleaseVersion)  {
        Log.e(this::class.java.simpleName, throwable?.stackTraceToString().toString())
    }
}