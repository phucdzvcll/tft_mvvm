package com.tft_mvvm.domain.features.champs.repository
import com.tft_mvvm.domain.features.champs.model.ChampListEntity

interface RepoRepository {
    suspend fun getChamps(
    ): ChampListEntity

    suspend fun getChampsByOrigin(
        origin:String
    ): ChampListEntity

    suspend fun getChampsByClass(
        classs:String
    ): ChampListEntity
}