package com.example.presentation.features.champs.model.di

import com.example.presentation.base.AppDispatchers
import com.example.presentation.mapper.ChampMapper
import com.example.presentation.features.champs.model.viewmodel.ChampViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    factory { ChampMapper() }

    viewModel {
        ChampViewModel(
            champsUseCase = get(),
            champListMapper = get()
        )
    }
}