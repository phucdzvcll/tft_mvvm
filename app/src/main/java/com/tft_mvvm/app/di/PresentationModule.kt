package com.tft_mvvm.app.di

import com.tft_mvvm.app.features.details.mapper.ItemHeaderMapper
import com.tft_mvvm.app.features.details.mapper.ItemMapper
import com.tft_mvvm.app.features.details.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.main.mapper.ChampMapper
import com.tft_mvvm.app.features.main.mapper.TeamBuilderRecommendMapper
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByGoldViewModel
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByRankViewModel
import com.tft_mvvm.app.features.main.viewmodel.ShowTeamRecommendViewModel
import com.tft_mvvm.app.features.dialog_show_details_champ.mapper.ChampDialogModelMapper
import com.tft_mvvm.app.features.dialog_show_details_champ.viewmodel.DialogShowDetailsChampViewModel
import com.tft_mvvm.app.features.main.mapper.ChampOfTeamMapper
import com.tft_mvvm.app.features.main.mapper.ItemOfTeamMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper() }
    factory { TeamBuilderRecommendMapper(champOfTeamMapper = get()) }
    factory { ItemMapper() }
    factory { ChampDialogModelMapper() }
    factory { ItemHeaderMapper(itemMapper = get()) }
    factory { ChampOfTeamMapper(itemOfTeamMapper = get()) }
    factory { ItemOfTeamMapper() }
    viewModel {
        DetailsViewModel(
            getChampByIdUseCase = get(),
            champMapper = get(),
            getClassAndOriginContentUseCase = get(),
            getListChampsByClassUseCase = get(),
            getListChampsByOriginUseCase = get(),
            itemHeaderMapper = get()
        )
    }

    viewModel {
        ShowChampByGoldViewModel(
            listChampsUseCase = get(),
            champMapper = get()
        )
    }

    viewModel {
        ShowChampByRankViewModel(
            listChampsUseCase = get(),
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