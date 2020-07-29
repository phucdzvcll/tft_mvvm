package com.example.common_jvm.exception

interface ExceptionInterceptor {
    fun handleException(exception: Exception): Failure?
}