package com.example.common_jvm.nullable

fun <T> List<T>?.defaultEmpty(): List<T> {
    return this ?: listOf()
}