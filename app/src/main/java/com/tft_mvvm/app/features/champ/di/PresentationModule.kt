package com.tft_mvvm.app.features.champ.di

import com.tft_mvvm.app.features.champ.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.features.champ.viewmodel.TeamRecommendViewModel
import com.tft_mvvm.app.mapper.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper(itemMapper = get()) }
    factory { TeamMapper() }
    factory { TeamBuilderMapper(champMapper = get()) }
    factory { ClassOrOriginMapper() }
    factory { ItemMapper() }
    factory { ChampByGoldMapper() }
    factory { DetailsChampMapper(itemMapper = get()) }
    viewModel {
        MainViewModel(
            champsUseCase = get(),
            champByGoldMapper = get()
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
            updateChampUseCase = get(),
            champByIdUseCase = get(),
            detailsChampMapper = get(),
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