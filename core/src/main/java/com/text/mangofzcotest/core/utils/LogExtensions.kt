package com.text.mangofzcotest.core.utils

import android.util.Log

inline fun <reified T : Any> T.log(message: String?) {
    log(this::class.java.simpleName, message.toString())
}

fun log(tag: String, message: String?) {
    if (!isReleaseVersion)  {
        Log.d(tag, message.toString())
    }
}

fun log(tag: String, throwable: Throwable?) {
    if (!isReleaseVersion)  {
        Log.e(tag, throwable?.stackTraceToString().toString())
    }
}

inline fun <reified T : Any> T.log(throwable: Throwable?) {
    log(this::class.java.simpleName, throwable)
}