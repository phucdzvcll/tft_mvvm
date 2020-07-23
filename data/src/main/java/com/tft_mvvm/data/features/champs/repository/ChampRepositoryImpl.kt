package com.tft_mvvm.data.features.champs.repository

import com.tft_mvvm.data.features.champs.mapper.ChampListMapper
import com.tft_mvvm.data.features.champs.service.ChampApiService
import com.tft_mvvm.domain.features.champs.model.ChampListEntity
import com.tft_mvvm.domain.features.champs.repository.ChampRepository

class ChampRepositoryImpl(private val champApiService: ChampApiService, private val champListMapper: ChampListMapper) : ChampRepository {
    override suspend fun getChamps(
        tags: List<String>,
        type: String,
        limitAmount: Double
    ): ChampListEntity {
        val champListResponse = champApiService.getChampList()
        return champListMapper.map(champListResponse)
    }
}