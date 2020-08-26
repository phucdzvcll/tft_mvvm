package com.tft_mvvm.data.features.champs.remote

import com.example.common_jvm.extension.nullable.default
import com.example.common_jvm.extension.nullable.defaultEmpty
import com.example.common_jvm.extension.nullable.defaultFalse
import com.tft_mvvm.data.features.champs.di.dataModule
import com.tft_mvvm.data.features.champs.service.ApiService
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
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

    fun mockHttpResponse(responseCode: Int, fileName: String = "", path: String? = null) {
        val body = try {
            getJson(fileName)
        } catch (e: Exception) {
            ""
        }
        val response = MockResponse()
            .setResponseCode(responseCode)
            .setBody(body)
        val dispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val prefix = if (path?.startsWith("/").default(true)) {
                    ""
                } else {
                    "/"
                }

                if (request.path == "$prefix$path" || path == null) {
                    return response
                }
                return MockResponse().setResponseCode(404)
            }
        }
        mockServer.dispatcher = dispatcher
    }

    fun getJson(path: String): String {
        val uri = javaClass.classLoader?.getResource(path)
        val file = File(uri?.path.defaultEmpty())
        return String(file.readBytes())
    }

}