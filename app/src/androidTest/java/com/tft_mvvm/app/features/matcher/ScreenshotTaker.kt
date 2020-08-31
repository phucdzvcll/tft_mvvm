package com.tft_mvvm.app.features.matcher

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import androidx.test.runner.screenshot.Screenshot
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Suppress("DEPRECATION")
class ScreenshotTaker(private val fileSaveName:String) {
    private var currentActivity: Activity? = null
        get() {
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
            getTargetContext().cacheDir.absolutePath + "/$fileSaveName/"
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