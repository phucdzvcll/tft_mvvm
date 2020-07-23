package com.tft_mvvm.domain.features.champs.repository

import com.tft_mvvm.domain.features.champs.model.ChampListEntity

interface ChampRepository {
    suspend fun getChamps(
        tags: List<String>,
        type: String,
        limitAmount: Double
    ): ChampListEntity
}