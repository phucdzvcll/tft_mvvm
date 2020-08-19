package com.tft_mvvm.domain.features.di

import com.tft_mvvm.domain.features.usecase.*

import org.koin.dsl.module

val domainModule = module {
    single {
        GetListChampsUseCase(
            repoRepository = get()
        )
    }
    single {
        GetListTeamBuilderUseCase(
            repoRepoRepository = get()
        )
    }
    single {
        GetClassAndOriginContentUseCase(
            repoRepository = get()
        )
    }
    single {
        GetChampByIdUseCase(
            repository = get()
        )
    }

    single {
        GetTeamRecommendForChampUseCase(
            repoRepository = get()
        )
    }
}