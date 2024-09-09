package com.text.mangofzcotest.core.utils

inline fun <C, R> C.ifNullOrBlank(defaultValue: () -> R): R where C : R?, R : CharSequence =
    if (isNullOrBlank()) defaultValue() else this

inline fun <C, R> C.ifNotNullOrBlank(value: (C) -> R): R? where C : R, R : CharSequence? {
    return if (isNullOrBlank()) this else value(this)
}
