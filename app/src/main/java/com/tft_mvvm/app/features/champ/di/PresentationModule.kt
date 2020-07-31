package com.tft_mvvm.app.features.champ.di

import com.tft_mvvm.app.features.champ.viewmodel.DetailsViewModel
import com.tft_mvvm.app.features.champ.viewmodel.MainViewModel
import com.tft_mvvm.app.mapper.ChampMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper() }

    viewModel {
        MainViewModel(
            champsUseCase = get(),
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
}
fun getPresentationKoinModule(): Module {
    return presentationModule

}