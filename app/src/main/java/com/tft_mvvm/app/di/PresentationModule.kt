package com.tft_mvvm.app.di

import com.tft_mvvm.app.features.details.mapper.ItemMapper
import com.tft_mvvm.app.features.details.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.main.mapper.ChampMapper
import com.tft_mvvm.app.features.main.mapper.TeamBuilderRecommendMapper
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByGoldViewModel
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByRankViewModel
import com.tft_mvvm.app.features.main.viewmodel.ShowTeamRecommendViewModel
import com.tft_mvvm.app.features.dialog_show_details_champ.mapper.ChampDialogModelMapper
import com.tft_mvvm.app.features.dialog_show_details_champ.viewmodel.DialogShowDetailsChampViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper() }
    factory { TeamBuilderRecommendMapper(champMapper = get()) }
    factory { ItemMapper() }
    factory { ChampDialogModelMapper() }
    viewModel {
        DetailsViewModel(
            getChampByIdUseCase = get(),
            itemMapper = get(),
            champMapper = get(),
            getClassAndOriginContentUseCase = get(),
            getListChampsByClassUseCase = get(),
            getListChampsByOriginUseCase = get(),
            getListSuitableItemsUseCase = get()
        )
    }

    viewModel {
        ShowChampByGoldViewModel(
            champsUseCase = get(),
            champMapper = get()
        )
    }

    viewModel {
        ShowChampByRankViewModel(
            champsUseCase = get(),
            champMapper = get()
        )
    }

    viewModel {
        ShowTeamRecommendViewModel(
            getListTeamBuilderUseCase = get(),
            teamBuilderRecommendMapper = get()
        )
    }

    viewModel {
        DialogShowDetailsChampViewModel(
            champDialogModelMapper = get(),
            getChampByIdUseCase = get()
        )
    }
}

fun getPresentationKoinModule(): Module {
    return presentationModule
}