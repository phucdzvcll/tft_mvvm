package com.tft_mvvm.app.di

import com.tft_mvvm.app.features.details.mapper.*
import com.tft_mvvm.app.features.details.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.dialog_show_details_champ.mapper.ChampDialogModelMapper
import com.tft_mvvm.app.features.dialog_show_details_champ.mapper.ItemSuitableMapper
import com.tft_mvvm.app.features.dialog_show_details_champ.viewmodel.DialogShowDetailsChampViewModel
import com.tft_mvvm.app.features.main.mapper.ChampOfTeamMapper
import com.tft_mvvm.app.features.main.mapper.ItemOfTeamMapper
import com.tft_mvvm.app.features.main.mapper.TeamBuilderRecommendMapper
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByGoldViewModel
import com.tft_mvvm.app.features.main.viewmodel.ShowChampByRankViewModel
import com.tft_mvvm.app.features.main.viewmodel.ShowTeamRecommendViewModel
import com.tft_mvvm.app.mapper.ChampMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper() }
    factory {
        TeamBuilderRecommendMapper(
            champOfTeamMapper = get()
        )
    }
    factory { ItemMapper() }
    factory { ChampDialogModelMapper(itemSuitableMapper = get()) }
    factory { ItemSuitableMapper() }
    factory { ItemHeaderMapper(itemMapper = get()) }
    factory {
        ChampOfTeamMapper(
            itemOfTeamMapper = get()
        )
    }
    factory { ItemOfTeamMapper() }
    factory {
        ChampOfChampDetailsMapper(
            itemMapper = get()
        )
    }
    factory {
        TeamRecommendForChampMapper(
            champOfChampDetailsMapper = get()
        )
    }
    factory {
        ClassAndOriginContentMapper(
            champOfChampDetailsMapper = get()
        )
    }
    viewModel {
        DetailsViewModel(
            getChampByIdUseCase = get(),
            getClassAndOriginContentUseCase = get(),
            getTeamRecommendForChampUseCase = get(),
            teamRecommendForChampMapper = get(),
            classAndOriginContentMapper = get(),
            appDispatchers = get(),
            itemHeaderMapper = get()
        )
    }

    viewModel {
        ShowChampByGoldViewModel(
            listChampsUseCase = get(),
            appDispatchers = get(),
            champMapper = get()
        )
    }

    viewModel {
        ShowChampByRankViewModel(
            listChampsUseCase = get(),
            appDispatchers = get(),
            champMapper = get()
        )
    }

    viewModel {
        ShowTeamRecommendViewModel(
            getListTeamBuilderUseCase = get(),
            appDispatchers = get(),
            teamBuilderRecommendMapper = get()
        )
    }

    viewModel {
        DialogShowDetailsChampViewModel(
            champDialogModelMapper = get(),
            appDispatchers = get(),
            getChampByIdUseCase = get()
        )
    }
}

fun getPresentationKoinModule(): Module {
    return presentationModule
}