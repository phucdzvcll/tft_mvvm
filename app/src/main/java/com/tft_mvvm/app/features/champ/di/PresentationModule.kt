package com.tft_mvvm.app.features.champ.di

import com.tft_mvvm.app.features.champ.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.features.champ.viewmodel.TeamRecommendViewModel
import com.tft_mvvm.app.mapper.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper() }
    factory { LoadChampByRankMapper(champListMapper = get()) }
    factory { TeamMapper() }
    factory { TeamBuilderMapper(champMapper = get()) }
    factory { ClassOrOriginMapper() }
    viewModel {
        MainViewModel(
            champsUseCase = get(),
            loadChampByRankMapper = get(),
            champListMapper = get()
        )
    }
    viewModel {
        DetailsViewModel(
            champsByOriginUseCase = get(),
            champsByClassUseCase = get(),
            classContent = get(),
            originContent = get(),
            classOrOriginMapper = get(),
            champListMapper = get()
        )
    }

    viewModel {
        TeamRecommendViewModel(
            teamBuilderMapper = get(),
            getTeamBuilderUseCase = get()
        )
    }
}

fun getPresentationKoinModule(): Module {
    return presentationModule

}