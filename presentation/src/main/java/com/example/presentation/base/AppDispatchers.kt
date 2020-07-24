package com.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher

class AppDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)