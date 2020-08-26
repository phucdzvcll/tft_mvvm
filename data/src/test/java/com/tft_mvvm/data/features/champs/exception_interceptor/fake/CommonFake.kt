package com.tft_mvvm.data.features.champs.exception_interceptor.fake

import com.example.common_jvm.exception.Failure

object CommonFake {

    fun provideApiErrorNoMessage() = Failure.ApiError(400, "")

    fun provideApiError() = Failure.ApiError(400, "There is a message")
}