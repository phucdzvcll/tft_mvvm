package com.tft_mvvm.app.di


import com.tft_mvvm.app.features.champ.di.getPresentationKoinModule
import com.tft_mvvm.data.di.getDataKoinModule
import com.tft_mvvm.domain.di.getDomainKoinModule
import org.koin.core.module.Module

fun getAppKoinModule():List<Module>{
        val modules = mutableListOf<Module>()
        modules.add(getDomainKoinModule())
        modules.add(getPresentationKoinModule())
        modules.add(getDataKoinModule())
        return modules
}