package com.example.presentation.features.champs.model.di

import com.example.presentation.features.champs.model.viewmodel.DetailsViewModel
import com.example.presentation.mapper.ChampMapper
import com.example.presentation.features.champs.model.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper() }

    viewModel {
        DetailsViewModel(
            champListMapper = get(),
            champsByClassUseCase = get(),
            champsByOriginUseCase = get()
        )
    }

    viewModel {
        MainViewModel(
            champsUseCase = get(),
            champListMapper = get()
        )
    }
}