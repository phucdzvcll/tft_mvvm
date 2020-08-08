package com.tft_mvvm.domain.features.di

import com.tft_mvvm.domain.features.usecase.*

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