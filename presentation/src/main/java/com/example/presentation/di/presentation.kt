package com.example.presentation.di

import com.example.presentation.features.champs.model.di.presentationModule
import org.koin.core.module.Module

fun getPresentationKoinModule(): Module {
    return presentationModule

}