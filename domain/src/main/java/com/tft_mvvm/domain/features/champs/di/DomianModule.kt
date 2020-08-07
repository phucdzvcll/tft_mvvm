package com.tft_mvvm.domain.features.champs.di

import com.tft_mvvm.domain.features.champs.usecase.*

import org.koin.dsl.module

val domainModule = module {
    single {
        GetChampsUseCase(
            repoRepository = get()
        )
    }
    single {
        GetChampsByOriginUseCase(
            repoRepository = get()
        )
    }
    single {
        GetChampsByClassUseCase(
            repoRepository = get()
        )
    }
    single {
        GetTeamBuilderUseCase(
            repoRepoRepository = get()
        )
    }
    single {
        GetClassAndOriginContentUseCase(
            repoRepository = get()
        )
    }

    single {
        GetListSuitableItemUseCase(
            repoRepository = get()
        )
    }
}