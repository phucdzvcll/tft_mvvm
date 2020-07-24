package com.tft_mvvm.domain.di

import com.tft_mvvm.domain.features.champs.di.domainModule
import org.koin.core.module.Module

fun getDomainKoinModule(): Module {
    return domainModule
}