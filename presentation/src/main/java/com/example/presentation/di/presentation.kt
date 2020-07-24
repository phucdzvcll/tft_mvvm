package com.example.presentation.di

import com.example.presentation.base.AppDispatchers
import com.example.presentation.features.champs.model.di.presentationModule
import com.example.presentation.features.champs.model.viewmodel.ChampViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun getPresentationKoinModule(): Module {
    return presentationModule

}