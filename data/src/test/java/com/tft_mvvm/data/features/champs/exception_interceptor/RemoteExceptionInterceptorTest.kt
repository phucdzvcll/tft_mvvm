package com.tft_mvvm.data.features.champs.exception_interceptor

import com.example.common_jvm.exception.Failure
import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.features.champs.exception_interceptor.fake.CommonFake
import io.mockk.every
import io.mockk.mockk
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RemoteExceptionInterceptorTest {

    private val interceptor = RemoteExceptionInterceptor()
    private val exception: Exception = mockk()
    private val unknownHostException: UnknownHostException = mockk()
    private val socketTimeoutException: SocketTimeoutException = mockk()
    private val httpException: HttpException = mockk()

    @Test
    fun handleException() {

        Assert.assertEquals(
            null,
            interceptor.handleException(exception)
        )

        Assert.assertEquals(
            Failure.InternetError,
            interceptor.handleException(unknownHostException)
        )

        Assert.assertEquals(
            Failure.ConnectionTimeout,
            interceptor.handleException(socketTimeoutException)
        )
    }

}