package com.tft_mvvm.app.features.matcher

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.*

class ScreenshotTestWatcher : TestWatcher() {
    private val screenshotTaker =
        ScreenshotTaker("error")

    override fun failed(
        e: Throwable,
        description: Description
    ) {
        screenshotTaker.takeScreenshot(getFileName(description = description))
    }

    private fun getFileName(description: Description): String {
        val className = description.className
        val methodName = description.methodName
        val dateTime = Calendar.getInstance().time.toString()
        return "$className-$methodName-$dateTime.png"
    }
}