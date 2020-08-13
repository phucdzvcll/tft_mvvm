package com.tft_mvvm.app.di

import com.tft_mvvm.app.features.details.mapper.ItemMapper
import com.tft_mvvm.app.features.details.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.main.mapper.ChampByGoldMapper
import com.tft_mvvm.app.features.main.mapper.ChampByRankMapper
import com.tft_mvvm.app.features.main.mapper.TeamBuilderRecommendMapper
import com.tft_mvvm.app.features.main.viewmodel.*
import com.tft_mvvm.app.mapper.TeamBuilderMapper
import com.tft_mvvm.app.mapper.TeamMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {

    factory { TeamMapper() }
    factory { TeamBuilderMapper(champMapper = get()) }
    factory { ChampByGoldMapper() }
    factory { ChampByRankMapper() }
    factory { TeamBuilderRecommendMapper() }
    factory { ItemMapper() }
    viewModel {
        MainViewModel(
            champsUseCase = get()
        )
    }
    viewModel {
        DetailsViewModel(
            getChampByIdUseCase = get(),
            itemMapper = get(),
            getListSuitableItemsUseCase = get()
        )
    }

    viewModel {
        TeamRecommendViewModel(
            teamBuilderMapper = get(),
            getListTeamBuilderUseCase = get()
        )
    }

    viewModel {
        ShowChampByGoldViewModel(
            champByGoldMapper = get(),
            champsUseCase = get()
        )
    }

    viewModel {
        ShowChampByRankViewModel(
            champsUseCase = get(),
            champByRankMapper = get()
        )
    }

    viewModel {
        ShowTeamRecommendViewModel(
            getListTeamBuilderUseCase = get(),
            teamBuilderRecommendMapper = get()
        )
    }
}

fun getPresentationKoinModule(): Module {
    return presentationModule
}