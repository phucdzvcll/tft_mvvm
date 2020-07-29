package com.tft_mvvm.domain.features.champs.di

import com.tft_mvvm.domain.features.champs.usecase.GetChampsByClassUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetChampsByOriginUseCase
import com.tft_mvvm.domain.features.champs.usecase.GetChampsUseCase

import org.koin.dsl.module

val domainModule = module {
    single { GetChampsUseCase(
        repoRepository = get()
        ) }
    single { GetChampsByOriginUseCase(
        repoRepository = get()
    ) }
    single { GetChampsByClassUseCase(
        repoRepository = get()
    ) }
}