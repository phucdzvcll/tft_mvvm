package com.tft_mvvm.data.features.champs.remote

import com.tft_mvvm.data.features.champs.di.dataModule
import com.tft_mvvm.data.features.champs.service.ApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest
import java.io.File

abstract class BaseTest : KoinTest {
    protected lateinit var mockServer: MockWebServer
    protected val apiServer: ApiService by inject()

    @Before
    open fun setUp() {
        configMockServer()
        startKoin { modules(dataModule(mockServer.url("/").toString())) }
    }

    @After
    open fun tearDown() {
        mockServer.shutdown()
        stopKoin()
    }

    private fun configMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    fun mockHttpResponse(mockWebServer: MockWebServer, fileName: String, responseCode: Int) =
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    fun getJson(path: String): String {
        val uri = javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

    fun mockHttpResponseUrl(mockWebServer: MockWebServer) =
        mockWebServer.url("/").toString()
}