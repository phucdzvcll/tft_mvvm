package com.tft_mvvm.app.features.main

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import androidx.test.runner.screenshot.Screenshot
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ScreenshotTestWatcher : TestWatcher() {
    private val screenshotTaker = ScreenshotTaker()

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