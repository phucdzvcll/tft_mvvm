package com.tft_mvvm.domain.features.champs.repository

import com.tft_mvvm.domain.features.champs.model.ChampListEntity

interface ChampRepository {
    suspend fun getChamps(
        name : String,
        linkimg:String,
        coat : String
    ): ChampListEntity
}