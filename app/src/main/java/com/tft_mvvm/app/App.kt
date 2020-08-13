package com.tft_mvvm.app

import android.app.Application
import com.tft_mvvm.app.di.getAppKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        initDi()
    }
    private fun initDi() {
        startKoin {
            androidContext(this@App)
            modules(
                getAppKoinModule()
            )
        }
    }
}