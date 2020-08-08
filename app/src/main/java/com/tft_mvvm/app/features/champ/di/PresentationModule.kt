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
    factory { TeamMapper() }
    factory { TeamBuilderMapper(champMapper = get()) }
    factory { ClassOrOriginMapper() }
    factory { ItemMapper() }
    viewModel {
        MainViewModel(
            champsUseCase = get(),
            champListMapper = get()
        )
    }
    viewModel {
        DetailsViewModel(
            listChampsByOriginUseCase = get(),
            listChampsByClassUseCase = get(),
            classAndOriginUseCase = get(),
            classOrOriginMapper = get(),
            itemMapper = get(),
            itemListSuitableItemsUseCase = get(),
            champListMapper = get()
        )
    }

    viewModel {
        TeamRecommendViewModel(
            teamBuilderMapper = get(),
            getListTeamBuilderUseCase = get()
        )
    }
}

fun getPresentationKoinModule(): Module {
    return presentationModule

}