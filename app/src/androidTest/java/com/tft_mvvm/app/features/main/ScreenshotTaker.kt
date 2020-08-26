package com.tft_mvvm.app.features.main

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import androidx.test.runner.screenshot.Screenshot
import org.junit.runner.Description
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ScreenshotTaker {
    private var currentActivity: Activity? = null
        private get() {
            InstrumentationRegistry.getInstrumentation()
                .runOnMainSync {
                    val resumedActivities: Collection<*> =
                        ActivityLifecycleMonitorRegistry.getInstance()
                            .getActivitiesInStage(Stage.RESUMED)
                    if (resumedActivities.iterator().hasNext()) {
                        field = resumedActivities.iterator().next() as Activity?
                    }
                }
            return field
        }

    fun takeScreenshot(
        fileName: String
    ) {
        val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            InstrumentationRegistry.getInstrumentation()
                .uiAutomation.takeScreenshot()
        } else {
            // only in-app view-elements are visible.
            Screenshot.capture(currentActivity!!).bitmap
        }

        val folder = File(
            androidx.test.InstrumentationRegistry.getTargetContext().cacheDir.absolutePath + "/test_screenshots/"
        )
        if (!folder.exists()) {
            folder.mkdirs()
        }
        storeBitmap(bitmap, folder.path + "/" + fileName)
    }

    private fun storeBitmap(bitmap: Bitmap, path: String) {
        var out: BufferedOutputStream? = null
        try {
            out = BufferedOutputStream(FileOutputStream(path))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}