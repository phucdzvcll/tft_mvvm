package com.tft_mvvm.data.di

import com.tft_mvvm.data.features.champs.di.dataModule
import org.koin.core.module.Module

fun getDataKoinModule(): Module{
    return dataModule("https://spreadsheets.google.com")

}
