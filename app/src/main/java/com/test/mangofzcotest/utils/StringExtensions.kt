package com.test.mangofzcotest.utils

inline fun <C, R> C.ifNullOrBlank(defaultValue: () -> R): R where C : R?, R : CharSequence =
    if (isNullOrBlank()) defaultValue() else this