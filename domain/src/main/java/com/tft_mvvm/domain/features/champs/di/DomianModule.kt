package com.tft_mvvm.domain.features.champs.di

import com.tft_mvvm.domain.features.champs.usecase.GetChampsUseCase

import org.koin.dsl.module

val domainModule = module {
    single {
        GetChampsUseCase(
           repoRepository = get()
        )
    }

}