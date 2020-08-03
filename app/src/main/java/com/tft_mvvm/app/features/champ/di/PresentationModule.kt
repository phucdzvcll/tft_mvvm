package com.tft_mvvm.app.features.champ.di

import com.tft_mvvm.app.features.champ.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.features.champ.viewmodel.TeamRecommendViewModel
import com.tft_mvvm.app.mapper.ChampMapper
import com.tft_mvvm.app.mapper.LoadChampByRankMapper
import com.tft_mvvm.app.mapper.TeamBuilderMapper
import com.tft_mvvm.app.mapper.TeamMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper() }
    factory { LoadChampByRankMapper(champListMapper = get()) }
    factory { TeamMapper() }
    factory { TeamBuilderMapper(champMapper = get()) }
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
            champListMapper = get()
        )
    }

    viewModel {
        TeamRecommendViewModel(
            teamBuilderMapper = get(),
            getTeamUseCase = get()
        )
    }
}

fun getPresentationKoinModule(): Module {
    return presentationModule

}