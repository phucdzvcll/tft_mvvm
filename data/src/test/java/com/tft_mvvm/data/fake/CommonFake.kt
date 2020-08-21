package com.tft_mvvm.data.fake

import com.example.common_jvm.exception.Failure


object CommonFake {
    fun provideFailure(exception: Exception) = Failure.InternetError
}